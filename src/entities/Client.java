package src.entities;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents an interface for Clients
 * To be inherited by Staff and Customer
 * @author Aryan
 */
public interface Client {
    /**
     * Client username
     */
    String username = null; //client username
    /**
     * Client password
     */
    String password = null;

    /**
     * Abstract method for logging into the system
     * @param username      System will look for this username in the database
     * @return              Returns true for successful login and false for unsuccessful login
     * @throws IOException
     */
    //not allowed to use static in interface, ide suggested using public boolean login()
    boolean login(String username) throws IOException;


}
