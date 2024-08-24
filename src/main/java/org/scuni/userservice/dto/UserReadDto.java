package org.scuni.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReadDto {
    private Integer id;
    private String username;
    private String email;
    private String imageFilename;
}
