package model;

import java.sql.SQLException;

import dao.DaoImplJDBC;

public class Employee extends Person {

	private int employeeId;
	
	final static int USER = 123;
    private String password = "test";

	public Employee(int employeeId, String password) {
		super(null);
		this.employeeId = employeeId;
		this.password = password;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public boolean login(int user, String password) {
    	
	// Connect to database data.	
/*
		  DaoImplJDBC dao = new DaoImplJDBC();
		  
		  dao.connect(); System.out.println("Conectado"); Employee employee =
		  dao.getEmployee(user, password); try { dao.disconnect(); } catch
		  (SQLException e) { e.printStackTrace(); }
		  
		  if(employee == null) { return false; }else{ return true; }
*/		 
		
		
	// Simple form of Login.
		  
		if((user == USER) && (password == this.password)){
			return true;
		}else {
			return false;
		}

    }
    
}
