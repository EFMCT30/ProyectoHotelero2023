package pe.edu.cibertec.ProyectoHotelero.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal precioTotal;
    private String comentarios;
    private Long clienteId; // Para vincular con el cliente
    private Long habitacionId; // Para vincular con la habitación


}

