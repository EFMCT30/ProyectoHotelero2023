package pe.edu.cibertec.ProyectoHotelero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.exceptions.HotelNotFoundException;
import pe.edu.cibertec.ProyectoHotelero.service.HabitacionService;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createHabitacion(@RequestBody Habitacion habitacion, @RequestParam Long hotelId) {
        try {
            if (habitacion == null) {
                return ResponseEntity.badRequest().body("La solicitud de creación de habitación es incorrecta.");
            }

            // Use the service to create the habitacion
            Habitacion createdHabitacion = habitacionService.createHabitacion(habitacion, hotelId);

            if (createdHabitacion != null) {
                // Si la habitación se creó exitosamente, devuelve una respuesta con la habitación creada y el código 201 Created.
                return ResponseEntity.status(HttpStatus.CREATED).body(createdHabitacion);
            } else {
                // Si no se pudo crear la habitación, devuelve una respuesta de error con el código 500 Internal Server Error u otro código de error adecuado.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear la habitación.");
            }
        } catch (HotelNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la habitación. El hotel con ID " + hotelId + " no existe.");
        } catch (Exception e) {
            // En caso de excepción, devuelve una respuesta de error con el código 500 Internal Server Error y el mensaje de error.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la habitación: " + e.getMessage());
        }
    }


}

