package es.urjc.computadores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerController {

	@RequestMapping("/ourProject/register")
	public String load(Model model, HttpServletRequest request) {
		
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken());
		
		
		//Developer dev = (Developer) developerRepo.findFirstByNickname(nickname);
		
		return "register";
	}
}
