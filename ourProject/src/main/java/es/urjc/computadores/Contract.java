package es.urjc.computadores;

import java.util.List;

import javax.persistence.Column;
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
	@Column(length=1000000)
	String information;
	String information2;
	String contractContent;
	Double contribution;
	
	protected  Contract(){
		
	}

	public Contract(User contributor, Project project, String contractContent, Double cont) {
		super();
		this.contributor = contributor;
		this.project = project;
		this.contribution = cont;
		this.contractContent = contractContent;
		this.information = createContractContent(project, contractContent, cont);
		this.information2 = createContractContent2(project, contractContent, cont);
	}
	
	private String createContractContent(Project project, String contractContent, Double cont) {
		
		String cad = "<p>";
		cad+= contractContent + "<br /><br />";
		cad+= "<b>Contribución realizada -> </b>" + cont + "<br /><br />";
		cad+= "<b>Recompensas Obtenidas:</b><br />";
		
		List<Reward> recom = project.rewards;
		int i = 1;
		for (Reward m : recom) {
			if (m.recompensa <= cont) {
				cad+="Recompensa " + i + ": " + m.descripcion + " -> " + m.recompensa + "<br />";
				i++;
			}
		}
		cad+="</p>";
		return cad;
	}
	
	private String createContractContent2(Project project, String contractContent, Double cont) {
		String cad = "";
		if (project.rewards.size()>0) {
			cad = "Recompensas Obtenidas:\n";
			
			List<Reward> recom = project.rewards;
			int i = 1;
			for (Reward m : recom) {
				if (m.recompensa <= cont) {
					cad+="Recompensa " + i + ": " + m.descripcion + " -> " + m.recompensa + "\n";
					i++;
				}
			}
			cad+="\n";
		}else {
			cad = "Ninguna recompensa";
		}
		
		return cad;
	}
	
	


}
