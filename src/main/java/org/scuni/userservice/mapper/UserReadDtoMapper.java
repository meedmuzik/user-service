package org.scuni.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.scuni.userservice.dto.UserReadDto;
import org.scuni.userservice.entity.User;
import org.scuni.userservice.service.KeycloakUserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserReadDtoMapper implements Mapper<User, UserReadDto> {
    private final KeycloakUserService keycloakUserService;

    @Override
    public UserReadDto map(User object) {
        UserRepresentation userRepresentation = keycloakUserService.getUserRepresentationById(object.getKeycloakId());
        return UserReadDto.builder()
                .id(object.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .imageFilename(object.getImageFilename())
                .build();
    }
}
