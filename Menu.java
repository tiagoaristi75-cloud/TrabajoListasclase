import java.util.Scanner;

public class Menu {

    private final Scanner sc;
    private final GestorVentas gestor;

    public Menu() {
        sc = new Scanner(System.in);
        gestor = new GestorVentas();
    }

    // ---------- Lectura de datos ----------
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numerico.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private boolean confirmar(String mensaje) {
        String r = leerTexto(mensaje + " (S/N): ").trim();
        return r.equalsIgnoreCase("S") || r.equalsIgnoreCase("SI");
    }
    private String textoMenu() {
        return "\n===== GESTION DE VENTAS =====\n"
                + "1. Registrar una venta\n"
                + "2. Mostrar las ventas\n"
                + "3. Buscar una venta\n"
                + "4. Modificar una venta\n"
                + "5. Cancelar una venta\n"
                + "6. Eliminar una venta\n"
                + "7. Generar informe\n"
                + "0. Salir";
    }

    private String opcionRegistrar() {
        int factura = leerEntero("Numero de factura: ");
        if (gestor.existeFactura(factura)) {
            return "Error: ya existe una venta con la factura #" + factura + ".";
        }
        String cliente = leerTexto("Nombre del cliente: ");
        String vendedor = leerTexto("Nombre del vendedor: ");
        double valor = leerDouble("Valor total de la venta: ");
        int cantidad = leerEntero("Cantidad de productos vendidos: ");
        return gestor.registrarVenta(factura, cliente, vendedor, valor, cantidad);
    }

    private String opcionBuscar() {
        return gestor.consultarVenta(leerEntero("Numero de factura a buscar: "));
    }

    private String opcionModificar() {
        int factura = leerEntero("Numero de factura a modificar: ");
        Venta v = gestor.buscarVenta(factura);
        if (v == null) {
            return "No existe una venta con la factura #" + factura + ".";
        }
        System.out.println("Venta actual: " + v);
        System.out.println("1. Cliente\n2. Vendedor\n3. Valor\n4. Cantidad de productos");
        int campo = leerEntero("Campo a modificar: ");
        switch (campo) {
            case 1:  return gestor.modificarCliente(factura, leerTexto("Nuevo cliente: "));
            case 2:  return gestor.modificarVendedor(factura, leerTexto("Nuevo vendedor: "));
            case 3:  return gestor.modificarValor(factura, leerDouble("Nuevo valor: "));
            case 4:  return gestor.modificarCantidad(factura, leerEntero("Nueva cantidad: "));
            default: return "Opcion no valida.";
        }
    }

    private String opcionCancelar() {
        return gestor.cancelarVenta(leerEntero("Numero de factura a cancelar: "));
    }

    private String opcionEliminar() {
        int factura = leerEntero("Numero de factura a eliminar: ");
        Venta v = gestor.buscarVenta(factura);
        if (v == null) {
            return "No existe una venta con la factura #" + factura + ".";
        }
        System.out.println(v);
        if (!confirmar("Esta seguro de eliminar definitivamente esta venta?")) {
            return "Eliminacion cancelada por el usuario.";
        }
        return gestor.eliminarVenta(factura);
    }

    private String procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:  return opcionRegistrar();
            case 2:  return gestor.mostrarVentas();
            case 3:  return opcionBuscar();
            case 4:  return opcionModificar();
            case 5:  return opcionCancelar();
            case 6:  return opcionEliminar();
            case 7:  return gestor.generarInforme();
            case 0:  return "Saliendo del sistema...";
            default: return "Opcion no valida.";
        }
    }

    // ---------- Ciclo principal ----------
    public String iniciar() {
        int opcion;
        do {
            System.out.println(textoMenu());
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println(procesarOpcion(opcion));
        } while (opcion != 0);
        return "Programa finalizado.";
    }
}
