package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.urjc.computadores.User.paymentMethod;

@Entity
public class Project {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	
	public String projectName;
	public String description;

	@OneToMany(mappedBy="project", cascade = CascadeType.ALL,orphanRemoval = true)
	public List<Goal> goals = new ArrayList<Goal>();
	
	// Reward
	public double moneyCollected;
	
	public String fechaCreacion;
	
	public paymentMethod getPaidWay;
	
	private String accountID;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany 
	private List<User> contributors = new ArrayList<User>();
	@OneToMany(mappedBy="project",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Contract> myProjectContracts = new ArrayList<Contract>();
	
	@OneToMany(mappedBy="project",cascade = CascadeType.ALL,orphanRemoval = true)
	public List<Comment> myComments = new ArrayList<Comment>();
	
	@ManyToOne
	public Developer developer;
	
	public String developerName;
	
	@OneToMany(mappedBy="project", cascade = CascadeType.ALL,orphanRemoval = true)
	public List<Reward> rewards = new ArrayList<Reward>();
	
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
	public Project(String projectName, String description, paymentMethod getPaidWay, String accountID, Developer dev, String fecha) {
		super();
		this.projectName = projectName;
		this.description = description;
		this.getPaidWay = getPaidWay;
		this.accountID = accountID;
		this.developer = dev;
		this.developerName = dev.nickname;
		this.fechaCreacion = fecha;
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
