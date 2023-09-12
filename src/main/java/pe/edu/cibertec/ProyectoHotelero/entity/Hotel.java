package pe.edu.cibertec.ProyectoHotelero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoteles")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long hotelId;

    private String nombre;
    private String direccion;
    private String telefono;
    private int estrellas;
    private String descripcion;
    @Column(name = "fecha_construccion")
    private Date fechaConstruccion;
    private String categoria;

    @OneToMany(mappedBy = "hotel")
    private List<Habitacion> habitaciones;
}
