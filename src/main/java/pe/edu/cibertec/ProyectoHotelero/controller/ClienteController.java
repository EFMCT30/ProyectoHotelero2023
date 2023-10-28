package pe.edu.cibertec.ProyectoHotelero.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteEmergencyContactDTO;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteUpdateDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteEmergencyContactService;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteService;

import java.util.List;

@RestController
@AllArgsConstructor
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

    @PostMapping("/createClientEmergencyContact/{clienteId}")
    public ResponseEntity<?> createClientEmergencyContact(
            @PathVariable Long clienteId,
            @RequestBody ClienteEmergencyContactDTO emergencyContactDTO) {
        clienteEmergencyContactService.crearClienteEmergencyContact(clienteId, emergencyContactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contacto de emergencia creado con éxito");
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


}
