package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Long> {

    @Query("SELECT h FROM Habitacion h WHERE h.disponible = true")
    List<Habitacion> findByDisponibleIsTrue();
}
