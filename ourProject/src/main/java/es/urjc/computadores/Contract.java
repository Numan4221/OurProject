package es.urjc.computadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Contract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	User contributor;
	@ManyToOne
	Project project;
	String contractContent;
	
	protected  Contract(){
		
	}

	public Contract(User contributor, Project project, String contractContent) {
		super();
		this.contributor = contributor;
		this.project = project;
		this.contractContent = contractContent;
	}
	
	


}
