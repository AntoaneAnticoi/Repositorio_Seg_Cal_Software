package com.ecommerce.backend.models;

public class CartItem {
    private String productId;
    private String nombre;
    private int cantidad;
    private double precioUnitario;

    public CartItem() {
    }

    public CartItem(String productId, String nombre, int cantidad, double precioUnitario) {
        this.productId = productId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return this.cantidad * this.precioUnitario;
    }
}
