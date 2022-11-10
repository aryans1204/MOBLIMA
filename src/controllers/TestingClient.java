package src.controllers;

import src.entities.Cinema;
import src.entities.Customer;
import src.entities.Movie;
import src.entities.Staff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestingClient {
    public static void main(String[] args) throws IOException {
        MovieController mvc = new MovieController("movies.dat");
        ClientController cl = new ClientController("customer.dat", "staff.dat");
	//invalid constructor for ci controller, need two parameters 
        CinemaController ci = new CinemaController("cinema.dat", "seat.dat");

        ArrayList<Movie> movieDB = mvc.getAllMoviesFromDB();
        ArrayList<Customer> customerDB = cl.getCustomerFromDB();
        ArrayList<Staff> staffDB = cl.getStaffFromDB();
        ArrayList<Cinema> cinemaDB = ci.getAllCinemasFromDB();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TestingClient.welcomeScreen();
        int option = 0;
        do {
            System.out.println("How can we help you today?");
            System.out.println("1. Login as Staff\n" +
                    "2. Login as a Customer\n" +
                    "3. Create an Account as Staff\n" +
                    "4. Create an Account as Customer" +
                    "5. Exit");
            option = Integer.parseInt(reader.readLine());

            switch (option) {
                case 1:
                    System.out.println("Please enter your username");
		    String username = reader.readLine();
		    Staff s = null;
		    for (Staff staff : staffDB) {
			if (staff.getUsername() == username) s = staff;
   		    }
		    if (s = null) {
			System.out.println("Username does not exist. Try again at a later time");
			break;
		    }
		    System.out.println("Please enter the password associated with your account");
		    String password = reader.readLine();
		    if (s.getPassword() == password) {
			System.out.println("Authenticated successfully");
		    }
		    else System.out.println("Incorrect password. Please try again later");
                    s.staffUI(movieDB);
		    break;
                case 2: 
                    System.out.println("Please enter your username");
		    String username = reader.readLine();
		    Customer c = null;
		    for (Customer customer : customerDB) {
			if (customer.getUsername() == username) c = customer;
   		    }
		    if (c = null) {
			System.out.println("Username does not exist. Try again at a later time");
			break;
		    }
		    System.out.println("Please enter the password associated with your account");
		    String password = reader.readLine();
		    if (c.getPassword() == password) {
			System.out.println("Authenticated successfully");
		    }
		    else System.out.println("Incorrect password. Please try again later");
                    c.customerUI(cinemaDB, movieDB);
  		    break;
            }
        } while (option != 5);

    }

    private static void welcomeScreen() {
        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                                     ║");
        System.out.println("║                                                                                                     ║");
        System.out.println("║      ▐▐          ▐▐     ▐▐▐▐▐▐      ▐▐▐▐▐▐▐▐    ▐▐          ▐▐    ▐▐          ▐▐         ▐▐▐▐       ║");
        System.out.println("║      ▐▐ ▐▐    ▐▐ ▐▐    ▐▐    ▐▐     ▐▐    ▐▐    ▐▐          ▐▐    ▐▐ ▐▐    ▐▐ ▐▐        ▐▐  ▐▐      ║");
        System.out.println("║      ▐▐  ▐▐  ▐▐  ▐▐   ▐▐      ▐▐    ▐▐    ▐▐    ▐▐          ▐▐    ▐▐  ▐▐  ▐▐  ▐▐       ▐▐    ▐▐     ║");
        System.out.println("║      ▐▐   ▐▐▐▐   ▐▐  ▐▐        ▐▐   ▐▐▐▐▐▐▐▐    ▐▐          ▐▐    ▐▐   ▐▐▐▐   ▐▐      ▐▐▐▐▐▐▐▐▐▐    ║");
        System.out.println("║      ▐▐          ▐▐   ▐▐      ▐▐    ▐▐    ▐▐    ▐▐          ▐▐    ▐▐          ▐▐     ▐▐        ▐▐   ║");
        System.out.println("║      ▐▐          ▐▐    ▐▐    ▐▐     ▐▐    ▐▐    ▐▐          ▐▐    ▐▐          ▐▐    ▐▐          ▐▐  ║");
        System.out.println("║      ▐▐          ▐▐     ▐▐▐▐▐▐      ▐▐▐▐▐▐▐▐    ▐▐▐▐▐▐▐▐▐▐  ▐▐    ▐▐          ▐▐   ▐▐            ▐▐ ║");
        System.out.println("║                                                                                                     ║");
        System.out.println("║                         Welcome to Movie Booking and Listing Application                            ║");
        System.out.println("║                                                                                                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
