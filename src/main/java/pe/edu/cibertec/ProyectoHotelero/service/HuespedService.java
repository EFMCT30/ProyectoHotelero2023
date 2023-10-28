package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.dto.request.HuespedDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.Huesped;
import pe.edu.cibertec.ProyectoHotelero.repository.HuespedRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Huesped> listarHuespedes() {
        return huespedRepository.findAll();
    }

    public ResponseEntity<String> createHuesped(HuespedDTO huespedDTO) {
        try {
            if (huespedDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("HuespedDTO cannot be null");
            }

            Huesped huesped = new Huesped();
            huesped.setNombre(huespedDTO.getNombre());
            huesped.setApellido(huespedDTO.getApellido());
            huesped.setDocumentoIdentidad(huespedDTO.getDocumentoIdentidad());
            huesped.setTelefono(huespedDTO.getTelefono());
            huesped.setCorreoElectronico(huespedDTO.getCorreoElectronico());

            // Add any additional validation or business logic here.

            huesped = huespedRepository.save(huesped);

            return ResponseEntity.status(HttpStatus.CREATED).body("Huesped creado exitosamente");
        } catch (Exception e) {
            // Handle exceptions, log errors, etc.
            String errorMessage = "Error al crear el huésped: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}




