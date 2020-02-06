package es.urjc.computadores;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OurProjectController {
	@GetMapping("/ourProject")
	public String ourProject(Model model) {
		model.addAttribute("name", "World");
		return "ourProject_template";
	}
	
	// me pones aqui una isntancia de este repo
	@Autowired
	private UserRepository userRepo;
	
	@PostConstruct
	public void init() {
		User us = new User("sergjio","1234");
		userRepo.save(us);
	}
	
}
