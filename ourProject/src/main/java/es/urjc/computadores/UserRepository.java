package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long>{
	// consulta por nombres de usuario
	List<User> findByNickname(String nickname);
	
	// consulta por enmail
	List<User> findByEmail(String email);
	
}
