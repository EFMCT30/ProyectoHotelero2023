package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.dto.request.HabitacionDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.service.HabitacionService;

import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public ResponseEntity<List<Habitacion>> listHabitaciones() {
        List<Habitacion> habitaciones = habitacionService.getAllHabitaciones();
        return ResponseEntity.ok(habitaciones);
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> crearHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        String message = habitacionService.crearHabitacion(habitacionDTO);
        return ResponseEntity.ok(message);
    }




}

