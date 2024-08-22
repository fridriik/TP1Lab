package tienda.negocio;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Producto;
import tienda.utils.ValidadorProductos;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tienda {
    private String nombre;
    private int maxProductosStock;
    private double saldoCaja;
    private Map<Producto, Integer> productosDisponibles;

    public Tienda(String nombre, int maxProductosStock, double saldoCaja) {
        this.nombre = nombre;
        this.maxProductosStock = maxProductosStock;
        this.saldoCaja = saldoCaja;
        this.productosDisponibles = new HashMap<>();
    }

    public void comprarProducto(Producto producto) {
        Producto productoEncontrado = null;
        for (Producto p : productosDisponibles.keySet()) {
            if (p.getIdentificador().equals(producto.getIdentificador()) &&
                    p.getDescripcion().equals(producto.getDescripcion())) {
                productoEncontrado = p;
                System.out.println("Producto tiene el mismo ID y descripción, verificar instancia");
                break;
            }
        }
        if (productoEncontrado != null) {
            int cantidadActual = productosDisponibles.get(productoEncontrado);
            productosDisponibles.put(productoEncontrado, cantidadActual + producto.getCantidadStock());
        } else {
            int cantidadCompra = producto.getCantidadStock();
            int totalUnidades = productosDisponibles.values().stream().mapToInt(Integer::intValue).sum();
            if (totalUnidades + cantidadCompra > maxProductosStock) {
                System.out.println("No se pueden agregar nuevos productos a la tienda ya que se alcanzó el máximo de stock");
                return;
            }
            double costoTotal = producto.getPrecioUnidad() * cantidadCompra;
            if (saldoCaja < costoTotal) {
                System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
                return;
            }
            saldoCaja -= costoTotal;
            productosDisponibles.put(producto, cantidadCompra);
            System.out.println("Producto agregado exitosamente");
        }
    }


    public void venderProductos(Map<Producto, Integer> productosVenta) {
        if (productosVenta.size() > 3) {
            System.out.println("No se pueden vender más de 3 productos en una venta");
            return;
        }
        double totalVenta = 0;
        boolean stockInsuficiente = false;
        boolean productoNoDisponible = false;
        for (Map.Entry<Producto, Integer> entry : productosVenta.entrySet()) {
            Producto producto = entry.getKey();
            int cantidadVendida = entry.getValue();
            if (!producto.isDisponibleVenta()) {
                System.out.println("El producto " + producto.getIdentificador() + " " + producto.getDescripcion() + " no se encuentra disponible");
                productoNoDisponible = true;
                continue;
            }
            if (cantidadVendida > producto.getCantidadStock()) {
                cantidadVendida = producto.getCantidadStock();
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

    public void cambiarDisponibilidadProducto(Producto producto, boolean disponible) {
        if (disponible) {
            productosDisponibles.putIfAbsent(producto, producto.getCantidadStock());
            producto.setDisponibleVenta(true);
        } else {
            productosDisponibles.remove(producto);
            producto.setDisponibleVenta(false);
        }
    }

    public List<String> obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        return productosDisponibles.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Bebida || entry.getKey() instanceof Envasado)
                .filter(entry -> entry.getKey().getDescuentoAplicado() < porcentajeDescuento)
                .sorted(Map.Entry.comparingByKey(Comparator.comparingDouble(Producto::calcularPrecioVenta)))
                .map(entry -> entry.getKey().getDescripcion().toUpperCase())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Tienda{" +
                ", \nproductosDisponibles=" + productosDisponibles +
                '}';
    }
}