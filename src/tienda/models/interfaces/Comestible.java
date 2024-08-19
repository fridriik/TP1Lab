package tienda.models.interfaces;

import java.time.LocalDate;

public interface Comestible {
    LocalDate getFechaVencimiento();
    void setFechaVencimiento(LocalDate fechaVencimiento);
    int getCalorias();
    void setCalorias(int calorias);
}
