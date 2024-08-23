package tienda.models.interfaces;

import java.time.LocalDate;

/**
 * Interfaz que define los métodos comunes para productos comestibles.
 */
public interface Comestible {
    /**
     * Obtiene la fecha de vencimiento del producto comestible.
     *
     * @return La fecha de vencimiento.
     */
    LocalDate getFechaVencimiento();

    /**
     * Establece la fecha de vencimiento del producto comestible.
     *
     * @param fechaVencimiento La nueva fecha de vencimiento.
     */
    void setFechaVencimiento(LocalDate fechaVencimiento);

    /**
     * Obtiene las calorías del producto comestible.
     *
     * @return Las calorías del producto.
     */
    double getCalorias();

    /**
     * Indica si el producto comestible es importado.
     *
     * @return true si el producto es importado, false en caso contrario.
     */
    boolean isImportado();
}