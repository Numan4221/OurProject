package es.urjc.computadores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class newProjectController {

	@Autowired
	UserRepository developerRepo;
	
	@RequestMapping("/ourProject/myProfile/newProject")
	public String load(Model model, @RequestParam String nickname,HttpServletRequest request) {
		
		Developer dev = (Developer) developerRepo.findFirstByNickname(nickname);
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		
		return "crearProyecto";
	}
	
}
