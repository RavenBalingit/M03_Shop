package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public interface Dao {

	void connect();
	
	Employee getEmployee(int employeeId, String password);
	
	void disconnect();
	
	ArrayList<Product> getInventory();
	
	boolean writeInventory(ArrayList<Product> inventory);

	boolean addProduct(String name, Amount price, int stock, boolean avaiblable);
	
	boolean updateProduct(Product product);

	boolean addStockProduct(String name, int stock);

	boolean deleteProduct(String name);
	
}
