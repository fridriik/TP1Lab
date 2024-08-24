package tienda.models.productos;

import java.util.Objects;

/**
 * Clase abstracta que representa un producto genérico en la tienda.
 * Contiene atributos y métodos comunes a todos los tipos de productos.
 */
public abstract class Producto {
    protected String identificador;
    protected String descripcion;
    protected int cantidadStock;
    protected double precioUnidad;
    protected double porcentajeGanancia;
    protected boolean disponibleVenta;
    protected double descuentoAplicado;

    /**
     * Constructor para crear un nuevo producto.
     *
     * @param descripcion       Descripción del producto.
     * @param cantidadStock     Cantidad inicial en stock.
     * @param precioUnidad      Precio por unidad.
     * @param porcentajeGanancia Porcentaje de ganancia sobre el precio.
     * @throws IllegalArgumentException Si el porcentaje de ganancia es negativo.
     */
    public Producto(String descripcion, int cantidadStock,
                    double precioUnidad, double porcentajeGanancia) {
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioUnidad = precioUnidad;
        this.porcentajeGanancia = porcentajeGanancia;
        this.disponibleVenta = true;
        this.descuentoAplicado = 0;
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }
        if (cantidadStock < 0) {
            throw new IllegalArgumentException("La cantidad de stock no puede ser negativa.");
        }
        if (precioUnidad < 0) {
            throw new IllegalArgumentException("El precio por unidad no puede ser negativo.");
        }
        if (porcentajeGanancia < 0) {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser negativo.");
        }
    }

    /**
     * Calcula el precio de venta del producto.
     * Este método debe ser implementado por las subclases.
     *
     * @return El precio de venta calculado.
     */
    public abstract double calcularPrecioVenta();

    /**
     * Aplica un descuento al producto.
     * Este método debe ser implementado por las subclases.
     *
     * @param porcentajeDescuento El porcentaje de descuento a aplicar.
     */
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
        return Objects.equals(descripcion, producto.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(descripcion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identificador);
        sb.append(" ").append(descripcion);
        sb.append(", Stock:").append(cantidadStock);
        sb.append(", Precio unidad: $").append(precioUnidad);
        return sb.toString();
    }
}