package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitSessionController {
	
	@RequestMapping("/ourProject/initSession")
	public String initSession(Model model) {
		
		//model.addAttribute(attributeName, attributeValue);
		
		return "login";
	}

}
