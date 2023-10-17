package pe.edu.cibertec.ProyectoHotelero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private Long habitacionId;
    private int numeroHabitacion;
    private String tipo;
    private int capacidad;
    private BigDecimal precioNoche;
    private boolean disponible;
    @Column(name = "fecha_ultima_mantenimiento")
    private Date fechaUltimaMantenimiento;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @OneToMany(mappedBy = "habitacion")
    private List<ReservaHabitacion> reservaHabitaciones;
}

