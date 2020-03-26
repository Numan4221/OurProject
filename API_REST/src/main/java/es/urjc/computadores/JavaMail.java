package es.urjc.computadores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class JavaMail {
 
	//@PostMapping(path="/ourProject/project/message", consumes = "application/json", produces = "application/json")
	@PostMapping(path="/ourProject/project/message")
	public void sendEmailComment (@RequestBody Mail email) {
	//@RequestMapping (value = "/ourProject/project/message", method = RequestMethod.POST)
	//@ResponseStatus(HttpStatus.CREATED)
	//public String sendEmail() {
	//public String sendEmail(@RequestParam(value="mail") Mail email) {
	//public ResponseEntity<String> sendEmail(@RequestBody Mail email) {
		
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

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //return new ResponseEntity<>("Correctametne enviado", HttpStatus.OK);
        //return "Correcto";
		//return "redirect:/ourProject";
	}
	
	@PostMapping(path="/ourProject/project/messagePDF")
	public String sendEmailPDF (@RequestBody Mail email) throws DocumentException {
	//@RequestMapping (value = "/ourProject/project/message", method = RequestMethod.POST)
	//@ResponseStatus(HttpStatus.CREATED)
	//public String sendEmail() {
	//public String sendEmail(@RequestParam(value="mail") Mail email) {
	//public ResponseEntity<String> sendEmail(@RequestBody Mail email) {
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
            //message.setText(email.getContent());
            message.setSubject(email.getTitle());
            
        	BodyPart texto = new MimeBodyPart();
        	texto.setText(email.getContent());
        	
        	BodyPart adjunto = new MimeBodyPart();
        	adjunto.setDataHandler(new DataHandler(new FileDataSource(email.getPdfDocument())));
        	adjunto.setFileName(email.getPdfDocument());
        	
        	MimeMultipart multiParte = new MimeMultipart();

        	multiParte.addBodyPart(texto);
        	multiParte.addBodyPart(adjunto);
        	
        	message.setContent(multiParte);
        	
            Transport.send(message);
            
            File doc = new File(email.getPdfDocument());
            if (doc.exists()) {
            	doc.delete();
            }else {
            	System.out.println("No lo encuentra");
            }
			

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //return new ResponseEntity<>("Correctametne enviado", HttpStatus.OK);
        return "Correcto";
		//return "redirect:/ourProject";
	}
	
}