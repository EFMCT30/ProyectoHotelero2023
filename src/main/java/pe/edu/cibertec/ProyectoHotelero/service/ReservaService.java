package pe.edu.cibertec.ProyectoHotelero.service;

import jakarta.servlet.http.HttpServletRequest;
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
import pe.edu.cibertec.ProyectoHotelero.dto.response.ReservaResponseDTO;
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


    public List<Reserva> listarTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public ResponseEntity<ReservaResponseDTO> crearReserva(ReservaDTO reservaDTO, HttpServletRequest request) {
        ReservaResponseDTO response;

        // Verificar si la habitación está disponible
        Habitacion habitacion = habitacionService.obtenerHabitacionPorId(reservaDTO.getHabitacionId());
        if (habitacion == null || !habitacion.isDisponible()) {
            response = new ReservaResponseDTO(null, "La habitación no está disponible");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Obtener el cliente desde el token
        Cliente cliente = getClienteFromToken(request);
        if (cliente == null) {
            response = new ReservaResponseDTO(null, "No se pudo obtener el cliente desde el token");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(reservaDTO.getFechaInicio());
        reserva.setFechaFin(reservaDTO.getFechaFin());
        reserva.setEstado("Pendiente"); // O establecer el estado deseado
        reserva.setPrecioTotal(reservaDTO.getPrecioTotal());
        reserva.setCliente(cliente);
        reserva.setComentarios(reservaDTO.getComentarios());

        // Cambiar el estado de la habitación a no disponible
        habitacion.setDisponible(false);
        habitacionService.actualizarHabitacion(habitacion);

        // Agregar más validaciones y lógica de negocio aquí.

        // Guardar la reserva en la base de datos
        reservaRepository.save(reserva);

        // Construir la respuesta con el objeto ReservaDTO y un mensaje
        response = new ReservaResponseDTO(reservaDTO, "Reserva creada con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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



    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }


//    no se elimina una reserva
    public void eliminarReserva (Long id) {
        reservaRepository.deleteById(id);
    }

}

