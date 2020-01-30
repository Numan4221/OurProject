package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OurProjectController {
	@GetMapping("/ourProject")
	public String ourProject(Model model) {
		model.addAttribute("name", "World");
		return "ourProject_template";
	}
}
