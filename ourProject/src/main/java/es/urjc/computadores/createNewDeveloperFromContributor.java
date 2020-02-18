package es.urjc.computadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class createNewDeveloperFromContributor {
	
	@Autowired
	private ServicioInterno serv;
	
	@RequestMapping("/ourProject/myProfile/changeToDeveloper")
	public String changeUser(Model model, @RequestParam String username) {
		
		if (serv.convertirseDesarrollador(username)) {
			return "redirect:/ourProject/myProfile";
		}
		
		return "500";

		//model.addAttribute(attributeName, attributeValue);
	}

}