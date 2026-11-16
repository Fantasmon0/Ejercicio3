package org.example;

public class ProductoDigital extends Producto {
    private double tamanoMB;

    public ProductoDigital(String codigo, String nombre, double precio, double tamanoMB) {
        super(codigo, nombre, precio);
        this.tamanoMB = tamanoMB;
    }

    public double getTamanoMB() {
        return tamanoMB;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio();
    }

     @Override
    public ProductoDigital crearVariante(String nuevoCodigo, double nuevoPrecio) {
        return new ProductoDigital(nuevoCodigo, getNombre(), nuevoPrecio, this.tamanoMB);
    }

    @Override
    public String toString() {
        String tamanoStr = (tamanoMB % 1 == 0)
                ? String.format("%.0f", tamanoMB)
                : String.format("%.2f", tamanoMB);
        return String.format("[Digital] %s %s, %s MB, Precio final: $%.2f",
                getCodigo(), getNombre(), tamanoStr, calcularPrecioFinal());
    }
}