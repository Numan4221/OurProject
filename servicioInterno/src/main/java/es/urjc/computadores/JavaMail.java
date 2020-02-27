package es.urjc.computadores;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaMail {

private final Properties properties = new Properties();
	
	private String password;
 
	private Session session;
 
	private void init() {
 
		properties.put("mail.smtp.host", "mail.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		properties.put("mail.smtp.mail.sender","emisor@gmail.com");
		properties.put("mail.smtp.user", "usuario");
		properties.put("mail.smtp.auth", "true");
 
		session = Session.getDefaultInstance(properties);
	}
 
	@PostMapping ("/ourProject/project/message")
	public String sendEmail() {
	//public String sendEmail(@RequestParam String receptor, String title, String content) {
		
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			//message.setSubject(title);
			//message.setText(content);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("axelsax1998@gmail.com"));
			message.setSubject("Titulo");
			message.setText("Esto es una prueba de mierda");
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), "password");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		}catch (MessagingException me){
                        //Aqui se deberia o mostrar un mensaje de error o en lugar
                        //de no hacer nada con la excepcion, lanzarla para que el modulo
                        //superior la capture y avise al usuario con un popup, por ejemplo.
			return "redirect:/ourProject";
		}
		
		return "redirect:/ourProject";
		
	}
	
}
