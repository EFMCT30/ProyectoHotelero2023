package pe.edu.cibertec.ProyectoHotelero.dto.request;

import lombok.Data;

@Data
public class HuespedDTO {
    private String nombre;
    private String apellido;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;
}

