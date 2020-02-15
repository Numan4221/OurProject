package es.urjc.computadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class profileController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@RequestMapping("/ourProject/myProfile")
	public String profileMode(Model model, String id, String username, String name, String surname, String email, String option, String accountNumber) {
		
		User user = (User) userRepo.findFirstByNickname("axwel");
		
		List<Contract> misContratos = contractRepo.findByContributorNickname(user.nickname);
	
		
		if (option != "" && option != null) {
			
			if (option.equals("Paypal")) {
				user.setMyPaymentMethod(paymentMethod.PAYPAL);
			}else {
				user.setMyPaymentMethod(paymentMethod.CREDITCARD);
			}
			userRepo.save(user);
		}
		
		if (accountNumber != "" && accountNumber != null) {		
			user.setAccountID(accountNumber);
			userRepo.save(user);
		}
		
		if (username != "" && username != null) {		
			user.nickname = username;
			userRepo.save(user);
		}
		
		if (name != "" && name != null) {		
			user.name = name;
			userRepo.save(user);
		}
		
		if (surname != "" && surname != null) {		
			user.surname = surname;
			userRepo.save(user);
		}
		
		if (email != "" && email != null) {		
			user.email = email;
			userRepo.save(user);
		}
		
		
		model.addAttribute("user", user);
		
		if (user.getMyPaymentMethod() != null) {
			if (user.getMyPaymentMethod() == paymentMethod.PAYPAL) {
				model.addAttribute("paypal", true);
			} else {
				model.addAttribute("paypal", false);
			}
		}
		
		model.addAttribute("misContratos", misContratos);
		
		return "myProfile";
	}
}
