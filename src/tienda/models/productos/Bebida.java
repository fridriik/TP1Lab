package tienda.models.productos;

import tienda.models.interfaces.Comestible;
import tienda.models.interfaces.Importado;

import java.time.LocalDate;

public class Bebida extends Producto implements Comestible, Importado {
    private double graduacionAlcoholica;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private int calorias;

    public Bebida(String identificador, String descripcion, int cantidadStock,
                  double precioUnidad, double porcentajeGanancia,
                  double graduacionAlcoholica, boolean importado) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        if (!identificador.matches("AC\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para bebida");
        }
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.importado = importado;
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
        if (graduacionAlcoholica <= 2) {
            this.calorias = calorias;
        } else if (graduacionAlcoholica <= 4.5) {
            this.calorias = (int)(calorias * 1.25);
        } else {
            this.calorias = (int)(calorias * 1.5);
        }
    }

    @Override
    public boolean esImportado() {
        return false;
    }

    @Override
    public void setImportado(boolean importado) {

    }
}