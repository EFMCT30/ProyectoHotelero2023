package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteEmergencyContactDTO;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteUpdateDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteEmergencyContactService;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteService;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteEmergencyContactService clienteEmergencyContactService;

    @GetMapping("/listarClientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientes);
        } else {
            return ResponseEntity.ok(clientes);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cliente> getClienteByUserId(@PathVariable Long userId) {
        Cliente cliente = clienteService.findByUserId(userId);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    @PostMapping("/createClientEmergencyContact/{clienteId}")
    public ResponseEntity<?> createClientEmergencyContact(
            @PathVariable Long clienteId,
            @RequestBody ClienteEmergencyContactDTO emergencyContactDTO) {
        ClienteEmergencyContactDTO createdContact = clienteEmergencyContactService.crearClienteEmergencyContact(clienteId, emergencyContactDTO);

        if (createdContact != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
    }


    @PutMapping("/updateClientInfo/{userId}")
    public ResponseEntity<?> updateClientInfo(@PathVariable Long userId, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        ResponseEntity<?> response = clienteService.updateClientInfo(userId, clienteUpdateDTO);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Información del cliente actualizada con éxito");
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PutMapping("/updateClientInfo")
    public ResponseEntity<?> updateClientInfo(@RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        HttpServletRequest request = null;
        Cliente cliente = clienteService.getClienteFromToken(request); // Obtiene el cliente desde el token

        if (cliente != null) {
            ResponseEntity<?> response = clienteService.updateClientInfo(cliente.getClienteId(), clienteUpdateDTO);

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response);
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo obtener la información del cliente desde el token");
        }
    }

    @GetMapping("/userInfo")
    public ResponseEntity<Cliente> getUserInfo(HttpServletRequest request) {
        // Obtener el usuario actual basado en el token JWT
        Cliente cliente = clienteService.getClienteFromToken(request);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


}
