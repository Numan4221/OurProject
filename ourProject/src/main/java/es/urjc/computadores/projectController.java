package es.urjc.computadores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class projectController {

	@Autowired
	private ProjectRepository projectRepo;
	
	@RequestMapping("/ourProject/project")
	public String initSession(Model model, @RequestParam long id) {
		
		//model.addAttribute(attributeName, attributeValue);
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();
		
		model.addAttribute("titulo", proyectoReal.projectName);
		model.addAttribute("desc", proyectoReal.description);
		model.addAttribute("goals", proyectoReal.goals);
		model.addAttribute("moneyCollected", proyectoReal.moneyCollected);
		model.addAttribute("developer", proyectoReal.developer.nickname);
		
		return "paginaProyecto";
	}
}
