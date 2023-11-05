package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByClienteId(Long userId);
    Cliente findByUserId(Long userId);


}
