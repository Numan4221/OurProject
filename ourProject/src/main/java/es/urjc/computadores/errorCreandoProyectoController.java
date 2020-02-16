package es.urjc.computadores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class errorCreandoProyectoController {

	@RequestMapping("/ourProject/myProfile/error")
	public String load(Model model,  @RequestParam  String nickname) {
		System.out.println(nickname);
		model.addAttribute("nick", nickname);
		return "401";
	}

}
