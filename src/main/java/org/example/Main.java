package org.example;

import java.util.Scanner;

public class Main {
    private static final Tienda tienda = new Tienda();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n--- MENÚ SHOPESFOT ---");
            System.out.println("1. Registrar producto físico");
            System.out.println("2. Registrar producto digital");
            System.out.println("3. Crear variante de producto");
            System.out.println("4. Mostrar catálogo");
            System.out.println("5. Agregar al carrito");
            System.out.println("6. Pagar");
            System.out.println("7. Salir");
            System.out.print("Ingrese opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Opción no válida.");
                continue;
            }

            if (opcion == 1) {
                registrarProductoFisico();
            } else if (opcion == 2) {
                registrarProductoDigital();
            } else if (opcion == 3) {
                crearVarianteProducto();
            } else if (opcion == 4) {
                mostrarCatalogo();
            } else if (opcion == 5) {
                agregarAlCarrito();
            } else if (opcion == 6) {
                procesarPago();
            } else if (opcion == 7) {
                System.out.println("Saliendo del sistema ShopESFOT...");
            } else {
                System.out.println("Error: Opción no válida.");
            }
        } while (opcion != 7);
    }

    private static void registrarProductoFisico() {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        if (codigo.isEmpty()) {
            System.out.println("Error: El código no puede estar vacío.");
            return;
        }

        if (tienda.obtenerProducto(codigo) != null) {
            System.out.println("Error: El código " + codigo + " ya está registrado.");
            return;
        }

        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine().trim());
            if (precio < 0) {
                System.out.println("Error: El precio no puede ser negativo.");
                return;
            }

            System.out.print("¿Desea indicar el peso? (s/n): ");
            String resp = sc.nextLine().trim().toLowerCase();

            if (resp.equals("s")) {
                System.out.print("Peso (kg): ");
                double peso = Double.parseDouble(sc.nextLine().trim());
                if (peso < 0) {
                    System.out.println("Error: El peso no puede ser negativo.");
                    return;
                }
                tienda.registrarProducto(new ProductoFisico(codigo, nombre, precio, peso));
            } else if (resp.equals("n")) {
                tienda.registrarProducto(new ProductoFisico(codigo, nombre, precio));
            } else {
                System.out.println("Error: Opción no válida. Debe ingresar 's' o 'n'.");
                return;
            }

            System.out.println("Producto físico registrado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un valor numérico válido.");
        }
    }

    private static void registrarProductoDigital() {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        if (codigo.isEmpty()) {
            System.out.println("Error: El código no puede estar vacío.");
            return;
        }

        if (tienda.obtenerProducto(codigo) != null) {
            System.out.println("Error: El código " + codigo + " ya está registrado.");
            return;
        }

        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine().trim());
            if (precio < 0) {
                System.out.println("Error: El precio no puede ser negativo.");
                return;
            }

            System.out.print("Tamaño (MB): ");
            double tamano = Double.parseDouble(sc.nextLine().trim());
            if (tamano < 0) {
                System.out.println("Error: El tamaño no puede ser negativo.");
                return;
            }

            tienda.registrarProducto(new ProductoDigital(codigo, nombre, precio, tamano));
            System.out.println("Producto digital registrado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un valor numérico válido.");
        }
    }

    private static void crearVarianteProducto() {
        if (tienda.catalogoVacio()) {
            System.out.println("Error: El catálogo está vacío.");
            return;
        }

        System.out.print("Código del producto original: ");
        String codigoOrig = sc.nextLine().trim().toUpperCase();
        Producto original = tienda.obtenerProducto(codigoOrig);

        if (original == null) {
            System.out.println("Error: El código " + codigoOrig + " no existe.");
            return;
        }

        System.out.print("Nuevo código: ");
        String nuevoCodigo = sc.nextLine().trim().toUpperCase();
        if (nuevoCodigo.isEmpty()) {
            System.out.println("Error: El nuevo código no puede estar vacío.");
            return;
        }
        if (tienda.obtenerProducto(nuevoCodigo) != null) {
            System.out.println("Error: El código " + nuevoCodigo + " ya está registrado.");
            return;
        }

        try {
            System.out.print("Nuevo precio: ");
            double nuevoPrecio = Double.parseDouble(sc.nextLine().trim());
            if (nuevoPrecio < 0) {
                System.out.println("Error: El precio no puede ser negativo.");
                return;
            }

            Producto variante = original.crearVariante(nuevoCodigo, nuevoPrecio);
            tienda.registrarProducto(variante);
            System.out.println("Variante " + nuevoCodigo + " creada a partir de " + codigoOrig + ".");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un precio válido.");
        }
    }

    private static void mostrarCatalogo() {
        if (tienda.catalogoVacio()) {
            System.out.println("Error: El catálogo está vacío.");
            return;
        }

        for (Producto p : tienda.getCatalogo()) {
            System.out.println(p);
        }
    }

    private static void agregarAlCarrito() {
        if (tienda.catalogoVacio()) {
            System.out.println("Error: El catálogo está vacío.");
            return;
        }

        System.out.print("Código: ");
        String codigo = sc.nextLine().trim().toUpperCase();
        if (tienda.obtenerProducto(codigo) == null) {
            System.out.println("Error: El código " + codigo + " no existe.");
            return;
        }

        System.out.print("¿Cuántas unidades? (Enter para 1): ");
        String cantStr = sc.nextLine().trim();

        if (cantStr.isEmpty()) {
            tienda.agregarAlCarrito(codigo);
        } else {
            try {
                int cantidad = Integer.parseInt(cantStr);
                tienda.agregarAlCarrito(codigo, cantidad);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    private static void procesarPago() {
        if (tienda.carritoVacio()) {
            System.out.println("Error: El carrito está vacío.");
            return;
        }

        double totalActual = tienda.calcularTotalCarrito();
        System.out.printf("Total: $%.2f%n", totalActual);
        System.out.print("¿Tiene cupón? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();

        if (resp.equals("n")) {
            double pagado = tienda.pagar();
            System.out.printf("Total a pagar: $%.2f%n", pagado);
            System.out.println("Gracias por su compra. El carrito quedó vacío.");
        } else if (resp.equals("s")) {
            System.out.print("Cupón: ");
            String cupon = sc.nextLine().trim();
            try {
                double pagado = tienda.pagar(cupon);
                System.out.println("Descuento aplicado: 10 %");
                System.out.printf("Total a pagar: $%.2f%n", pagado);
                System.out.println("Gracias por su compra. El carrito quedó vacío.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Opción no válida. Debe ingresar 's' o 'n'.");
        }
    }
}