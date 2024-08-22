package tienda.models.interfaces;

import java.time.LocalDate;

public interface Comestible {
    LocalDate getFechaVencimiento();
    void setFechaVencimiento(LocalDate fechaVencimiento);
    double getCalorias();
    boolean isImportado();
}
