package model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "wholesalerPrise")
public class Amount {
    private double value;

    @XmlAttribute(name = "currency")
    private final static String currency = "€";

    public Amount() {
        // Constructor por defecto
    }

    public Amount(double value) {
        this.value = value;
    }

    @XmlValue
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return value + currency;
    }
}
