public class Venta {

    private final int numeroFactura;
    private String cliente;
    private String vendedor;
    private double valorTotal;
    private int cantidadProductos;
    private boolean cancelada;

    public Venta(int numeroFactura, String cliente, String vendedor, double valorTotal, int cantidadProductos) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente.trim();
        this.vendedor = vendedor.trim();
        this.valorTotal = valorTotal;
        this.cantidadProductos = cantidadProductos;
        this.cancelada = false;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public String getCliente() {
        return cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public String getEstado() {
        return cancelada ? "CANCELADA" : "REGISTRADA";
    }
    public boolean setCliente(String cliente) {
        if (!textoValido(cliente)) {
            return false;
        }
        this.cliente = cliente.trim();
        return true;
    }

    public boolean setVendedor(String vendedor) {
        if (!textoValido(vendedor)) {
            return false;
        }
        this.vendedor = vendedor.trim();
        return true;
    }

    public boolean setValorTotal(double valorTotal) {
        if (valorTotal <= 0) {
            return false;
        }
        this.valorTotal = valorTotal;
        return true;
    }

    public boolean setCantidadProductos(int cantidadProductos) {
        if (cantidadProductos <= 0) {
            return false;
        }
        this.cantidadProductos = cantidadProductos;
        return true;
    }


    public boolean cancelar() {
        if (cancelada) {
            return false;
        }
        cancelada = true;
        return true;
    }

    public static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean datosValidos(int numeroFactura, String cliente, String vendedor,
                                       double valorTotal, int cantidadProductos) {
        return numeroFactura > 0
                && textoValido(cliente)
                && textoValido(vendedor)
                && valorTotal > 0
                && cantidadProductos > 0;
    }

    @Override
    public String toString() {
        return String.format("Factura #%d | Cliente: %s | Vendedor: %s | Valor: $%,.2f | Productos: %d | Estado: %s",
                numeroFactura, cliente, vendedor, valorTotal, cantidadProductos, getEstado());
    }
}