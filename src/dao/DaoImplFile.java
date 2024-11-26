package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXResult;

import org.xml.sax.SAXException;

import dao.xml.SaxReader;
import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplFile implements Dao{

	public void connect() {
		// TODO Auto-generated method stub	
	}

	
	public void disconnect() {
		
	}

	
	public ArrayList<Product> getInventory() {
		
		ArrayList<Product> products = null;	
		ArrayList<Product> inventoryLoader = new ArrayList<>(); try (BufferedReader
		br = new BufferedReader(new FileReader(System.getProperty("user.dir") +
		File.separator + "files" + File.separator + "inputInventory.txt"))) { String
		line;
	  
		while ((line = br.readLine()) != null) { String[] sections = line.split(";");
		String name = ""; double wholesalerPrice = 0.0; int stock = 0;
	  
		for (int i = 0; i < sections.length; i++) { String[] data =
		sections[i].split(":");
	  
		switch (i) { case 0: name = data[1]; break; case 1: wholesalerPrice =
		Double.parseDouble(data[1]); break; case 2: stock =
		Integer.parseInt(data[1]); break; default: break; } } inventoryLoader.add(new
		Product(name, new Amount(wholesalerPrice), stock, true)); }
	  
		} catch (FileNotFoundException e) {
		System.out.println("Archivo de inventario no encontrado.");
		e.printStackTrace(); } catch (IOException e) {
		System.out.println("Error al leer el archivo de inventario.");
		e.printStackTrace(); }
		 
		return products;
        
    }

	
	public boolean writeInventory(ArrayList<Product> inventory) {
	    boolean Written = false;
	    int numberProducts = 0;
	    try {
	        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	        
	        String dirPath = System.getProperty("user.dir") + File.separator + "files";
	        
	        File file = new File(dirPath + File.separator + "inventory_" + date + ".txt");

	        try (FileWriter myWriter = new FileWriter(file)) {
	        	
	            for (Product product : inventory) {
	                if (product != null) {
	                	numberProducts++;
	                    myWriter.write(numberProducts+"Product:" + product.getName()+ ";Stock:" + product.getStock() + ";\n");
	                }
	            }
	            myWriter.write("Numero total de productos: " + numberProducts + ";\n");
	            System.out.println("File inventory finished: " + file.getName());
	            Written = true;
	        }

	    } catch (IOException e) {
	    	Written = false;
	        System.out.println("Error al escribir el archivo de inventario.");
	        e.printStackTrace();
	        return Written;
	    }
	    return Written;
	}

	public Employee getEmployee(int employeeId, String password) {
		return null;
	}

}
