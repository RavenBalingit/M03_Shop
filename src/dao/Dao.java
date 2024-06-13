package dao;

import model.Employee; 
import model.Client;



public interface Dao {
    
    void connect();
    
    Employee getEmployee(int employeeId, String password);
    
    Client getMember_id(int memberId);

    
    void disconnect();

	
}
