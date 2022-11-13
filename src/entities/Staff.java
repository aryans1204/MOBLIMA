package src.entities;

import src.controllers.ClientController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

//ide suggested @SuppressWarnings("serial")
@SuppressWarnings("serial")
/**
 * Entity class that represents a Staff (Admin)
 * @author Aryan
 */
public class Staff implements Client, Serializable {
    /**
     * username of the Staff
     */
    private String username;
    /**
     * password of th Staff
     */
    private String password;
    /**
     * authentication indicator
     */
    private boolean auth = false;
    /**
     * associated cinema of the staff
     */
    private Cinema cinema;
    /**
     * associated ClientController
     */
    private static ClientController cl;

    /**
     * @param username represents username of the Staff
     * @param password represents password of the Staff
     * @param cinema   represents Cinema where Staff works
     *                 Constructor for the Staff
     */
    public Staff(String username, String password, Cinema cinema) {
        this.username = username;
        this.password = password;
        this.cinema = cinema;
    }

    /**
     * Method to login a Staff
     *
     * @param username represents username of taff for login purposes
     * @return boolean
     * @throws IOException
     */
    public boolean login(String username) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter password: ");
        String password = sc.nextLine();
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

    /**
     * Getter for the Staff username
     *
     * @return String username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for the Staff password
     *
     * @return String password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for the Cinema of the Staff
     *
     * @return Cinema associated cinema
     */
    public Cinema getCinema() {
        return this.cinema;
    }

    /**
     * Getter for the uuthentication variable of Staff
     *
     * @return boolean whether authenticated
     */
    public boolean isAuth() {
        return auth;
    }


    /**
     * Set authentication to true in login
     *
     * @param auth true or false
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * Setter for username of Staff
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for password of Staff
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for Cinema of Staff
     *
     * @param cinema associated Cinema
     */
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

}
