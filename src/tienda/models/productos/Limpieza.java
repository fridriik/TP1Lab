package tienda.models.productos;

public class Limpieza extends Producto {
    private String tipoAplicacion;

    public Limpieza(String identificador, String descripcion, int cantidadStock,
                            double precioUnidad, double porcentajeGanancia,
                            String tipoAplicacion) {
        super(identificador, descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        setTipoAplicacion(tipoAplicacion);
        validarIdentificador(identificador);
        validarPorcentajeGanancia();
    }

    private void validarIdentificador(String identificador) {
        if (!identificador.matches("AZ\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para producto de limpieza");
        }
    }

    public void setTipoAplicacion(String tipoAplicacion) {
        String tipoUpperCase = tipoAplicacion.toUpperCase();
        if (!esTipoAplicacionValido(tipoUpperCase)) {
            throw new IllegalArgumentException("Tipo de aplicación inválido. Debe ser COCINA, BAÑO, ROPA o MULTIUSO.");
        }
        this.tipoAplicacion = tipoUpperCase;
    }

    private boolean esTipoAplicacionValido(String tipo) {
        return tipo.equals("COCINA") || tipo.equals("BAÑO") || tipo.equals("ROPA") || tipo.equals("MULTIUSO");
    }

    public String getTipoAplicacion() {
        return tipoAplicacion;
    }

    private void validarPorcentajeGanancia() {
        if (!tipoAplicacion.equals("COCINA") && !tipoAplicacion.equals("MULTIUSO")) {
            if (porcentajeGanancia < 10 || porcentajeGanancia > 25) {
                throw new IllegalArgumentException("Porcentaje de ganancia inválido para este tipo de producto de limpieza");
            }
        }
    }

    @Override
    public double calcularPrecioVenta() {
        return precioUnidad * (1 + porcentajeGanancia / 100);
    }

    @Override
    public double aplicarDescuento(double porcentajeDescuento) {
        return calcularPrecioVenta() * (1 - Math.min(porcentajeDescuento, 20) / 100);
    }
}