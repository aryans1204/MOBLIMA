package src.controllers;

import src.boundaries.BookingUI;
import src.boundaries.MovieListing;
import src.boundaries.SystemConfig;
import src.entities.Customer;
import src.entities.Staff;
import src.entities.Ticket;

import java.util.Scanner;

/**
 * Controller for managing the Session between Customers and Staff
 *
 * @author xichen
 * @version 1.0
 * @since 2022-11-13
 */
public class SessionController {
    /**
     * User interface for staff
     *
     * @param s is staff object, after staff has logged in
     * @throws Exception e
     */
    public static void staffUI(Staff s) throws Exception {
        if (!s.isAuth())
            return;
        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.print("\nHow can we help you today?: \n" +
                    "1. Create Movie Listing\n" +
                    "2. Update Movie Listing\n" +
                    "3. Remove Movie Listing\n" +
                    "4. Search Movie\n" +
                    "5. List Movie\n" +
                    "6. List Top 5 Movies by Reviews\n" +
                    "7. List Top 5 Movies by TotalSales\n" +
                    "8. Add Showtime for a Movie\n" +
                    "9. Remove Showtime for a Movie\n" +
                    "10. Configure Ticket Prices for a Movie\n" +
                    "11.Configure holidays\n" +
                    "12. Exit\n" +
                    "Select option: ");
            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    s.getCinema().updateMovies(MovieListing.createMovieListing(s.getCinema().getName()));
                    break;
                case 2:
                    MovieListing.updateMovieListing(s.getCinema());
                    break;
                case 3:
                    MovieListing.removeMovieListing();
                    break;
                case 4:
                    MovieListing.searchMovie();
                    break;
                case 5:
                    MovieListing.listMovie();
                    break;
                case 6:
                    MovieListing.listByReview();
                    break;
                case 7:
                    MovieListing.listBySales();
                    break;
                case 8:
                    MovieListing.addShowtime(s.getCinema());
                    break;
                case 9:
                    MovieListing.removeShowtime(s.getCinema());
                    break;
                case 10:
                    SystemConfig.configTicketPrices(s.getCinema());
                    break;
                case 11:
                    SystemConfig.configHolidays();
                    break;//break for case 10
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input!\n Please try again");
            }
        }
    }

    /**
     * UI for customers
     *
     * @param c is passed in after customer has logged in
     * @throws Exception e
     */
    public static void customerUI(Customer c) throws Exception {
        if (!c.isAuth())
            return;
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        while (!exit) {
            System.out.println("\nHow can we help you today?\n" +
                    "1. Search for a Movie\n" +
                    "2. List Movies by Cinema\n" +
                    "3. Check seat availability of a movie at a cinema for a given showtime\n" +
                    "4. Purchase a ticket for a movie\n" +
                    "5. View Booking History\n" +
                    "6. List Top 5 Movies by Ticket Sales or Reviewer's rating(as set by Cinema staff)\n" +
                    "7. Add a review or rating for a movie\n" +
                    "8. Exit");
            int option = Integer.parseInt(sc.nextLine());
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
                    Ticket t = BookingUI.makeBooking(c);
                    if (t != null) {
                        c.addBookings(t);

                        System.out.println("Ticket booked successfully");
                    } else {
                        System.out.println("There was an error in booking your ticket. please try again");
                    }
                    break;
                case 5:
                    c.viewBookings();
                    break;
                case 6:
                    int choice = 0;
                    do {
                        System.out.println("1. Based on total sales");
                        System.out.println("2. Based on reviews");
                        System.out.println("3. Back");
                        choice = Integer.parseInt(sc.nextLine());
                        if (choice == 1) {
                            MovieListing.listBySales();
                            break;
                        }
                        if (choice == 2) {
                            MovieListing.listByReview();
                            break;
                        }
                    } while (choice != 3);
                    break;
                case 7:
                    BookingUI.addReview(c);
                    break;
                case 8:
                    exit = true;
                    break;
            }
        }
    }

}
