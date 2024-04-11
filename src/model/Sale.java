package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Client;

public class Sale {
	private Client client;
	ArrayList<Product> products;
	Amount amount;
	LocalDateTime date;

	public Sale(Client client, ArrayList<Product> products, Amount amount, LocalDateTime date) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
		this.date = LocalDateTime.now();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String productNames ="";
		String client = "\nClient:" +  this.getClient() + "\n" ;
		for (Product product : this.products) {
			if (product != null) {
				if (product.getName() != null) {
					productNames += product.getName() + " Cost: " + product.getPublicPrice() +"\n";
				
				}
			}
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
		
		String salesDate=date.format(format);
		
		return client + "products:\n" + productNames + "amount=[" + amount + "] + Fecha y hora de venta" + salesDate +"\n";

	}

}
