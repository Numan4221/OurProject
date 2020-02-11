package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	enum paymentMethod {CREDITCARD,PAYPAL}
	
	public String nickname;
	private String password;
	public String email;
	public String name;
	public String surname;
	private paymentMethod myPaymentMethod;
	
	@ManyToMany
	private List<Project> financedProjects = new ArrayList<Project>();
	
	

	/*
	 * Constructor sin parametros para la base de datos
	 */
	protected User() {
		
	}
	
	/*
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
	
	
	
	
	
	
	
}
