package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Project {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public String projectName;
	public String description;
	// Developer aun no esta
	public String developer;
	@Embedded
	public List<Double> goals = new ArrayList<Double>();
	// Reward
	public double moneyCollected;
	
	@ManyToMany(mappedBy="financedProjects")
	private List<User> contributors = new ArrayList<User>();
	
	// comentarios
	
	/***
	 * Constructor por defecto para la base de datos
	 */
	protected Project(){
		
	}
	
	/***
	 * Constructor normal
	 * @param projectName
	 * @param description
	 * @param developer
	 * @param goals
	 */
	public Project(String projectName, String description, String developer, List<Double> goals) {
		super();
		this.projectName = projectName;
		this.description = description;
		this.developer = developer;
		//this.goals = goals;
	}
	

	public List<Double> getGoals() {
		return goals;
	}
	public void setGoals(List<Double> goals) {
		this.goals = goals;
	}
	
	public List<User> getContributors() {
		return contributors;
	}
	public void setContributors(List<User> contributors) {
		this.contributors = contributors;
	}
	
	
}
