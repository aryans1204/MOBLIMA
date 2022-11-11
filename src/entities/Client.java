package src.entities;

import java.io.IOException;
import java.util.ArrayList;

public interface Client {
    String username = null; //client username
    String password = null;

    //not allowed to use static in interface, ide suggested using public boolean login()
    boolean login(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

    boolean createAccount(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

}
