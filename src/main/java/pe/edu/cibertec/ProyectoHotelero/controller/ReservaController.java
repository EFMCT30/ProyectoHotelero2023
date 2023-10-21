package pe.edu.cibertec.ProyectoHotelero.controller;

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
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final HabitacionService habitacionService;
    private final ClienteService clienteService;

    @Autowired
    public ReservaController(ReservaService reservaService, HabitacionService habitacionService, ClienteService clienteService) {
        this.reservaService = reservaService;
        this.habitacionService = habitacionService;
        this.clienteService = clienteService;
    }

    @PostMapping("/crearReserva")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        String message = reservaService.crearReserva(reservaDTO);
        return ResponseEntity.ok(message);
    }

}
