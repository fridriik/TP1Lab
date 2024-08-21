package tienda.negocio;

import tienda.models.productos.Producto;
import java.util.List;

public class GestionDeProductos {
    public void comprarProducto(Producto producto, Tienda tienda) {
        int totalUnidades = tienda.getProductos().stream().mapToInt(Producto::getCantidadStock).sum();
        if (totalUnidades + producto.getCantidadStock() > tienda.getMaxProductosStock()) {
            System.out.println("No se pueden agregar nuevos productos a la tienda ya que se alcanzó el máximo de stock");
            return;
        }

        double costoTotal = producto.getPrecioUnidad() * producto.getCantidadStock();
        if (tienda.getSaldoCaja() < costoTotal) {
            System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
            return;
        }

        tienda.setSaldoCaja(tienda.getSaldoCaja() - costoTotal);
        tienda.getProductos().add(producto);
        System.out.println("Producto agregado exitosamente");
    }

    public void venderProductos(List<Producto> productosVenta, Tienda tienda) {
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

        tienda.setSaldoCaja(tienda.getSaldoCaja() + totalVenta);
    }
}