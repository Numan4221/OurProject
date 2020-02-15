package es.urjc.computadores;

import java.util.Date;
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
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping("/ourProject/project")
	public String initSession(Model model, @RequestParam long id, String comentario) {
		
		//model.addAttribute(attributeName, attributeValue);
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();
		long n = 1;
		Optional<User> usuario = userRepo.findById(n);
		User myUser = usuario.get();

		if (comentario!= null) {
			Comment myComment = new Comment (myUser, proyectoReal, comentario, new Date());
			commentRepo.save(myComment);
		}
		
		model.addAttribute("titulo", proyectoReal.projectName);
		model.addAttribute("desc", proyectoReal.description);
		model.addAttribute("goals", proyectoReal.goals);
		model.addAttribute("recompensas", proyectoReal.rewards);
		model.addAttribute("moneyCollected", proyectoReal.moneyCollected);
		model.addAttribute("comments", proyectoReal.myComments);
		model.addAttribute("developer", proyectoReal.developer.nickname);
		model.addAttribute("id", id);
		model.addAttribute("usuarioPropio", myUser.nickname);
		model.addAttribute("fecha", proyectoReal.fechaCreacion);
		
		return "paginaProyecto";
	}
}
