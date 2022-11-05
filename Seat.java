import java.util.*;
import java.io.*;

public class Seat implements Serializable {
    private enum SeatType {
	STANDARD,
	GOLD,
	PLATINUM
    } type;

    private Customer customer;

    pubic Seat(Cinema cinema, SeatType type, Movie movie, LocalDate showtime, Customer customer) {
	this.type = type;
	this.customer = customer;
    }

    public static HashMap<String, ArrayList<Seat>> getSeatsFromDB(ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDate>> showtimes, Cinema cinema) {
	ArrayList<Seat> seats = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
             fis = new FileInputStream(fileName);
             in = new ObjectInputStream(fis);
             seats = (ArrayList<Seat>) in.readObject();
             in.close();
         } catch (IOException ex) {
             ex.printStackTrace();
         } catch (ClassNotFoundException ex) {
             ex.printStackTrace();
         }
        HashMap<String, ArrayList<Seat>> s = new HashMap<>(); 
	for (Movie movie : movies) {
		String title = movie.getTitle();
		ArrayList<LocalDate> showtime = showtimes.get(title);
		for (LocalDate sho : showtime) {
			String key = title + sho.toString();
			s.put(key, seats);
		}
	}

	return s;
    }
    
    public static insertToDB(Seat seat, String filename) {
	
}
