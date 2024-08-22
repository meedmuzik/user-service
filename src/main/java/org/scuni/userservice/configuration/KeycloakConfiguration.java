package org.scuni.userservice.configuration;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfiguration {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .clientId(keycloakProperties.getAdmin().getClientId())
                .clientSecret(keycloakProperties.getAdmin().getClientSecret())
                .grantType(keycloakProperties.getGrantType())
                .realm(keycloakProperties.getRealm())
                .serverUrl(keycloakProperties.getServerUrl())
                .build();
    }
}
