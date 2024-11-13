package org.scuni.userservice.mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.scuni.userservice.dto.UserCreateDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCreateMapper implements Mapper<UserCreateDto, UserRepresentation> {
    @Override
    public UserRepresentation map(UserCreateDto object) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(object.getEmail());
        userRepresentation.setUsername(object.getUsername());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(object.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }
}
