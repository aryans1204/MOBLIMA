import java.io.*;

public interface Client {
    public String username; //client username
    
    public boolean login(String username, String password, Database db);  //returns successful login or not, login also initializes state for client

    public boolean createAccount(Database db); //returns true if account creation was successful, false otherwise. for staff, it must ask for cinema as well.

    public void checkMovieListings(Cinema cinema);  //view all the movie listings for a given Cinema.
}
