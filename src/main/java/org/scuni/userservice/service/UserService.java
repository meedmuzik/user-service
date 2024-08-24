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

    public Integer createUser(UserCreateDto userCreateDto) {
        keycloakUserService.createUser(userCreateDto);
        String keycloakId = keycloakUserService.getKeycloakId(userCreateDto.getUsername());
        User user = User.builder()
                .keycloakId(keycloakId)
                .build();
        userRepository.saveAndFlush(user);
        return user.getId();
    }

    public void updateImageFilenameByImageFilename(String filename, Integer id) {
        userRepository.updateImageFilenameById(filename, id);
    }

    public UserReadDto getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userReadDtoMapper::map)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
    }

    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.deleteUserById(user.getKeycloakId());
        userRepository.delete(user);
    }


    public void updatePassword(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.resetPassword(user.getKeycloakId());
    }

    public void sendVerificationEmail(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to get user"));
        keycloakUserService.sendVerificationEmail(user.getKeycloakId());
    }
}
