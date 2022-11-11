package src.entities;

import src.boundaries.MovieListing;
import src.boundaries.SystemConfig;
import src.controllers.CinemaController;
import src.controllers.ClientController;
import src.controllers.HolidayController;
import src.controllers.MovieController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

//ide suggested @SuppressWarnings("serial")
@SuppressWarnings("serial")
public class Staff implements Client, Serializable {
    private String username;
    private String password;
    private boolean auth = false;
    private Cinema cinema;   //associated cinema of the staff. Where the staff works
    private static MovieController mvc;
    private static CinemaController ci;
    private static ClientController cl;
    private static HolidayController ho;

    public Staff(String username, String password, Cinema cinema) {
        this.username = username;
        this.password = password;
        this.cinema = cinema;
    }

    public boolean login(String username) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        username = reader.readLine();
        System.out.println("Enter password: ");
        String password = reader.readLine();
        ArrayList<Staff> staffs = cl.getStaffList();

        for (Staff s : staffs) {
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                System.out.println("Authenticated successfully");

                //same as customer class, auth has to be static in a static method. either that or method becomes non static
                auth = true;
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

    public void staffUI() throws Exception {
        if (!auth) return;
        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.print("\n\nCinema Staff Selection: \n" +
                    "1. Create Movie Listing\n" +
                    "2. Update Movie Listing\n" +
                    "3. Remove Movie Listing\n" +
                    "4. List Movie\n" +
                    "5. List Top 5 Movies by Reviews\n" +
                    "6. Search Movie\n" +
                    "7. List Top 5 Movies by TotalSales\n" +
                    "8. Configure Ticket Prices for a Movie\n" +
                    "9.Configure holidays" +
                    "10. Exit" +
                    "Select option: ");
            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    this.cinema.updateMovies(MovieListing.createMovieListing());
                    break;
                case 2:
                    MovieListing.updateMovieListing(this.cinema);
                    break;
                case 3:
                    MovieListing.removeMovieListing();
                    break;
                case 4:
                    MovieListing.listMovie();
                    break;
                case 5:
                    MovieListing.listByReview();
                    break;
                case 6:
                    MovieListing.searchMovie();
                    break;
                case 7:
                    MovieListing.listBySales();
                    break;
                case 8:
                    SystemConfig.configTicketPrices(this.cinema);
                    break;
                case 9:
                    SystemConfig.configHolidays();
                    break;//break for case 10
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input!\n Please try again");
            }
        }

        sc.close();
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

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public static void setMvc(MovieController mvc) {
        Staff.mvc = mvc;
    }

    public static void setCi(CinemaController ci) {
        Staff.ci = ci;
    }

    public static void setCl(ClientController cl) {
        Staff.cl = cl;
    }

    public static HolidayController getHo() {
        return ho;
    }

    public static void setHo(HolidayController ho) {
        Staff.ho = ho;
    }

    public MovieController getMvc() {
        return mvc;
    }

    public CinemaController getCi() {
        return ci;
    }

    public ClientController getCl() {
        return cl;
    }
}
