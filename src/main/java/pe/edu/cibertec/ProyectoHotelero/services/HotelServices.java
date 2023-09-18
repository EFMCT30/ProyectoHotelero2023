package pe.edu.cibertec.ProyectoHotelero.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.repository.HotelRepository;

import java.util.List;
import java.util.Optional;


@Service
public class HotelServices {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getallhotel(){
        return hotelRepository.findAll();
    }
    //@RequestMapping

    public Hotel gethotelbyid(Long hotelId){
        return hotelRepository.findById(hotelId).orElse(null);
    }
}
