package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepository extends JpaRepository<Project,Long>{
	
	/***
	 * Busca por nombre de Usuario  y nombre del Proyecto
	 * @param projectName
	 * @param developer
	 * @return Lista de Proyectos 
	 */
	List<Project> findByProjectNameAndDeveloperNickname(String projectName,String nickname);
	
	/***
	 * 
	 * @param projectName
	 * @param nickname
	 * @param page
	 * @return
	 */
	Page<Project> findByProjectNameAndDeveloperNickname(String projectName,String nickname, Pageable page);
	
	/***
	 * Busca por nombre del proyecto
	 * @param projectName
	 * @return Lista de Proyectos 
	 */
	List<Project> findByProjectName(String projectName);
	
	/***
	 * Busca por nombre del proyecto
	 * @param projectName
	 * @param page
	 * @return Pagina de projectos
	 */
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
	Page<Project> findByDeveloperNickname(String nickname,Pageable page);
	
}
