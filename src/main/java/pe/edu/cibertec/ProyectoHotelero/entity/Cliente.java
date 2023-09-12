package pe.edu.cibertec.ProyectoHotelero.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    private boolean activo;
    private String preferencias;

    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas;
}

