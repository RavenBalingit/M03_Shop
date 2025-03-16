package model;

import dao.DaoImplMongoDB;

public class Employee extends Person {
    
    private int user;
    private String password;
    private DaoImplMongoDB dao; 

    public Employee(int user, String password) {
        super(null);
        this.user = user;
        this.password = password;
        this.dao = new DaoImplMongoDB(); 
    }
    
    public Employee(DaoImplMongoDB dao) { 
        super(null);
        this.dao = dao;
    }

    public Employee() {
        super(null);
        this.dao = new DaoImplMongoDB();
    }

    public int getUser() {
        return user;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean login(int user, String password) {
        this.user = user; 
        this.password = password;
        
        dao.connect();
        Employee employee = dao.getEmployee(user, password);
        if (employee == null) {
            return false;
        } else {
            return true;
        }
    }
}