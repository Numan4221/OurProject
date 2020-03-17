package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerController {

	@RequestMapping("/ourProject/register")
	public String load(Model model) {
		
		//Developer dev = (Developer) developerRepo.findFirstByNickname(nickname);
		
		return "register";
	}
}
