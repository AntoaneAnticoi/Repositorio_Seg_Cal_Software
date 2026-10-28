package com.ecommerce.backend.services;

import com.ecommerce.backend.models.Cart;
import com.ecommerce.backend.models.CartItem;
import com.ecommerce.backend.models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    // Carritos en memoria: clave userId -> Cart
    public static Map<String, Cart> carritos = new HashMap<>();

    private final SearchQueryBuilder searchQueryBuilder;

    public CartService(SearchQueryBuilder searchQueryBuilder) {
        this.searchQueryBuilder = searchQueryBuilder;
    }

    public Cart obtenerCarrito(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio");
        }
        if (!carritos.containsKey(userId)) {
            carritos.put(userId, new Cart("cart-" + userId, userId));
        }
        return carritos.get(userId);
    }

    public Cart agregarAlCarrito(String userId, String productId, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Product producto = searchQueryBuilder.obtenerPorId(productId);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        if (cantidad > producto.getStock()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        Cart carrito = obtenerCarrito(userId);

        // Buscar si el producto ya existe en el carrito
        CartItem itemExistente = null;
        for (CartItem item : carrito.getItems()) {
            if (item.getProductId().equals(productId)) {
                itemExistente = item;
                break;
            }
        }

        if (itemExistente != null) {
            int nuevaCantidad = itemExistente.getCantidad() + cantidad;
            if (nuevaCantidad > producto.getStock()) {
                throw new RuntimeException("Stock insuficiente. No puedes superar las " + producto.getStock() + " unidades");
            }
            itemExistente.setCantidad(nuevaCantidad);
        } else {
            CartItem nuevoItem = new CartItem(
                    producto.getId(),
                    producto.getNombre(),
                    cantidad,
                    producto.getPrecio()
            );
            carrito.getItems().add(nuevoItem);
        }

        return carrito;
    }

    public Cart eliminarItem(String userId, String productId) {
        Cart carrito = obtenerCarrito(userId);
        carrito.getItems().removeIf(item -> item.getProductId().equals(productId));
        return carrito;
    }

    public void vaciarCarrito(String userId) {
        Cart carrito = obtenerCarrito(userId);
        carrito.getItems().clear();
    }
}
