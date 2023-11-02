package pe.edu.cibertec.ProyectoHotelero.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ClienteEmergencyContacts")
public class ClienteEmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emergency_contact_id")
    private Long emergencyContactId;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "address")
    private String address;
    // Definir la relación con la entidad Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Constructores, getters y setters
}

