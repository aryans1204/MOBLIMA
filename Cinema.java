import java.util.*;
import java.io.*;

public class Cinema implements Serializable {
    private HashMap<String, Double> prices = new HashMap<>();  //prices indicator per movie-title for Staff to set.
    private ArrayList<Movie>  movies;  //array of Movies at this cinema;
    private HashMap<String, ArrayList<LocalDate>> showtimes;
    private HashMap<String, ArrayList<Seat>> seats;  //key is the movie title + showtime.toString().
    private String cinemaName;
    public Cinema(String cinemaName, ArrayList<Movie> movies, HashMap<Movie, ArrayList<LocalDate>> showtimes, HashMap<String, ArrayList<Seat> seats) {
	
	this.movies = movies;
	this.cinemaName = cinemaName;
	ArrayList<LocalDate> shows = new ArrayList<>();
	this.showtimes = new HashMap<>();
	for (Movie mov : showtimes.keySet()) {
		this.showtimes.put(mov.getTitle(), showtimes.get(mov));
		this.prices.put(mov.getTitle(), 0);  //0 is a sign that Staff hasn't configured prices yet
	}
	this.seats = seats;
    }

    public void setTicketPrice(String title, double price) {
	this.prices.put(title, price);
    }

    public void setShowtime(String title, LocalDate showtime) {
	ArrayList<LocalDate> shows = this.showtimes.get(title);
	shows.add(showtime);
	this.showtimes.put(title, shows);
    }

    public void setName(String name) {
	this.cinemaname = name;
    }

    public String getName() {
	return this.cinemaName;
    }

    public double getTicketPrice(String title) {
	return this.prices.get(title);
    }

    public getShowtimes(String title) {
	return this.showtimes.get(title);
    }

    public ArrayList<Movie> getMovies() {
	return this.movies;
    }

    public void updateMovies(Movie newMovie) {
	this.movies.add(newMovie);
    }
    
    public void setSeats(String title, Seat newSeat) {
	ArrayList<Seat> s = this.seats.get(title);
	s.add(newSeat);
	this.seats.put(title, s);
    }
    
    public void setSeats(HashMap<String, ArrayList<LocalDate>> seats) {
	this.seats = seats;
    }

    public ArrayList<Seat> getSeats(String title) {
	return this.seats.get(title);
    }    
    public void printLayout(Movie movie, LocalDate showtime);  //prints layout of the Cinema based on available seats for the Movie at the particular showtime
}	
