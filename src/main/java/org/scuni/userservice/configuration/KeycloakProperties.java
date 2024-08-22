package org.scuni.userservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String realm;
    private String serverUrl;
    private String grantType;
    private Admin admin;

    @Data
    public static class Admin {
        private String clientId;
        private String clientSecret;
    }
}
