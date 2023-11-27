package pe.edu.cibertec.ProyectoHotelero.dto.response;

import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;

public class ReservaResponseDTO {
    private ReservaDTO reservaDTO;
    private String message;

    public ReservaResponseDTO(ReservaDTO reservaDTO, String message) {
        this.reservaDTO = reservaDTO;
        this.message = message;
    }

    public ReservaDTO getReservaDTO() {
        return reservaDTO;
    }

    public String getMessage() {
        return message;
    }
}
