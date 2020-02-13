package es.urjc.computadores;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class OurProjectController {
	
	@GetMapping("/ourProject")
	public String ourProject(Model model, /*@RequestParam*/ String username) {
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
	
	@Autowired
	private ContractRepository contractRepo;
	/*
	@Autowired
	private DeveloperRepository devRepo;
	*/
	@PostConstruct
	public void init() {


		
		
		User us = new User("sergjio","1234","soyfeo@sergio.com","sergio","plaza");
		User us1 = new User("dani","1234","soyfeo@dani.com","daniel","jimenez");
		
		
		Developer dev = new Developer("axwel","3565","soyfeo@axwel.com","alejandro", "garcia");
		
		userRepo.save(dev);
		userRepo.save(us);
		userRepo.save(us1);
		
		Project p1 = new Project("takeMe","Jueguito rico", null,paymentMethod.CREDITCARD,"ES-51561321518");
		Project p2 = new Project("BiruBiru","A Beber", null,paymentMethod.PAYPAL,"PAY-54215");
		
		p1.developer = dev;
		
		p1.getContributors().add(us);
		p1.getContributors().add(us1);
		p2.getContributors().add(us1);
		p2.getContributors().add(dev);
		
		projectRepo.save(p1);
		projectRepo.save(p2);
		
		
		/*
		us.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p2);
		*/
		
		Contract c1 = new Contract(us1,p1,"500€");
		Contract c2 = new Contract(us,p1,"100€");
		Contract c3 = new Contract(us1,p2,"1000€");
		Contract c4 = new Contract(dev,p2,"999€");

		
		contractRepo.save(c1);
		contractRepo.save(c2);
		contractRepo.save(c3);
		contractRepo.save(c4);
		
		/*
		
		us.getMyContracts().add(c2);
		us1.getMyContracts().add(c1);
		us1.getMyContracts().add(c3);
		*/
		
		/*
		userRepo.save(dev);
		userRepo.save(us);
		userRepo.save(us1);
		
		projectRepo.save(p1);
		projectRepo.save(p2);

		contractRepo.save(c1);
		contractRepo.save(c2);
		contractRepo.save(c3);
		*/

		
		/*
		us1.getMyContracts().add(c1);
		
		userRepo.save(us);
		*/
	}
	
}
