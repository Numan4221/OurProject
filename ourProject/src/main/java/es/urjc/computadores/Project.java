package es.urjc.computadores;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	@LazyCollection(LazyCollectionOption.FALSE)
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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="project",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Contract> myProjectContracts = new ArrayList<Contract>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="project",cascade = CascadeType.ALL,orphanRemoval = true)
	public List<Comment> myComments = new ArrayList<Comment>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	public Developer developer;
	
	public String developerName;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="project", cascade = CascadeType.ALL,orphanRemoval = true)
	public List<Reward> rewards = new ArrayList<Reward>();

	
	boolean hasImage = false;
	boolean noHasImage = true;
	
	String imageURL;
	
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
		Collections.sort(goals, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				Goal g1 = (Goal) o1;
				Goal g2 = (Goal) o2;
				return new Double(g1.goal).compareTo(new Double(g2.goal));
			}
		});
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

	public List<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		Collections.sort(rewards, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				Reward g1 = (Reward) o1;
				Reward g2 = (Reward) o2;
				return new Double(g1.recompensa).compareTo(new Double(g2.recompensa));
			}
		});
		this.rewards = rewards;
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
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
