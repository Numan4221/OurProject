package es.urjc.computadores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	
	@Autowired
	private GoalRepository goalRepo;

	@RequestMapping("/ourProject/project")
	public String load(Model model,  @RequestParam  long id, String comentario, String donation,
			String accountNumber, String service, String newProject, String nombre, String descripcion, String accountID,
			String m1_cant, String m1_desc, String m2_cant, String m2_desc, String m3_cant, String m3_desc,
			String m4_cant, String m4_desc, String m5_cant, String m5_desc) {

		User myUser = (User) userRepo.findFirstByNickname("sergjio");
		// model.addAttribute(attributeName, attributeValue);
		// Si id no es -1, significa que el proyecto ya estaba creado
		if (id != -1) {
			Optional<Project> proyecto = projectRepo.findById(id);
			Project proyectoReal = proyecto.get();
			/*
			 * long n = 1; Optional<User> usuario = userRepo.findById(n); User myUser =
			 * usuario.get();
			 */
			

			if (comentario != null) {
				Comment myComment = new Comment(myUser, proyectoReal, comentario, new Date());
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
		}
		//Si el id es -1, es que hay que crear el proyecto
		else if (id == -1) {
			List<Project> projectAux = projectRepo.findByProjectName(nombre);
			
			if (projectAux.size() != 0) {
				//Redirect a error
				//model.addAttribute("nickname", myUser.nickname);
				String url = "redirect:/ourProject/myProfile/error?nickname=";
				String name = myUser.nickname;
				String dest = url + name;
				return dest;
			}
			else {
				if (service != "" && service != null) {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					Project p;
					if (service.equals("Paypal")) {
						p = new Project(nombre, descripcion, paymentMethod.PAYPAL, accountID, (Developer) myUser, dateFormat.format(date));
					} else {
						p = new Project(nombre, descripcion, paymentMethod.CREDITCARD, accountID, (Developer) myUser, dateFormat.format(date));
					}
					List <Goal> lista = new ArrayList<>();
					Goal g1, g2, g3, g4, g5;
					if (m1_cant != "" && m1_cant != null && m1_desc != "" && m1_desc != null) {
						g1 = new Goal(p, Double.valueOf(m1_cant), m1_desc);
						lista.add(g1);
					}
					if (m2_cant != "" && m2_cant != null && m2_desc != "" && m2_desc != null) {
						g2 = new Goal(p, Double.valueOf(m2_cant), m2_desc);
						lista.add(g2);
					}
					if (m3_cant != "" && m3_cant != null && m3_desc != "" && m3_desc != null) {
						g3 = new Goal(p, Double.valueOf(m3_cant), m3_desc);
						lista.add(g3);
					}
					if (m4_cant != "" && m4_cant != null && m4_desc != "" && m4_desc != null) {
						g4 = new Goal(p, Double.valueOf(m4_cant), m4_desc);
						lista.add(g4);
					}
					if (m5_cant != "" && m5_cant != null && m5_desc != "" && m5_desc != null) {
						g5 = new Goal(p, Double.valueOf(m5_cant), m5_desc);
						lista.add(g5);
					}
					p.setGoals(lista);
					projectRepo.save(p);
					goalRepo.saveAll(lista);
					userRepo.save(myUser);
					
					model.addAttribute("titulo", p.projectName);
					model.addAttribute("desc", p.description);
					model.addAttribute("goals", p.goals);
					model.addAttribute("recompensas", p.rewards);
					model.addAttribute("moneyCollected", p.moneyCollected);
					model.addAttribute("comments", p.myComments);
					model.addAttribute("developer", p.developer.nickname);
					model.addAttribute("id", id);
					model.addAttribute("usuarioPropio", myUser.nickname);
					model.addAttribute("fecha", p.fechaCreacion);
				}
				
			}
		}

		return "paginaProyecto";
	}

}
