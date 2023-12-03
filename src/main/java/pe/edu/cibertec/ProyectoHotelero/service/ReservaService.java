package pe.edu.cibertec.ProyectoHotelero.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;
import pe.edu.cibertec.ProyectoHotelero.dto.response.ReservaResponseDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;

import pe.edu.cibertec.ProyectoHotelero.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j

@Service
@AllArgsConstructor
public class ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;
    @Autowired
    private final HabitacionService habitacionService;

    @Autowired
    private ClienteService clienteService;


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
        Cliente cliente = clienteService.getClienteFromToken(request);
        if (cliente == null) {
            response = new ReservaResponseDTO(null, "No se pudo obtener  holas el cliente desde el token");
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

    private final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    public ResponseEntity<ReservaResponseDTO> actualizarReserva(Long reservaId, ReservaDTO reservaDTO, HttpServletRequest request) {
        ReservaResponseDTO response;

        // Obtener el cliente desde el token
        Cliente cliente = clienteService.getClienteFromToken(request);
        if (cliente == null) {
            logger.error("No se pudo obtener el cliente desde el token");
            response = new ReservaResponseDTO(null, "No se pudo obtener el cliente desde el token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Verificar si la reserva existe
        Optional<Reserva> optionalReserva = reservaRepository.findById(reservaId);
        if (optionalReserva.isEmpty()) {
            logger.error("La reserva no existe");
            response = new ReservaResponseDTO(null, "La reserva no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Obtener la reserva existente
        Reserva reserva = optionalReserva.get();

        // Verificar si el cliente que está intentando actualizar la reserva es el propietario
        if (!reserva.getCliente().equals(cliente)) {
            logger.warn("Intento de actualización de reserva por un cliente no autorizado");
            response = new ReservaResponseDTO(null, "No tienes permisos para actualizar esta reserva");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Verificar si la habitación está disponible (puedes omitir esto si no necesitas verificar la disponibilidad)
        Habitacion habitacion = habitacionService.obtenerHabitacionPorId(reservaDTO.getHabitacionId());
        if (habitacion == null || !habitacion.isDisponible()) {
            logger.error("La habitación no está disponible");
            response = new ReservaResponseDTO(null, "La habitación no está disponible");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Actualizar los campos de la reserva
        reserva.setFechaInicio(reservaDTO.getFechaInicio());
        reserva.setFechaFin(reservaDTO.getFechaFin());
        reserva.setPrecioTotal(reservaDTO.getPrecioTotal());
        reserva.setComentarios(reservaDTO.getComentarios());

        // Actualizar más campos según sea necesario

        // Guardar la reserva actualizada en la base de datos
        reservaRepository.save(reserva);

        // Construir la respuesta con el objeto ReservaDTO y un mensaje
        response = new ReservaResponseDTO(reservaDTO, "Reserva actualizada con éxito");
        logger.info("Reserva actualizada con éxito por el cliente: {}", cliente.getClienteId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }


    //    no se elimina una reserva
    public void eliminarReserva (Long id) {
        reservaRepository.deleteById(id);
    }

}