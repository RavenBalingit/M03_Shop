package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "products")
public class ProductList {
    private List<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    public ProductList(List<Product> products) {
        this.products = products;
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @XmlAttribute(name = "total")
    public int getTotal() {
        return products != null ? products.size() : 0;
    }

    public void setTotal(int total) {
        // No es necesario, se calcula automáticamente
    }

    @Override
    public String toString() {
        return "ProductList [products=" + products + "]";
    }
}
