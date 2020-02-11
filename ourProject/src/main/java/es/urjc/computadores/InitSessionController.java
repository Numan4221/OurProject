package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitSessionController {
	
	@GetMapping("/ourProject/initSession")
	public String initSession(Model model) {
		
		//model.addAttribute(attributeName, attributeValue);
		
		return "initSessionView";
	}

}
