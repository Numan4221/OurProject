package es.urjc.computadores;

public class Mail {
	private long id;
	private String sender;
	private String receiver;
	private String title;
	private String content;
	private PDFClass document;

	public Mail(long id, String sender, String receiver, String title, String content, PDFClass document) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.document = document;
	}
	
	public Mail(long id, String sender, String receiver, String title, String content) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public PDFClass getDocument() {
		return document;
	}
	public void setDocument(PDFClass document) {
		this.document = document;
	}
	
	
}
