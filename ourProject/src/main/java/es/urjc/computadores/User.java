package es.urjc.computadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	enum paymentMethod {CREDITCARD,PAYPAL}
	
	public String nickname;
	private String password;
	public String email;
	public String name;
	public String surname;
	private paymentMethod myPaymentMethod;
	
	
	// spring necesita un constructor sin parametros
	protected User() {
		
	}



	public User(String nickname, String password) {
		super();
		this.nickname = nickname;
		this.password = password;
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
