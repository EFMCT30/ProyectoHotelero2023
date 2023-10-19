package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.exceptions.HotelNotFoundException;
import pe.edu.cibertec.ProyectoHotelero.repository.HabitacionRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.HotelRepository;

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

    public boolean verificarDisponibilidad(Long habitacionId) {
        Habitacion habitacion = obtenerHabitacionPorId(habitacionId);
        return habitacion != null && habitacion.isDisponible();
    }

    // Otros métodos relacionados con las habitaciones

    public Habitacion createHabitacion(Habitacion habitacion, Long hotelId) {
        // Verificar si el hotel existe
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel == null) {
            // El hotel con el ID proporcionado no existe, por lo que no se puede crear la habitación.
            throw new HotelNotFoundException("El hotel con ID " + hotelId + " no existe.");
        }

        // Asignar el hotel a la habitación
        habitacion.setHotel(hotel);

        // Guardar la habitación en la base de datos
        return habitacionRepository.save(habitacion);
    }

}

