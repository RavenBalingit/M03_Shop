package model;

import dao.Dao; 
import dao.DaoImplJDBC;

public class Employee extends Person implements main.Logable {
    private int employeeId;
    private String password;
    private Dao dao;

    public Employee(String name) {
        super(name);
        this.dao = new DaoImplJDBC();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean login(int user, String password) {
        dao.connect();
        Employee employee = dao.getEmployee(user, password);
        dao.disconnect();

        if (employee != null) {
            this.employeeId = employee.getEmployeeId();
            this.setName(employee.getName());
            this.password = employee.getPassword();
            return true;
        }
        return false;
    }
}
