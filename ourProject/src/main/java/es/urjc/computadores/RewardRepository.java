package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward,Long>{
	
	List <Reward> findByProjectProjectName(String projectName);

}
