package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Tienda {
    private HashMap<String, Producto> catalogo;
    private ArrayList<Producto> carrito;
    private HashSet<String> cuponesValidos;
    private HashSet<String> cuponesUsados;

    public Tienda() {
        this.catalogo = new HashMap<>();
        this.carrito = new ArrayList<>();
        this.cuponesValidos = new HashSet<>();
        this.cuponesUsados = new HashSet<>();

        this.cuponesValidos.add("ESFOT10");
        this.cuponesValidos.add("POO10");
    }

    public boolean registrarProducto(Producto p) {
        if (catalogo.containsKey(p.getCodigo())) {
            return false;
        }
        catalogo.put(p.getCodigo(), p);
        return true;
    }

    public Producto obtenerProducto(String codigo) {
        return catalogo.get(codigo);
    }

    public boolean catalogoVacio() {
        return catalogo.isEmpty();
    }

    public Collection<Producto> getCatalogo() {
        return catalogo.values();
    }

    public boolean carritoVacio() {
        return carrito.isEmpty();
    }

    public void agregarAlCarrito(String codigo) {
        agregarAlCarrito(codigo, 1);
    }


    public void agregarAlCarrito(String codigo, int cantidad) {
        Producto p = catalogo.get(codigo);
        if (p == null) {
            System.out.println("Error: El código " + codigo + " no existe.");
            return;
        }
        if (cantidad <= 0) {
            System.out.println("Error: La cantidad debe ser mayor a 0.");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            carrito.add(p);
        }

        if (cantidad == 1) {
            System.out.println("Se agregó 1 unidad de " + p.getNombre() + ".");
        } else {
            System.out.println("Se agregaron " + cantidad + " unidades de " + p.getNombre() + ".");
        }
    }

    public double calcularTotalCarrito() {
        double total = 0.0;
        for (Producto p : carrito) {
            total += p.calcularPrecioFinal();
        }
        return total;
    }
    public double pagar() {
        double total = calcularTotalCarrito();
        carrito.clear();
        return total;
    }

    public double pagar(String cupon) {
        String codigoCupon = cupon.trim().toUpperCase();
        if (!cuponesValidos.contains(codigoCupon)) {
            System.out.println("El cupón " + cupon + " no es válido.");
        }
        if (cuponesUsados.contains(codigoCupon)) {
            System.out.println("El cupón " + cupon + " ya fue utilizado.");
        }

        cuponesUsados.add(codigoCupon);
        return pagar() * 0.90;
    }
}