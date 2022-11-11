package src.controllers;

import src.entities.Cinema;
import src.entities.Customer;
import src.entities.Movie;
import src.entities.Staff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class TestingClient {
    public static void main(String[] args) throws Exception {
        MovieController mvc = new MovieController("../../data/movies.dat");
        ClientController cl = new ClientController("../../data/customer.dat", "../../data/staff.dat");
        //invalid constructor for ci controller, need two parameters
        CinemaController ci = new CinemaController("../../data/cinema.dat", "../../data/seat.dat");
        HolidayController h = new HolidayController("../../data/holidays.dat");
        ArrayList<Movie> movieDB = mvc.getAllMoviesFromDB();
        ArrayList<Customer> customerDB = cl.getCustomerFromDB();
        ArrayList<Staff> staffDB = cl.getStaffFromDB();
        ArrayList<Cinema> cinemaDB = ci.getAllCinemasFromDB();
        ArrayList<LocalDate> holidays = h.getAllHolidaysFromDB();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TestingClient.welcomeScreen();
        String password, username;
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
                    username = reader.readLine();
                    Staff s = null;
                    for (Staff staff : staffDB) {
                        if (Objects.equals(staff.getUsername(), username)) s = staff;
                    }
                    if (s == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    System.out.println("Please enter the password associated with your account");
                    password = reader.readLine();
                    if (Objects.equals(s.getPassword(), password)) {
                        System.out.println("Authenticated successfully");
                    } else System.out.println("Incorrect password. Please try again later");
                    s.staffUI(movieDB, holidays);
                    break;
                case 2:
                    System.out.println("Please enter your username");
                    username = reader.readLine();
                    Customer c = null;
                    for (Customer customer : customerDB) {
                        if (customer.getUsername() == username) c = customer;
                    }
                    if (c == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    System.out.println("Please enter the password associated with your account");
                    password = reader.readLine();
                    if (Objects.equals(c.getPassword(), password)) {
                        System.out.println("Authenticated successfully");
                    } else System.out.println("Incorrect password. Please try again later");
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
