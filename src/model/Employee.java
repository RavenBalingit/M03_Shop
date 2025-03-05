package model;

import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import dao.DaoImplHibernate;
import dao.DaoImplJDBC;

@Entity
@Table(name = "Employee")
public class Employee extends Person {
	
	@Id	
	@Column(name = "user")
	private int user;
	@Column(name = "password")
    private String password;

	public Employee(int user, String password) {
		super(null);
		this.user = user;
		this.password = password;
	}
	
	public Employee() {
		super(null);
	}

	
	public int getUser() {
		return user;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public boolean login(int user, String password) {
    	
		// Connect to database data.	
		//DaoImplJDBC dao = new DaoImplJDBC();
		DaoImplHibernate dao = new DaoImplHibernate();
		
	    dao.connect(); System.out.println("Conectado");
	    Employee employee = dao.getEmployee(user, password);
	    dao.disconnect();
	  
	    if(employee == null) { return false; }else{ return true; }		 
		
		
	// Simple form of Login.
	/*	  
		if((user == USER) && (password == this.password)){
			return true;
		}else {
			return false;
		}
	 */
    }
    
}
