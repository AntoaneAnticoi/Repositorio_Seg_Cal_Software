package com.ecommerce.backend.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String id;
    private String userId;
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotal() {
        double total = 0.0;
        if (items != null) {
            for (CartItem item : items) {
                total += item.getSubtotal();
            }
        }
        return total;
    }
}
