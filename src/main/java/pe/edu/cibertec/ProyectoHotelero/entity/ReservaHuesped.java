package pe.edu.cibertec.ProyectoHotelero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva_huespedes")
public class ReservaHuesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    @ManyToOne
    @JoinColumn(name = "huesped_id")
    private Huesped huesped;

}

