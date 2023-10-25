package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.HabitacionDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.exceptions.HotelNotFoundException;
import pe.edu.cibertec.ProyectoHotelero.repository.HabitacionRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.HotelRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    private final HotelRepository hotelRepository;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepository, HotelRepository hotelRepository) {
        this.habitacionRepository = habitacionRepository;
        this.hotelRepository = hotelRepository;
    }

    public Habitacion obtenerHabitacionPorId(Long id) {
        return habitacionRepository.findById(id).orElse(null);
    }
    public List<Habitacion> getAllHabitaciones() {
        return habitacionRepository.findAll();
    }

    public boolean verificarDisponibilidad(Long habitacionId) {
        Habitacion habitacion = obtenerHabitacionPorId(habitacionId);
        return habitacion != null && habitacion.isDisponible();
    }


    public String crearHabitacion(HabitacionDTO habitacionDTO) {
        // Verificar si el hotel existe
        Optional<Hotel> optionalHotel = hotelRepository.findById(habitacionDTO.getHotelId());
        if (optionalHotel.isEmpty()) {
            return "No se pudo crear la habitación. El hotel con ID " + habitacionDTO.getHotelId() + " no existe.";
        }

        Hotel hotel = optionalHotel.get();

        // Crea la habitación a partir de los datos del DTO
        Habitacion habitacion = new Habitacion();
        habitacion.setHotel(hotel);
        habitacion.setNumeroHabitacion(habitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setCapacidad(habitacionDTO.getCapacidad());
        habitacion.setPrecioNoche(habitacionDTO.getPrecioNoche());
        habitacion.setDisponible(true); // Puedes establecerlo según tus necesidades
        habitacion.setFechaUltimaMantenimiento(habitacionDTO.getFechaUltimaMantenimiento());

        // Agrega más validaciones y lógica de negocio aquí.

        habitacion = habitacionRepository.save(habitacion); // Guarda la habitación en la base de datos

        // Agrega la habitación a la lista de habitaciones del hotel
        hotel.getHabitaciones().add(habitacion);
        hotelRepository.save(hotel);

        return "Habitación creada con éxito";
    }


    public void actualizarHabitacion(Habitacion habitacion) {
        habitacionRepository.save(habitacion);
    }
}

