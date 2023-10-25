package pe.edu.cibertec.ProyectoHotelero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteUpdateDTO {
    private String nombre;
    private String telefono;
    private String direccion;
    private String preferencias;
    private boolean activo;

}
