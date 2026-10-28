package com.ecommerce.backend.services;

import com.ecommerce.backend.models.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    // Simulación de base de datos en memoria
    public static Map<String, User> usuarios = new HashMap<>();

    public AuthService() {
        // Usuarios iniciales de prueba
        usuarios.put("admin@test.com", new User("1", "admin@test.com", "123456", "Administrador"));
        usuarios.put("estudiante@test.com", new User("2", "estudiante@test.com", "password123", "Estudiante"));
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("El email y la contraseña son obligatorios");
        }

        User usuario = usuarios.get(email.toLowerCase());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.isBloqueado()) {
            throw new RuntimeException("Cuenta bloqueada por demasiados intentos fallidos");
        }

        // Validación simple de contraseña
        if (usuario.getPassword().equals(password)) {
            // Reiniciar intentos fallidos al iniciar sesión correctamente
            usuario.setIntentosFallidos(0);
            return usuario;
        } else {
            // Aumentar contador de intentos fallidos
            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);

            if (intentos >= 5) {
                usuario.setBloqueado(true);
                throw new RuntimeException("Cuenta bloqueada tras 5 intentos fallidos");
            }

            throw new RuntimeException("Contraseña incorrecta. Intentos: " + intentos + "/5");
        }
    }

    public User registrar(String email, String password, String nombre) {
        if (email == null || password == null || nombre == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        if (usuarios.containsKey(email.toLowerCase())) {
            throw new RuntimeException("El usuario ya existe");
        }
        String id = String.valueOf(usuarios.size() + 1);
        User nuevo = new User(id, email.toLowerCase(), password, nombre);
        usuarios.put(email.toLowerCase(), nuevo);
        return nuevo;
    }

    public User obtenerPorEmail(String email) {
        return usuarios.get(email.toLowerCase());
    }
}
