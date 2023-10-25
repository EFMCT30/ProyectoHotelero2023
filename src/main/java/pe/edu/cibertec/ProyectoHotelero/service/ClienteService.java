package pe.edu.cibertec.ProyectoHotelero.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteUpdateDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;


    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClienteUserId(Long id) {return clienteRepository.findByClienteId(id);}

    public ResponseEntity<?> updateClientInfo(Long userId, ClienteUpdateDTO clienteUpdateDTO) {
        Cliente cliente = clienteRepository.findByClienteId(userId);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        cliente.setActivo(clienteUpdateDTO.isActivo());
        cliente.setNombre(clienteUpdateDTO.getNombre());
        cliente.setTelefono(clienteUpdateDTO.getTelefono());
        cliente.setDireccion(clienteUpdateDTO.getDireccion());
        cliente.setPreferencias(clienteUpdateDTO.getPreferencias());

        clienteRepository.save(cliente);

        return ResponseEntity.ok("Información del cliente actualizada con éxito");
    }


}

