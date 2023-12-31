package pe.edu.cibertec.ProyectoHotelero.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteUpdateDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;


    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findByUserId(Long userId) {
        return clienteRepository.findByUserId(userId);
    }

    public ResponseEntity<?> updateClientInfo(HttpServletRequest request, ClienteUpdateDTO clienteUpdateDTO) {
        Cliente cliente = getClienteFromToken(request);
        log.error(String.valueOf(cliente));
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        cliente.setActivo(clienteUpdateDTO.isActivo());
        cliente.setNombre(clienteUpdateDTO.getNombre());
        cliente.setTelefono(clienteUpdateDTO.getTelefono());
        cliente.setDireccion(clienteUpdateDTO.getDireccion());
        cliente.setPreferencias(clienteUpdateDTO.getPreferencias());
        cliente.setFechaRegistro(clienteUpdateDTO.getFechaRegistro());

        clienteRepository.save(cliente);

        return ResponseEntity.ok(cliente);
    }

    public Cliente getClienteFromToken(HttpServletRequest request) {
        // Obten el nombre de usuario (user_id) del token JWT actual
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca al cliente en la base de datos por el nombre de usuario (user_id)
        Cliente cliente = clienteRepository.findByUserUsername(username);

        // Si se encuentra el cliente, lo retornas; de lo contrario, puedes manejar el error como desees
        if (cliente != null) {
            return cliente;
        } else {
            return null;
        }
    }


}

