import java.util.*;
import Ticket;
import Customer;
import Movie;

public abstract class CinemaAbs {
    Movie[] movies; //list of movies available at the cinema;
    HashMap<String, HashMap<String, HashMap<Integer, Customer>>> seats;  //returns customer if taken else null for a movie for a showtime at cinema
    HashMap<String, HashMap<String, HashMap<String, String>>> tID;  //format is map.get(username).get(movie.title).get(movie.showtime) return tID
    int code; //0, 1, 2...

    enum CinemaType {
	GOLD,
	PLATINUM,
	DIRECTORS_CUT
    };

    public abstract void printLayout(Movie movie, String showtime);   //print seat layout for a given Movie based on showtime.

    public abstract Ticket makeBooking(Customer client, Movie movie, String showtime); //makes booking for client based on showtime if seats are available, returns Ticket.

    public abstract int[] freeSeats();  //returns the IDs of the free seats. and empty array otherwise. looking at seats that are null

    public abstract CinemaType getType(); //get cinema type;
}
