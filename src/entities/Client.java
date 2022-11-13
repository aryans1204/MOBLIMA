package src.entities;

import java.io.IOException;

public interface Client {
    String username = null; //client username
    String password = null;

    //not allowed to use static in interface, ide suggested using public boolean login()
    boolean login(String username) throws IOException;

    String getUsername();

    void setUsername(String username);

    String getPassword();
    
    void setPassword(String password);

}
