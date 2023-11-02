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
@Table(name = "facturaciones")
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facturacion_id")
    private Long facturacionId;

    @Column(name = "fecha_facturacion")
    private Date fechaFacturacion;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "facturacion_reserva",
            joinColumns = @JoinColumn(name = "facturacion_id"),
            inverseJoinColumns = @JoinColumn(name = "reserva_id")
    )
    private List<Reserva> reservas;
}

