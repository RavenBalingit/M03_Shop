package model;


public class Employee extends Person implements main.Logable {
	private int employeeId;
	final static int USER = 123;
	final static String PASSWORD = "test";

	public Employee(String name) {
		super(name);
		
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public boolean login(int user, String password) {
		if (user == USER && password.equals(PASSWORD)) {
			return true;
		}
		return false;
	}



}
