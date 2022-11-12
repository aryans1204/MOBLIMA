package SC2002Link.src.entities;

import SC2002Link.src.controllers.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

//ide suggested @SuppressWarnings("serial")
@SuppressWarnings("serial")
public class Staff implements Client, Serializable {
    private String username;
    private String password;
    private boolean auth = false;
    private Cinema cinema;   //associated cinema of the staff. Where the staff works
    private static ClientController cl;

    public Staff(String username, String password, Cinema cinema) {
        this.username = username;
        this.password = password;
        this.cinema = cinema;
    }

    public boolean login(String username) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter password: ");
        String password = reader.readLine();
        ArrayList<Staff> staffs = ClientController.getStaffList();

        for (Staff s : staffs) {
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                System.out.println("Authenticated successfully");

                //same as customer class, auth has to be static in a static method. either that or method becomes non static
                setAuth(true);
                return true;
            }
        }
        System.out.println("Username or password incorrect. Please try again later");
        return false;
    }


    public boolean createAccount(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tries = 9;  //9 tries before system shuts;
        String tempUsername;
        do {
            System.out.println("Enter username: ");
            String username = reader.readLine();
            tempUsername = username;


            if (cl.checkUsernameExist(tempUsername)) System.out.println("Username already exists, try another one!");
            tries--;
            if (tries == 0) System.out.println("Too many tries. System quitting now");
        }
        while (cl.checkUsernameExist(tempUsername) && tries != 0);
        if (tries == 0) return false;
        setUsername(tempUsername);
        System.out.println("Enter password: ");
        String password = reader.readLine();
        setPassword(password);
        System.out.println("Account created successfully");
        staffDB.add(this);
        return true;
    }

    //Getters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Cinema getCinema() {
        return this.cinema;
    }

    public boolean isAuth() {
		return auth;
	}


	//Setters
    public void setAuth(boolean auth) {
		this.auth = auth;
	}
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public static void setCl(ClientController cl) {
        Staff.cl = cl;
    }


    public ClientController getCl() {
        return cl;
    }
}
