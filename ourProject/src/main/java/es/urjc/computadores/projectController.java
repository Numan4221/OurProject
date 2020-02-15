package es.urjc.computadores;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class projectController {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@RequestMapping("/ourProject/project")
	public String load(Model model, @RequestParam long id, String comentario, String donation, String accountNumber, String service) {
		
		//model.addAttribute(attributeName, attributeValue);
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();
		/*
		long n = 1;
		Optional<User> usuario = userRepo.findById(n);
		User myUser = usuario.get();*/
		User myUser = (User) userRepo.findFirstByNickname("dani");

		if (comentario!= null) {
			Comment myComment = new Comment (myUser, proyectoReal, comentario, new Date());
			commentRepo.save(myComment);
		}
		
		if (donation != null && accountNumber != null) {
			Double quantity = Double.valueOf(donation);
			proyectoReal.moneyCollected += quantity;
			projectRepo.save(proyectoReal);
			myUser.setAccountID(accountNumber);
			userRepo.save(myUser);
			model.addAttribute("comeFromDonation", true);
			
			Contract cont = new Contract(myUser, proyectoReal, "Gracias por su donación", quantity);
			contractRepo.save(cont);
		}
		
		if (service != "" && service != null) {
			if (service.equals("Paypal")) {
				myUser.setMyPaymentMethod(paymentMethod.PAYPAL);
			} else {
				myUser.setMyPaymentMethod(paymentMethod.CREDITCARD);
			}
			userRepo.save(myUser);
		}
		
			
		
		model.addAttribute("titulo", proyectoReal.projectName);
		model.addAttribute("desc", proyectoReal.description);
		model.addAttribute("goals", proyectoReal.goals);
		model.addAttribute("recompensas", proyectoReal.rewards);
		model.addAttribute("moneyCollected", proyectoReal.moneyCollected);
		model.addAttribute("comments", proyectoReal.myComments);
		model.addAttribute("developer", proyectoReal.developer.nickname);
		model.addAttribute("id", id);
		model.addAttribute("usuarioPropio", myUser.nickname);
		model.addAttribute("fecha", proyectoReal.fechaCreacion);
		
		return "paginaProyecto";
	}


}
