package dao;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {
    
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;
    private ObjectId id;
    
    @Override
    public void connect() {
        if (mongoClient == null) { 
            String uri = "mongodb://localhost:27017";
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(mongoClientURI);
            mongoDatabase = mongoClient.getDatabase("M06Tienda");
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        collection = mongoDatabase.getCollection("users");
        Document document = collection.find().first();
        if (document == null) {
            document = new Document("_id", new ObjectId())
                .append("user", employeeId)
                .append("password", password);
            collection.insertOne(document);
            System.out.println("document inserted" + document);
            this.id = new ObjectId();
        } 
        document = collection.find(Filters.and(Filters.eq("user", employeeId), Filters.eq("password", password))).first();
        if (document != null) {
            return new Employee(document.getInteger("user"), document.getString("password"));
        } else {
            return null;
        }
    }

    @Override
    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    @Override
    public ArrayList<Product> getInventory() {
        ArrayList<Product> products = new ArrayList<Product>();
        
        collection = mongoDatabase.getCollection("inventory");
        Document document = collection.find().first();
        if (document == null) {
            addDefaultProducts();
        } 
        
        for (Document doc : collection.find()) {
            String name = doc.getString("name");
            boolean available = doc.getBoolean("available", true);
            int stock = doc.getInteger("stock", 0);

            Double wholesalerPriceValue = null;
            Object wholesalerPriceObj = doc.get("wholesalerPrice");
            if (wholesalerPriceObj instanceof Document) {
                Document wholesalerPriceDoc = (Document) wholesalerPriceObj;
                wholesalerPriceValue = wholesalerPriceDoc.getDouble("value");
            }
            
            if (wholesalerPriceValue == null) {
                System.err.println("Warning: Product '" + name + "' has no valid wholesalerPrice. Setting default to 0.0.");
                wholesalerPriceValue = 0.0;
            }
            
            System.out.println("document read on list " + ": " + doc.toJson());
            Product product = new Product(name, new Amount(wholesalerPriceValue), stock, available);
            products.add(product);
        }
        
        return products;
    }

    @Override
    public boolean writeInventory(ArrayList<Product> inventory) {
        collection = mongoDatabase.getCollection("historical_inventory");
        
        for (Product productData : inventory) {
            LocalDateTime dateTimeNow = LocalDateTime.now();
            
            productData.getWholesalerPrice();
            Document document = new Document("_id", new ObjectId())
                .append("id", productData.getId())
                .append("name", productData.getName())
                .append("wholesalerPrice", new Document("value", productData.getWholesalerPrice().getValue()).append("currency", Amount.getCurrency()))
                .append("available", productData.getAvailable())
                .append("stock", productData.getStock())
                .append("created_at", dateTimeNow);
                
            collection.insertOne(document);
        }
        
        return true;
    }

    @Override
    public boolean addProduct(String name, Amount price, int stock, boolean avaiblable) {
        collection = mongoDatabase.getCollection("inventory");
        int newId = 0;
        Document lastDocument = collection.find().sort(Sorts.descending("id")).first();
        if (lastDocument != null) {
            Integer lastId = lastDocument.getInteger("id");
            if (lastId != null) {
                newId = lastId + 1;
            }
        }
        
        Document document = new Document("_id", new ObjectId())
            .append("name", name)
            .append("wholesalerPrice", new Document("value", price.getValue()).append("currency", price.getCurrency()))
            .append("available", avaiblable)
            .append("stock", stock)
            .append("id", newId);
        collection.insertOne(document);

        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        collection = mongoDatabase.getCollection("inventory");
        
        Document document = collection.find(Filters.regex("name", "^" + product.getName() + "$", "i")).first();
        if (document == null) {
            return false;
        } else {
            collection.updateOne(document, Updates.set("stock", product.getStock()));
            return true;
        }
    }

    @Override
    public boolean addStockProduct(String name, int stock) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteProduct(String name) {
        collection = mongoDatabase.getCollection("inventory");
        Document document = collection.find(Filters.regex("name", "^" + name + "$", "i")).first();
        
        if (document == null) {
            System.out.println("FAIL PROCCESS");
            return false;
        } else {
            System.out.println("OK PROCCESS");
            collection.deleteOne(document);
            return true;
        }                   
    }
    
    private void addDefaultProducts() {
        Document product1 = new Document("name", "Manzana")
            .append("wholesalerPrice", new Document("value", 10.0).append("currency", "€"))
            .append("available", true)
            .append("stock", 10)
            .append("id", 1);
            
        Document product2 = new Document("name", "Pera")
            .append("wholesalerPrice", new Document("value", 20.0).append("currency", "€"))
            .append("available", true)            
            .append("stock", 20)
            .append("id", 2);
        
        Document product3 = new Document("name", "Hamburguesa")
            .append("wholesalerPrice", new Document("value", 30.0).append("currency", "€"))
            .append("available", true)
            .append("stock", 30)
            .append("id", 3);
        
        Document product4 = new Document("name", "Fresa")
            .append("wholesalerPrice", new Document("value", 5.0).append("currency", "€"))
            .append("available", true)            
            .append("stock", 20)
            .append("id", 4);

        collection.insertMany(Arrays.asList(product1, product2, product3, product4));
    }
}