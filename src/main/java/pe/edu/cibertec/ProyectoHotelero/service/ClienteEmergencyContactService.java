package pe.edu.cibertec.ProyectoHotelero.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteEmergencyContactDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.ClienteEmergencyContact;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteEmergencyContactRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;

@Service
@AllArgsConstructor
public class ClienteEmergencyContactService {
    @Autowired
    private ClienteEmergencyContactRepository clienteEmergencyContactRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public void crearClienteEmergencyContact(Long clienteId, ClienteEmergencyContactDTO emergencyContactDTO) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente != null) {
            ClienteEmergencyContact emergencyContact = new ClienteEmergencyContact();
            emergencyContact.setContactName(emergencyContactDTO.getContactName());
            emergencyContact.setContactPhone(emergencyContactDTO.getContactPhone());
            emergencyContact.setContactEmail(emergencyContactDTO.getContactEmail());
            emergencyContact.setRelationship(emergencyContactDTO.getRelationship());
            emergencyContact.setAddress(emergencyContactDTO.getAddress());
            emergencyContact.setCliente(cliente);

            clienteEmergencyContactRepository.save(emergencyContact);
        } else {
            // Manejo de errores o lanzar una excepción si el cliente no se encuentra
        }
    }
}
