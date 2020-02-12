package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class profileController {

	@RequestMapping("/ourProject/myProfile")
	public String initSession(Model model, @RequestParam String username) {
		
		model.addAttribute("username", username);
		
		return "myProfile";
	}
}
