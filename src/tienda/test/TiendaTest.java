package tienda.test;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Limpieza;
import tienda.models.productos.Producto;
import tienda.negocio.Tienda;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TiendaTest {
    public static void main(String[] args) {
        Tienda tienda = new Tienda("Mi Tienda", 100, 10000);

        Envasado cafe = new Envasado(
                "AB122",
                "Café",
                50,
                10,
                15,
                "Lata",
                false,
                LocalDate.of(2025, 6, 15)
        );
        Envasado aceite = new Envasado(
                "AB455",
                "Aceite",
                30,
                50,
                20,
                "Botella",
                true,
                LocalDate.of(2024, 12, 31)
        );

        Bebida vino = new Bebida(
                "AC789",
                "Vino",
                20,
                100,
                25,
                12,
                false,
                LocalDate.of(2025, 12, 31)
        );

        Bebida gaseosa = new Bebida(
                "AC121",
                "Gaseosa Cola",
                100,
                50.0,
                15.0,
                0.0,
                false,
                LocalDate.of(2024, 8, 31)
        );

        Limpieza detergente = new Limpieza(
                "AZ101",
                "Detergente",
                40,
                15,
                12,
                "COCINA"
        );

        System.out.println("Comprando productos:");
        tienda.comprarProducto(cafe);
        tienda.comprarProducto(aceite);
        tienda.comprarProducto(vino);
        tienda.comprarProducto(detergente);

        System.out.println("\nVendiendo productos:");
        List<Producto> venta1 = Arrays.asList(cafe, aceite, vino);
        tienda.venderProductos(venta1);

        System.out.println("\nIntentando vender producto no disponible:");
        Envasado productoAgotado = new Envasado(
                "AB789",
                "Producto Agotado",
                0,
                10,
                15,
                "Caja",
                false,
                LocalDate.of(2024, 12, 31)
        );
        productoAgotado.setDisponibleVenta(false);
        List<Producto> venta2 = Arrays.asList(productoAgotado, detergente);
        tienda.venderProductos(venta2);

        System.out.println("\nObteniendo comestibles con menor descuento:");
        List<String> comestiblesMenorDescuento = tienda.obtenerComestiblesConMenorDescuento(5);
        System.out.println(String.join(", ", comestiblesMenorDescuento));
    }
}
