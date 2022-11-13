package src.entities;

import src.controllers.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Customer/Movie-goer in the system
 */
@SuppressWarnings("serial")
public class Customer implements Client, Serializable {
    /**
     * Customer's email
     */
    private String email;
    /**
     * Customer's mobile number
     */
    private int mobileNumber;
    /**
     * Customer's name
     */
    private String name;
    /**
     * Customer's age
     */
    private int age;
    /**
     * Customer's authentication indicator. Whether customer is authenticated or not. All methods check for authentication.
     */
    private boolean auth = false;  //authentication indicator. Whether customer is authenticated or not. All methods must check for authentication.
    /**
     * Customer's username
     */
    private String username;
    /**
     * Customer's password
     */
    private String password;
    /**
     * Array list of Ticket object to store Customer's bookings
     */
    private ArrayList<Ticket> bookings = new ArrayList<>();
    /**
     * ClientController to get Customer's data from the database
     */
    private static ClientController cl;

    /**
     * Creates a Customer/Movie-goer with the given attributes
     * @param name			This Customer's name
     * @param age			This Customer's age
     * @param username		This Customer's username
     * @param password		This Customer's password
     * @param email			This Customer's email
     * @param mobileNumber	This Customer's mobile number
     */
    //Constructor for Customer object.
    public Customer(String name, int age, String username, String password, String email, int mobileNumber) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    /**
     * A method for logging into the system and set auth to True
     * @param tempUsername  System will look for this username in the database
     * @return  Returns true for successful login and false for unsuccessful login
     * @throws IOException
     */
    public boolean login(String tempUsername) throws IOException {
        int tries = 9;
        String tempPassword;

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        tempPassword = sc.nextLine();
        ArrayList<Customer> customers;

        customers = ClientController.getCustomerList();  //Fetch all customers details from DB
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(tempUsername) && customers.get(i).getPassword().equals(tempPassword)) {

                System.out.println("Authenticated successfully");

                //same as customer class, auth has to be static in a static method. either that or method becomes non static
                auth = true;
                return true;
            }
        }

        System.out.println("Username or password incorrect. Please try again.");

        return false;

    }

    /**
     * A method for Customer's to view their past bookings
     */
    public void viewBookings() {
        System.out.println("Your past bookings are available here");
        for (Ticket ticket : this.bookings) {
            if (ticket != null) System.out.println(ticket.toString());
        }
    }

    /**
     * A method to check the authentication status of the Customer
     * @return
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Gets the name of this Customer
     * @return  this Customer's name
     */
    //Getters
    public String getName() {
        return name;
    }

    /**
     * Gets the age of this Customer
     * @return  this Customer's age
     */
    public int getAge() {
        return age;
    }  //method to get the age of the client, as an int.

    /**
     * Gets the username of this Customer
     * @return  this Customer's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of this Customer
     * @return  this Customer's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the mobile number of this Customer
     * @return  this Customer's mobile number
     */
    public int getMobileNumber() {
        return this.mobileNumber;
    }

    /**
     * Gets the email of this Customer
     * @return  this Customer's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Changes the name of this Customer
     * @param name  This Customer's new name
     */
    //Setters
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the age of this Customer
     * @param age   This Customer's new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Changes the username of this Customer
     * @param username  This Customer's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Changes the password of this Customer
     * @param password  This Customer's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the email of this Customer
     * @param email This Customer's new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Changes the mobile number of this Customer
     * @param mobileNumber  This Customer's new mobile number
     */
    public void setNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * A method to add bookings for Customer
     * @param t
     */
    public void addBookings(Ticket t) {
        this.bookings.add(t);
    }

    /**
     * Overrides toString method to store Customer detail in a specific format
     * @return  a string of Customer details
     */
    @Override
    public String toString() {
        String customerDetail;
        customerDetail = "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Username: " + username + "\n"
                + "Password: " + password;
        return customerDetail;
    }
}
