import java.util.*;

public abstract class CinemaAbs {
    Movie[] movies; //list of movies available at the cinema;
    HashMap<String, HashMap<String, HashMap<Integer, Customer>>> seats;  //returns customer if taken else null for a movie for a showtime at cinema
    HashMap<String, HashMap<String, HashMap<String, String>>> tID;  //format is map.get(username).get(movie.title).get(movie.showtime) return tID
    HashMap<String, HashMap<String, Integer>> free;  //free seats
    int code;

    public abstract void printLayout(Movie movie, Date showtime);   //print seat layout for a given Movie based on showtime.

    public abstract String makeBooking(Client client, Movie movie, Date showtime); //makes booking for client based on showtime if seats are available, returns tID.
}
