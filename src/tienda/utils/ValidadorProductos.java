package tienda.utils;

import tienda.models.productos.Producto;
import tienda.models.productos.Envasado;
import tienda.models.productos.Bebida;
import tienda.models.productos.Limpieza;

public class ValidadorProductos {
    public static void validarIdentificador(Producto producto) {
        if (producto instanceof Envasado) {
            if (!producto.getIdentificador().matches("AB\\d{3}")) {
                throw new IllegalArgumentException("Identificador inválido para producto envasado");
            }
        } else if (producto instanceof Bebida) {
            if (!producto.getIdentificador().matches("AC\\d{3}")) {
                throw new IllegalArgumentException("Identificador inválido para bebida");
            }
        } else if (producto instanceof Limpieza) {
            if (!producto.getIdentificador().matches("AZ\\d{3}")) {
                throw new IllegalArgumentException("Identificador inválido para producto de limpieza");
            }
        }
    }

    public static void validarPorcentajeGanancia(Limpieza producto) {
        if (!producto.getTipoAplicacion().equals("COCINA") && !producto.getTipoAplicacion().equals("MULTIUSO")) {
            if (producto.getPorcentajeGanancia() < 10 || producto.getPorcentajeGanancia() > 25) {
                throw new IllegalArgumentException("Porcentaje de ganancia inválido para este tipo de producto de limpieza");
            }
        }
    }
}