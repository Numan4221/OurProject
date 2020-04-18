package es.urjc.computadores;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="ourProject")
public interface GoalRepository extends JpaRepository<Goal,Long>{

	@CacheEvict(allEntries=true)
	Goal save(Goal goal);
	
	@CacheEvict(allEntries=true)
	void delete(Goal goal);
	
	//@Cacheable
	List<Goal> findAll();
	
	/***
	 * Filtra las metas por el nombre del proyecto
	 * @param projectName
	 * @return Lista de metas
	 */	
	//@Cacheable
	List<Goal> findByProjectProjectName(String projectName);
	
	/***
	 * Filtra las metas por el nombre del proyecto
	 * @param projectName
	 * @param page
	 * @return Pagina con las metas
	 */
	//@Cacheable
	Page<Goal> findByProjectProjectName(String projectName,Pageable page);
	
	/***
	 * Filtra las metas por el nombre del proyecto y el nombre del desarrollador
	 * @param projectName
	 * @param developerName
	 * @return Lista de Metas
	 */
	//@Cacheable
	List<Goal> findByProjectProjectNameAndProjectDeveloperName(String projectName,String developerName);
	
	/***
	 * Filtra las metas por el nombre del proyecto y el nombre del desarrollador
	 * @param projectName
	 * @param developerName
	 * @param page
	 * @return Lista de Metas
	 */
	//@Cacheable
	Page<Goal> findByProjectProjectNameAndProjectDeveloperName(String projectName,String developerName,Pageable page);
	
}
