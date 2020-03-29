package es.urjc.computadores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class createNewDeveloperFromContributor {
	
	@Autowired
	private ServicioInterno serv;
	
	@RequestMapping("/ourProject/myProfile/changeToDeveloper")
	public String changeUser(Model model, @RequestParam String username ,HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		if (serv.convertirseDesarrollador(username,session,request,response)) {
			return "redirect:/ourProject/myProfile";
		}
		
		return "500";

		//model.addAttribute(attributeName, attributeValue);
	}

}
