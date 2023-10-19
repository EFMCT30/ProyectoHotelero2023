package pe.edu.cibertec.ProyectoHotelero.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitacionDTO {
    @NotNull
    private Long hotelId;  // Agregamos el campo para el hotelId
    private int numeroHabitacion;
    private String tipo;
    private int capacidad;
    private boolean disponible;
    private BigDecimal precioNoche;
    private Date fechaUltimaMantenimiento;

    // Constructor, getters y setters
}

