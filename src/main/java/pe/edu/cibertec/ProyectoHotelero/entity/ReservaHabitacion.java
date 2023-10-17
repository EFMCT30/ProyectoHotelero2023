package pe.edu.cibertec.ProyectoHotelero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "reserva_habitaciones")
public class ReservaHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_habitacion_id")
    private Long reservaHabitacionId;
    private int cantidad;
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;
}
