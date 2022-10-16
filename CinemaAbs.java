import java.util.*;
public abstract class CinemaAbs {
    Movie[] movies; //list of movies available at the cinema;
    boolean[][][] seats; //seats[movie][showtime][seatNo] is the general format since seats are assigned pr movie per showtime.
    Client[][][] client;  //defining a client per seat, if no client on that seat then null
    String[][][] tID;  //associated transactionID for each client
    public abstract void printLayout(Movie movie, Date showtime);   //print seat layout for a given Movie based on showtime.
    public abstract String makeBooking(Client client, Movie movie, Date showtime); //makes booking for client based on showtime if seats are available, returns tID.
}
