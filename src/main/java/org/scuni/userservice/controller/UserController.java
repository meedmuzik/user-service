package org.scuni.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.scuni.userservice.dto.UserCreateDto;
import org.scuni.userservice.dto.UserReadDto;
import org.scuni.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody UserCreateDto userCreateDto) {
        Long userId = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("userId", userId,
                        "imageUrl", "/api/v1/images/user/" + userId,
                        "message", "user was created"
                )
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserReadDto> getUserById(@PathVariable("id") Long id) {
        UserReadDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("user/{id}/update_password")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id) {
        userService.updatePassword(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("user/{id}/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable("id") Long id) {
        userService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
