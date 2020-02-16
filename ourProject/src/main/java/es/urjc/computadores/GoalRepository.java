package es.urjc.computadores;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Long>{

	/***
	 * Filtra las metas por el nombre del proyecto
	 * @param projectName
	 * @return Lista de metas
	 */
	List<Goal> findByProjectProjectName(String projectName);
	
	/***
	 * Filtra las metas por el nombre del proyecto
	 * @param projectName
	 * @param page
	 * @return Pagina con las metas
	 */
	Page<Goal> findByProjectProjectName(String projectName,Pageable page);
	
	/***
	 * Filtra las metas por el nombre del proyecto y el nombre del desarrollador
	 * @param projectName
	 * @param developerName
	 * @return Lista de Metas
	 */
	List<Goal> findByProjectProjectNameAndProjectDeveloperName(String projectName,String developerName);
	
	/***
	 * Filtra las metas por el nombre del proyecto y el nombre del desarrollador
	 * @param projectName
	 * @param developerName
	 * @param page
	 * @return Lista de Metas
	 */
	Page<Goal> findByProjectProjectNameAndProjectDeveloperName(String projectName,String developerName,Pageable page);
	
}
