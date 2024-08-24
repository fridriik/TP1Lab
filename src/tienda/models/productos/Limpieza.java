package tienda.models.productos;

/**
 * Clase que representa un producto de limpieza en la tienda.
 * Extiende la clase Producto y añade atributos específicos para productos de limpieza.
 */
public class Limpieza extends Producto {
    private static int contadorLimpieza = 1;
    private String tipoAplicacion;

    /**
     * Constructor para crear un nuevo producto de limpieza.
     * Tiene un identificador AZ*** -> * = autoincremental
     *
     * @param descripcion Descripción del producto.
     * @param cantidadStock Cantidad inicial en stock.
     * @param precioUnidad Precio por unidad.
     * @param porcentajeGanancia Porcentaje de ganancia sobre el precio.
     * @param tipoAplicacion Tipo de aplicación del producto (COCINA, BANIO, ROPA, MULTIUSO).
     * @throws IllegalArgumentException Si el porcentaje de ganancia o descuento no cumple con las restricciones.
     */
    public Limpieza(String descripcion, int cantidadStock,
                    double precioUnidad, double porcentajeGanancia,
                    String tipoAplicacion) {
        super(descripcion, cantidadStock, precioUnidad, porcentajeGanancia);
        setTipoAplicacion(tipoAplicacion);
        this.identificador = "AZ" + String.format("%03d", contadorLimpieza++);
        validarPorcentajes();
    }

    /**
     * Valida los porcentajes de ganancia y descuento según las reglas de negocio.
     *
     * @throws IllegalArgumentException Si los porcentajes no cumplen con las restricciones.
     */
    private void validarPorcentajes() {
        if (getTipoAplicacion() != null &&
                !getTipoAplicacion().equals("COCINA") &&
                !getTipoAplicacion().equals("MULTIUSO") &&
                (getPorcentajeGanancia() < 10 || getPorcentajeGanancia() > 25)) {
            throw new IllegalArgumentException("El porcentaje de ganancia para productos de limpieza debe estar entre 10% y 25%, excepto COCINA y MULTIUSO.");
        }
    }

    /**
     * Establece el tipo de aplicación del producto de limpieza.
     *
     * @param tipoAplicacion El tipo de aplicación a establecer.
     * @throws IllegalArgumentException Si el tipo de aplicación no es válido.
     */
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
        if (porcentajeDescuento > 20) {
            throw new IllegalArgumentException("El porcentaje de descuento no puede superar el 20% para productos de limpieza.");
        }
        this.descuentoAplicado = porcentajeDescuento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbSuper = new StringBuilder(super.toString());
        sb.append("| ");
        sb.append("Producto de limpieza: ");
        sb.append(sbSuper);
        sb.append(", Tipo de aplicación: ").append(tipoAplicacion);
        sb.append(" |");
        return sb.toString();
    }
}