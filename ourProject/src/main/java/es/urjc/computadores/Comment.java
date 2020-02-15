package es.urjc.computadores;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	public User user;
	
	
	@ManyToOne
	public Project project;
	
	
	public String comment;
	
	public Date date; 
	
	public int numberOfLikes;
	
	protected Comment() {

	}

	public Comment(User user, Project project, String comment, Date date) {
		super();
		this.user = user;
		this.project = project;
		this.comment = comment;
		this.date = date;
		this.numberOfLikes = 0;
	}
	

	/*
	public Comment(User user, String comment, Date date) {
		super();
		this.user = user;
		this.comment = comment;
		this.date = date;
		this.numberOfLikes = 0;
	}
	
	*/

}
