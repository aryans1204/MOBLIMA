import java.util.*;
import java.io.*;

import Client,Cinema,Movie;

public class Customer implements Client {
    private Ticket[] bookings;  //previous bookings made by customer given by tickets.
    private boolean auth = false;  //authenticatiuon indicator. Whether customer is authenticated or not. All methods must check for authentication.
    public String username;

    public boolean login(String password, Database db) {
        if (!db.user_accounts.containsKey(this.username)) return false;

        else if (db.user_accounts.get(this.username) != password) return false;
        this.auth = true;
        System.out.println("Authenticated succesfully");
        bookings = new Ticket[100];
        return true;
    }

    public boolean createAccount(Database db) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tries = 9;  //9 tries before system shuts;
        do {
            System.out.println("Enter username: ");
            String username = reader.readLine();
            if (db.user_accounts.containsKey(username)) System.out.println("Username already taken");
            if (tries == 0) System.out.println("Too many tries. System quitting now");
            tries--;
        }
        while (db.user_accounts.containsKey(username) && tries != 0);
        if (tries == 0) return false;
        System.out.println("Enter password: ");
        String password = reader.readLine();
        db.user_accounts.put(username, password);
        this.username = username;
        System.out.println("Account created successfully");
        return true;
    }

    public void checkMovieListings(Cinema cinema) {
        if (!auth) return;
        for (Movie movie : cinema.movies) {
            System.out.println(movie.title);
            System.out.println(movie.status);
            System.out.println(movie.synopsis);
            System.out.println("Director: " + movie.Director + "Cast: " + movie.Cast1 + " " + movie.Cast2);
            System.out.println(movie.rating);
            System.out.println("Review                                                          Rating");  //please keep review length to 64 characters
            System.out.println("=======================================================================");
            for (for (int j = 0; j < movie.reviews.length; j++)) {
                System.out.print(movie.reviews[j]);
                for (int i = 0; i < 66 - movie.reviews[j].length(); i++) System.out.print(" ");
                System.out.println(movie.ratings[j]);
            }
        }
    }

    public String makeBooking(Cinema cinema, Movie movie) throws Exception {
        if (!auth) return "0xdeadbeef";
        cinema.printLayout(movie, showtime);  //prints layout for movie and showtime at the cinema.
        int frees = cinema.free.get(movie.title).get(movie.showtime);
        if (frees == 0) return "0xdeadbeef";  //0xdeadbeef is code for error occured, that is no free seats
        Ticket ticket = new Ticket(movie, cinema, this);  //creates a booking
        BufferedReader reader = new BufferedReader(new InputStreamreader(System.in));
        do {
            System.out.println("Please enter which seat number you would like: ");
            int seatNo = reader.readLine();
            if (cinema.seats.get(movie.title).get(movie.showtime).get(seatNo) != null)
                SYstem.out.println("Seat is taken. Please choose another seat");
        }
        while (cinema.seats.get(movie.title).get(movie.showtime).get(seatNo) != null);
        cinema.seats.get(movie.title).get(movie.showtime).put(seatNo, this);
        cinema.free.get(movie.title).put(movie.showtime, frees - 1);
        String transactionID = UUID.randomUUID().toString();
        cinema.tID.get(this.username).get(movie.title).put(movie.showtime, transactionID);
        return transactionID;
    }

    public void searchMovie(String movieTitle, MovieController m);  //searches for a Movie in our database and prints out the Review and rating

    public void viewBookings();  //view all the past bookings made by the Customer.

    public void checkSeats(Cinema cinema, String showtime) {
	cinema.printLayout(this, showtime); //simple wrapper to print layout
    }

    public void listTopFive(String criterion, MovieController m, TicketController t);  //require read access to Movie CRUD if criterion is Rating OR require Ticket CURD if criterion is sales. 
}
