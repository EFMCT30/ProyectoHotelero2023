package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.entity.Reserva;
import pe.edu.cibertec.ProyectoHotelero.repository.ReservaRepository;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(Reserva reserva) {
        // Agrega lógica para validar y crear una nueva reserva en la base de datos
        // Por ejemplo, puedes verificar la disponibilidad de habitaciones y calcular el precio total.
        return reservaRepository.save(reserva);
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

