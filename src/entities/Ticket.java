package src.entities;

import src.controllers.HolidayController;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Ticket implements Serializable {
    private Movie movie;  //associated movie with the ticket
    private Cinema cinema; //associated cinema of the movie, this will also provide seat number
    private Customer customer;  //purchasing customer details
    private Transaction transaction;
    private LocalDateTime showtime;
    private Seat seat;
    private double price;

    Scanner sc = new Scanner(System.in);

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

    public boolean setHolidaysArray(ArrayList<LocalDate> a) {
        if (a == null) {
            return false;
        } else {
            HolidayController.setHolidays(a);
        }
        return true;
    }

    public boolean isAHoliday() {
        ArrayList<LocalDate> Holidays = HolidayController.getHolidays();
        LocalDate show_date = showtime.toLocalDate();
        if (Holidays != null) {
            for (int i = 0; i < Holidays.size(); i++) {
                if (Holidays.get(i) == show_date)
                    return true;
            }
        }
        return false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPrice() {
        return price;
    }

    public Seat getSeat() {
        return seat;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public Cinema getCinema() {
        //method to return cinema associated with the ticket.
        return cinema;
    }

    public Movie getMovie() {
        //method to return the movie for the ticket
        return movie;
    }

    public LocalDateTime getShowtime() {
        //method to return showtime of the ticket.
        return showtime;
    }

    public void setPrice(double newPrice) {
        price = newPrice;
    }

    @Override
    public String toString() {
        return "Date of Movie: " + showtime.toString() + "\n" +
                "Movie Title: " + movie.getTitle() + "\n" +
                "Cinema Name: " + cinema.getName() + "\n" +
                "Price paid: " + price + "\n" +
                "Transaction ID: " + transaction.getTID();
    }
}
