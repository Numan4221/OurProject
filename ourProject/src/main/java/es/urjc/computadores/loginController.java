package es.urjc.computadores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {

		@Autowired
		UserRepository developerRepo;
		
		@RequestMapping("/ourProject/login")
		public String load(Model model , HttpServletRequest request) {
			
			CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
			model.addAttribute("token", token.getToken());
			
			return "login";
		}
		
	
}
