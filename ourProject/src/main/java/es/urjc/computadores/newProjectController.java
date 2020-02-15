package es.urjc.computadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class newProjectController {

	@Autowired
	UserRepository developerRepo;
	
	@RequestMapping("/ourProject/myProfile/newProject")
	public String load(Model model, @RequestParam String nickname) {
		
		Developer dev = (Developer) developerRepo.findFirstByNickname(nickname);
		
		return "crearProyecto";
	}
	
}
