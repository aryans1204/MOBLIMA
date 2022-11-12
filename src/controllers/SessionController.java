package SC2002Link.src.controllers;

import SC2002Link.src.boundaries.BookingUI;
import SC2002Link.src.boundaries.MovieListing;
import SC2002Link.src.boundaries.SystemConfig;
import SC2002Link.src.entities.Customer;
import SC2002Link.src.entities.Staff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SessionController {
    public static void staffUI(Staff s) throws Exception {
    	if(!s.isAuth())
    		return;
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
                    "9.Configure holidays\n" +
                    "10. Exit\n" +
                    "Select option: ");
            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    s.getCinema().updateMovies(MovieListing.createMovieListing());
                    break;
                case 2:
                    MovieListing.updateMovieListing(s.getCinema());
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
                    SystemConfig.configTicketPrices(s.getCinema());
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

    public static void customerUI(Customer c) throws Exception {
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
                    c.addBookings(BookingUI.makeBooking(c));
                    break;
                case 5:
                    c.viewBookings();
                    break;
                case 6:
                    MovieListing.listBySales();
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
