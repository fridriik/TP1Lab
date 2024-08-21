package tienda.negocio;

import tienda.models.interfaces.Comestible;
import tienda.models.productos.Bebida;
import tienda.models.productos.Envasado;
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
        new GestionDeProductos().comprarProducto(producto, this);
    }

    public void venderProductos(List<Producto> productosVenta) {
        new GestionDeProductos().venderProductos(productosVenta, this);
    }

    public List<String> obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        return productos.stream()
                .filter(p -> p instanceof Comestible) // Filtrar solo comestibles
                .filter(p -> !(p instanceof Bebida bebida && bebida.isImportado()) &&
                        !(p instanceof Envasado envasado && envasado.isImportado()))
                .sorted(Comparator.comparingDouble(Producto::calcularPrecioVenta))
                .map(p -> p.getDescripcion().toUpperCase())
                .collect(Collectors.toList());
    }

    public int getMaxProductosStock() {
        return maxProductosStock;
    }

    public double getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}