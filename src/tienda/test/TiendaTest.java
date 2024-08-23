package tienda.test;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Limpieza;
import tienda.models.productos.Producto;
import tienda.negocio.Tienda;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiendaTest {
    public static void main(String[] args) {
        Tienda tienda = new Tienda(
                "Mi Tienda",
                100,
                10000);

        Envasado cafe = new Envasado(
                "Café",
                10,
                10,
                15,
                "Lata",
                false,
                LocalDate.of(2025, 6, 15)
        );

        Envasado cafeColombiano = new Envasado(
                "Café colombiano",
                10,
                100,
                15,
                "Lata",
                true,
                LocalDate.of(2025, 6, 15)
        );

        Bebida seltzer = new Bebida(
                "Seltzer",
                10,
                100,
                9,
                3,
                false,
                LocalDate.of(2025, 12, 31)
        );

        /*//Creados para testear casos malos
        Envasado oroComestible = new Envasado(
                "Oro comestible",
                1,
                10000,
                50,
                "Caja de seguridad",
                true,
                LocalDate.of(2025, 6, 15)
        );

        Limpieza suavizante = new Limpieza(
                "Suavizante",
                10,
                150,
                26,
                "ROPA"
        );

        Limpieza pastillaInodoro = new Limpieza(
                "Pastilla inodoro",
                10,
                15,
                9,
                "BANIO"
        );

        Bebida cervezaKirin = new Bebida(
                "Kirin",
                50,
                200,
                7,
                16,
                true,
                LocalDate.of(2025, 12, 31)
        );*/

        Bebida cervezaQuilmes = new Bebida(
                "Quilmes",
                10,
                10,
                7,
                16,
                false,
                LocalDate.of(2025, 12, 31)
        );

        Bebida agua = new Bebida(
                "Agua",
                10,
                10,
                5,
                0,
                true,
                LocalDate.of(2025, 12, 31)
        );

        Limpieza detergente = new Limpieza(
                "Detergente",
                20,
                15,
                12,
                "COCINA"
        );

        System.out.println("\nComprando productos:");
        tienda.comprarProducto(cafe);
        System.out.println(tienda);
        tienda.comprarProducto(cafeColombiano);
        tienda.comprarProducto(seltzer);
        tienda.comprarProducto(detergente);
        //tienda.comprarProducto(cervezaKirin);
        System.out.println(tienda);

        System.out.println("\nCalorias según graduaciones alcoholicas:");
        System.out.println("Calorías de seltzer: " + seltzer.getCalorias());
        System.out.println("Calorías de Quilmes: " + cervezaQuilmes.getCalorias());
        System.out.println("Calorías de agua: " + agua.getCalorias());

        System.out.println("\nPrecios con descuento:");
        System.out.println("Precio base del seltzer: " + seltzer.getPrecioUnidad());
        System.out.println("Precio total del seltzer: " + seltzer.calcularPrecioVenta());
        //seltzer.aplicarDescuento(15);
        seltzer.aplicarDescuento(4);
        System.out.println("Precio total del seltzer: " + seltzer.calcularPrecioVenta());

        System.out.println("Precio base del cafe: " + cafe.getPrecioUnidad());
        System.out.println("Precio total del cafe: " + cafe.calcularPrecioVenta());
        //cafe.aplicarDescuento(20);
        cafe.aplicarDescuento(15);
        System.out.println("Precio total del cafe: " + cafe.calcularPrecioVenta());

        System.out.println("Precio base del detergente: " + detergente.getPrecioUnidad());
        System.out.println("Precio total del detergente: " + detergente.calcularPrecioVenta());
        //detergente.aplicarDescuento(25);
        detergente.aplicarDescuento(20);
        System.out.println("Precio total del detergente: " + detergente.calcularPrecioVenta());

        System.out.println("\nSon importados?");
        System.out.println("Precio con impuesto: " + agua.calcularPrecioVenta());
        System.out.println("Precio sin impuesto: " + cervezaQuilmes.calcularPrecioVenta());

        System.out.println("\nVendiendo productos:");
        Map<Producto, Integer> venta1 = new HashMap<>();
        venta1.put(seltzer, 10);
        venta1.put(detergente, 12);
        venta1.put(cafe, 5);
        tienda.venderProductos(venta1);

        /* Intento de venta sin stock
        Map<Producto, Integer> venta11 = new HashMap<>();
        venta11.put(seltzer, 10);
        tienda.venderProductos(venta11);*/

        System.out.println("\nIntentando vender producto no disponible:");
        tienda.cambiarDisponibilidadProducto(detergente, false);
        Map<Producto, Integer> venta2 = new HashMap<>();
        venta2.put(detergente, 1);
        tienda.venderProductos(venta2);

        System.out.println("\nIntentando vender producto redisponible:");
        tienda.cambiarDisponibilidadProducto(detergente, true);
        Map<Producto, Integer> venta3 = new HashMap<>();
        venta3.put(detergente, 1);
        tienda.venderProductos(venta3);

        System.out.println("\nObteniendo comestibles con menor descuento:");
        List<String> comestiblesMenorDescuento = tienda.obtenerComestiblesConMenorDescuento(5);
        System.out.println(String.join(", ", comestiblesMenorDescuento));
    }
}