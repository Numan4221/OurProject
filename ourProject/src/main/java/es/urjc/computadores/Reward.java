package es.urjc.computadores;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reward {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	double recompensa;
	String descripcion;
	
	@ManyToOne
	public Project project;
	
	protected Reward() {
		
	}
	
	public Reward(double recompensa, String descripcion, Project project) {
		super();
		this.recompensa = recompensa;
		this.descripcion = descripcion;
		this.project = project;
	}
	
	
	

}
