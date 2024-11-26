package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

	private Connection conn;
	
	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/SHOPSILES2";
		String user = "root";
		String pass = "";
		try {
			this.conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee(int employeeId, String password) {
		
		Employee employee = null;
		String query = "select employeeId, employeePassword from Employee where employeeId = ? AND employeePassword = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) { 
			// set id to search for
			ps.setInt(1,employeeId);
			ps.setString(2,password);
		  	//System.out.println(ps.toString());
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		employee =  new Employee(rs.getInt(1), rs.getString(2));            		            				
	        	}
	        }
	    } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void disconnect() {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}


	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}



}
