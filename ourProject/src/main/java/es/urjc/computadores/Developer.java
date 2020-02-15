package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Developer extends User {
	
	@OneToMany(mappedBy="developer", cascade = CascadeType.REMOVE)
	private List<Project> myProjects = new ArrayList<Project>();
	

	public List<Project> getMyProjects() {
		return myProjects;
	}

	public void setMyProjects(List<Project> myProjects) {
		this.myProjects = myProjects;
	}

	protected Developer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Developer(String nickname, String password, String email, String name, String surname) {
		super(nickname, password, email, name, surname);
		isDeveloper = true;
		// TODO Auto-generated constructor stub
	}
	
	public Developer(User us) {
		super(us.nickname,us.getPassword(),us.email,us.name,us.surname);
		isDeveloper = true;
	}
	
	
	public void createProject() {
		
	}
	
	public void deleteProject() {
		
	}
	
	public void editProject() {
		
	}
	

}
