package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.utils.ValidadorProductos;
import java.time.LocalDate;
import java.util.Objects;

public class Bebida extends Producto implements Comestible {
    private double graduacionAlcoholica;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private double calorias;

    public Bebida(String identificador,
                  String descripcion,
                  int cantidadStock,
                  double precioUnidad,
                  double porcentajeGanancia,
                  double graduacionAlcoholica,
                  boolean importado,
                  LocalDate fechaVencimiento) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        ValidadorProductos.validarProducto(this);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calcularCalorias();
    }

    public boolean isImportado() {
        return importado;
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

    private double calcularCalorias() {
        if (graduacionAlcoholica <= 2) {
            return graduacionAlcoholica;
        } else if (graduacionAlcoholica <= 4.5) {
            return (graduacionAlcoholica * 1.25);
        } else {
            return (graduacionAlcoholica * 1.5);
        }
    }

    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        if (getClass() != o.getClass()) return false;
        Bebida bebida = (Bebida) o;
        return Double.compare(bebida.graduacionAlcoholica, graduacionAlcoholica) == 0 &&
                importado == bebida.importado &&
                Objects.equals(fechaVencimiento, bebida.fechaVencimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), graduacionAlcoholica, importado, fechaVencimiento);
    }

}
