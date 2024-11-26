package dao;

import java.util.ArrayList;
import java.util.List;

import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;
import dao.xml.DomWriter;
import model.Employee;
import model.Product;
import model.ProductList;

public class DaoImplJaxb implements Dao{

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = null;    
	    try {
			JaxbUnMarshaller unMarshaller = new JaxbUnMarshaller();
		    products = unMarshaller.init();
	    }catch (Exception e) {
			e.printStackTrace();
		}
	    return products;
	}


	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		
		try {
			ProductList products = new ProductList(inventory);
			JaxbMarshaller marshaller = new JaxbMarshaller();
			return marshaller.init(products);

		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
