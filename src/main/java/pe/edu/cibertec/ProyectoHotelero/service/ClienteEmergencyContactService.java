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


    public ClienteEmergencyContactDTO crearOActualizarClienteEmergencyContact(Long clienteId, ClienteEmergencyContactDTO emergencyContactDTO) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente != null) {
            // Verificar si el cliente ya tiene un contacto de emergencia
            ClienteEmergencyContact existingEmergencyContact = clienteEmergencyContactRepository.findByCliente(cliente);

            if (existingEmergencyContact != null) {
                // Actualizar el contacto de emergencia existente con la nueva información
                existingEmergencyContact.setContactName(emergencyContactDTO.getContactName());
                existingEmergencyContact.setContactPhone(emergencyContactDTO.getContactPhone());
                existingEmergencyContact.setContactEmail(emergencyContactDTO.getContactEmail());
                existingEmergencyContact.setRelationship(emergencyContactDTO.getRelationship());
                existingEmergencyContact.setAddress(emergencyContactDTO.getAddress());

                ClienteEmergencyContact updatedEmergencyContact = clienteEmergencyContactRepository.save(existingEmergencyContact);

                // Convertir el objeto updatedEmergencyContact a ClienteEmergencyContactDTO si es necesario
                // y devolverlo
                return convertToDTO(updatedEmergencyContact);
            } else {
                // Si no hay un contacto de emergencia existente, crear uno nuevo
                ClienteEmergencyContact newEmergencyContact = new ClienteEmergencyContact();
                newEmergencyContact.setContactName(emergencyContactDTO.getContactName());
                newEmergencyContact.setContactPhone(emergencyContactDTO.getContactPhone());
                newEmergencyContact.setContactEmail(emergencyContactDTO.getContactEmail());
                newEmergencyContact.setRelationship(emergencyContactDTO.getRelationship());
                newEmergencyContact.setAddress(emergencyContactDTO.getAddress());
                newEmergencyContact.setCliente(cliente);

                ClienteEmergencyContact savedEmergencyContact = clienteEmergencyContactRepository.save(newEmergencyContact);

                // Convertir el objeto savedEmergencyContact a ClienteEmergencyContactDTO si es necesario
                // y devolverlo
                return convertToDTO(savedEmergencyContact);
            }
        } else {
            // Cliente no encontrado, devolver un valor especial o null
            return null;
        }
    }


//    public ClienteEmergencyContactDTO crearClienteEmergencyContact(Long clienteId, ClienteEmergencyContactDTO emergencyContactDTO) {
//        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
//
//        if (cliente != null) {
//            ClienteEmergencyContact emergencyContact = new ClienteEmergencyContact();
//            emergencyContact.setContactName(emergencyContactDTO.getContactName());
//            emergencyContact.setContactPhone(emergencyContactDTO.getContactPhone());
//            emergencyContact.setContactEmail(emergencyContactDTO.getContactEmail());
//            emergencyContact.setRelationship(emergencyContactDTO.getRelationship());
//            emergencyContact.setAddress(emergencyContactDTO.getAddress());
//            emergencyContact.setCliente(cliente);
//
//            ClienteEmergencyContact savedEmergencyContact = clienteEmergencyContactRepository.save(emergencyContact);
//
//            // Convertir el objeto savedEmergencyContact a ClienteEmergencyContactDTO si es necesario
//            // y devolverlo
//            return convertToDTO(savedEmergencyContact);
//        } else {
//            // Cliente no encontrado, devolver un valor especial o null
//            return null;
//        }
//    }
//


    public ClienteEmergencyContact buscarPorClienteId(Cliente cliente) {
        // Hacer uso del método de búsqueda en la interfaz
        return clienteEmergencyContactRepository.findByCliente(cliente);
    }
}
