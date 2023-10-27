package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.ProyectoHotelero.entity.Huesped;

public interface HuespedRepository  extends JpaRepository<Huesped,Long> {
}
