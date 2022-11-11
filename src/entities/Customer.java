package src.entities;

import src.boundaries.BookingUI;
import src.boundaries.MovieListing;
import src.controllers.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Customer implements Client, Serializable {
    private String email;
    private int mobileNumber;
    private String name;
    private int age;
    private boolean auth = false;  //authentication indicator. Whether customer is authenticated or not. All methods must check for authentication.
    private String username;
    private String password;
    private ArrayList<Ticket> bookings = new ArrayList<>();
    private static ClientController cl;

    //Constructor for Customer object.
    public Customer(String name, int age, String username, String password, String email, int mobileNumber) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }


    public boolean login(String tempUsername) throws IOException {
        int tries = 9;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your password: ");
        tempPassword = reader.readLine();

        ArrayList<Customer> customers = new ArrayList<>();
        customers = cl.getCustomerFromDB();  //Fetch all customers details from DB
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(tempUsername) && customers.get(i).getPassword().equals(tempPassword)) {
                System.out.println("Authenticated successfully");

                //auth is non static, cannot be used in a static method. IDE suggests declaring auth as static or make method 'non-static'
                auth = true;
                return true;
            }
        }
        return false;
    }

    public boolean createAccount(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException {
        int tries = 9;  //9 tries before system shuts;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Enter username: ");
            tempUsername = reader.readLine();
            if (cl.checkUsernameExist(tempUsername)) System.out.println("Username already taken");
            tries--;
            if (tries == 0) System.out.println("Too many tries. System quitting now");
        } while (cl.checkUsernameExist(tempUsername) && tries != 0);
        if (tries == 0) return false;
        setUsername(tempUsername);
        System.out.println("Enter password: ");
        tempPassword = reader.readLine();
        setPassword(tempPassword);
        System.out.println("Enter your name: ");
        String tempName = reader.readLine();
        setName(tempName);
        System.out.println("Enter email: ");
        String tempEmail = reader.readLine();
        setEmail(tempEmail);
        System.out.println("Enter mobile number: ");
        int tempNumber = Integer.parseInt(reader.readLine());
        setNumber(tempNumber);
        System.out.println("Enter age: ");
        int tempage = Integer.parseInt(reader.readLine());
        setAge(tempage);
        customerDB.add(this);
        System.out.println("Account created successfully");
        return true;
    }

    public static ClientController getCl() {
        return cl;
    }

    public static void setCl(ClientController cl) {
        Customer.cl = cl;
    }


    public void customerUI() throws Exception {
        if (!auth) return;
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!exit) {
            System.out.println("How can we help you today\n" +
                    "1. Search for a Movie\n" +
                    "2. List Movies by Cinema\n" +
                    "3. Check seat availability of a movie at a cinema for a given showtime\n" +
                    "4. Purchase a ticket for a movie\n" +
                    "5. View Booking History\n" +
                    "6. List Top 5 Movies by Ticket Sales or Reviewer's rating(as set by Cinema staff)\n" +
                    "7. Add a review or rating for a movie\n" +
                    "8. Exit");
            int option = Integer.parseInt(reader.readLine());
            switch (option) {
                case 1:
                    MovieListing.searchMovie();
                    break;
                case 2:
                    MovieListing.listMoviesByCinema();
                    break;
                case 3:
                    BookingUI.checkSeatAvailability();
                    break;
                case 4:
                    this.bookings.add(BookingUI.makeBooking(this));
                    break;
                case 5:
                    viewBookings();
                    break;
                case 6:
                    MovieListing.listBySales();
                    break;
                case 7:
                    BookingUI.addReview(this);
                    break;
                case 8:
                    exit = true;
                    break;
            }
        }
    }

    private void viewBookings() {
        System.out.println("Your past bookings are available here");
        for (Ticket ticket : this.bookings) {
            System.out.println(ticket.toString());
        }
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }  //method to get the age of the client, as an int.

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMobileNumber() {
        return this.mobileNumber;
    }

    public String getEmail() {
        return this.email;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

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
