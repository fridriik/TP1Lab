package tienda.models.productos;

import tienda.models.interfaces.Comestible;

import java.time.LocalDate;

/**
 * Clase que representa un producto envasado en la tienda.
 * Extiende la clase Producto e implementa la interfaz Comestible.
 */
public class Envasado extends Producto implements Comestible {
    private static int contadorEnvasado = 1;
    private String tipoEnvase;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private int calorias;

    /**
     * Constructor para crear un nuevo producto envasado.
     * Tiene un identificador AB*** -> * = autoincremental
     *
     * @param descripcion Descripción del producto.
     * @param cantidadStock Cantidad inicial en stock.
     * @param precioUnidad Precio por unidad.
     * @param porcentajeGanancia Porcentaje de ganancia sobre el precio.
     * @param tipoEnvase Tipo de envase del producto.
     * @param importado Indica si el producto es importado.
     * @param fechaVencimiento Fecha de vencimiento del producto.
     * @throws IllegalArgumentException Si el porcentaje de ganancia o descuento no cumple con las restricciones.
     */
    public Envasado(String descripcion,
                    int cantidadStock,
                    double precioUnidad,
                    double porcentajeGanancia,
                    String tipoEnvase,
                    boolean importado,
                    LocalDate fechaVencimiento) {
        super(descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        this.tipoEnvase = tipoEnvase;
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
        this.identificador = "AB" + String.format("%03d", contadorEnvasado++);
        if (getPorcentajeGanancia() > 20) {
            throw new IllegalArgumentException("El porcentaje de ganancia para productos comestibles no puede superar el 20%.");
        }
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
        if (porcentajeDescuento > 15) {
            throw new IllegalArgumentException("El porcentaje de descuento no puede superar el 15% para productos envasados.");
        }
        this.descuentoAplicado = porcentajeDescuento;
    }

    @Override
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbSuper = new StringBuilder(super.toString());
        sb.append("| ");
        sb.append("Producto envasado: ");
        sb.append(sbSuper);
        sb.append(", Tipo de envase: ").append(tipoEnvase);
        sb.append(", Vence: ").append(fechaVencimiento);
        sb.append(" |");
        return sb.toString();
    }
}