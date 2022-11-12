package src;

import src.controllers.*;
import src.entities.Customer;
import src.entities.Staff;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        MovieController mvc = new MovieController(Paths.get("").toAbsolutePath().toString() + "\\data\\movies.dat");
        ClientController cl = new ClientController(Paths.get("").toAbsolutePath().toString() + "\\data\\customers.dat", Paths.get("").toAbsolutePath().toString() + "\\data\\staffs.dat");
        //invalid constructor for ci controller, need two parameters
        CinemaController ci = new CinemaController(Paths.get("").toAbsolutePath().toString() + "\\data\\cinemas.dat", Paths.get("").toAbsolutePath().toString() + "\\data\\seats.dat");
        HolidayController h = new HolidayController(Paths.get("").toAbsolutePath().toString() + "\\data\\holidays.dat");


        App.welcomeScreen();
        String username;
        int option = 0;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("How can we help you today?");
            System.out.println("1. Login as Staff\n" +
                    "2. Login as a Customer\n" +
                    "3. Create an Account as Staff\n" +
                    "4. Create an Account as Customer\n" +
                    "5. Exit");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.println("Please enter your username");
                    username = sc.nextLine();
                    Staff s = null;
                    for (Staff staff : ClientController.getStaffList()) {
                        if (Objects.equals(staff.getUsername(), username)) s = staff;
                    }
                    if (s == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    s.login(username);
                    SessionController.staffUI(s);
                    break;
                case 2:
                    System.out.println("Please enter your username");
                    username = sc.nextLine();
                    Customer c = null;
                    for (Customer customer : ClientController.getCustomerList()) {
                        if (customer.getUsername().equals(username)) c = customer;
                    }
                    if (c == null) {
                        System.out.println("Username does not exist. Try again at a later time");
                        break;
                    }
                    c.login(username);
                    SessionController.customerUI(c);
                    break;
                default:
                    System.out.println("Make a valid choice");
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
