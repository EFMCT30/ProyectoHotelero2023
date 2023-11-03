package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByNombre(String nombre);

    @Query("SELECT h FROM Hotel h WHERE h.nombre LIKE %:searchTerm%")
    List<Hotel> findByNombreContainingCustom(@Param("searchTerm") String searchTerm);
}
