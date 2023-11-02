    package pe.edu.cibertec.ProyectoHotelero.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import pe.edu.cibertec.ProyectoHotelero.dto.request.HuespedDTO;
    import pe.edu.cibertec.ProyectoHotelero.entity.Huesped;
    import pe.edu.cibertec.ProyectoHotelero.service.HuespedService;

    import java.util.List;


    @RestController
    @RequestMapping("/huespedes")
    public class HuespedController {

        @Autowired
        private HuespedService huespedService;

        @GetMapping
        public List<Huesped> listarHuespedes() {
            return huespedService.listarHuespedes();
        }

        @PostMapping("/create")
        public ResponseEntity<String> createHuesped(@RequestBody HuespedDTO huespedDTO) {
            ResponseEntity<String> response;

            try {
                ResponseEntity<String> createResponse = huespedService.createHuesped(huespedDTO);
                return createResponse;
            } catch (Exception e) {
                String errorMessage = "Error al crear el huésped: " + e.getMessage();
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }

            return response;
        }
    }
