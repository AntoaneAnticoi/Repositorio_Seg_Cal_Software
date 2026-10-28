package com.ecommerce.backend.controllers;

import com.ecommerce.backend.services.AvatarUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    private final AvatarUploadService avatarUploadService;

    public AvatarController(AvatarUploadService avatarUploadService) {
        this.avatarUploadService = avatarUploadService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> subirAvatar(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file) {
        try {
            String mensaje = avatarUploadService.subirAvatar(userId, file);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
