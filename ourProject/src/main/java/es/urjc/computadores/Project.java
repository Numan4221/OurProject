package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import es.urjc.computadores.User.paymentMethod;

@Entity
public class Project {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public String projectName;
	public String description;

	@OneToMany(mappedBy="project")
	public List<Goal> goals = new ArrayList<Goal>();
	
	
	// Reward
	public double moneyCollected;
	
	public paymentMethod getPaidWay;
	
	private String accountID;
	
	
	@ManyToMany
	private List<User> contributors = new ArrayList<User>();
	@OneToMany(mappedBy="project")
	private List<Contract> myProjectContracts = new ArrayList<Contract>();
	
	@OneToMany(mappedBy="project")
	private List<Comment> myComments = new ArrayList<Comment>();
	
	@ManyToOne
	public Developer developer;
	
	@OneToMany(mappedBy="project")
	private List<Reward> rewards = new ArrayList<Reward>();
	
	@Lob
	public byte[] projectPic;
	
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
	public Project(String projectName, String description, paymentMethod getPaidWay, String accountID, Developer dev) {
		super();
		this.projectName = projectName;
		this.description = description;
		this.getPaidWay = getPaidWay;
		this.accountID = accountID;
		this.developer = dev;
	}
	



	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public List<User> getContributors() {
		return contributors;
	}
	public void setContributors(List<User> contributors) {
		this.contributors = contributors;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
	
}
