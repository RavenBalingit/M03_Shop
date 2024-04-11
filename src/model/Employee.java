package model;

import main.Logable;

public class Employee extends Person implements Logable {
    private int employeeId;
    private static int USER = 123;
    private static String PASSWORD = "test";

    @Override
    public boolean login(int user, String password) {
        return user == USER && password.equals(PASSWORD);
    }
}
