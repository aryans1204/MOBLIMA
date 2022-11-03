import java.util.*;
public abstract class TicketAbs {
    Movie movie;  //associated movie with the ticket
    Cinema cinema; //associated cinema of the movie, this will also provide seat number
    Customer customer;  //purchasing customer details
    String showtime;
    int seatNo;   //in Cinema class, the free seat id will be passed to Ticket
    double price;
    String priceFileName;
    
    //this is the constructor:
    //constructor will call function calculatePrice to set the price for the ticket.
    //public abstract Ticket(Movie a, Cinema b, Customer c, String d, int sit_no, String price_FileName);
    
    
    //calculate price calls getPricesFromDB to get the default price of the movie by movie id.
    public abstract void calculatePrice();
    public abstract int[] getPricesFromDB();
    
    public abstract double getPrice();  //method to get price of a ticket based on the factors

    public abstract Cinema getCinema(); //method to return cinema associated with the ticket.

    public abstract Movie getMovie(); //method to return the movie for the ticket

    public abstract Date getShowtime();  //method to return showtime of the ticket.
    
    public abstract Customer getCustomer(); //method to return customer of ticket
}
