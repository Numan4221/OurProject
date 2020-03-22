package es.urjc.computadores;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class donationController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	
	@RequestMapping("/ourProject/project/donationPage/{id}")
	public String load(Model model, HttpSession session, @PathVariable (required = true) long id,HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();

		String username = (String) session.getAttribute("username");

		User myUser = (User) userRepo.findFirstByNickname(username);
		
		model.addAttribute("project", proyectoReal);
		model.addAttribute("account", myUser.getAccountID());
		model.addAttribute("user", myUser);
		
		return "paginaDonacion";
	}
}
