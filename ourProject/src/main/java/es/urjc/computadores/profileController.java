package es.urjc.computadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class profileController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@RequestMapping("/ourProject/myProfile")
	public String profileMode(Model model, @RequestParam String nombre) {
		
		User miUsuario = (User) userRepo.findByNickname(nombre);
		
		List<Contract> misContratos = contractRepo.findByContributorNickname(nombre);
		
		model.addAttribute("usern", nombre);
		
		model.addAttribute("misContratos", misContratos);
		
		return "myProfile";
	}
}
