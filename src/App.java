package SC2002Link.src;

import SC2002Link.src.entities.Cinema;
import SC2002Link.src.entities.Customer;
import SC2002Link.src.entities.Seat;
import SC2002Link.src.entities.Staff;
import SC2002Link.src.controllers.*; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Objects;

public class App {
    public static void main(String[] args) throws Exception {
        MovieController mvc = new MovieController(Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\movies.dat");
        ClientController cl = new ClientController(Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\customers.dat", Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\staffs.dat");
        //invalid constructor for ci controller, need two parameters
        CinemaController ci = new CinemaController(Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\cinemas.dat", Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\seats.dat");
        HolidayController h = new HolidayController(Paths.get("").toAbsolutePath().toString() + "\\src\\SC2002Link\\data\\holidays.dat");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        App.welcomeScreen();
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
                    SessionController.staffUI(s);
                    break;
                case 2:
                    System.out.println("Please enter your username");
                    username = reader.readLine();
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
