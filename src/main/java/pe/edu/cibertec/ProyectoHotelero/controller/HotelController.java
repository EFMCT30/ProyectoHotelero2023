package pe.edu.cibertec.ProyectoHotelero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.service.HotelServices;

import java.util.List;

@RestController
@RequestMapping("/hoteles")
public class HotelController {
    @Autowired
    private HotelServices hotelServices;

    @GetMapping
    public ResponseEntity<List<Hotel>> Listhotel(){
        List<Hotel> hotels = hotelServices.getallhotel();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> Listhotelbyid(@PathVariable("id")Long hotelId ){
        Hotel hotels = hotelServices.gethotelbyid(hotelId);
        if (hotels == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        try {
            if (hotel == null) {
                return ResponseEntity.badRequest().build();
            }

            // Use the service to save the hotel
            Hotel createdHotel = hotelServices.saveOrUpdateHotel(hotel);

            if (createdHotel != null) {
                // Si el hotel se creó exitosamente, devuelve una respuesta con el hotel creado y el código 201 Created.
                return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
            } else {
                // Si no se pudo crear el hotel, devuelve una respuesta de error con el código 500 Internal Server Error u otro código de error adecuado.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el hotel.");
            }
        } catch (Exception e) {
            // En caso de excepción, devuelve una respuesta de error con el código 500 Internal Server Error y el mensaje de error.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el hotel: " + e.getMessage());
        }
    }


    // Delete a hotel by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long hotelId) {
        Hotel hotel = hotelServices.gethotelbyid(hotelId);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }

        // Use the service to delete the hotel
        hotelServices.deleteHotelById(hotelId);

        // Return a 204 No Content response indicating success
        return ResponseEntity.noContent().build();
    }
}
