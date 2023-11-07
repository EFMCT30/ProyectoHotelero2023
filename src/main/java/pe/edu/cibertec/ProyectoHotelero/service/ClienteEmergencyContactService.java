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

    public ClienteEmergencyContactDTO crearClienteEmergencyContact(Long clienteId, ClienteEmergencyContactDTO emergencyContactDTO) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente != null) {
            ClienteEmergencyContact emergencyContact = new ClienteEmergencyContact();
            emergencyContact.setContactName(emergencyContactDTO.getContactName());
            emergencyContact.setContactPhone(emergencyContactDTO.getContactPhone());
            emergencyContact.setContactEmail(emergencyContactDTO.getContactEmail());
            emergencyContact.setRelationship(emergencyContactDTO.getRelationship());
            emergencyContact.setAddress(emergencyContactDTO.getAddress());
            emergencyContact.setCliente(cliente);

            ClienteEmergencyContact savedEmergencyContact = clienteEmergencyContactRepository.save(emergencyContact);

            // Convertir el objeto savedEmergencyContact a ClienteEmergencyContactDTO si es necesario
            // y devolverlo
            return convertToDTO(savedEmergencyContact);
        } else {
            // Cliente no encontrado, devolver un valor especial o null
            return null;
        }
    }

    public ClienteEmergencyContactDTO convertToDTO(ClienteEmergencyContact emergencyContact) {
        ClienteEmergencyContactDTO dto = new ClienteEmergencyContactDTO();
        dto.setContactName(emergencyContact.getContactName());
        dto.setContactPhone(emergencyContact.getContactPhone());
        dto.setContactEmail(emergencyContact.getContactEmail());
        dto.setRelationship(emergencyContact.getRelationship());
        dto.setAddress(emergencyContact.getAddress());
        // Otros campos que puedas tener en tu DTO

        return dto;
    }


    public ClienteEmergencyContact buscarPorClienteId(Cliente cliente) {
        // Hacer uso del método de búsqueda en la interfaz
        return clienteEmergencyContactRepository.findByCliente(cliente);
    }
}
