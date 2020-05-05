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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class JavaMail {
	
	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
	private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
	
	@GetMapping("")
	public String initPage() {
		
		return "true";
		
	}
 
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
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");



        
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
	public String sendEmailPDF (@RequestBody Mail email) throws DocumentException, FileNotFoundException {
	//@RequestMapping (value = "/ourProject/project/message", method = RequestMethod.POST)
	//@ResponseStatus(HttpStatus.CREATED)
	//public String sendEmail() {
	//public String sendEmail(@RequestParam(value="mail") Mail email) {
	//public ResponseEntity<String> sendEmail(@RequestBody Mail email) {
		
		
		Document document = new Document();
		// PdfWriter.getInstance(document, new FileOutputStream("contrato.pdf"));
		String documentName = email.contrato[0] + email.contrato[1] + email.contrato[2] + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(documentName));
		/*
		 * try { PdfWriter.getInstance(document, new FileOutputStream(pdfDocument)); }
		 * catch (FileNotFoundException fileNotFoundException) {
		 * System.out.println("No such file was found to generate the PDF " +
		 * "(No se encontró el fichero para generar el pdf)" + fileNotFoundException); }
		 */
		document.open();

		document.addTitle("Contrato " + email.contrato[2]);
		document.addSubject("OurProject");
		document.addKeywords("OurProject, PDF, Contract");
		document.addAuthor("OurProject");
		document.addCreator(email.contrato[1]);

		Chunk chunk = new Chunk("Contrato: " + email.contrato[2], chapterFont);
		chunk.setBackground(BaseColor.LIGHT_GRAY);

		Chapter chapter = new Chapter(new Paragraph(chunk), 1);
		chapter.setNumberDepth(0);
		chapter.add(new Paragraph(email.contrato[5] + " " + email.contrato[3], paragraphFont));
		chapter.add(new Paragraph(email.contrato[4], paragraphFont));

		document.add(chapter);

		document.close();
		
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