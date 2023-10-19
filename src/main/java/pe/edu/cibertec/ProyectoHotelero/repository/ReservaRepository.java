package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
