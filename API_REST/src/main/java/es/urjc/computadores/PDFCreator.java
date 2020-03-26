package es.urjc.computadores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class PDFCreator {

	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
	private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private static final String iTextExampleImage = "/src/main/resources/static/img/topImage.png";

	// @PostMapping(path="/ourProject/project/pdf", consumes = "application/json",
	// produces = "application/json")
	@PostMapping(path = "/ourProject/project/pdf")
	public void createPDF(@RequestBody String[] contrato) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		// PdfWriter.getInstance(document, new FileOutputStream("contrato.pdf"));
		String documentName = contrato[0] + contrato[1] + contrato[2] + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(documentName));
		/*
		 * try { PdfWriter.getInstance(document, new FileOutputStream(pdfDocument)); }
		 * catch (FileNotFoundException fileNotFoundException) {
		 * System.out.println("No such file was found to generate the PDF " +
		 * "(No se encontró el fichero para generar el pdf)" + fileNotFoundException); }
		 */
		document.open();

		document.addTitle("Contrato " + contrato[2]);
		document.addSubject("OurProject");
		document.addKeywords("OurProject, PDF, Contract");
		document.addAuthor("OurProject");
		document.addCreator(contrato[1]);

		Chunk chunk = new Chunk("Contrato: " + contrato[2], chapterFont);
		chunk.setBackground(BaseColor.LIGHT_GRAY);

		Chapter chapter = new Chapter(new Paragraph(chunk), 1);
		chapter.setNumberDepth(0);
		chapter.add(new Paragraph(contrato[5] + " " + contrato[3], paragraphFont));
		chapter.add(new Paragraph(contrato[4], paragraphFont));

		Image image;
		try {
			image = Image.getInstance(iTextExampleImage);
			image.setAbsolutePosition(450, 700);
			image.scalePercent(20);
			chapter.add(image);
		} catch (BadElementException ex) {
			System.out.println("Image BadElementException" + ex);
		} catch (IOException ex) {
			System.out.println("Image IOException " + ex);
		}
		document.add(chapter);

		document.close();
		// return "Creado";
	}

	@PostMapping(path = "/ourProject/contract/pdf")
	public String returnPDF(@RequestBody String pdfName) throws DocumentException, IOException {
		File doc = new File(pdfName);

		//return doc;
		return doc.getAbsolutePath();
	}
}
