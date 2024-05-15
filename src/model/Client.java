package model;

import main.Payable;

public class Client extends Person implements Payable {
	
	final int MEMBER_ID = 456;
	final double BALANCE = 50;
	private int member_id = MEMBER_ID;
	private Amount balance = new Amount (BALANCE);
	

	
	public Client(String name) {
		super(name);
	}

	public boolean pay(Amount amount) {
	this.balance.setValue(this.balance.getValue() - amount.getValue());
	if(this.balance.getValue() < 0) {
		return false;
	}
	return true;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}
}
