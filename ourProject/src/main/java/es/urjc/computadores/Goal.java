package es.urjc.computadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Goal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public Double goal;
	public String description;
	
	@ManyToOne
	public Project project;
	
	protected Goal() {
		
	}
	public Goal(Project project,Double goal,String description) {
		super();
		this.project = project;
		this.goal = goal;
		this.description = description;
	}
}
