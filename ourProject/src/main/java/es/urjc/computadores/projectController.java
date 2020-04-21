package es.urjc.computadores;

import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;


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
	
	@Autowired
	private ImageService imageService;

	@PostMapping("/ourProject/project/donation")
	public String donation(Model model, HttpSession session, @RequestParam long id,HttpServletRequest request , String donation, String accountNumber, String service) throws URISyntaxException {

		
		System.out.println(request.isUserInRole("USER")+ " es su rol"); 
		System.out.println(request.isUserInRole("[USER]")+ " es su rol"); 
		System.out.println(request.isUserInRole("ROLE_USER")+ " es su rol"); 
		System.out.println(request.getUserPrincipal().getName()); 
		
		
		
		String username = (String) session.getAttribute("username");

		User myUser = (User) userRepo.findFirstByNickname(username);

		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();

		if (donation != null && accountNumber != null) {
			Double quantity = Double.valueOf(donation);
			proyectoReal.moneyCollected += quantity;
			projectRepo.save(proyectoReal);
			myUser.setAccountID(accountNumber);
			userRepo.save(myUser);
			model.addAttribute("comeFromDonation", true);

			Contract cont = new Contract(myUser, proyectoReal, "Gracias por su donación", quantity);
			contractRepo.save(cont);

			// CONTRATO A CREAR EN PDF PARA EL MECENAS
			String[] pdfFile = new String[6];
			pdfFile[0] = cont.contributor.nickname;
			pdfFile[1] = cont.project.developerName;
			pdfFile[2] = cont.project.projectName;
			pdfFile[3] = cont.contribution.toString() + " €";
			pdfFile[4] = cont.information2;
			pdfFile[5] = "Gracias por su donación";

			// Ya tenemos el contrato, se crea el pdf sobre este:
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://127.0.0.1:9999/ourProject/project/pdf";
			URI uri = new URI(url);

			restTemplate.postForEntity(uri, pdfFile, String.class).getBody();
			// String data = restTemplate.getForObject(url, String.class);

			// ENVÍA UN CORREO CON PDF AL MECENAS DEL PROYECTO
			String email = "ourprojectdistribuidas@gmail.com";
			//String receptor = "axelsax1998@gmail.com";
			String receptor = cont.contributor.email;
			String title = "Contrato " + proyectoReal.projectName;
			String content = "Contrato";
			String document = pdfFile[0] + pdfFile[1] + pdfFile[2] + ".pdf";

			url = "http://127.0.0.1:9999/ourProject/project/messagePDF";
			uri = new URI(url);

			Mail mail = new Mail(email, receptor, title, content, document);

			// restTemplate.getForObject(url, Mail.class, mail);
			String data = restTemplate.postForEntity(uri, mail, String.class).getBody();
			System.out.println(data);
			// String data = restTemplate.getForObject(url, String.class);

			// CONTRATO A CREAR EN PDF PARA EL CREADOR
			pdfFile[0] = cont.contributor.nickname;
			pdfFile[1] = cont.project.developerName;
			pdfFile[2] = cont.project.projectName;
			pdfFile[3] = cont.contribution.toString() + " €";
			pdfFile[4] = cont.information2;
			pdfFile[5] = "Se ha recibido la siguiente donación: ";

			// Ya tenemos el contrato, se crea el pdf sobre este:
			url = "http://127.0.0.1:9999/ourProject/project/pdf";
			uri = new URI(url);

			restTemplate.postForEntity(uri, pdfFile, String.class).getBody();

			// ENVÍA UN CORREO CON PDF AL CREADOR DEL PROYECTO
			email = "ourprojectdistribuidas@gmail.com";
			//receptor = "ourprojectdistribuidas@gmail.com";
			receptor = cont.project.developer.email;
			title = "Contrato " + proyectoReal.projectName;
			content = "Contrato";
			document = pdfFile[0] + pdfFile[1] + pdfFile[2] + ".pdf";

			url = "http://127.0.0.1:9999/ourProject/project/messagePDF";
			uri = new URI(url);

			mail = new Mail(email, receptor, title, content, document);

			// restTemplate.getForObject(url, Mail.class, mail);
			restTemplate.postForEntity(uri, mail, String.class).getBody();
			// String data = restTemplate.getForObject(url, String.class);
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
		model.addAttribute("hasImage",proyectoReal.hasImage);
		model.addAttribute("noHasImage",proyectoReal.noHasImage);
		model.addAttribute("imageURL",proyectoReal.imageURL);
		return "redirect:/ourProject/project/" + id;
		// return "paginaProyecto";
	}

	@PostMapping("/ourProject/project/create")
	public String creation(Model model,HttpSession session,  @RequestParam long id, String service, String newProject, String nombre,
			String descripcion, String accountID, String m1_cant, String m1_desc, String m2_cant, String m2_desc,
			String m3_cant, String m3_desc, String m4_cant, String m4_desc, String m5_cant, String m5_desc,
			String r1_cant, String r1_desc, String r2_cant, String r2_desc, String r3_cant, String r3_desc,
			String r4_cant, String r4_desc, String r5_cant, String r5_desc, MultipartFile imagenFile) {

		String username = (String) session.getAttribute("username");

		User myUser = (User) userRepo.findFirstByNickname(username);

		if (id == -1) {
			List<Project> projectAux = projectRepo.findByProjectName(nombre);

			if (projectAux.size() != 0) {
				// Redirect a error
				// model.addAttribute("nickname", myUser.nickname);
				String url = "redirect:/ourProject/myProfile/error?nickname=";
				String name = myUser.nickname;
				String dest = url + name;
				return dest;
			} else {
				if (service != "" && service != null) {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					Project p;
					if (service.equals("Paypal")) {
						p = new Project(nombre, descripcion, paymentMethod.PAYPAL, accountID, (Developer) myUser,
								dateFormat.format(date));
					} else {
						p = new Project(nombre, descripcion, paymentMethod.CREDITCARD, accountID, (Developer) myUser,
								dateFormat.format(date));
					}

					// Guardamos los objetivos o metas del proyecto

					List<Goal> lista = new ArrayList<>();
					Goal g1, g2, g3, g4, g5;
					if (!m1_cant.isEmpty()  && !m1_desc.isEmpty()) {
						g1 = new Goal(p, Double.valueOf(m1_cant), m1_desc);
						lista.add(g1);
					}
					if (!m2_cant.isEmpty() && !m2_desc.isEmpty()) {
						System.out.println(m2_cant);
						System.out.println(m2_desc);
						g2 = new Goal(p, Double.valueOf(m2_cant), m2_desc);
						lista.add(g2);
					}
					if (!m3_cant.isEmpty()  && !m3_desc.isEmpty()) {
						g3 = new Goal(p, Double.valueOf(m3_cant), m3_desc);
						lista.add(g3);
					}
					if (!m4_cant.isEmpty()  && !m4_desc.isEmpty()) {
						g4 = new Goal(p, Double.valueOf(m4_cant), m4_desc);
						lista.add(g4);
					}
					if (!m5_cant.isEmpty()  && !m5_desc.isEmpty()) {
						g5 = new Goal(p, Double.valueOf(m5_cant), m5_desc);
						lista.add(g5);
					}

					// Guardamos las recompensas que se ofrecen a los contribuyentes
					List<Reward> listaRe = new ArrayList<>();
					Reward r1, r2, r3, r4, r5;
					if (!r1_cant.isEmpty()  && !r1_desc.isEmpty()) {
						r1 = new Reward(Double.valueOf(r1_cant), r1_desc, p);
						listaRe.add(r1);
					}
					if (!r2_cant.isEmpty()  && !r2_desc.isEmpty()) {
						r2 = new Reward(Double.valueOf(r2_cant), r2_desc, p);
						listaRe.add(r2);
					}
					if (!r3_cant.isEmpty()  && !r3_desc.isEmpty()) {
						r3 = new Reward(Double.valueOf(r3_cant), r3_desc, p);
						listaRe.add(r3);
					}
					if (!r4_cant.isEmpty()  && !r4_desc.isEmpty()) {
						r4 = new Reward(Double.valueOf(r4_cant), r4_desc, p);
						listaRe.add(r4);
					}
					if (!r5_cant.isEmpty()  && !r5_desc.isEmpty()) {
						r5 = new Reward(Double.valueOf(r5_cant), r5_desc, p);
						listaRe.add(r5);
					}

					p.setRewards(listaRe);
					p.setGoals(lista);
					

					
					projectRepo.save(p);
					goalRepo.saveAll(lista);
					userRepo.save(myUser);
					
					//comprobamos si hay imagen
					if(imagenFile != null) {
						p.hasImage = true;
						p.noHasImage = false;
						try {
							p.imageURL = imageService.saveImage("proyectos", p.id, imagenFile);
							imageService.resize(p.imageURL);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println(" no hay imagen");
					}
					projectRepo.save(p);
					model.addAttribute("titulo", p.projectName);
					model.addAttribute("desc", p.description);
					model.addAttribute("goals", p.goals);
					model.addAttribute("recompensas", p.rewards);
					model.addAttribute("moneyCollected", p.moneyCollected);
					model.addAttribute("comments", p.myComments);
					model.addAttribute("developer", p.developer.nickname);
					model.addAttribute("id", p.id);
					model.addAttribute("usuarioPropio", myUser.nickname);
					model.addAttribute("fecha", p.fechaCreacion);
					model.addAttribute("hasImage",p.hasImage);
					model.addAttribute("noHasImage",p.noHasImage);
					model.addAttribute("imageURL",p.imageURL);
					return "redirect:/ourProject/project/" + p.id;
				}
			}
		}
		return "redirect:/ourProject";
		// return "paginaProyecto";
	}

	@PostMapping("/ourProject/project/{id}/comment")
	public String comment(Model model, HttpSession session, @RequestParam long id, String comentario)throws URISyntaxException {

		String username = (String) session.getAttribute("username");

		User myUser = (User) userRepo.findFirstByNickname(username);
		// model.addAttribute(attributeName, attributeValue);
		// Si id no es -1, significa que el proyecto ya estaba creado
		if (id != -1) {
			Optional<Project> proyecto = projectRepo.findById(id);
			Project proyectoReal = proyecto.get();

			if (comentario != null) {
				Comment myComment = new Comment(myUser, proyectoReal, comentario, new Date());
				commentRepo.save(myComment);

				// Envio de un correo básico cuando se haga un comentario
				// Solo lo recibe el creador del proyecto

				String email = "ourprojectdistribuidas@gmail.com";
				String receptor = proyectoReal.developer.email;
				//String receptor = "axelsax1998@gmail.com";
				String title = "Tienes un nuevo comentario en el proyecto: " + proyectoReal.projectName;
				String content = "Comentario de " + myComment.user.name + ":\n" + myComment.comment;

				RestTemplate restTemplate = new RestTemplate();
				String url = "http://127.0.0.1:9999/ourProject/project/message";
				URI uri = new URI(url);
				Mail mail = new Mail(email, receptor, title, content, "");

				// restTemplate.getForObject(url, Mail.class, mail);
				restTemplate.postForEntity(uri, mail, String.class).getBody();
				// String data = restTemplate.getForObject(url, String.class);
			}
		}
		return "redirect:/ourProject/project/" + id;
		// return "paginaProyecto";
	}

	@RequestMapping("/ourProject/project/{id}")
	public String load(Model model, @PathVariable (required = false) long id , HttpServletRequest request ) {

		//Hay que proteger la pagina de un proyecto ya que se puede comentar directamente desde ahi
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		System.out.println();
		model.addAttribute("token", token.getToken());
		
		
		
		Optional<Project> proyecto = projectRepo.findById(id);
		Project proyectoReal = proyecto.get();

		model.addAttribute("titulo", proyectoReal.projectName);
		model.addAttribute("desc", proyectoReal.description);
		model.addAttribute("goals", proyectoReal.goals);
		model.addAttribute("recompensas", proyectoReal.rewards);
		model.addAttribute("moneyCollected", proyectoReal.moneyCollected);
		model.addAttribute("comments", proyectoReal.myComments);
		model.addAttribute("developer", proyectoReal.developer.nickname);
		model.addAttribute("id", id);
		model.addAttribute("fecha", proyectoReal.fechaCreacion);
		model.addAttribute("hasImage",proyectoReal.hasImage);
		model.addAttribute("noHasImage",proyectoReal.noHasImage);
		model.addAttribute("imageURL",proyectoReal.imageURL);

		return "paginaProyecto";
	}

	/*
	 * @RequestMapping("/ourProject/project") public String load(Model
	 * model, @RequestParam long id, String comentario, String donation, String
	 * accountNumber, String service, String newProject, String nombre, String
	 * descripcion, String accountID, String m1_cant, String m1_desc, String
	 * m2_cant, String m2_desc, String m3_cant, String m3_desc, String m4_cant,
	 * String m4_desc, String m5_cant, String m5_desc, String r1_cant, String
	 * r1_desc, String r2_cant, String r2_desc, String r3_cant, String r3_desc,
	 * String r4_cant, String r4_desc, String r5_cant, String r5_desc) {
	 * 
	 * User myUser = (User) userRepo.findFirstByNickname("sergjio"); if (id != -1) {
	 * Optional<Project> proyecto = projectRepo.findById(id); Project proyectoReal =
	 * proyecto.get();
	 * 
	 * 
	 * if (comentario != null) { Comment myComment = new Comment(myUser,
	 * proyectoReal, comentario, new Date()); commentRepo.save(myComment); }
	 * 
	 * if (donation != null && accountNumber != null) { Double quantity =
	 * Double.valueOf(donation); proyectoReal.moneyCollected += quantity;
	 * projectRepo.save(proyectoReal); myUser.setAccountID(accountNumber);
	 * userRepo.save(myUser); model.addAttribute("comeFromDonation", true);
	 * 
	 * Contract cont = new Contract(myUser, proyectoReal, "Gracias por su donación",
	 * quantity); contractRepo.save(cont); }
	 * 
	 * if (service != "" && service != null) { if (service.equals("Paypal")) {
	 * myUser.setMyPaymentMethod(paymentMethod.PAYPAL); } else {
	 * myUser.setMyPaymentMethod(paymentMethod.CREDITCARD); } userRepo.save(myUser);
	 * }
	 * 
	 * model.addAttribute("titulo", proyectoReal.projectName);
	 * model.addAttribute("desc", proyectoReal.description);
	 * model.addAttribute("goals", proyectoReal.goals);
	 * model.addAttribute("recompensas", proyectoReal.rewards);
	 * model.addAttribute("moneyCollected", proyectoReal.moneyCollected);
	 * model.addAttribute("comments", proyectoReal.myComments);
	 * model.addAttribute("developer", proyectoReal.developer.nickname);
	 * model.addAttribute("id", id); model.addAttribute("usuarioPropio",
	 * myUser.nickname); model.addAttribute("fecha", proyectoReal.fechaCreacion); }
	 * //Si el id es -1, es que hay que crear el proyecto else if (id == -1) {
	 * List<Project> projectAux = projectRepo.findByProjectName(nombre);
	 * 
	 * if (projectAux.size() != 0) { //Redirect a error
	 * //model.addAttribute("nickname", myUser.nickname); String url =
	 * "redirect:/ourProject/myProfile/error?nickname="; String name =
	 * myUser.nickname; String dest = url + name; return dest; } else { if (service
	 * != "" && service != null) { DateFormat dateFormat = new
	 * SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); Date date = new Date(); Project p;
	 * if (service.equals("Paypal")) { p = new Project(nombre, descripcion,
	 * paymentMethod.PAYPAL, accountID, (Developer) myUser,
	 * dateFormat.format(date)); } else { p = new Project(nombre, descripcion,
	 * paymentMethod.CREDITCARD, accountID, (Developer) myUser,
	 * dateFormat.format(date)); }
	 * 
	 * //Guardamos los objetivos o metas del proyecto
	 * 
	 * List <Goal> lista = new ArrayList<>(); Goal g1, g2, g3, g4, g5; if (m1_cant
	 * != "" && m1_cant != null && m1_desc != "" && m1_desc != null) { g1 = new
	 * Goal(p, Double.valueOf(m1_cant), m1_desc); lista.add(g1); } if (m2_cant != ""
	 * && m2_cant != null && m2_desc != "" && m2_desc != null) { g2 = new Goal(p,
	 * Double.valueOf(m2_cant), m2_desc); lista.add(g2); } if (m3_cant != "" &&
	 * m3_cant != null && m3_desc != "" && m3_desc != null) { g3 = new Goal(p,
	 * Double.valueOf(m3_cant), m3_desc); lista.add(g3); } if (m4_cant != "" &&
	 * m4_cant != null && m4_desc != "" && m4_desc != null) { g4 = new Goal(p,
	 * Double.valueOf(m4_cant), m4_desc); lista.add(g4); } if (m5_cant != "" &&
	 * m5_cant != null && m5_desc != "" && m5_desc != null) { g5 = new Goal(p,
	 * Double.valueOf(m5_cant), m5_desc); lista.add(g5); }
	 * 
	 * //Guardamos las recompensas que se ofrecen a los contribuyentes List <Reward>
	 * listaRe = new ArrayList<>(); Reward r1, r2, r3, r4, r5; if (r1_cant != "" &&
	 * r1_cant != null && r1_desc != "" && r1_desc != null) { r1 = new
	 * Reward(Double.valueOf(r1_cant), r1_desc, p); listaRe.add(r1); } if (r2_cant
	 * != "" && r2_cant != null && r2_desc != "" && r2_desc != null) { r2 = new
	 * Reward(Double.valueOf(r2_cant), r2_desc, p); listaRe.add(r2); } if (r3_cant
	 * != "" && r3_cant != null && r3_desc != "" && r3_desc != null) { r3 = new
	 * Reward(Double.valueOf(r3_cant), r3_desc, p); listaRe.add(r3); } if (r4_cant
	 * != "" && r4_cant != null && r4_desc != "" && r4_desc != null) { r4 = new
	 * Reward(Double.valueOf(r4_cant), r4_desc, p); listaRe.add(r4); } if (r5_cant
	 * != "" && r5_cant != null && r5_desc != "" && r5_desc != null) { r5 = new
	 * Reward(Double.valueOf(r5_cant), r5_desc, p); listaRe.add(r5); }
	 * 
	 * p.setRewards(listaRe); p.setGoals(lista); projectRepo.save(p);
	 * goalRepo.saveAll(lista); userRepo.save(myUser);
	 * 
	 * model.addAttribute("titulo", p.projectName); model.addAttribute("desc",
	 * p.description); model.addAttribute("goals", p.goals);
	 * model.addAttribute("recompensas", p.rewards);
	 * model.addAttribute("moneyCollected", p.moneyCollected);
	 * model.addAttribute("comments", p.myComments); model.addAttribute("developer",
	 * p.developer.nickname); model.addAttribute("id", p.id);
	 * model.addAttribute("usuarioPropio", myUser.nickname);
	 * model.addAttribute("fecha", p.fechaCreacion); }
	 * 
	 * } }
	 * 
	 * return "paginaProyecto"; }
	 */
}
