package es.urjc.computadores;

public class Mail {
	private String sender;
	private String receiver;
	private String title;
	private String content;
	private String pdfDocument;
	public String[] contrato;

	public Mail(String sender, String receiver, String title, String content, String document) {
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.pdfDocument = document;
	}

	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPdfDocument() {
		return pdfDocument;
	}
	public void setPdfDocument(String pdfDocument) {
		this.pdfDocument = pdfDocument;
	}
	public String[] getContrato() {
		return contrato;
	}

	public void setContrato(String[] contrato) {
		this.contrato = contrato;
	}
}