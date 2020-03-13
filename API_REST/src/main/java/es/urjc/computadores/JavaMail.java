package es.urjc.computadores;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaMail {
 
	@RequestMapping (value = "/ourProject/project/message", method = RequestMethod.POST)
	//@ResponseStatus(HttpStatus.CREATED)
	//public String sendEmail() {
	//public String sendEmail(@RequestParam(value="mail") Mail email) {
	public ResponseEntity<String> sendEmail(@RequestBody Mail email) {
		/*String receptor = "axelsax1998@gmail.com";
		String title = "Esto funciona putoooooo";
		String content = "Bueno, eso creo";*/
		
		final String username = "ourprojectdistribuidas@gmail.com";
        final String password = "chocopiso123";
        
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email.getReceiver())
            );
            message.setSubject(email.getTitle());
            message.setText(email.getContent());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Correctametne enviado", HttpStatus.OK);
		//return "redirect:/ourProject";
	}
	
}