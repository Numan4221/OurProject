package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public enum paymentMethod {CREDITCARD,PAYPAL}
	
	public String nickname;
	private String password;
	public String email;
	public String name;
	public String surname;
	private paymentMethod myPaymentMethod;
	public boolean isDeveloper;
	

	@ManyToMany(mappedBy="contributors")
	private List<Project> financedProjects = new ArrayList<Project>();
	
	@OneToMany(mappedBy="contributor")
	private List<Contract> myContracts = new ArrayList<Contract>();
	
	

	/***
	 * Constructor sin parametros para la base de datos
	 */
	protected User() {
	}
	
	/***
	 * Constructor utilizado por la aplicación
	 * @param nickname
	 * @param password
	 * @param email
	 * @param name
	 * @param surname
	 */
	public User(String nickname, String password, String email, String name, String surname) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		isDeveloper = false;
	}

	public List<Project> getFinancedProjects() {
		return financedProjects;
	}

	public void setFinancedProjects(List<Project> financedProjects) {
		this.financedProjects = financedProjects;
	}


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public paymentMethod getMyPaymentMethod() {
		return myPaymentMethod;
	}



	public void setMyPaymentMethod(paymentMethod myPaymentMethod) {
		this.myPaymentMethod = myPaymentMethod;
	}

	public List<Contract> getMyContracts() {
		return myContracts;
	}

	public void setMyContracts(List<Contract> myContracts) {
		this.myContracts = myContracts;
	}
	
	
	
	
	
	
	
}
