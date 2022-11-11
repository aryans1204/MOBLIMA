package src.controllers;

import src.entities.Customer;
import src.entities.Staff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class TestingClient {
    public static void main(String[] args) throws Exception {
        MovieController mvc = new MovieController("movies.dat");
        ClientController cl = new ClientController("customers.dat", "staffs.dat");
        //invalid constructor for ci controller, need two parameters
        CinemaController ci = new CinemaController("cinemas.dat", "data/seats.dat");
        HolidayController h = new HolidayController("holidays.dat");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TestingClient.welcomeScreen();
        String username;
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
                    for (Staff staff : ClientController.getStaffList()) {
                        if (Objects.equals(staff.getUsername(), username)) s = staff;
                    }
                    if (s == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    s.login(username);
                    s.staffUI();
                    break;
                case 2:
                    System.out.println("Please enter your username");
                    username = reader.readLine();
                    Customer c = null;
                    for (Customer customer : ClientController.getCustomerList()) {
                        if (customer.getUsername() == username) c = customer;
                    }
                    if (c == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    c.login(username);
                    c.customerUI();
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
