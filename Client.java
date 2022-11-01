import java.io.*;

import Cinema;

public interface Client {
    public String username = null; //client username
    public String password = null;

    public boolean login(CustomerController c) throws IOException;  //returns successful login or not, login also initializes state for client

    public boolean createAccount(CustomerController c) throws IOException; //returns true if account creation was successful, false otherwise. for staff, it must ask for cinema as well.

    public void listTopFive(String criterion, MovieController m);  //list top five movies based on Ticket Sales or Movie reviews.
}
