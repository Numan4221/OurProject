package es.urjc.computadores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class OurProjectController {
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	

	
	
	@GetMapping("/ourProject")
	public String ourProject(Model model, HttpServletRequest req,/* @RequestParam */ String usern) {

		if (usern != null) {
			model.addAttribute("usern", usern);
		}
		
		
		//session.getAttribute("nickname");
		if ( req.getUserPrincipal() == null) {
			model.addAttribute("isLogged", false);
		} else {
			model.addAttribute("isLogged", true);
		}

		List<Project> listProject = projectRepo.findAll();
		Project[] arrayProjects = listProject.toArray(new Project[listProject.size()]);
		model.addAttribute("arrayProjects", arrayProjects);
		/*
		 * for (int i = 0; i< arrayProjects.length; i++) { String name = "name_p" + i;
		 * String desc = "desc_p" + i; String dev = "devName_p" + i; String goal =
		 * "goal_p" + i; String money = "moneyCollected_p" + i; model.addAttribute(name,
		 * arrayProjects[i].projectName); model.addAttribute(desc,
		 * arrayProjects[i].description); model.addAttribute(dev,
		 * arrayProjects[i].developer.nickname); //model.addAttribute(goal,
		 * arrayProjects[i].goals.get(0)); model.addAttribute(money,
		 * arrayProjects[i].moneyCollected); }
		 */
		return "index";
	}
	
	@PostMapping("/ourProject/init")
	public String ourProjectInit (Model model , HttpSession session, @RequestParam String nickname, @RequestParam String pass) {
		
		User newUser = userRepo.findFirstByNickname(nickname);
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		for (String role : newUser.getRoles()) {
			 roles.add(new SimpleGrantedAuthority(role));
		}
		
		UsernamePasswordAuthenticationToken authReq
	      = new UsernamePasswordAuthenticationToken(nickname, pass, roles);
	    Authentication auth = authenticationProvider.authenticate(authReq);
	    
	
	    
	    SecurityContext sc = SecurityContextHolder.getContext();
	    
	    sc.setAuthentication(auth);
	   
	    
	    session.setAttribute("username", nickname);
	    session.setAttribute("password", pass);
	    session.setAttribute("roles", roles);
	    session.setAttribute("token", auth);
	    
	    session.setMaxInactiveInterval(300);
	    
	    
	    
	    return "redirect:/ourProject";
	}
	
	@GetMapping("/ourProject/logout") 
	public String logout(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "redirect:/ourProject";
	}
	
	@PostMapping("/ourProject/newUser")
	public String outProjectRegister (Model model, HttpSession session, @RequestParam String nickname, @RequestParam String pass,  @RequestParam String email,  @RequestParam String name,  @RequestParam String surname) throws Exception {
		
		
		User a = userRepo.findFirstByNickname(nickname);
		if (a != null) {
			throw new Exception("Nombre de usuario ya en uso");
		}
		
		User b = userRepo.findFirstByEmail(email);
		if (b != null) {
			throw new Exception("Email ya registrado");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(pass);
		
		User newUser = new User(nickname, hashedPassword, email, name, surname);
		
		model.addAttribute("nickname", nickname);
		model.addAttribute("password", pass);
		
		userRepo.save(newUser);
		
		
		//Le iniciamos sesion
		List<GrantedAuthority> roles = new ArrayList<>();
		
		for (String role : newUser.getRoles()) {
			 roles.add(new SimpleGrantedAuthority(role));
		}
		
		UsernamePasswordAuthenticationToken authReq
	      = new UsernamePasswordAuthenticationToken(nickname, pass, roles);
	    Authentication auth = authenticationProvider.authenticate(authReq);
	    
	
	    
	    SecurityContext sc = SecurityContextHolder.getContext();
	    
	    sc.setAuthentication(auth);
	   
	    
	    session.setAttribute("username", nickname);
	    session.setAttribute("password", pass);
	    session.setAttribute("roles", roles);
	    session.setAttribute("token", auth);
	    
	    session.setMaxInactiveInterval(-1);
	    
	    
	    
	    return "redirect:/ourProject";
	}

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private ContractRepository contractRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private RewardRepository rewardRepo;

	@Autowired
	private GoalRepository goalRepo;

	@Autowired
	private ServicioInterno borrador;

	/*
	 * @Autowired private DeveloperRepository devRepo;
	 */
	@PostConstruct
	public void init() {

		if (userRepo.findAll().size()<=1) {

			User us = new User("sergjio", "1234", "sergioplarrosaps@gmail.com", "sergio", "plaza");
			User us1 = new User("dani", "45sdf", "danijimpac@gmail.com", "daniel", "jimenez");
			User us2 = new User("fonti", "suhfued", "sergioplarrosaps@gmail.com", "david", "fontela");
			/*
			 * User us2 = new User("dani","1234","soyfeo@dani2.com","daniel2","jimenez");
			 * User us3 = new User("dani","1234","soyfeo@dani3.com","daniel3","jimenez");
			 * User us4 = new User("dani","1234","soyfeo@dani4.com","daniel4","jimenez");
			 */

			Developer dev = new Developer("axwel", "35658945", "axelheart1998@gmail.com", "alejandro", "garcia");
			Developer dev2 = new Developer("carlos", "chefff5", "alexgr15ro@gmail.com", "carlos", "ruiz");
			Developer dev3 = new Developer("chonoOut", "chefff5", "danijimpac@gmail.com", "chono", "out");
			
			userRepo.save(dev);
			userRepo.save(dev2);
			userRepo.save(dev3);
			userRepo.save(us);
			userRepo.save(us1);
			userRepo.save(us2);
			/*
			 * userRepo.save(us2); userRepo.save(us3); userRepo.save(us4);
			 */

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2016/11/16 12:08:43

			Project p1 = new Project("takeMe", "Corre y huye de los enemigos en nuestro mapa procedural", paymentMethod.CREDITCARD, "ES-51561321518", dev,
					dateFormat.format(date));
			Project p2 = new Project("BiruBiru", "Ganas de fiesta? Sal y diviertete, dalo todo con tus amigos y BiruBiru", paymentMethod.PAYPAL, "PAY-54215", dev3,
					dateFormat.format(date));
			Project p3 = new Project("Leyendas de Malaga", "La costa del sol y un monton de sucesos extraños, ¿serás capaz de mantener tu imperio inmobiliario?", paymentMethod.CREDITCARD, "ES-854256", dev2,
					dateFormat.format(date));
			Project p4 = new Project("ICE3", "Unete al cubo, juega en este entorno helado con los muñecos más monos del panorama", paymentMethod.PAYPAL, "PAY-98653", dev3,
					dateFormat.format(date));
			

			p1.getContributors().add(us);
			p1.getContributors().add(us1);
			p2.getContributors().add(us1);
			p2.getContributors().add(dev);

			projectRepo.save(p1);
			projectRepo.save(p2);
			projectRepo.save(p3);
			projectRepo.save(p4);
			/*
			 * projectRepo.save(p3); projectRepo.save(p4); projectRepo.save(p5);
			 */

			Goal g1 = new Goal(p1, 100.0, "100 primero niveles");
			Goal g2 = new Goal(p2, 50.0, "Juego extra: Yo nunca");
			Goal g3 = new Goal(p2, 150.0, "Juego extra: Naufragos en Ron");
			Goal g4 = new Goal(p2, 300.0, "Herramienta para incluir preguntas y retos");
			Goal g5 = new Goal(p1, 250.0, " 3 malos nuevos y niveles hasta el 150");
			Goal g6 = new Goal(p3, 50.0, "Mision especial: Capitalismo salvaje");
			Goal g7 = new Goal(p3, 120.0, "DLC");
			Goal g8 = new Goal(p4, 80.0, "DLC Fire3");
			Goal g9 = new Goal(p4, 320.0, "Editor de escenarios gratuito");

			goalRepo.save(g1);
			goalRepo.save(g2);
			goalRepo.save(g3);
			goalRepo.save(g4);
			goalRepo.save(g5);
			goalRepo.save(g6);
			goalRepo.save(g7);
			goalRepo.save(g8);
			goalRepo.save(g9);

			/*
			 * us.getFinancedProjects().add(p1); us1.getFinancedProjects().add(p1);
			 * us1.getFinancedProjects().add(p2);
			 */

			/*
			 * Contract c1 = new Contract(us1,p1,"500€", 500.00); Contract c2 = new
			 * Contract(us,p1,"100€", 100.00); Contract c3 = new Contract(us1,p2,"1000€",
			 * 1000.00); Contract c4 = new Contract(dev,p2,"999€", 999.99);
			 * 
			 * 
			 * contractRepo.save(c1); contractRepo.save(c2); contractRepo.save(c3);
			 * contractRepo.save(c4);
			 */

			Comment com1 = new Comment(us1, p1, "El juego no se hace nada repetitivo, con estos escenarios siempre te sorprendes", new Date());
			Comment com3 = new Comment(us, p1, "Ojala lo acaben tiene pintaza", new Date());
			Comment com2 = new Comment(dev, p2, "Siempre he querido un juego de este estilo la verda que hacía falta", new Date());
			Comment com4 = new Comment(us2, p3, " gestion de recursos, al fin algo bueno", new Date());
			Comment com5 = new Comment(dev, p3, " espero que este mejor balanceado que la beta", new Date());
			Comment com6 = new Comment(dev2, p4, " La parte social del proyecto me fascina", new Date());
			Comment com7 = new Comment(us, p4, " que ganas tengo d ellegar a la meta de los mapas", new Date());
			
			
			commentRepo.save(com1);
			commentRepo.save(com2);
			commentRepo.save(com3);
			commentRepo.save(com4);
			commentRepo.save(com5);
			commentRepo.save(com6);
			commentRepo.save(com7);
			

			Reward r1 = new Reward(5.0, "video exclusivo",p1);
			Reward r2 = new Reward( 10.0, "Claves juego",p2);
			Reward r3 = new Reward( 30.0, "DLC",p2);
			Reward r4 = new Reward( 50.0, "Quedada con desarrolladores",p2);
			Reward r5 = new Reward( 20.0, "Todo el contenido del juego y avances",p2);
			Reward r6 = new Reward( 15.0, "Clave juego",p3);
			Reward r7 = new Reward( 30.0, "2000 monedas extra al día",p3);
			Reward r8 = new Reward( 7.0, "Apadrina a un pinguino",p4);
			Reward r9 = new Reward( 13.5, "Peluche de Globi the Walrus",p4);

			rewardRepo.save(r1);
			rewardRepo.save(r2);
			rewardRepo.save(r5);
			rewardRepo.save(r3);
			rewardRepo.save(r4);
			rewardRepo.save(r6);
			rewardRepo.save(r7);
			rewardRepo.save(r8);
			rewardRepo.save(r9);
		}
	}

}
