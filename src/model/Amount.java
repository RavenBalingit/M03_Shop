package model;

public class Amount {
	private double value;
	private final String currency = "€";
	
	public Amount(double value) {
		super();
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double Value) {
		this.value = Value;
	}
	
	@Override
	public String toString(){
		return value + currency;
	}
}
