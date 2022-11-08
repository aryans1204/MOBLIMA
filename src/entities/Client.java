package entities;

import java.io.*;
import java.util.*;

public interface Client {
    public String username = null; //client username
    public String password = null;

    public boolean login(ArrayList<Object> clientDB) throws IOException;

    public boolean createAccount(ArrayList<Object> clientDB) throws IOException;

}
