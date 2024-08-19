package tienda.test;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Limpieza;
import tienda.models.productos.Producto;
import tienda.negocio.Tienda;

import java.util.Arrays;
import java.util.List;

public class TiendaTest {
    public static void main(String[] args) {
        Tienda tienda = new Tienda("Mi Tienda", 100, 10000);

        Envasado cafe = new Envasado("AB123", "Café", 50, 10, 15, "Lata", false);
        Envasado aceite = new Envasado("AB456", "Aceite", 30, 50, 20, "Botella", true);
        Bebida vino = new Bebida("AC789", "Vino", 20, 100, 25, 12, false);
        Limpieza detergente = new Limpieza("AZ101", "Detergente", 40, 15, 12, "COCINA");

        System.out.println("Comprando productos:");
        tienda.comprarProducto(cafe);
        tienda.comprarProducto(aceite);
        tienda.comprarProducto(vino);
        tienda.comprarProducto(detergente);

        System.out.println("\nVendiendo productos:");
        List<Producto> venta1 = Arrays.asList(cafe, aceite, vino);
        tienda.venderProductos(venta1);

        System.out.println("\nIntentando vender producto no disponible:");
        Envasado productoAgotado = new Envasado("AB789", "Producto Agotado", 0, 10, 15, "Caja", false);
        productoAgotado.setDisponibleVenta(false);
        List<Producto> venta2 = Arrays.asList(productoAgotado, detergente);
        tienda.venderProductos(venta2);

        System.out.println("\nObteniendo comestibles con menor descuento:");
        List<String> comestiblesMenorDescuento = tienda.obtenerComestiblesConMenorDescuento(5);
        System.out.println(String.join(", ", comestiblesMenorDescuento));
    }
}
