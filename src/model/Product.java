package model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"available", "wholesalerPrice", "publicPrice", "stock"}) // Orden en el XML
public class Product {
    private int id;
    private String name;
    private boolean available;
    private Amount wholesalerPrice;
    private Amount publicPrice;
    private int stock;
    public static int totalProducts;

    public Product(String name, Amount wholesalerPrice,  int stock, boolean available) {
        this.id = ++totalProducts;
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.publicPrice = new Amount(wholesalerPrice.getValue() * 2); // PublicPrice se calcula aquí
        this.available = available;
        this.stock = stock;
    }

    public Product() {
        this.id = ++totalProducts; // Constructor por defecto
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public boolean getAvailable() {    	
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @XmlElement(name = "wholesalerPrise")
    public Amount getWholesalerPrice() {    	
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
    	this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
        this.wholesalerPrice = wholesalerPrice;
    }

    @XmlElement(name = "publicPrice")
    public Amount getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(Amount publicPrice) {
        this.publicPrice = publicPrice;
    }

    @XmlElement(name = "stock")
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.available = stock > 0;
    }
    
    public void expire() {
		setAvailable(false);
	}
    
    @Override
    public String toString() {
        return "Product [name=" + name + ", publicPrice=" + publicPrice + ", stock=" + stock + "]";
    }
}
