package tienda.negocio;

import tienda.models.interfaces.Comestible;
import tienda.models.interfaces.Importado;
import tienda.models.productos.Producto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {
    private String nombre;
    private int maxProductosStock;
    private double saldoCaja;
    private List<Producto> productos;

    public Tienda(String nombre, int maxProductosStock, double saldoCaja) {
        this.nombre = nombre;
        this.maxProductosStock = maxProductosStock;
        this.saldoCaja = saldoCaja;
        this.productos = new ArrayList<>();
    }

    public void comprarProducto(Producto producto) {
        int totalUnidades = productos.stream().mapToInt(p -> p.getCantidadStock()).sum();
        if (totalUnidades + producto.getCantidadStock() > maxProductosStock) {
            System.out.println("No se pueden agregar nuevos productos a la tienda ya que se alcanzó el máximo de stock");
            return;
        }

        double costoTotal = producto.getPrecioUnidad() * producto.getCantidadStock();
        if (saldoCaja < costoTotal) {
            System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
            return;
        }

        saldoCaja -= costoTotal;
        productos.add(producto);
        System.out.println("Producto agregado exitosamente");
    }

    public void venderProductos(List<Producto> productosVenta) {
        if (productosVenta.size() > 3) {
            System.out.println("No se pueden vender más de 3 productos en una venta");
            return;
        }

        double totalVenta = 0;
        boolean stockInsuficiente = false;
        boolean productoNoDisponible = false;

        for (Producto producto : productosVenta) {
            if (!producto.isDisponibleVenta()) {
                System.out.println("El producto " + producto.getIdentificador() + " " + producto.getDescripcion() + " no se encuentra disponible");
                productoNoDisponible = true;
                continue;
            }

            int cantidadVendida = Math.min(producto.getCantidadStock(), 12);
            if (cantidadVendida < 12) {
                stockInsuficiente = true;
            }

            double precioVenta = producto.calcularPrecioVenta();
            totalVenta += precioVenta * cantidadVendida;

            producto.setCantidadStock(producto.getCantidadStock() - cantidadVendida);
            if (producto.getCantidadStock() == 0) {
                producto.setDisponibleVenta(false);
            }

            System.out.printf("%s %s %d x %.2f\n", producto.getIdentificador(), producto.getDescripcion(), cantidadVendida, precioVenta);
        }

        System.out.printf("TOTAL VENTA: %.2f\n", totalVenta);
        if (stockInsuficiente) {
            System.out.println("Hay productos con stock disponible menor al solicitado");
        }
        if (productoNoDisponible) {
            System.out.println("Algunos productos no estaban disponibles para la venta");
        }

        saldoCaja += totalVenta;
    }

    public List<String> obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        return productos.stream()
                .filter(p -> p instanceof Comestible)
                .filter(p -> !(p instanceof Importado) || !((Importado) p).esImportado())
                .filter(p -> p.aplicarDescuento(porcentajeDescuento) > p.calcularPrecioVenta() * (1 - porcentajeDescuento / 100))
                .sorted(Comparator.comparingDouble(Producto::calcularPrecioVenta))
                .map(p -> p.getDescripcion().toUpperCase())
                .collect(Collectors.toList());
    }
}
