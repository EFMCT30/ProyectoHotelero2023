package pe.edu.cibertec.ProyectoHotelero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.service.HotelServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hoteles")
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
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        if (hotel == null) {
            return ResponseEntity.badRequest().build();
        }
        // Use the service to save the hotel
        Hotel createdHotel = hotelServices.saveOrUpdateHotel(hotel);
        // Return the created hotel with a 201 Created status
        return ResponseEntity.status(201).body(createdHotel);
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
