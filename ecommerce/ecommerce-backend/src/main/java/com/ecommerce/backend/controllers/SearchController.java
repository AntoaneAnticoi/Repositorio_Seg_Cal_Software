package com.ecommerce.backend.controllers;

import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.services.SearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchQueryBuilder searchQueryBuilder;

    public SearchController(SearchQueryBuilder searchQueryBuilder) {
        this.searchQueryBuilder = searchQueryBuilder;
    }

    @GetMapping
    public List<Product> buscar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Double minPrecio,
            @RequestParam(required = false) Double maxPrecio) {
        return searchQueryBuilder.buscar(texto, minPrecio, maxPrecio);
    }

    @GetMapping("/todos")
    public List<Product> obtenerTodos() {
        return searchQueryBuilder.obtenerTodos();
    }
}
