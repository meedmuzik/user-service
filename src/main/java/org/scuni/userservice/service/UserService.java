package org.scuni.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scuni.userservice.dto.UserCreateDto;
import org.scuni.userservice.dto.UserReadDto;
import org.scuni.userservice.entity.User;
import org.scuni.userservice.exception.UserNotFoundException;
import org.scuni.userservice.mapper.UserReadDtoMapper;
import org.scuni.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;

    private final UserReadDtoMapper userReadDtoMapper;

    public Long createUser(UserCreateDto userCreateDto) {
        keycloakUserService.createUser(userCreateDto);
        String keycloakId = keycloakUserService.getKeycloakId(userCreateDto.getUsername());
        User user = User.builder()
                .keycloakId(keycloakId)
                .build();
        userRepository.save(user);
        return user.getId();
    }

    public void updateImageFilenameByImageFilename(String filename, Long id) {
        userRepository.updateImageFilenameById(filename, id);
    }

    public UserReadDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userReadDtoMapper::map)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.deleteUserById(user.getKeycloakId());
        userRepository.delete(user);
    }


    public void updatePassword(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.resetPassword(user.getKeycloakId());
    }

    public void sendVerificationEmail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.sendVerificationEmail(user.getKeycloakId());
    }
}
