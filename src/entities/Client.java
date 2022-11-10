package src.entities;

import java.io.*;
import java.util.*;

public interface Client {
    public String username = null; //client username
    public String password = null;
    
    //not allowed to use static in interface, ide suggested using public boolean login()
    public static boolean login(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

    public boolean createAccount(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

}
