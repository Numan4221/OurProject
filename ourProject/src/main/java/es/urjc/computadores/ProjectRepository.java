package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project,Long>{
	
	/***
	 * 
	 * @param projectName
	 * @param developer
	 * @return Una lista de Proyectos con ese nombre y de ese desarrollador
	 */
	List<Project> findByProjectNameAndDeveloper(String projectName,String developer);
	
	
}
