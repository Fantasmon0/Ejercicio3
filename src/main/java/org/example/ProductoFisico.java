package org.example;

public class ProductoFisico extends Producto {
    private double pesoKg;

    public ProductoFisico(String codigo, String nombre, double precio, double pesoKg) {
        super(codigo, nombre, precio);
        this.pesoKg = pesoKg;
    }

    public ProductoFisico(String codigo, String nombre, double precio) {
        this(codigo, nombre, precio, 1.0);
    }

    public double getPesoKg() {
        return pesoKg;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() + 2.50 + (0.80 * pesoKg);
    }

    @Override
    public ProductoFisico crearVariante(String nuevoCodigo, double nuevoPrecio) {
        return new ProductoFisico(nuevoCodigo, getNombre(), nuevoPrecio, this.pesoKg);
    }

    @Override
    public String toString() {
        return String.format("[Físico] %s %s, %.1f kg, Precio final: $%.2f",
                getCodigo(), getNombre(), pesoKg, calcularPrecioFinal());
    }
}