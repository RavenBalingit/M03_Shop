package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"available", "wholesalerPrice", "publicPrice", "stock"}) // Orden en el XML

@Entity
@Table(name = "inventory")
public class Product implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	@Column(name = "available")
    private boolean available;
	@Column(name = "name")
    private String name;
    @Transient
    private Amount wholesalerPrice;
    @Transient
    private Amount publicPrice;
    @Column(name = "price")
    private double price;
    @Column(name = "stock")
    private int stock;
    @Transient
    public static int totalProducts = 0;

    public Product(String name, Amount wholesalerPrice,  int stock, boolean available) {
        this.id = ++totalProducts;
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
        this.available = available;
        this.stock = stock;
    }
    
    public Product(int id, String name, Amount wholesalerPrice, Amount publicPrice,  int stock, boolean available) {
        this.id = id;
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.publicPrice = publicPrice;
        this.available = available;
        this.stock = stock;
        this.totalProducts++;
    }

    public Product() {
        this.id = ++totalProducts;
    }

    @PostLoad
    private void calculatePrices() {
        this.publicPrice = new Amount(this.price * 2); // price = publicPrice * 2
        this.wholesalerPrice = new Amount(this.price); // wholesalerPrice = price

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

    @XmlElement(name = "wholesalerPrice")
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
    
    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void expire() {
		setAvailable(false);
	}

	@Override
    public String toString() {
        return "Product [id=" + id + "name=" + name + ", wholesalerPrice=" + wholesalerPrice + ", publicPrice=" + publicPrice + ", stock=" + stock + ", available=" + available + "]";
    }
}
