package es.urjc.computadores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long>{
	
	List <Contract> findByContributorNicknameAndProjectProjectName(String nickname, String projectname);
	
	List <Contract> findByContributorNickname(String nickName);
	
	List <Contract> findByProjectProjectName(String projectName);
	
	
}
