package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Long>{

	List<Goal> findByProjectProjectName(String projectName);
	
}
