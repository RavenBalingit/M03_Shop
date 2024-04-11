package model;

import main.Payable;

public class Client extends Person implements Payable {
    private int memberId;
    private Amount balance;
    private static final int MEMBER_ID = 456;
    private static final double INITIAL_BALANCE = 50.00;

    public Client() {
        this.memberId = MEMBER_ID; 
        this.balance = new Amount(INITIAL_BALANCE); 
    }

    @Override
    public boolean pay(Amount amount) {
        double newBalance = this.balance.getValue() - amount.getValue();
        if (newBalance >= 0) {
            this.balance.setValue(newBalance);
            return true;
        }
        return false; 
    }

    // Getters y Setters
    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Amount getBalance() {
        return this.balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @Override
    public String getName() {
        return this.name; // Asumiendo que Person tiene un atributo 'name' protegido
    }

    public void setName(String name) {
        this.name = name; // Asumiendo que Person tiene un atributo 'name' protegido
    }
}
