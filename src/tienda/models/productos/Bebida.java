package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.utils.ValidadorProductos;

import java.time.LocalDate;

public class Bebida extends Producto implements Comestible {
    private double graduacionAlcoholica;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private int calorias;

    public Bebida(String identificador,
                  String descripcion,
                  int cantidadStock,
                  double precioUnidad,
                  double porcentajeGanancia,
                  double graduacionAlcoholica,
                  boolean importado,
                  LocalDate fechaVencimiento) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        ValidadorProductos.validarIdentificador(this);
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
        return precio;
    }

    @Override
    public double aplicarDescuento(double porcentajeDescuento) {
        return calcularPrecioVenta() * (1 - Math.min(porcentajeDescuento, 10) / 100);
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
    public int getCalorias() {
        return calorias;
    }

    private int calcularCalorias() {
        if (graduacionAlcoholica <= 2) {
            return (int) graduacionAlcoholica; // Calorías igual a la graduación alcohólica
        } else if (graduacionAlcoholica <= 4.5) {
            return (int) (graduacionAlcoholica * 1.25);
        } else {
            return (int) (graduacionAlcoholica * 1.5);
        }
    }
}
