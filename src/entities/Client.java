package src.entities;

import java.io.*;
import java.util.*;

public interface Client {
    public String username = null; //client username
    public String password = null;

    public static boolean login(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

    public boolean createAccount(Arraylist<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException;

}
