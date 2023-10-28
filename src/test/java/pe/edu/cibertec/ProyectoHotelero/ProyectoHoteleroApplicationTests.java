package pe.edu.cibertec.ProyectoHotelero;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootTest
class ProyectoHoteleroApplicationTests {

	@GetMapping("/accessAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public String accessAdmin(){
		return "Hola, has accedito con rol de ADMIN";
	}

	@GetMapping("/accessUser")
	@PreAuthorize("hasRole('USER')")
	public String accessUser(){
		return "Hola, has accedito con rol de USER";
	}

	@GetMapping("/accessInvited")
	@PreAuthorize("hasRole('INVITED')")
	public String accessInvited(){
		return "Hola, has accedito con rol de INVITED";
	}


}
