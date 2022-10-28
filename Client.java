import java.io.*;

import Cinema;

public interface Client {
    public String username; //client username

    public boolean login(String username, String password);  //returns successful login or not, login also initializes state for client

    public boolean createAccount(); //returns true if account creation was successful, false otherwise. for staff, it must ask for cinema as well.

    public listTopFive(String criterion);  //list top five movies based on Ticket Sales or Movie reviews.
}
