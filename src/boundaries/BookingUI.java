package src.boundaries;

import src.controllers.CinemaController;
import src.controllers.MovieController;
import src.entities.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Boundary class providing the Booking related User Interface for Customer.
 *
 * @author Aryan
 */
public class BookingUI {
    /**
     * Utility to print the lauout of Cinema at a given showtime for a Movie, to check for Seat availability.
     *
     * @throws Exception
     */
    public static void checkSeatAvailability() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        System.out.println("Enter the name of the Cinema you would like to check seat availability for");
        String cinemaname = reader.readLine();
        System.out.println("Enter the Movie for which you would like to check availability");
        String moviename = reader.readLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
        LocalDateTime showtime = null;
        boolean e = false;
        while (!e) {
            try {
                System.out.println("Enter showtime e.g (20/11/2022 09:00AM): ");
                showtime = LocalDateTime.parse(reader.readLine(), formatter);
                e = true;
            } catch (DateTimeParseException d) {
                System.out.println("Invalid date format, Please try again");
            }
        }

        for (Cinema cinema : cinemaDB) {
            if (Objects.equals(cinema.getName(), cinemaname)) {
                cinema.printLayout(moviename, showtime);
                break;
            }
        }
    }

    /**
     * User Interface to make Booking for a customer at specified movie, cinema and showtime
     *
     * @param customer represents customer to makeBooking for
     * @return Ticket returns the Ticket corresponding to the booked ticket
     * @throws Exception
     */
    public static Ticket makeBooking(Customer customer) throws Exception {
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter movie title to make booking");
        String title = reader.readLine();
        System.out.println("Enter cinema name you would like to make booking at");
        String cinemaName = reader.readLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
        LocalDateTime showtime = null;
        boolean e = false;
        while (!e) {
            try {
                System.out.println("Enter showtime e.g (20/11/2022 09:00AM): ");
                showtime = LocalDateTime.parse(reader.readLine(), formatter);
                e = true;
            } catch (DateTimeParseException d) {
                System.out.println("Invalid date format, Please try again");
            }
        }
        for (Cinema cinema : cinemaDB) {
            if (Objects.equals(cinema.getName(), cinemaName)) {
//            	System.out.println(cinema.getName());
//            	System.out.println(title + showtime.toString());
                ArrayList<Seat> seats = cinema.getSeats(title + showtime.toString());
                cinema.printLayout(title, showtime);
                String seatNo;
                do {
                    System.out.println("Which seat would you like to select?: Enter the alphabet along with the column number like so (C06)");
                    seatNo = reader.readLine();
                } while (seatNo.charAt(0) > 'J' || seatNo.charAt(0) < 'A');
                int row = seatNo.charAt(0) % 65;
                int col = Integer.parseInt(seatNo.substring(1));
                int index = row * 16 + col;
                if (!cinema.setCustomer(customer, title + showtime.toString(), index)) return null;
                Movie mov = movieDB.get(0);
                for (Movie movie : movieDB) {
                    if (movie.getTitle().equals(title)) {
                        if (movie.getStatus() != MovieStatus.NOW_SHOWING && movie.getStatus() != MovieStatus.PREVIEW) {
                            System.out.println("Movie is no longer available for booking.");
                            return null;
                        }
                        mov = movie;
                        break;
                    }
                }
                String TID = cinema.getName().hashCode() % 1000 + showtime.toString();

                //Constructor has been updated, creation of ticket here need to match constructor
                Ticket newTicket = new Ticket(mov, cinema, customer, showtime, cinema.getSeat(title + showtime.toString(), index), TID, customer.getUsername(), customer.getEmail(), customer.getMobileNumber());
                System.out.println("The price of your ticket is: " + newTicket.getPrice());
                mov.setTotalSales(1);
                CinemaController.setCinemaDB(cinemaDB);
                MovieController.setMovieDB(movieDB);
                return newTicket;
            }
        }
        return null;
    }

    /**
     * Adds a review for a specific Movie for a particular Customer
     *
     * @param c represents Customer who is adding review
     * @throws Exception
     */
    public static void addReview(Customer c) throws Exception {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();

        System.out.println("Enter the title of the Movie you would like to add a review for");
        Scanner sc = new Scanner(System.in);
        String title = sc.nextLine();
        int index = 0;
        for (int i = 0; i < movieDB.size(); i++) {
            if (Objects.equals(movieDB.get(i).getTitle(), title)) {
                index = i;
                break;
            }
            if (index == -1) System.out.println("Movie not found. Please try again");
        }
        while (index == -1) ;

        System.out.println("Enter your rating for the movie, on a scale of 1.0 - 5.0 ");
        int rating = Integer.parseInt(sc.nextLine());
        System.out.println("Enter your review for the movie");
        String comment = sc.nextLine();
        Review newReview = new Review(c.getUsername(), rating, comment);

        //is this 'i' supposed to be 'index'? variable 'i' was not created
        movieDB.get(index).addReview(newReview);
        System.out.println("You're review was added successfully");
        MovieController.setMovieDB(movieDB);
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        for (int i = 0; i < cinemaDB.size(); i++) {
            ArrayList<Movie> movies = cinemaDB.get(i).getMovies();
            for (int j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(movieDB.get(index).getTitle())) movies.set(j, movieDB.get(index));
            }
        }
        CinemaController.setCinemaDB(cinemaDB);
    }
}
