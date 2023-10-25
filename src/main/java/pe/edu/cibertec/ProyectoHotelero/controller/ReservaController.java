package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;
import pe.edu.cibertec.ProyectoHotelero.service.ClienteService;
import pe.edu.cibertec.ProyectoHotelero.service.HabitacionService;
import pe.edu.cibertec.ProyectoHotelero.service.ReservaService;
@RestController
@AllArgsConstructor
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private final ReservaService reservaService;
    @Autowired
    private final HabitacionService habitacionService;
    @Autowired
    private final ClienteService clienteService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearReserva")
    @Transactional
    public ResponseEntity<String> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        String message = String.valueOf( reservaService.crearReserva(reservaDTO));
        return ResponseEntity.ok(message);
    }


}
