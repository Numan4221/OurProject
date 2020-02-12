package es.urjc.computadores;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OurProjectController {
	
	@GetMapping("/ourProject")
	public String ourProject(Model model, @RequestParam String username) {
		if (username != null) {
		model.addAttribute("username", username);
		System.out.println("username: " + username);
		}
		return "ourProject_template";
	}
	
	// me pones aqui una isntancia de este repo
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private ProjectRepository projectRepo;
	
	@PostConstruct
	public void init() {
		Project p1 = new Project("takeMe","Jueguito rico", " DanielJ",null);
		Project p2 = new Project("BiruBiru","A Beber", " ChonoOut",null);
		
		projectRepo.save(p1);
		projectRepo.save(p2);
		
		User us = new User("sergjio","1234","soyfeo@sergio.com","sergio","plaza");
		User us1 = new User("dani","1234","soyfeo@dani.com","daniel","jimenez");
		
		us.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p2);
		
		userRepo.save(us);
		userRepo.save(us1);
		
		
		
	}
	
}
