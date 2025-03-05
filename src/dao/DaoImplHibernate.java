package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Amount;
import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao{

	private Connection conn;
	private SessionFactory factory;
	
	@Override
	public void connect() {
		String url = "jdbc:mysql://localhost:8889/TiendaM06";
		String user = "root";
		String pass = "root";
		try {
			this.conn = DriverManager.getConnection(url, user, pass);
			factory = new Configuration().configure("main/resources/hibernate.cfg.xml").addAnnotatedClass(Product.class).buildSessionFactory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployee(int user, String password) {
	    Employee employee = null;
	    try (Session session = factory.openSession()) {
	        session.beginTransaction();

	        employee = session.get(Employee.class, user);

	        session.getTransaction().commit();
	    } catch (Exception e) {
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
		ArrayList<Product> products = null;
		try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            
            EntityManager em = session.getEntityManagerFactory().createEntityManager();
            TypedQuery<Product> query = em.createQuery("from Product", Product.class);
            products = (ArrayList<Product>) query.getResultList();
            
            
            session.getTransaction().commit();
            
            return products;
        }catch (Exception e) {
        	e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		try(Session session = factory.getCurrentSession()) {
			session.beginTransaction();
            
			for(Product product : inventory) {
				ProductHistory history = new ProductHistory();
	            history.setIdProducto(product);
	            history.setAvailable(product.getAvailable());
	            history.setCreatedAt(LocalDateTime.now());
	            history.setName(product.getName());
	            history.setPrice(product.getPublicPrice().getValue());
	            history.setStock(product.getStock());
	            
	            session.save(history);
			}
			
            session.getTransaction().commit();

            return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addProduct(String name, Amount price, int stock, boolean avaiblable) {
		try(Session session = factory.getCurrentSession()) {
			session.beginTransaction();
            
			Product newProduct = new Product();
			newProduct.setAvailable(avaiblable);
			newProduct.setName(name);
			newProduct.setWholesalerPrice(price);
			newProduct.setPrice(price.getValue());
			newProduct.setStock(stock);
            
            session.save(newProduct);
			
            session.getTransaction().commit();

            return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addStockProduct(String name, int stock) {
		try(Session session = factory.getCurrentSession()) {
			session.beginTransaction();
            
			String hql = "FROM Product WHERE lower(name) = lower(:name)";
	        Product product = session.createQuery(hql, Product.class)
	                                 .setParameter("name", name)
	                                 .uniqueResult();
			
	        product.setStock(product.getStock() + stock);
            session.update(product);
            
            session.getTransaction().commit();

            return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteProduct(String name) {
	    try (Session session = factory.getCurrentSession()) {
	        session.beginTransaction();

	        // Buscar el producto
	        String hql = "FROM Product WHERE lower(name) = lower(:name)";
	        Product product = session.createQuery(hql, Product.class)
	                                 .setParameter("name", name.toLowerCase()) // Asegurar minúsculas
	                                 .uniqueResult();

	        if (product != null) { // Verificar que el producto exista
	            session.delete(product);
	            session.getTransaction().commit();
	            return true;
	        } else {
	            session.getTransaction().rollback(); // No eliminar nada si el producto no existe
	            System.out.println("Producto no encontrado: " + name);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

}
