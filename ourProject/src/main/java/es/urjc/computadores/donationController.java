package es.urjc.computadores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class donationController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	
	@RequestMapping("/ourProject/paginaDonacion")
	public String load(Model model, @RequestParam long id) {
		
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();
		long n = 1;
		Optional<User> usuario = userRepo.findById(n);
		User myUser = usuario.get();
		//model.addAttribute("username", username);
		
		model.addAttribute("project", proyectoReal);
		model.addAttribute("user", myUser);
		
		return "paginaDonacion";
	}
}
