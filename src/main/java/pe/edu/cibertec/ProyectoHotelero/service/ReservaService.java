package pe.edu.cibertec.ProyectoHotelero.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;
import pe.edu.cibertec.ProyectoHotelero.entity.UserEntity;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.ReservaRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;
    @Autowired
    private final HabitacionService habitacionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClienteRepository clienteRepository;





    public ResponseEntity<String> crearReserva(ReservaDTO reservaDTO) {
        // Verificar si la habitación está disponible
        Habitacion habitacion = habitacionService.obtenerHabitacionPorId(reservaDTO.getHabitacionId());
        if (habitacion == null || !habitacion.isDisponible()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La habitación no está disponible");
        }
        // Obtener el nombre de usuario del token JWT desde SecurityContextHolder
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        log.info("Username: {}", username);
        log.info("User Optional: {}", userOptional);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
        }
        UserEntity user = userOptional.get();
        log.info("User: {}", user.getCliente());

        // Check if the user has a client associated with it
        if (user.getId() != null) {
            Long clientId = user.getCliente().getClienteId();
            // Obtener el cliente Id autenticado
            Cliente cliente = clienteRepository.findByClienteId(clientId);
           if (cliente == null) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente con ID " + username + " no existe o no está asociado a un usuario");
           }
            // Crear la reserva
            Reserva reserva = new Reserva();
            reserva.setFechaInicio(reservaDTO.getFechaInicio());
            reserva.setFechaFin(reservaDTO.getFechaFin());
            reserva.setEstado("Pendiente"); // O establece el estado deseado
            reserva.setPrecioTotal(reservaDTO.getPrecioTotal());
            reserva.setCliente(cliente);
            reserva.setComentarios(reservaDTO.getComentarios());
            // Cambiar el estado de la habitación a no disponible
            habitacion.setDisponible(false);
            habitacionService.actualizarHabitacion(habitacion);
            // Agregar más validaciones y lógica de negocio aquí.

            reservaRepository.save(reserva);

            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva creada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene un cliente asociado");
        }
    }



    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

}

