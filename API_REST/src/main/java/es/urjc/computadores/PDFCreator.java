package es.urjc.computadores;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class PDFCreator {

	@RequestMapping (value = "/ourProject/project/pdf", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public void createPDF() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Hello World", font);
		 
		document.add(chunk);
		document.close();
	}
}
