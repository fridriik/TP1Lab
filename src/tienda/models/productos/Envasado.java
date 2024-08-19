package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.models.interfaces.Importado;

import java.time.LocalDate;

public class Envasado extends Producto implements Comestible, Importado {
    private String tipoEnvase;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private int calorias;

    public Envasado(String identificador, String descripcion, int cantidadStock,
                            double precioUnidad, double porcentajeGanancia,
                            String tipoEnvase, boolean importado) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        if (!identificador.matches("AB\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para producto envasado");
        }
        this.tipoEnvase = tipoEnvase;
        this.importado = importado;
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

    @Override
    public LocalDate getFechaVencimiento() {
        return null;
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {

    }

    @Override
    public int getCalorias() {
        return 0;
    }

    @Override
    public void setCalorias(int calorias) {

    }

    @Override
    public boolean esImportado() {
        return false;
    }

    @Override
    public void setImportado(boolean importado) {

    }
}
