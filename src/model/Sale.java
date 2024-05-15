package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {
	Client client;
	private  ArrayList<Product> products;
	Amount amount;
	LocalDateTime time;

	public Sale(Client client, ArrayList<Product> products , Amount amount, LocalDateTime time) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
		this.time = time;
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

	@Override
	public String toString() {
		String productNames ="";
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String client = "\nClient:" +  this.client.getName().toUpperCase() + "\n" ;
		for (Product product : this.products) {
			if (product != null) {
				if (product.getName() != null) {
					productNames += product.getName() + " Cost: " + product.getPublicPrice() +"\n";
				
				}
			}
		}
		return client + "products:\n" + productNames + "amount=[" + amount + "]\n" + "time=[" + time.format(myFormatObj) + "]";

	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
