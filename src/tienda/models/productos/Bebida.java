package tienda.models.productos;

import tienda.models.interfaces.Comestible;

import java.time.LocalDate;

/**
 * Clase que representa una bebida en la tienda.
 * Extiende la clase Producto e implementa la interfaz Comestible.
 */
public class Bebida extends Producto implements Comestible {
    private static int contadorBebida = 1;
    private double graduacionAlcoholica;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private double calorias;

    /**
     * Constructor para crear una nueva bebida.
     * Tiene un identificador AC*** -> * = autoincremental
     *
     * @param descripcion Descripción del producto.
     * @param cantidadStock Cantidad inicial en stock.
     * @param precioUnidad Precio por unidad.
     * @param porcentajeGanancia Porcentaje de ganancia sobre el precio.
     * @param graduacionAlcoholica Graduación alcohólica de la bebida.
     * @param importado Indica si la bebida es importada.
     * @param fechaVencimiento Fecha de vencimiento de la bebida.
     * @throws IllegalArgumentException Si el porcentaje de ganancia o descuento no cumple con las restricciones.
     */
    public Bebida(String descripcion,
                  int cantidadStock,
                  double precioUnidad,
                  double porcentajeGanancia,
                  double graduacionAlcoholica,
                  boolean importado,
                  LocalDate fechaVencimiento) {
        super(descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calcularCalorias();
        this.identificador = "AC" + String.format("%03d", contadorBebida++);
        if (porcentajeGanancia > 20) {
            throw new IllegalArgumentException("El porcentaje de ganancia para productos comestibles no puede superar el 20%.");
        }
    }

    @Override
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
        if (porcentajeDescuento > 10) {
            throw new IllegalArgumentException("El porcentaje de descuento no puede superar el 10% para bebidas.");
        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbSuper = new StringBuilder(super.toString());
        sb.append("| ");
        sb.append("Producto bebible: ");
        sb.append(sbSuper);
        sb.append(", Graduación alcohólica: ").append(graduacionAlcoholica).append("%");
        sb.append(", Importado: ").append(importado ? "Sí" : "No");
        sb.append(", Calorías: ").append(calorias);
        sb.append(", Vence: ").append(fechaVencimiento);
        sb.append(" |");
        return sb.toString();
    }
}