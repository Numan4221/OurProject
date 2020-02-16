package es.urjc.computadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioInterno {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private GoalRepository goalRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public boolean deleteProject(String projectName, String developerNickname) {
		Project proj = projectRepo.findFirstByProjectNameAndDeveloperNickname(projectName, developerNickname);
		if(proj != null) {
			List<Goal> goals = goalRepo.findByProjectProjectNameAndProjectDeveloperName(projectName, developerNickname);
			//List<Goal> goals = goalRepo.findByProjectProjectName(projectName);
			goalRepo.deleteAll(goals);
			List<Contract> contracts = contractRepo.findByProjectProjectName(projectName);
			contractRepo.deleteAll(contracts);
			List<Comment> comments = commentRepo.findByProjectProjectName(projectName);
			commentRepo.deleteAll(comments);
			projectRepo.delete(proj);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean convertirseDesarrollador(String userName) {
		User user = userRepo.findFirstByNickname(userName);
		if(user != null) {
			if(user.getClass() != Developer.class) {
				List<Project> financedProjects = user.getFinancedProjects();
				List<Contract> contracts = user.getMyContracts();
				List<Comment> comments = user.getMyComments();
				Developer dev = new Developer(user.nickname,user.getPassword(),user.email,user.name,user.surname);
				userRepo.save(dev);
				for(Project pro : financedProjects) {
					List <User> contributors = pro.getContributors();
					contributors.remove(user);
					contributors.add(dev);
					pro.setContributors(contributors);
					projectRepo.save(pro);
				}
				for(Contract cont : contracts) {
					cont.contributor = dev;
					contractRepo.save(cont);
				}
				for(Comment com : comments) {
					com.user = dev;
					commentRepo.save(com);
				}
				userRepo.delete(user);
				return true;
			}
		}
		
		return false;
		
	}
	
	
}
