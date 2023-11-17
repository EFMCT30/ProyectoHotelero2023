package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteEmergencyContactDTO;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ClienteUpdateDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.ClienteEmergencyContact;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteEmergencyContactService;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteService;

import java.io.Console;
import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/cliente")
@Slf4j
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




//    @PostMapping("/createClientEmergencyContact/{clienteId}")
//    public ResponseEntity<?> createClientEmergencyContact(
//            @PathVariable Long clienteId,
//            @RequestBody ClienteEmergencyContactDTO emergencyContactDTO) {
//        ClienteEmergencyContactDTO createdContact = clienteEmergencyContactService.crearClienteEmergencyContact(clienteId, emergencyContactDTO);
//
//        if (createdContact != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
//        }
//    }


//    @PutMapping("/updateClientInfo/{userId}")
//    public ResponseEntity<?> updateClientInfo(@PathVariable Long userId, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
//        ResponseEntity<?> response = clienteService.updateClientInfo(userId, clienteUpdateDTO);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return ResponseEntity.ok("Información del cliente actualizada con éxito");
//        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
//        }
//    }
//
//    @PostMapping("/createClientEmergencyContact")
//    public ResponseEntity<?> createClientEmergencyContact(
//            @RequestBody ClienteEmergencyContactDTO emergencyContactDTO,
//            HttpServletRequest request) {
//        // Obtener el cliente desde el token
//        Cliente cliente = clienteService.getClienteFromToken(request);
//
//        if (cliente != null) {
//            ClienteEmergencyContactDTO createdContact = clienteEmergencyContactService.crearClienteEmergencyContact(cliente.getClienteId(), emergencyContactDTO);
//
//            if (createdContact != null) {
//                return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el contacto de emergencia");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo obtener la información del cliente desde el token");
//        }
//    }

    @PutMapping("/updateEmergencyContact")
    public ResponseEntity<?> updateOrCreateClientEmergencyContact(
            @RequestBody ClienteEmergencyContactDTO emergencyContactDTO,
            HttpServletRequest request) {
        // Obtener el cliente desde el token
        Cliente cliente = clienteService.getClienteFromToken(request);

        if (cliente != null) {
            ClienteEmergencyContactDTO updatedOrCreatedContact = clienteEmergencyContactService.crearOActualizarClienteEmergencyContact(cliente.getClienteId(), emergencyContactDTO);

            if (updatedOrCreatedContact != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedOrCreatedContact);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar o crear el contacto de emergencia");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo obtener la información del cliente desde el token");
        }
    }

    @PutMapping("/updateClientInfo")
    public ResponseEntity<?> updateClientInfo(@RequestBody ClienteUpdateDTO clienteUpdateDTO,HttpServletRequest request) {
        Cliente cliente = clienteService.getClienteFromToken(request); // Obtiene el cliente desde el token

        if (cliente != null) {
            ResponseEntity<?> response = clienteService.updateClientInfo(request,clienteUpdateDTO);
            log.debug(String.valueOf(response));
//            if (response.getStatusCode() == HttpStatus.OK) {
              return ResponseEntity.ok(response);
//            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
//            }
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


    @GetMapping("/userInfoContact")
    public ResponseEntity<ClienteEmergencyContact> getUserInfoContact(HttpServletRequest request) {
        // Obtener el cliente basado en el token JWT
        Cliente cliente = clienteService.getClienteFromToken(request);

        if (cliente != null) {
            // Buscar la información de contacto de emergencia del cliente obtenido del token
            ClienteEmergencyContact clienteEmergencyContact = clienteEmergencyContactService.buscarPorClienteId(cliente);

            if (clienteEmergencyContact != null) {
                return ResponseEntity.ok(clienteEmergencyContact);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }




}
