package pe.edu.cibertec.ProyectoHotelero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.services.HotelServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hoteles")
public class HotelController {

    @Autowired
    private HotelServices hotelServices;

    @GetMapping
    public ResponseEntity<List<Hotel>> Listhotel(){
       List<Hotel> hotels = hotelServices.getallhotel();
       return ResponseEntity.ok(hotels);
   }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> Listhotelbyid(@PathVariable("hotelId")Long hotelId ){
        Hotel hotels = hotelServices.gethotelbyid(hotelId);
        if (hotels == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotels);
    }




}
