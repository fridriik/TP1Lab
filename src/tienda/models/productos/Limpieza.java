package tienda.models.productos;

import tienda.utils.ValidadorProductos;

import java.util.Objects;

public class Limpieza extends Producto {
    private String tipoAplicacion;

    public Limpieza(String identificador, String descripcion, int cantidadStock,
                    double precioUnidad, double porcentajeGanancia,
                    String tipoAplicacion) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        setTipoAplicacion(tipoAplicacion);
        ValidadorProductos.validarProducto(this);
    }

    public void setTipoAplicacion(String tipoAplicacion) {
        String tipoUpperCase = tipoAplicacion.toUpperCase();
        if (!esTipoAplicacionValido(tipoUpperCase)) {
            throw new IllegalArgumentException("Tipo de aplicación inválido. Debe ser COCINA, BANIO, ROPA o MULTIUSO.");
        }
        this.tipoAplicacion = tipoUpperCase;
    }

    private boolean esTipoAplicacionValido(String tipo) {
        return tipo.equals("COCINA") || tipo.equals("BANIO") || tipo.equals("ROPA") || tipo.equals("MULTIUSO");
    }

    public String getTipoAplicacion() {
        return tipoAplicacion;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = precioUnidad * (1 + porcentajeGanancia / 100);
        return precio * (1 - descuentoAplicado / 100);
    }

    @Override
    public void aplicarDescuento(double porcentajeDescuento) {
        this.descuentoAplicado = porcentajeDescuento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        if (getClass() != o.getClass()) return false;
        Limpieza limpieza = (Limpieza) o;
        return Objects.equals(tipoAplicacion, limpieza.tipoAplicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoAplicacion);
    }

}