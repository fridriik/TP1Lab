package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.utils.ValidadorProductos;

import java.time.LocalDate;
import java.util.Objects;

public class Envasado extends Producto implements Comestible {
    private String tipoEnvase;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private int calorias;

    public Envasado(String identificador,
                    String descripcion,
                    int cantidadStock,
                    double precioUnidad,
                    double porcentajeGanancia,
                    String tipoEnvase,
                    boolean importado,
                    LocalDate fechaVencimiento) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        ValidadorProductos.validarProducto(this);
        this.tipoEnvase = tipoEnvase;
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = precioUnidad * (1 + porcentajeGanancia / 100);
        if (importado) {
            precio *= 1.12;
        }
        return precio * (1 - descuentoAplicado / 100);
    }

    @Override
    public void aplicarDescuento(double porcentajeDescuento) {
        this.descuentoAplicado = porcentajeDescuento;
    }

    public boolean isImportado() {
        return importado;
    }

    @Override
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public double getCalorias() {
        return calorias;
    }

    public String getTipoEnvase() {
        return tipoEnvase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        if (getClass() != o.getClass()) return false;
        Envasado envasado = (Envasado) o;
        return importado == envasado.importado &&
                Objects.equals(tipoEnvase, envasado.tipoEnvase) &&
                Objects.equals(fechaVencimiento, envasado.fechaVencimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoEnvase, importado, fechaVencimiento);
    }

}
