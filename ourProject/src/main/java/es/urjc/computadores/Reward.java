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
		Reward other = (Reward) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
