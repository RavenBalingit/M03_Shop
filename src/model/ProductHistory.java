package model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {	

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id")
	    private int id;
		@Column(name = "available")
	    private boolean available;
		@Column(name = "created_at")
		private LocalDateTime createdAt;
	    @OneToOne
	    @JoinColumn(name = "id_producto")
		private Product idProducto;
		@Column(name = "name")
	    private String name;
	    @Column(name = "price")
	    private double price;
	    @Column(name = "stock")
	    private int stock;
	    
	    public ProductHistory() {
		}
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public boolean isAvailable() {
			return available;
		}
		public void setAvailable(boolean available) {
			this.available = available;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime localDateTime) {
			this.createdAt = localDateTime;
		}
		public Product getIdProducto() {
			return idProducto;
		}
		public void setIdProducto(Product idProducto) {
			this.idProducto = idProducto;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getStock() {
			return stock;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
	    
	    
}
