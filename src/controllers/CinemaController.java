package controllers;

import java.util.*;
import java.io.*;
import java.time.*;

public class CinemaController {
    private String filename;
    private String seatFilename;

    public CinemaController(String filename, String seatFileName) {
	this.filename = filename;
	this.seatFileName = seatFileName;
    }

   public void insertCinemaIntoDB(String name, HashMap<String, Double> prices, ArrayList<Movie>  movies, HashMap<String, ArrayList<LocalDate>> showtimes) {

	ArrayList<Cinema> cinemas = new ArrayList<>();
	Cinema newCinema = new Cinema(name, prices, movies, showtimes)
	cinema.setSeats(getSeatsFromDB(movies, showtimes));	
	FileOutputStream fos = null;
	ObjectOutputStream out = null;
	File f = new File(filename);
	if(f.exists())
	movies = this.getAllCinemasFromDB();//Read in existing data in db
	else {
		System.out.println("File: " + filename + " does not exist");
		System.out.println("Creating new DB");
	}
	cinemas.add(newCinema);
	try {
		fos = new FileOutputStream(filename);
		out = new ObjectOutputStream(fos);
		out.writeObject(cinemas);
		out.close();
	} catch (IOException ex) {
		ex.printStackTrace();
	}
    }
    
    public HashMap<String, ArrayList<Seat>> getSeatsFromDB(ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDate>> showtimes) {
	ArrayList<Seat> seats = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
             fis = new FileInputStream(seatFileName);
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

    public ArrayList<Cinema> getAllCinemasFromDB() {
	ArrayList<Cinema> cinemas = null;
	FileInputStream fis = null;
	ObjectInputStream in = null;
	try {
		fis = new FileInputStream(filename);
		in = new ObjectInputStream(fis);
		movies = (ArrayList<Cinema>) in.readObject();
		in.close();
	} catch (IOException ex) {
		ex.printStackTrace();
	} catch (ClassNotFoundException ex) {
		ex.printStackTrace();
	}
	return cinemas;
    }
	
    public void insertSeatIntoDB(SeatType type, Customer customer) { 
	try {
		FileOutputStream fos = new FileOutputStream(seatFileName);
  		ObjectOutputStream out = new ObjectOutputStream(fos);
		Seat newSeat = new Seat(type, customer);
		out.writeObject(newSeat);
	} catch (IOException ex) {
		ex.printStackTrace();
	}	
}  	 
