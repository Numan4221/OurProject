package es.urjc.computadores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class deleteProjectController {
	
	@Autowired
	private ServicioInterno serv;
	
	@RequestMapping("/ourProject/myProfile/deleteProject")
	public String deleteProject(Model model, HttpServletRequest request, @RequestParam String projectName, @RequestParam String developerNickname) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		if (serv.deleteProject(projectName, developerNickname)) {
			return "redirect:/ourProject/myProfile";
		}
		
		return "500";

		//model.addAttribute(attributeName, attributeValue);
	}

}
