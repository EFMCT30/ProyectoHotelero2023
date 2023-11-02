package pe.edu.cibertec.ProyectoHotelero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "huespedes")
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "huesped_id")
    private Long huespedId;

    private String nombre;
    private String apellido;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;

    @ManyToMany(mappedBy = "huespedes")
    private Set<Reserva> reservas;
}
