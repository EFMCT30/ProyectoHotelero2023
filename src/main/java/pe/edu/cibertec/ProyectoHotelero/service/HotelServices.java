package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import pe.edu.cibertec.ProyectoHotelero.entity.Hotel;
import pe.edu.cibertec.ProyectoHotelero.repository.HotelRepository;
import org.springframework.stereotype.Service;


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
    }public Hotel saveOrUpdateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    public Hotel createHotelWithBuilder(String nombre, String direccion, String telefono, int estrellas,
                                        String descripcion, Date fechaConstruccion, String categoria, boolean disponible) {
        // Use the @Builder annotation to create a new Hotel object
        Hotel newHotel = Hotel.builder()
                .nombre(nombre)
                .direccion(direccion)
                .telefono(telefono)
                .estrellas(estrellas)
                .descripcion(descripcion)
                .fechaConstruccion(fechaConstruccion)
                .categoria(categoria)
                .disponible(disponible)
                .build();
        // Save the newly created hotel entity
        return hotelRepository.save(newHotel);
    }

    //actualizar
    public Hotel updateHotel(Long hotelId, Hotel updatedHotel) {
        Hotel existingHotel = gethotelbyid(hotelId);

        if (existingHotel == null) {
            return null;
        }

        // Update the existing hotel with the new details
        existingHotel.setNombre(updatedHotel.getNombre());
        existingHotel.setDireccion(updatedHotel.getDireccion());
        existingHotel.setTelefono(updatedHotel.getTelefono());
        existingHotel.setEstrellas(updatedHotel.getEstrellas());
        existingHotel.setDescripcion(updatedHotel.getDescripcion());
        existingHotel.setFechaConstruccion(updatedHotel.getFechaConstruccion());
        existingHotel.setCategoria(updatedHotel.getCategoria());
        existingHotel.setDisponible(updatedHotel.isDisponible());

        // Save the updated hotel
        return saveOrUpdateHotel(existingHotel);
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
    //ARREGLAR SERVICE

}




