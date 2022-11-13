package src.entities;

import java.io.IOException;

/**
 * Represents an interface for Clients
 * To be inherited by Staff and Customer
 *
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
     *
     * @param username System will look for this username in the database
     * @return Returns true for successful login and false for unsuccessful login
     * @throws IOException
     */

    boolean login(String username) throws IOException;

    /**
     * Getter for client username
     *
     * @return client username
     */
    String getUsername();

    /**
     * Setter for Client username
     *
     * @param username client username
     */
    void setUsername(String username);

    /**
     * Getter for client password
     *
     * @return client password
     */
    String getPassword();

    /**
     * Setter for client password
     *
     * @param password client password
     */
    void setPassword(String password);

}
