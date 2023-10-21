package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.ReservaDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;
import pe.edu.cibertec.ProyectoHotelero.entity.UserEntity;
import pe.edu.cibertec.ProyectoHotelero.repository.ReservaRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionService habitacionService;
    private final ClienteService clienteService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, HabitacionService habitacionService, ClienteService clienteService) {
        this.reservaRepository = reservaRepository;
        this.habitacionService = habitacionService;
        this.clienteService = clienteService;
    }

    public String crearReserva(ReservaDTO reservaDTO) {
        // Verificar si la habitación está disponible
        if (!habitacionService.verificarDisponibilidad(reservaDTO.getHabitacionId())) {
            return "La habitación no está disponible";
        }

        // Obtener el nombre de usuario del token JWT desde SecurityContextHolder
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByUsername(username); // Suponiendo que tienes un repositorio llamado "userRepository" para los usuarios
        Long userId = user.get().getId();

        // Obtener el cliente autenticado
        Cliente cliente = clienteService.obtenerClienteUserId(userId);

        // Verificar si el cliente existe y si está asociado a un usuario
        if (cliente == null ) {
            return "El cliente no existe o no está asociado a un usuario";
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(reservaDTO.getFechaInicio());
        reserva.setFechaFin(reservaDTO.getFechaFin());
        reserva.setEstado("Pendiente"); // O establece el estado deseado
        reserva.setPrecioTotal(reservaDTO.getPrecioTotal());
        reserva.setCliente(cliente);
        reserva.setComentarios(reservaDTO.getComentarios());

        // Agregar más validaciones y lógica de negocio aquí.

        reservaRepository.save(reserva);

        return "Reserva creada con éxito";
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

    // Otros métodos relacionados con las reservas
}

