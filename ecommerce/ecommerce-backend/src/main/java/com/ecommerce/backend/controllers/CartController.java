package com.ecommerce.backend.controllers;

import com.ecommerce.backend.models.Cart;
import com.ecommerce.backend.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public Cart obtenerCarrito(@PathVariable String userId) {
        return cartService.obtenerCarrito(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody Map<String, Object> body) {
        try {
            String userId = (String) body.get("userId");
            String productId = (String) body.get("productId");
            int cantidad = Integer.parseInt(body.get("cantidad").toString());
            Cart carrito = cartService.agregarAlCarrito(userId, productId, cantidad);
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/item/{productId}")
    public Cart eliminarItem(@PathVariable String userId, @PathVariable String productId) {
        return cartService.eliminarItem(userId, productId);
    }

    @DeleteMapping("/{userId}/vaciar")
    public String vaciarCarrito(@PathVariable String userId) {
        cartService.vaciarCarrito(userId);
        return "Carrito vaciado con éxito";
    }
}
