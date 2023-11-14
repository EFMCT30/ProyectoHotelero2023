package pe.edu.cibertec.ProyectoHotelero.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ProyectoHotelero.dto.request.CreateUserDTO;
import pe.edu.cibertec.ProyectoHotelero.entity.*;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteEmergencyContactRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;
import pe.edu.cibertec.ProyectoHotelero.repository.UserRepository;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteEmergencyContactRepository clienteEmergencyContactRepository;

    @GetMapping("/index")
    public String hello(){
        return "Hello World Not Secured";
    }

    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hello World Secured";
    }

//    @Transactional
//    @PostMapping("/createUser")
//    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
//
//        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
//                .map(role -> RoleEntity.builder()
//                        .name(ERole.valueOf(role))
//                        .build())
//                .collect(Collectors.toSet());
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(createUserDTO.getUsername())
//                .password(passwordEncoder.encode(createUserDTO.getPassword()))
//                .email(createUserDTO.getEmail())
//                .roles(roles)
//                .build();
//
//        userRepository.save(userEntity);
//
//        // Crear un nuevo Cliente
//        Cliente cliente = new Cliente();
//        cliente.setEmail(userEntity.getEmail()); // Establecer el email del cliente como el email del usuario
//        cliente.setFechaRegistro(new Date()); // Establecer la fecha de registro
//        // Asignar el usuario al cliente
//        cliente.setUser(userEntity);
//
//        // Guardar el cliente en la base de datos
//        clienteRepository.save(cliente);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
//    }

    @Transactional
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        // Crear un nuevo Cliente
        Cliente cliente = new Cliente();
        cliente.setEmail(userEntity.getEmail()); // Establecer el email del cliente como el email del usuario
        cliente.setFechaRegistro(new Date()); // Establecer la fecha de registro
        // Asignar el usuario al cliente
        cliente.setUser(userEntity);

        // Guardar el cliente en la base de datos
        clienteRepository.save(cliente);

        // Crear automáticamente un contacto de emergencia para el cliente
        ClienteEmergencyContact emergencyContact = new ClienteEmergencyContact();
        emergencyContact.setContactName("");
        emergencyContact.setCliente(cliente);

        // Guardar el contacto de emergencia en la base de datos
        clienteEmergencyContactRepository.save(emergencyContact);

        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id".concat(id);
    }
}
