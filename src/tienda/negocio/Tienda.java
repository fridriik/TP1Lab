package tienda.negocio;

import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
import tienda.models.productos.Producto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que representa una tienda con operaciones de compra, venta y gestión de productos.
 */
public class Tienda {
    private String nombre;
    private int maxProductosStock;
    private double saldoCaja;
    private Map<Producto, Integer> productosDisponibles;

    /**
     * Constructor de la clase Tienda.
     *
     * @param nombre Nombre de la tienda.
     * @param maxProductosStock Máximo número de productos que puede tener la tienda en stock.
     * @param saldoCaja Saldo inicial de la caja de la tienda.
     * @throws IllegalArgumentException Si el nombre de la tienda está vacío, si el máximo de productos
     *                                   en stock es 0 o si el saldo inicial es negativo.
     */
    public Tienda(String nombre, int maxProductosStock, double saldoCaja) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tienda no puede estar vacío.");
        }
        if (maxProductosStock <= 0) {
            throw new IllegalArgumentException("El máximo de productos en stock debe ser mayor que cero.");
        }
        if (saldoCaja < 0) {
            throw new IllegalArgumentException("El saldo inicial de la caja no puede ser negativo.");
        }

        this.nombre = nombre;
        this.maxProductosStock = maxProductosStock;
        this.saldoCaja = saldoCaja;
        this.productosDisponibles = new HashMap<>();
    }

    /**
     * Compra un producto para añadirlo al stock de la tienda.
     *
     * @param producto Producto a comprar.
     * @throws IllegalArgumentException Si el producto ya existe, si se excede el límite de stock,
     *                                  si no hay saldo suficiente, o si el producto o sus atributos no son válidos.
     */
    public void comprarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (producto.getCantidadStock() < 0) {
            throw new IllegalArgumentException("La cantidad de stock no puede ser negativa.");
        }
        if (producto.getPrecioUnidad() < 0) {
            throw new IllegalArgumentException("El precio por unidad no puede ser negativo.");
        }

        validarProductoExistente(producto);

        int cantidadCompra = producto.getCantidadStock();
        if (!hayEspacioSuficiente(cantidadCompra)) {
            throw new IllegalArgumentException("No se pueden agregar nuevos productos a la tienda ya que se alcanzó el máximo de stock");
        }

        double costoTotal = producto.getPrecioUnidad() * cantidadCompra;
        if (!haySaldoSuficiente(costoTotal)) {
            throw new IllegalArgumentException("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");
        }

        realizarCompra(producto, cantidadCompra, costoTotal);
    }

    private void validarProductoExistente(Producto producto) {
        for (Producto p : productosDisponibles.keySet()) {
            if (p.getDescripcion().equals(producto.getDescripcion())) {
                throw new IllegalArgumentException("Error: Ya existe un producto con la misma descripción.");
            }
        }
    }

    private boolean hayEspacioSuficiente(int cantidadCompra) {
        int totalUnidades = productosDisponibles.values().stream().mapToInt(Integer::intValue).sum();
        return totalUnidades + cantidadCompra <= maxProductosStock;
    }

    private boolean haySaldoSuficiente(double costoTotal) {
        return saldoCaja >= costoTotal;
    }

    private void realizarCompra(Producto producto, int cantidadCompra, double costoTotal) {
        saldoCaja -= costoTotal;
        productosDisponibles.put(producto, cantidadCompra);
        System.out.println("Producto agregado exitosamente");
    }

    /**
     * Realiza una venta de productos.
     *
     * @param productosVenta Mapa de productos a vender y sus cantidades.
     * @throws IllegalArgumentException Si se intenta vender más de 3 productos diferentes,
     *                                  si algún producto o cantidad no es válido.
     */
    public void venderProductos(Map<Producto, Integer> productosVenta) {
        if (productosVenta == null || productosVenta.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos a vender no puede ser nula o vacía.");
        }
        if (productosVenta.size() > 3) {
            throw new IllegalArgumentException("No se pueden vender más de 3 productos en una venta");
        }

        double totalVenta = 0;
        boolean stockInsuficiente = false;
        boolean productoNoDisponible = false;

        for (Map.Entry<Producto, Integer> entry : productosVenta.entrySet()) {
            Producto producto = entry.getKey();
            int cantidadVendida = entry.getValue();

            if (producto == null) {
                throw new IllegalArgumentException("El producto no puede ser nulo.");
            }
            if (cantidadVendida <= 0) {
                throw new IllegalArgumentException("La cantidad a vender debe ser mayor que cero.");
            }

            if (!producto.isDisponibleVenta()) {
                System.out.println("El producto " + producto.getIdentificador() + " " + producto.getDescripcion() + " no se encuentra disponible");
                productoNoDisponible = true;
                continue;
            }

            stockInsuficiente |= ajustarCantidadVendida(producto, cantidadVendida);
            double precioVenta = producto.calcularPrecioVenta();
            totalVenta += precioVenta * cantidadVendida;
            actualizarStockProducto(producto, cantidadVendida);
            imprimirDetalleVenta(producto, cantidadVendida, precioVenta);
        }

        finalizarVenta(totalVenta, stockInsuficiente, productoNoDisponible);
    }

    private boolean ajustarCantidadVendida(Producto producto, int cantidadVendida) {
        if (cantidadVendida > producto.getCantidadStock()) {
            cantidadVendida = producto.getCantidadStock();
            return true;
        }
        return false;
    }

    private void actualizarStockProducto(Producto producto, int cantidadVendida) {
        producto.setCantidadStock(producto.getCantidadStock() - cantidadVendida);
        if (producto.getCantidadStock() == 0) {
            producto.setDisponibleVenta(false);
        }
    }

    private void imprimirDetalleVenta(Producto producto, int cantidadVendida, double precioVenta) {
        System.out.printf("%s %s %d x %.2f\n", producto.getIdentificador(), producto.getDescripcion(), cantidadVendida, precioVenta);
    }

    private void finalizarVenta(double totalVenta, boolean stockInsuficiente, boolean productoNoDisponible) {
        System.out.printf("TOTAL VENTA: %.2f\n", totalVenta);
        if (stockInsuficiente) {
            System.out.println("Hay productos con stock disponible menor al solicitado");
        }
        if (productoNoDisponible) {
            System.out.println("Algunos productos no estaban disponibles para la venta");
        }
        saldoCaja += totalVenta;
    }

    /**
     * Cambia la disponibilidad de un producto.
     *
     * @param producto Producto a modificar.
     * @param disponible Nueva disponibilidad del producto.
     */
    public void cambiarDisponibilidadProducto(Producto producto, boolean disponible) {
        if (disponible) {
            productosDisponibles.putIfAbsent(producto, producto.getCantidadStock());
            producto.setDisponibleVenta(true);
        } else {
            productosDisponibles.remove(producto);
            producto.setDisponibleVenta(false);
        }
    }

    /**
     * Obtiene una lista de productos comestibles con un descuento menor al especificado.
     *
     * @param porcentajeDescuento Porcentaje de descuento máximo a considerar.
     * @return Lista de descripciones de productos que cumplen con el criterio.
     */
    public List<String> obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        return productosDisponibles.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Bebida || entry.getKey() instanceof Envasado)
                .filter(entry -> {
                    double descuentoAplicado = entry.getKey().getDescuentoAplicado();
                    return descuentoAplicado >= 1.0 && descuentoAplicado < porcentajeDescuento;
                })
                .sorted(Map.Entry.comparingByKey(Comparator.comparingDouble(Producto::calcularPrecioVenta)))
                .map(entry -> entry.getKey().getDescripcion().toUpperCase())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tienda: ").append(nombre).append("\n");
        sb.append("Saldo en caja: $").append(saldoCaja).append("\n");
        sb.append("Productos disponibles:\n");

        productosDisponibles.forEach((producto, cantidad) -> {
            sb.append(producto.toString());
        });

        return sb.toString();
    }

}