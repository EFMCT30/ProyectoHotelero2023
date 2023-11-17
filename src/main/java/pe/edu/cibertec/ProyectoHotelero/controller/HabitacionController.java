package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.cibertec.ProyectoHotelero.dto.request.HabitacionDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Habitacion;
import pe.edu.cibertec.ProyectoHotelero.service.HabitacionService;
import pe.edu.cibertec.ProyectoHotelero.util.ImageManager;
import java.io.IOException;
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
    public ResponseEntity<?> crearHabitacion(@RequestBody HabitacionDTO habitacion) {
        Habitacion createdHabitacion = habitacionService.crearHabitacion(habitacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabitacion);
    }

    @PutMapping("/uploadPhoto/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarFoto(@PathVariable("id") Long habitacionId, @RequestPart("file") MultipartFile file) throws Exception {
        Habitacion existingHabitacion = habitacionService.obtenerHabitacionPorId(habitacionId);
        ImageManager imageManagerObject = new ImageManager();
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf('.');
        String extension = filename.substring(index + 1);
        System.out.println("ESTA es la extension: "+extension);
        boolean success = extension.equals("jpg") || extension.equals("jepg")? imageManagerObject.storeFile(file) : false;
        if(success){
            String url = "uploads/images/"+file.getOriginalFilename();
            existingHabitacion.setImageUrl(url);
            System.out.println(url);
            Habitacion updated = habitacionService.actualizarHabitacion(existingHabitacion);
            if(updated != null){
                return ResponseEntity.status(HttpStatus.OK).body(updated);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NO SE PUDO ACTUALIZAR");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabitacion(@PathVariable("id") Long habitacionId) {
        Habitacion habitacion = habitacionService.obtenerHabitacionPorId(habitacionId);
        if (habitacion == null) {
            return ResponseEntity.notFound().build();
        }

        // Use the service to delete the hotel
        habitacionService.eliminarhabitacionid(habitacionId);

        // Return a 204 No Content response indicating success
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{habitacionId}")
    public ResponseEntity<?> actualizarHabitacion(@PathVariable Long habitacionId, @RequestBody HabitacionDTO updatedHabitacionDTO) {
        Habitacion existingHabitacion = habitacionService.obtenerHabitacionPorId(habitacionId);

        if (existingHabitacion == null) {
            return ResponseEntity.notFound().build(); // o puedes lanzar una excepción u otro manejo de error
        }


        existingHabitacion.setNumeroHabitacion(updatedHabitacionDTO.getNumeroHabitacion());
        existingHabitacion.setTipo(updatedHabitacionDTO.getTipo());
        existingHabitacion.setCapacidad(updatedHabitacionDTO.getCapacidad());
        existingHabitacion.setPrecioNoche(updatedHabitacionDTO.getPrecioNoche());
        existingHabitacion.setDisponible(updatedHabitacionDTO.isDisponible());
        existingHabitacion.setFechaUltimaMantenimiento(updatedHabitacionDTO.getFechaUltimaMantenimiento());
        Habitacion updated = habitacionService.actualizarHabitacion(existingHabitacion);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar a habitación");
        }
    }

    @GetMapping("/disponibles")
    public List<Habitacion> getHabitacionesDisponibles() {
        return habitacionService.getHabitacionesDisponibles();
    }

}

