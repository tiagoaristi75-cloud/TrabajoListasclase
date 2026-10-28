import java.util.ArrayList;

public class GestorVentas {

    private final ArrayList<Venta> ventas;

    public GestorVentas() {
        ventas = new ArrayList<>();
    }

    // ---------- 1. Registrar ----------
    public boolean existeFactura(int numeroFactura) {
        return buscarVenta(numeroFactura) != null;
    }

    public String registrarVenta(int numeroFactura, String cliente, String vendedor,
                                 double valorTotal, int cantidadProductos) {
        if (!Venta.datosValidos(numeroFactura, cliente, vendedor, valorTotal, cantidadProductos)) {
            return "Error: datos invalidos. Revise que ningun campo este vacio y que los numeros sean mayores a 0.";
        }
        if (existeFactura(numeroFactura)) {
            return "Error: ya existe una venta con la factura #" + numeroFactura + ".";
        }
        ventas.add(new Venta(numeroFactura, cliente, vendedor, valorTotal, cantidadProductos));
        return "Venta #" + numeroFactura + " registrada correctamente.";
    }

    // ---------- 2. Mostrar ----------
    public String mostrarVentas() {
        if (ventas.isEmpty()) {
            return "No hay ventas registradas.";
        }
        StringBuilder sb = new StringBuilder("===== LISTADO DE VENTAS =====\n");
        for (Venta v : ventas) {
            sb.append(v).append("\n");
        }
        sb.append("Activas: ").append(cantidadVentasActivas())
          .append(" | Canceladas: ").append(cantidadVentasCanceladas());
        return sb.toString();
    }

    // ---------- 3. Buscar ----------
    public Venta buscarVenta(int numeroFactura) {
        for (Venta v : ventas) {
            if (v.getNumeroFactura() == numeroFactura) {
                return v;
            }
        }
        return null;
    }

    public String consultarVenta(int numeroFactura) {
        Venta v = buscarVenta(numeroFactura);
        return (v == null) ? "No existe una venta con la factura #" + numeroFactura + "." : v.toString();
    }

    // ---------- 4. Modificar ----------
    public String modificarCliente(int numeroFactura, String nuevoCliente) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        if (v.isCancelada()) return "No se puede modificar una venta cancelada.";
        return v.setCliente(nuevoCliente) ? "Cliente actualizado." : "Error: el nombre no puede estar vacio.";
    }

    public String modificarVendedor(int numeroFactura, String nuevoVendedor) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        if (v.isCancelada()) return "No se puede modificar una venta cancelada.";
        return v.setVendedor(nuevoVendedor) ? "Vendedor actualizado." : "Error: el nombre no puede estar vacio.";
    }

    public String modificarValor(int numeroFactura, double nuevoValor) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        if (v.isCancelada()) return "No se puede modificar una venta cancelada.";
        return v.setValorTotal(nuevoValor) ? "Valor actualizado." : "Error: el valor debe ser mayor a 0.";
    }

    public String modificarCantidad(int numeroFactura, int nuevaCantidad) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        if (v.isCancelada()) return "No se puede modificar una venta cancelada.";
        return v.setCantidadProductos(nuevaCantidad) ? "Cantidad actualizada." : "Error: la cantidad debe ser mayor a 0.";
    }

    // ---------- 5. Cancelar ----------
    public String cancelarVenta(int numeroFactura) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        return v.cancelar() ? "Venta #" + numeroFactura + " cancelada." : "La venta ya estaba cancelada.";
    }

    // ---------- 6. Eliminar ----------
    public String eliminarVenta(int numeroFactura) {
        Venta v = buscarVenta(numeroFactura);
        if (v == null) return "No existe la factura #" + numeroFactura + ".";
        ventas.remove(v);
        return "Venta #" + numeroFactura + " eliminada definitivamente.";
    }

    // ---------- 7. Informe ----------
    public double totalRecaudado() {
        double total = 0;
        for (Venta v : ventas) {
            if (!v.isCancelada()) {
                total += v.getValorTotal();
            }
        }
        return total;
    }

    public Venta ventaMayorValor() {
        Venta mayor = null;
        for (Venta v : ventas) {
            if (!v.isCancelada() && (mayor == null || v.getValorTotal() > mayor.getValorTotal())) {
                mayor = v;
            }
        }
        return mayor;
    }

    public Venta ventaMenorValor() {
        Venta menor = null;
        for (Venta v : ventas) {
            if (!v.isCancelada() && (menor == null || v.getValorTotal() < menor.getValorTotal())) {
                menor = v;
            }
        }
        return menor;
    }

    public int cantidadVentasActivas() {
        int contador = 0;
        for (Venta v : ventas) {
            if (!v.isCancelada()) {
                contador++;
            }
        }
        return contador;
    }

    public int cantidadVentasCanceladas() {
        return ventas.size() - cantidadVentasActivas();
    }

    public double promedioVentasActivas() {
        int activas = cantidadVentasActivas();
        return (activas == 0) ? 0 : totalRecaudado() / activas;
    }

    public String generarInforme() {
        if (ventas.isEmpty()) {
            return "No hay ventas para generar el informe.";
        }
        Venta mayor = ventaMayorValor();
        Venta menor = ventaMenorValor();
        return "===== INFORME DE VENTAS =====\n"
                + String.format("Total recaudado (no canceladas): $%,.2f%n", totalRecaudado())
                + "Venta de mayor valor: " + (mayor == null ? "N/A" : mayor) + "\n"
                + "Venta de menor valor: " + (menor == null ? "N/A" : menor) + "\n"
                + String.format("Promedio de ventas activas: $%,.2f%n", promedioVentasActivas())
                + "Cantidad de ventas canceladas: " + cantidadVentasCanceladas();
    }

    public ArrayList<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }
}
