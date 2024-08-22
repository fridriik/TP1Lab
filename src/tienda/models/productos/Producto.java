package tienda.models.productos;

import java.util.Objects;

public abstract class Producto {
    protected String identificador;
    protected String descripcion;
    protected int cantidadStock;
    protected double precioUnidad;
    protected double porcentajeGanancia;
    protected boolean disponibleVenta;
    protected double descuentoAplicado;

    public Producto(String identificador, String descripcion, int cantidadStock,
                    double precioUnidad, double porcentajeGanancia) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioUnidad = precioUnidad;
        this.porcentajeGanancia = porcentajeGanancia;
        this.disponibleVenta = true;
        this.descuentoAplicado = 0;
    }

    public abstract double calcularPrecioVenta();
    public abstract void aplicarDescuento(double porcentajeDescuento);

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

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(identificador, producto.identificador) && Objects.equals(descripcion, producto.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, descripcion);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "\nidentificador='" + identificador + '\'' +
                ", \ndescripcion='" + descripcion + '\'' +
                ", \ncantidadStock=" + cantidadStock +
                ", \nprecioUnidad=" + precioUnidad +
                '}';
    }
}