package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.utils.ValidadorProductos;

import java.time.LocalDate;

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
        ValidadorProductos.validarIdentificador(this);
        this.tipoEnvase = tipoEnvase;
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = precioUnidad * (1 + Math.min(porcentajeGanancia, 20) / 100);
        if (importado) {
            precio *= 1.12;
        }
        return precio;
    }

    @Override
    public double aplicarDescuento(double porcentajeDescuento) {
        return calcularPrecioVenta() * (1 - Math.min(porcentajeDescuento, 15) / 100);
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
    public int getCalorias() {
        return calorias;
    }
}
