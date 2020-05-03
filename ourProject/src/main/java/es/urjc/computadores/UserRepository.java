package es.urjc.computadores;


import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


@CacheConfig(cacheNames="ourProject")
public interface UserRepository extends CrudRepository<User,Long>{
	
	/***
	 * Devuelve el usuario con dicho nombre
	 * @param nickname
	 * @return
	 */
	User findFirstByNickname(String nickname);
	
	//@Cacheable
	List<User> findAll();
	
	/***
	 * Devuelve el usuario con dicho email
	 * @param email
	 * @return
	 */
	//@Cacheable
	User findFirstByEmail(String email);
	
	@CacheEvict(allEntries=true)
	User save(User user);
	
	@CacheEvict(allEntries=true)
	void delete(User user);
	
}
