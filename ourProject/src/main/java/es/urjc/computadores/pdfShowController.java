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

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sun.xml.txw2.Document;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class pdfShowController {

	@Autowired
	private ContractRepository contractRepo;

	@RequestMapping("/ourProject/contract/{id}/pdf")
	public String load(Model model, @PathVariable(required = false) long id) throws URISyntaxException {

		Optional<Contract> contrato = contractRepo.findById(id);
		Contract cont = contrato.get();

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

		restTemplate.postForEntity(uri, pdfFile, Document.class).getBody();

		/*
		 * url = "http://127.0.0.1:9999/ourProject/contract/pdf"; uri = new URI(url);
		 * String documentName = pdfFile[0] + pdfFile[1] + pdfFile[2] + ".pdf"; /*File
		 * pdf2 = restTemplate.postForEntity(uri, documentName, File.class).getBody();
		 * return pdf2; String pdf2 = restTemplate.postForEntity(uri, documentName,
		 * String.class).getBody(); File doc = new File(pdf2);
		 * model.addAttribute("path", doc);
		 */

		// ENVÍA UN CORREO CON PDF AL MECENAS DEL PROYECTO
		String username = "ourprojectdistribuidas@gmail.com";
		String receptor = "axelsax1998@gmail.com";
		// String receptor = cont.contributor.email;
		String title = "Reenvío del Contrato " + cont.project.projectName;
		String content = "Contrato";
		String document = pdfFile[0] + pdfFile[1] + pdfFile[2] + ".pdf";

		url = "http://127.0.0.1:9999/ourProject/project/messagePDF";
		uri = new URI(url);

		Mail mail = new Mail(username, receptor, title, content, document);

		// restTemplate.getForObject(url, Mail.class, mail);
		String data = restTemplate.postForEntity(uri, mail, String.class).getBody();

		// return "pdf";
		return "redirect:/ourProject/myProfile";
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
