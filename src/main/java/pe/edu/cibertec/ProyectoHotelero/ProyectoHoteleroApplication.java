package pe.edu.cibertec.ProyectoHotelero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.cibertec.ProyectoHotelero.entity.ERole;
import pe.edu.cibertec.ProyectoHotelero.entity.RoleEntity;
import pe.edu.cibertec.ProyectoHotelero.entity.UserEntity;
import pe.edu.cibertec.ProyectoHotelero.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
public class ProyectoHoteleroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoHoteleroApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			if (userRepository.count() == 0) {
				UserEntity userEntity = UserEntity.builder()
						.email("cdiazfarfan@gmail.com")
						.username("cesar")
						.password(passwordEncoder.encode("1234"))
						.roles(Set.of(RoleEntity.builder()
								.name(ERole.valueOf(ERole.ADMIN.name()))
								.build()))
						.build();
				UserEntity userEntity2 = UserEntity.builder()
						.email("andrea@gmail.com")
						.username("andrea")
						.password(passwordEncoder.encode("1234"))
						.roles(Set.of(RoleEntity.builder()
								.name(ERole.valueOf(ERole.USER.name()))
								.build()))
						.build();
				UserEntity userEntity3 = UserEntity.builder()
						.email("pamela@gmail.com")
						.username("pamela")
						.password(passwordEncoder.encode("1234"))
						.roles(Set.of(RoleEntity.builder()
								.name(ERole.valueOf(ERole.INVITED.name()))
								.build()))
						.build();
				userRepository.save(userEntity);
				userRepository.save(userEntity2);
				userRepository.save(userEntity3);
			}
		};
	}
}
