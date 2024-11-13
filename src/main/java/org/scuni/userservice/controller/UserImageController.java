package org.scuni.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.scuni.userservice.entity.Image;
import org.scuni.userservice.service.ImageService;
import org.scuni.userservice.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/images/")
@RequiredArgsConstructor
public class UserImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping("/user/{id}")
        public ResponseEntity<Object> uploadImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image) {
        String imageFilename = imageService.upload(image);
        userService.updateImageFilenameByImageFilename(imageFilename, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "image was created"));
    }

    @GetMapping("/user/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable("imageId") String imageId) {
        Image download = imageService.download(imageId);
        String extension = download.getExtension();
        if (Objects.equals(extension, "jpg")) extension = "jpeg";
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/" + extension))
                .body(new ByteArrayResource(download.getBytes()));
    }
}
