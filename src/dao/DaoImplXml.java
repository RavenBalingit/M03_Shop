package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.xml.DomWriter;
import dao.xml.SaxReader;
import model.Employee;
import model.Product;

public class DaoImplXml implements Dao{

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
			SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
			SAXParser saxParse = saxParseFactory.newSAXParser();
			File file = new File("xml/inputInventory.xml");
			SaxReader handler = new SaxReader();
			saxParse.parse(file, handler);
			
			products = handler.getProducts();
			
		// Print all the information in products
			System.out.println("Inventory loaded");
			for(Product product : products) {
				System.out.println(product);
			}
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		
		DomWriter domWriter = new DomWriter(inventory);
		return domWriter.generateDocument();
	}

}
