package es.urjc.computadores;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="ourProject")
public interface RewardRepository extends JpaRepository<Reward,Long>{
	
	
	@CacheEvict(allEntries=true)
	Reward save(Reward reward);
	
	@CacheEvict(allEntries=true)
	void delete(Reward reward);
	
	//@Cacheable
	List<Reward> findAll();
	
	/***
	 * Busqueda de recompensa por nombre de proyecto sin paginar
	 * @param projectName
	 * @return
	 */
	
	//@Cacheable
	List <Reward> findByProjectProjectName(String projectName);
	
	/***
	 * Busqueda de recompensa por nombre de proyecto paginada
	 * @param projectName
	 * @param page
	 * @return
	 */
	//@Cacheable
	Page <Reward> findByProjectProjectName(String projectName,Pageable page);

}
