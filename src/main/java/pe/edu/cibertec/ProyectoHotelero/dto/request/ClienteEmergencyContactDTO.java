package pe.edu.cibertec.ProyectoHotelero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ClienteEmergencyContactDTO {

    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String relationship;
    private String address;
}
