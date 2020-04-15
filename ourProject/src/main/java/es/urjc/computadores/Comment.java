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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
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
