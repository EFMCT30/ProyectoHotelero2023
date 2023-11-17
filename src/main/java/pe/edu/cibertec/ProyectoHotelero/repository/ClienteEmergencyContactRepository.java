package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.ClienteEmergencyContact;

@Repository
public interface ClienteEmergencyContactRepository extends JpaRepository<ClienteEmergencyContact, Long> {

    ClienteEmergencyContact findByCliente(Cliente cliente);

}
