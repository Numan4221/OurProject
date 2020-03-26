package es.urjc.computadores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class profileController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@RequestMapping("/ourProject/myProfile")
	public String profileMode(Model model, HttpSession session, HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());

		String username = (String) session.getAttribute("username");

		User user = (User) userRepo.findFirstByNickname(username);
		
		List<Contract> misContratos = contractRepo.findByContributorNickname(user.nickname);
	
		model.addAttribute("user", user);
		model.addAttribute("nickname", user.name);
		model.addAttribute("account",user.getAccountID());
		
		if (user.getMyPaymentMethod() != null) {
			if (user.getMyPaymentMethod() == paymentMethod.PAYPAL) {
				model.addAttribute("paypal", true);
			} else {
				model.addAttribute("paypal", false);
			}
		}
		
		if (user.isDeveloper) {
			List<Project> proyectosPropios = projectRepo.findByDeveloperNickname(user.nickname);
			if (proyectosPropios.size()!= 0) {
				model.addAttribute("proyectosPropios", proyectosPropios);
				model.addAttribute("tieneProyectosPropios", true);
			}
			else 
			{
				model.addAttribute("tieneProyectosPropios", false);
			}
			
		}
		
		if (misContratos.size()!=0) {
			model.addAttribute("misContratos", misContratos);
			model.addAttribute("tieneContratos", true);
		}else {
			model.addAttribute("tieneContratos", false);
		}
		
		return "myProfile";
	}
	
	@PostMapping("/ourProject/myProfile/change")
	public String profileChange(Model model, HttpSession session,HttpServletRequest request, String id, String username, String name, String surname, String email, String option, String accountNumber, String newDev) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		String usern = (String) session.getAttribute("username");

		User user = (User) userRepo.findFirstByNickname(usern);
	
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
		
		if (newDev != "" && newDev != null) {
			user.isDeveloper= true;
			userRepo.save(user);
		}
		return "redirect:/ourProject/myProfile";
	}
	
}
