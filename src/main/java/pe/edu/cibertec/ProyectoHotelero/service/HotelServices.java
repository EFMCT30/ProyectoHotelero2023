package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.repository.HotelRepository;


import java.sql.Date;
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
    public Hotel saveOrUpdateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    public Hotel createHotelWithBuilder(String nombre, String direccion, String telefono, int estrellas,
                                        String descripcion, Date fechaConstruccion, String categoria) {
        // Use the @Builder annotation to create a new Hotel object
        Hotel newHotel = Hotel.builder()
                .nombre(nombre)
                .direccion(direccion)
                .telefono(telefono)
                .estrellas(estrellas)
                .descripcion(descripcion)
                .fechaConstruccion(fechaConstruccion)
                .categoria(categoria)
                .build();
        // Save the newly created hotel entity
        return hotelRepository.save(newHotel);
    }
    // Method to delete a hotel by ID
    public void deleteHotelById(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    public List<Hotel> buscarHotelesPorNombre(String nombre) {
        return hotelRepository.findByNombre(nombre);
    }

    public List<Hotel> buscarHotelesPorIniciales(String iniciales) {
        return hotelRepository.findByNombreContainingCustom(iniciales);
    }

}




