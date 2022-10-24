import java.util.*;

public abstract class TicketAbs {
    Movie movie;  //associated movie with the ticket
    Cinema cinema; //associated cinema of the movie, this will also provide seat number
    Customer client;  //purchasing client details
    String showtime;
    int seatNo;

    public abstract double getPrice();  //method to get price of a ticket based on the factors

    public abstract Cinema getCinema(); //method to return cinema associated with the ticket.

    public abstract Movie getMovie(); //method to return the movie for the ticket

    public abstract Date getShowtime();  //method to return showtime of the ticket.
}
