package tienda.utils;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Limpieza;
import tienda.models.productos.Producto;

public class ValidadorProductos {
    public static void validarProducto(Producto producto) {
        String identificador = producto.getIdentificador();
        double porcentajeGanancia = producto.getPorcentajeGanancia();
        double descuento = producto.getDescuentoAplicado();
        boolean esBebida = producto instanceof Bebida;
        boolean esEnvasado = producto instanceof Envasado;
        boolean esLimpieza = producto instanceof Limpieza;

        if (esEnvasado && !identificador.matches("AB\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para producto envasado");
        }
        if (esBebida && !identificador.matches("AC\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para bebida");
        }
        if (esLimpieza && !identificador.matches("AZ\\d{3}")) {
            throw new IllegalArgumentException("Identificador inválido para producto de limpieza");
        }
        if (porcentajeGanancia < 0) {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser negativo");
        }
        if ((esEnvasado || esBebida) && porcentajeGanancia >= 20) {
            throw new IllegalArgumentException("El porcentaje de ganancia para productos comestibles no puede superar el 20%");
        }
        if (esLimpieza) {
            Limpieza limpieza = (Limpieza) producto;
            if (limpieza.getTipoAplicacion() != null &&
                    !limpieza.getTipoAplicacion().equals("COCINA") &&
                    !limpieza.getTipoAplicacion().equals("MULTIUSO") &&
                    (porcentajeGanancia < 10 || porcentajeGanancia > 25)) {
                throw new IllegalArgumentException("El porcentaje de ganancia para productos de limpieza debe estar entre 10% y 25%, excepto COCINA y MULTIUSO");
            }
        }
        if (esBebida && descuento >= 10){
            throw new IllegalArgumentException("El porcentaje de descuento de las bebidas no puede superar el 10%.");
        }
        if (esEnvasado && descuento >= 15){
            throw new IllegalArgumentException("El porcentaje de descuento de los envasados no puede superar el 15%.");
        }
        if (esLimpieza && descuento >= 20){
            throw new IllegalArgumentException("El porcentaje de descuento de los productos de limpieza no puede superar el 20%.");
        }
    }
}
