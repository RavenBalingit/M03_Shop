package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

	private Connection conn;
	
	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:8889/TiendaM06";
		String user = "root";
		String pass = "root";
		try {
			this.conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee(int user, String password) {
		
		Employee employee = null;
		int id = 0;
		String query = "SELECT employeeId, user, employeePassword FROM Employee where employeeId = ? AND employeePassword = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) { 
			// set id to search for
			ps.setInt(1,user);
			ps.setString(2,password);
		  	//System.out.println(ps.toString());
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		employee =  new Employee(rs.getInt(1), rs.getString(2));            		            				
	        	}
	        }
	        System.out.println("Employee loged OK");
	    } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void disconnect() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		
		ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Inventory";
        try (PreparedStatement ps = conn.prepareStatement(query)) { 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    boolean available = rs.getBoolean("available");
                    double wholesalerPrice = rs.getDouble("wholesalerPrice");
                    double publicPrice = rs.getDouble("publicPrice");
                    int stock = rs.getInt("stock");

                    Product product = new Product(id, name, new Amount(wholesalerPrice), new Amount(publicPrice), stock, available);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		
	    String insertQuery = "INSERT INTO historical_inventory (id_producto, name, wholesalerPrice, available, stock, created_at) "
	            + "VALUES (?, ?, ?, ?, ?, ?)";
	   

	    Set<String> existingProductNames = new HashSet<>();
	    String loadExistingNamesQuery = "SELECT name FROM historical_inventory";
	    try (PreparedStatement loadPs = conn.prepareStatement(loadExistingNamesQuery);
	         ResultSet rs = loadPs.executeQuery()) {
	        while (rs.next()) {
	            existingProductNames.add(rs.getString("name"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }

	    try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String currentTime = LocalDateTime.now().format(formatter);

	        for (Product product : inventory) {
	                // Insertar el registro si el nombre no existe
	                insertPs.setInt(1, product.getId());
	                insertPs.setString(2, product.getName());
	                insertPs.setDouble(3, product.getWholesalerPrice().getValue());
	                insertPs.setBoolean(4, product.getAvailable());
	                insertPs.setInt(5, product.getStock());
	                insertPs.setString(6, currentTime);
	                
	                insertPs.addBatch();
	        }

	        insertPs.executeBatch();

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}



	@Override
	public boolean addProduct(String name, Amount price, int stock, boolean avaiblable) {
		
		String insertQuery = "INSERT INTO Inventory (name, available, wholesalerPrice, publicPrice, stock) "
                + "VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {
	        
		    ps.setString(1, name);
		    ps.setBoolean(2, avaiblable);
		    ps.setDouble(3, price.getValue());
		    ps.setDouble(4, price.getValue()*2);
		    ps.setInt(5, stock);
		    
		    ps.addBatch();
		
		ps.executeBatch();
		return true;
		} catch (SQLException e) {
		e.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean addStockProduct(String name, int stock) {
		
		String updateQuery = "UPDATE Inventory SET stock = stock + ? WHERE name = ?";
		try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
	        
		    ps.setInt(1, stock);
		    ps.setString(2, name);
		    
		    ps.addBatch();
		
		ps.executeBatch();
		return true;
		} catch (SQLException e) {
		e.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean deleteProduct(String name) {
		
		String deleteQuery = "DELETE FROM Inventory WHERE name = ?";
		try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
			ps.setString(1, name);
	        int rowsAffected = ps.executeUpdate();
	        System.out.println("Filas eliminadas: " + rowsAffected);
	        return rowsAffected > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;		
		}
	}

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}



}
