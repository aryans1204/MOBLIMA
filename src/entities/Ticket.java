package src.entities;

import src.controllers.HolidayController;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Ticket in the system
 *
 * @author XiChen
 */
@SuppressWarnings("serial")
public class Ticket implements Serializable {

    /**
     * This Ticket's associated Movie
     */
    private Movie movie;  //associated movie with the ticket

    /**
     * This Ticket's associated Cinema
     */
    private Cinema cinema; //associated cinema of the movie, this will also provide seat number

    /**
     * This Ticket's associated Customer
     */
    private Customer customer;  //purchasing customer details

    /**
     * This Ticket's associated Transaction
     */
    private Transaction transaction;

    /**
     * This Ticket's associated showtime
     */
    private LocalDateTime showtime;

    /**
     * This Ticket's associated Seat
     */
    private Seat seat;

    /**
     * This Ticket's associated price
     */
    private double price;

    Scanner sc = new Scanner(System.in);

    /**
     * Creates a Ticket with the given attributes
     *
     * @param movie            This Ticket's Movie
     * @param cinema           This Ticket's Cinema
     * @param customer         This Ticket's Customer
     * @param showtime         This Ticket's showtime
     * @param seat             This Ticket's Seat
     * @param TID              This Ticket's Transaction
     * @param custName         This Ticket's Customer name
     * @param custEmail        This Ticket's Customer email
     * @param custMobileNumber This Ticket's Customer mobile number
     */
    public Ticket(Movie movie, Cinema cinema, Customer customer, LocalDateTime showtime, Seat seat, String TID, String custName, String custEmail, int custMobileNumber) {
        this.movie = movie;
        this.cinema = cinema;
        this.customer = customer;
        this.showtime = showtime;
        this.seat = seat;
        transaction = new Transaction(TID, custName, custEmail, Integer.toString(custMobileNumber));
        double priceL = cinema.getTicketPrice(movie.getTitle());
        if (priceL == 0) {
            price = 0;
            calculatePrice();
        } else {
            price = priceL;
            calculatePrice();
        }
    }

    /**
     * A method to calculate Ticket price
     *
     * @author Aryan
     */
    public void calculatePrice() {
        //gets price from prices database and does algorithm on it.
        //prices database stores default values for this particular movie and cinema and showtime
        //if child or senior x0.8
        double moviePrice;
        MovieType movieType;
        int customerAge;
        SeatType seatType;
        int date;
        double multiplier = 1;
        String day;


        if (price == 0)
            moviePrice = 10.0;
        else
            moviePrice = price;

        movieType = movie.getType();
        customerAge = customer.getAge();
        seatType = seat.getType();

        //update multiplier for different days of week

        //need to create class to calculate holidays
        day = showtime.getDayOfWeek().toString();
        if ("SATURDAY".equalsIgnoreCase(day) || "SUNDAY".equalsIgnoreCase(day) || this.isAHoliday()) {
            multiplier = multiplier * 1.5;
        }

        //update multiplier for movietype
        switch (movieType) {
            case TWO_D:
                //no change in multiplier;
                break;
            case THREE_D:
                multiplier = multiplier * 1.2;
                break;
            case BLOCKBUSTER:
                multiplier = multiplier * 1.5;
                break;
            default:
                //no change
                break;
        }

        //update multiplier for cinemaType
        switch (seatType) {
            case STANDARD:
                //no change in multiplier
                break;
            case GOLD:
                multiplier = multiplier * 1.2;
                break;
            case PLATINUM:
                multiplier = multiplier * 1.5;
                break;
            default:
                //no change
                //break;
        }

        //update multiplier by age
        if (customerAge < 12) multiplier = multiplier * 0.8;
            //no price change if its adult since default price is for adults
        else if (customerAge >= 56) multiplier = 0.7 * multiplier;


        //by the end of all the multipliers, the formula will look like this:
        //multiplier = multiplier * movieType * clientAge * cinemaType * date;
        price = moviePrice * multiplier;


    }

    /*public int[] getPricesFromDB() {
		int[] prices = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;

		try {
			fis = new FileInputStream(priceFileName);
			in = new ObjectInputStream(fis);
			prices = (int[]) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return prices;
	}*/

    /**
     * A method to set holidays
     *
     * @param a Dates to set as holidays
     * @return True if successfully set holiday, False if unsuccessful
     */
    public boolean setHolidaysArray(ArrayList<LocalDate> a) {
        if (a == null) {
            return false;
        } else {
            HolidayController.setHolidays(a);
        }
        return true;
    }

    /**
     * A method to check if the day is a holiday
     *
     * @return True if it is a holiday, False if it is not
     */
    public boolean isAHoliday() {
        ArrayList<LocalDate> Holidays = HolidayController.getHolidays();
        LocalDate show_date = showtime.toLocalDate();
        if (Holidays != null) {
            for (int i = 0; i < Holidays.size(); i++) {
                if (Holidays.get(i).equals(show_date))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the Customer of this Ticket
     *
     * @return This Ticket's Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the price of this Ticket
     *
     * @return This Ticket's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the Seat of this Ticket
     *
     * @return This Ticket's Seat
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Gets the Transaction of this Ticket
     *
     * @return This Ticket's Transaction
     */
    public Transaction getTransaction() {
        return this.transaction;
    }

    /**
     * Gets the Cinema of this Ticket
     *
     * @return This Ticket's Cinema
     */
    public Cinema getCinema() {
        //method to return cinema associated with the ticket.
        return cinema;
    }

    /**
     * Gets the Movie of this Ticket
     *
     * @return This Ticket's Movie
     */
    public Movie getMovie() {
        //method to return the movie for the ticket
        return movie;
    }

    /**
     * Gets the showtime of this Ticket
     *
     * @return This Ticket's showtime
     */
    public LocalDateTime getShowtime() {
        //method to return showtime of the ticket.
        return showtime;
    }

    /**
     * Changes the price of the Ticket
     *
     * @param newPrice This Ticket's new price
     */
    public void setPrice(double newPrice) {
        price = newPrice;
    }

    /**
     * Overrides toString method to store Ticket detail in a specific format
     *
     * @return a string of Ticket details
     */
    @Override
    public String toString() {
        return "Date of Movie: " + showtime.toString() + "\n" +
                "Movie Title: " + movie.getTitle() + "\n" +
                "Cinema Name: " + cinema.getName() + "\n" +
                "Price paid: " + price + "\n" +
                "Transaction ID: " + transaction.getTID();
    }
}
