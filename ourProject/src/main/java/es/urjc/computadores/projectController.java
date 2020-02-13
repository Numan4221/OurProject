package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class projectController {

	@RequestMapping("/ourProject/project")
	public String initSession(Model model) {
		
		//model.addAttribute(attributeName, attributeValue);
		


		
		
		return "project";
	}
}
