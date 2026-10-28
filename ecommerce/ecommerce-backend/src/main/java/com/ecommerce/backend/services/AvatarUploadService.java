package com.ecommerce.backend.services;

import com.ecommerce.backend.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AvatarUploadService {

    public String subirAvatar(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Debes seleccionar un archivo para subir");
        }

        // Validación simple de tamaño (máximo 5MB = 5.000.000 bytes)
        if (file.getSize() > 5000000) {
            throw new IllegalArgumentException("El archivo supera el tamaño máximo permitido de 5MB");
        }

        // Validación simple por extensión de archivo
        String nombreOriginal = file.getOriginalFilename();
        if (nombreOriginal == null) {
            throw new IllegalArgumentException("Nombre de archivo inválido");
        }

        String nombreMinusculas = nombreOriginal.toLowerCase();
        if (!nombreMinusculas.endsWith(".jpg") && !nombreMinusculas.endsWith(".jpeg") && !nombreMinusculas.endsWith(".png")) {
            throw new IllegalArgumentException("Formato no permitido. Solo se aceptan imágenes .jpg o .png");
        }

        // Asignar avatar al usuario si existe en memoria
        for (User u : AuthService.usuarios.values()) {
            if (u.getId().equals(userId) || u.getEmail().equalsIgnoreCase(userId)) {
                u.setAvatarUrl("/uploads/avatars/" + userId + "_" + nombreOriginal);
                break;
            }
        }

        return "Avatar subido con éxito: " + nombreOriginal;
    }
}
