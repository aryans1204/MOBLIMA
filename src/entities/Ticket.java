package src.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.time.*;

/**
 * Represents a ticket containing all information of customer's bookings
 * @author Xi Chen
 */
public class Ticket implements Serializable {
    /**
     * Associated movie object
     */
    Movie movie;  //associated movie with the ticket
    /**
     * Associated cinema object
     */
    Cinema cinema; //associated cinema of the movie, this will also provide seat number
    /**
     * Purchasing customer object
     */
    Customer customer;  //purchasing customer details
    /**
     * Transaction object for this ticket
     */
    Transaction transaction;
    /**
     * LocalDate object
     */
    LocalDate showtime;
    /**
     * Seat object for the seat of purchased by customer
     */
    Seat seat;
    /**
     * Price of this ticket
     */
    double price;
    /**
     * Array list of local date object to keep track of holiday periods
     */
    ArrayList<LocalDate>Holidays = null;
    Scanner sc= new Scanner(System.in);

    /**
     * Creates a new Ticket with the given attributes
     * @param movie             This Ticket's movie
     * @param cinema            This Ticket's cinema
     * @param customer          This Ticket's customer
     * @param showtime          This Ticket's showtime
     * @param seat              This Ticket's seat
     * @param TID               This Ticket's transaction ID
     * @param custName          This Ticket's customer name
     * @param custEmail         This Ticket's customer email
     * @param custMobileNumber  This Ticket's customer mobile number
     */
    public Ticket(Movie movie, Cinema cinema, Customer customer, LocalDate showtime, Seat seat, String TID, String custName, String custEmail, String custMobileNumber) {
    	this.movie = movie;
    	this.cinema = cinema;
    	this.customer = customer;
    	this.showtime = showtime;
    	this.seat = seat;
    	transaction = new Transaction(TID, custName, custEmail, custMobileNumber);
	    double priceL = cinema.getTicketPrice(movie.getTitle());
	    if (priceL == 0) {
	    	price = 0;
	    	calculatePrice();
	    }
	    else {
	    	price = priceL;
	    	calculatePrice();
	    }
    }

    /**
     * A method to calculate the prices of this Ticket
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
    	
    	
    	if (price==0)
    		moviePrice = 10.0;
    	else
    		moviePrice = price;
    		
    	movieType = movie.getType();
    	customerAge = customer.getAge();
    	seatType = seat.getType();

        //update multiplier for different days of week

	    //need to create class to calculate holidays
    	HolidayController a = new HolidayController(holidayFileName);
	    day = showtime.getDayOfWeek().toString();
    	if ("SATURDAY".equalsIgnoreCase(day)||"SUNDAY".equalsIgnoreCase(day)||a.isAHoliday(showtime)) {
    		multiplier = multiplier*1.5;
    	}

        //update multiplier for movietype
        switch(movieType){
               case TWO_D:
                    //no change in multiplier;
                    break;
               case THREE_D:
                    multiplier = multiplier *1.2;
                    break;
               case BLOCKBUSTER:
                    multiplier = multiplier * 1.5;
                    break;
               default:
                   //no change
                   break;
         }

         //update multiplier for cinemaType
         switch(seatType){
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
        if (customerAge<12) multiplier = multiplier * 0.8;
        //no price change if its adult since default price is for adults
        else if (customerAge>=56)multiplier = 0.7*multiplier;
              

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
     * A method to check if Ticket is purchased during a Holiday
     * @return  True for Holiday, False for not a Holiday
     */
    public boolean isAHoliday() {
    	for (int i=0;i<Holidays.size();i++) {
    		if(Holidays.get(i) == showtime)
    			return true;
    	}
    	return false;
    }

    /**
     * Gets Customer object of this Ticket
     * @return  This Ticket's Customer object
     */
    public Customer getCustomer(){
         return customer;
    }

    /**
     * Gets the Price of this Ticket
     * @return  This Ticket's Price
     */
    public double getPrice(){
    	return price;
    }

    /**
     * Gets the Seat object of this Ticket
     * @return  This Ticket's Seat object
     */
    public Seat getSeat() {
	return seat;
    }

    /**
     * Gets the Transaction object of this Ticket
     * @return  This Ticket's Transaction object
     */
    public Transaction getTransaction() {
	return this.transaction;
    }

    /**
     * Gets the Cinema Object of this Ticket
     * @return  This Ticket's Cinema object
     */
    public Cinema getCinema() {
    	//method to return cinema associated with the ticket.
    	return cinema;
    }

    /**
     * Gets the Movie object of this Ticket
     * @return  This Ticket's Movie object
     */
    public Movie getMovie() {
    	//method to return the movie for the ticket
    	return movie;
    }

    /**
     * Gets the LocalDate object of this Ticket
     * @return  This Ticket's LocalDate object
     */
    public LocalDate getShowtime() {
    	//method to return showtime of the ticket.
    	return showtime;
    }

    /**
     * Changes the price of this Ticket
     * @param newPrice  The new price of Ticket
     */
    public void setPrice(double newPrice){
    	price = newPrice;
    }

    /**
     * Overrides toString method to store Ticket in a specific format
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
