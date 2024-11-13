package org.scuni.userservice.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.scuni.userservice.dto.UserCreateDto;
import org.scuni.userservice.exception.UserAlreadyExistException;
import org.scuni.userservice.mapper.UserCreateMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakUserService {
    private final static String UPDATE_ACTION = "UPDATE_PASSWORD";

    @Value("${keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;
    private final UserCreateMapper userCreateMapper;

    public void createUser(UserCreateDto userCreateDto) {
        UserRepresentation userRepresentation = userCreateMapper.map(userCreateDto);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        if (response.getStatus() != HttpStatus.SC_CREATED) {
            throw new UserAlreadyExistException("User already exist");
        }
        log.info("Responce code: {}", response.getStatus());
    }

    public String getKeycloakId(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        return userRepresentations.get(0).getId();
    }

    public UserRepresentation getUserRepresentationById(String keycloakId) {
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(keycloakId);
        return userResource.toRepresentation();
    }

    public void deleteUserById(String keycloakId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(keycloakId);
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    public void resetPassword(String keycloakId) {
        UsersResource usersResource = getUsersResource();
        List<String> actions = new ArrayList<>();
        actions.add(UPDATE_ACTION);
        usersResource.get(keycloakId).executeActionsEmail(actions);
    }

    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }
}
