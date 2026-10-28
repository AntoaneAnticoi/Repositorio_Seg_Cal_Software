package com.ecommerce.backend.services;

import com.ecommerce.backend.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchQueryBuilder {

    // Lista de productos en memoria
    public static List<Product> productos = new ArrayList<>();

    public SearchQueryBuilder() {
        // Productos iniciales de ejemplo
        if (productos.isEmpty()) {
            productos.add(new Product("1", "Notebook Gamer", "Laptop para juegos de alta gama con 16GB RAM", 1200.0, 10, "Computación"));
            productos.add(new Product("2", "Mouse Inalámbrico", "Mouse ergonómico con conexión Bluetooth", 25.0, 50, "Accesorios"));
            productos.add(new Product("3", "Teclado Mecánico", "Teclado RGB con switches mecánicos", 80.0, 20, "Accesorios"));
            productos.add(new Product("4", "Monitor 24 pulgadas", "Monitor Full HD 144Hz para oficina o gaming", 200.0, 15, "Monitores"));
            productos.add(new Product("5", "Auriculares Bluetooth", "Auriculares con cancelación de ruido", 60.0, 30, "Audio"));
        }
    }

    public List<Product> buscar(String texto, Double minPrecio, Double maxPrecio) {
        List<Product> resultados = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            Product p = productos.get(i);
            boolean coincideTexto = true;
            boolean coincidePrecio = true;

            // Filtro por texto en nombre o descripción
            if (texto != null && !texto.trim().isEmpty()) {
                String busqueda = texto.toLowerCase().trim();
                boolean enNombre = p.getNombre() != null && p.getNombre().toLowerCase().contains(busqueda);
                boolean enDesc = p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(busqueda);
                if (!enNombre && !enDesc) {
                    coincideTexto = false;
                }
            }

            // Filtro por rango de precios
            if (minPrecio != null && p.getPrecio() < minPrecio) {
                coincidePrecio = false;
            }
            if (maxPrecio != null && p.getPrecio() > maxPrecio) {
                coincidePrecio = false;
            }

            if (coincideTexto && coincidePrecio) {
                resultados.add(p);
            }
        }

        return resultados;
    }

    public List<Product> obtenerTodos() {
        return productos;
    }

    public Product obtenerPorId(String id) {
        for (Product p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void agregarProducto(Product p) {
        if (p != null) {
            productos.add(p);
        }
    }
}
