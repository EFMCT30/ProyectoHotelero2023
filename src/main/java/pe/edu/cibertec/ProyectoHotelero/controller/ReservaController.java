package pe.edu.cibertec.ProyectoHotelero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;
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
        // El token JWT se verifica en el filtro JwtAuthorizationFilter, por lo que no es necesario aquí.

        // Verificar si la habitación está disponible
        if (!habitacionService.verificarDisponibilidad(reservaDTO.getHabitacionId())) {
            return new ResponseEntity<>("La habitación no está disponible", HttpStatus.BAD_REQUEST);
        }

        // Obtener el nombre de usuario del token JWT desde SecurityContextHolder
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Obtener el cliente autenticado
        Cliente cliente = clienteService.obtenerClientePorNombre(username);

        // Verificar si el cliente existe
        if (cliente == null) {
            return new ResponseEntity<>("El cliente no existe", HttpStatus.BAD_REQUEST);
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(reservaDTO.getFechaInicio());
        reserva.setFechaFin(reservaDTO.getFechaFin());
        reserva.setEstado("Pendiente"); // O establece el estado deseado
        reserva.setPrecioTotal(reservaDTO.getPrecioTotal());
        reserva.setCliente(cliente);
        reserva.setComentarios(reservaDTO.getComentarios());

        // Puedes agregar más validaciones y lógica de negocio aquí.

        reservaService.crearReserva(reserva);

        return new ResponseEntity<>("Reserva creada con éxito", HttpStatus.CREATED);
    }

}
