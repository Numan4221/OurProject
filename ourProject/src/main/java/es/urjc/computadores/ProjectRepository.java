package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CacheConfig(cacheNames="ourProject")
public interface ProjectRepository extends JpaRepository<Project,Long>{
	
	@CacheEvict(allEntries=true)
	Project save(Project user);
	
	
	@CacheEvict(allEntries=true)
	void delete(Project project);
	
	/***
	 * Busca por nombre de Usuario  y nombre del Proyecto
	 * @param projectName
	 * @param developer
	 * @return Priemr proyecto encontrado
	 */
	@Cacheable
	Project findFirstByProjectNameAndDeveloperNickname(String projectName,String nickname);

	/***
	 * Busca por nombre del proyecto
	 * @param projectName
	 * @return Lista de Proyectos 
	 */
	@Cacheable
	List<Project> findByProjectName(String projectName);
	
	/***
	 * Busca por nombre del proyecto
	 * @param projectName
	 * @param page
	 * @return Pagina de projectos
	 */
	@Cacheable
	Page<Project> findByProjectName(String projectName,Pageable page);
	
	/***
	 * Busca por nombre de Usuario
	 * @param nickname
	 * @return Lista de Proyectos
	 */
	List<Project> findByDeveloperNickname(String nickname);
	
	/***
	 * Busca por nombre de Usuario
	 * @param nickname
	 * @param page
	 * @return
	 */
	@Cacheable
	Page<Project> findByDeveloperNickname(String nickname,Pageable page);
	
}
