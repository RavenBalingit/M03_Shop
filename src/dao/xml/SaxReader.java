package dao.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {

    private ArrayList<Product> products = new ArrayList<>();
    private Product product;
    private StringBuilder buffer = new StringBuilder();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "product":
                String productName = attributes.getValue("name");
                product = new Product(productName, new Amount(0), 0, true);
                break;
            case "WholesalerPrise":
                buffer.setLength(0);
                break;
            case "Stock":
                buffer.setLength(0);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "WholesalerPrise":
                double wholesalerPrice = Double.parseDouble(buffer.toString());
                product.setWholesalerPrice(new Amount(wholesalerPrice));
                product.setPublicPrice(new Amount(wholesalerPrice * 2));
                break;
            case "Stock":
                int stock = Integer.parseInt(buffer.toString());
                product.setStock(stock);
                product.setAvailable(stock > 0);
                break;
            case "product":
                products.add(product);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }
}
