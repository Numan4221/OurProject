package es.urjc.computadores;


import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long>{
	
	/***
	 * Devuelve el usuario con dicho nombre
	 * @param nickname
	 * @return
	 */
	User findFirstByNickname(String nickname);
	
	/***
	 * Devuelve el usuario con dicho email
	 * @param email
	 * @return
	 */
	User findFirstByEmail(String email);
	
}
