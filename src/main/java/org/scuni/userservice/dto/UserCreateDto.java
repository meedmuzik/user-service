package org.scuni.userservice.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String email;
    private String username;
    private String password;
}
