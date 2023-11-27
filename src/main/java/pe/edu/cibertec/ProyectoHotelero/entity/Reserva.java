package pe.edu.cibertec.ProyectoHotelero.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "reserva_id")
        private Long reservaId;
        @Column(name = "fecha_inicio")
        private Date fechaInicio;
        @Column(name = "fecha_fin")
        private Date fechaFin;
        private String estado;
        @Column(name = "precio_total")
        private BigDecimal precioTotal;
        @Column(name = "fecha_creacion")
        private Timestamp fechaCreacion;
        private String comentarios;
        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "cliente_id")
        private Cliente cliente;
        @JsonIgnore
        @OneToMany(mappedBy = "reserva")
        private List<ReservaHabitacion> reservaHabitaciones;

        @JsonIgnore
        @ManyToMany
        @JoinTable(
                name = "reserva_huespedes",
                joinColumns = @JoinColumn(name = "reserva_id"),
                inverseJoinColumns = @JoinColumn(name = "huesped_id")
        )
        private Set<Huesped> huespedes;


}
