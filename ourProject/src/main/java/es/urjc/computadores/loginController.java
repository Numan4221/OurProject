package es.urjc.computadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {

		@Autowired
		UserRepository developerRepo;
		
		@RequestMapping("/ourProject/login")
		public String load(Model model) {
			
			
			return "login";
		}
		
	
}
