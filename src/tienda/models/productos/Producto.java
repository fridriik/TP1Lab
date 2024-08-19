package tienda.models.productos;

public abstract class Producto {
    protected String identificador;
    protected String descripcion;
    protected int cantidadStock;
    protected double precioUnidad;
    protected double porcentajeGanancia;
    protected boolean disponibleVenta;

    public Producto(String identificador, String descripcion, int cantidadStock,
                    double precioUnidad, double porcentajeGanancia) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioUnidad = precioUnidad;
        this.porcentajeGanancia = porcentajeGanancia;
        this.disponibleVenta = true;
    }

    public abstract double calcularPrecioVenta();
    public abstract double aplicarDescuento(double porcentajeDescuento);

    public void setDisponibleVenta(boolean disponibleVenta) {
        this.disponibleVenta = disponibleVenta;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public boolean isDisponibleVenta() {
        return disponibleVenta;
    }
}