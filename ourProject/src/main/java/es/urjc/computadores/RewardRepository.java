package es.urjc.computadores;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward,Long>{
	
	
	@CacheEvict(allEntries=true)
	Reward save(Reward reward);
	
	@CacheEvict(allEntries=true)
	void delete(Reward reward);
	/***
	 * Busqueda de recompensa por nombre de proyecto sin paginar
	 * @param projectName
	 * @return
	 */
	List <Reward> findByProjectProjectName(String projectName);
	
	/***
	 * Busqueda de recompensa por nombre de proyecto paginada
	 * @param projectName
	 * @param page
	 * @return
	 */
	Page <Reward> findByProjectProjectName(String projectName,Pageable page);

}
