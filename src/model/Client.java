package model;

import main.Payable;
import dao.Dao; 
import dao.DaoImplJDBC;

public class Client extends Person implements Payable {

	private int member_id;
	private Amount balance;
    public Dao dao;

	
	public Client(String name) {
		super(name);
        this.dao = new DaoImplJDBC();
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
	



	@Override
	public boolean findClient(int member_id) {
		  dao.connect();
	        Client client = dao.getMember_id(member_id);
	        dao.disconnect();

	        if (client != null) {
	            this.member_id = client.getMember_id();
	            this.balance = client.getBalance();
	            
	            return true;
	        }
	        return false;
	}

	
	
}
