package pe.edu.cibertec.ProyectoHotelero.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.ProyectoHotelero.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);}
