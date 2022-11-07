package entities;

import java.io.*;
public interface Client {
    public String username = null; //client username
    public String password = null;

    public boolean login(ArrayList<Cinema> cinemaDB) throws IOException;

    public boolean createAccount(ArrayList<Cinema> cinemaDB) throws IOException;

}
