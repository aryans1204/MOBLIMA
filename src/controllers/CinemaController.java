package src.controllers;

import src.entities.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CinemaController {
    private String filename;
    private String seatFileName;
    private static ArrayList<Cinema> cinemaDB;

    public CinemaController(String filename, String seatFileName) {
        this.filename = filename;
        this.seatFileName = seatFileName;
        cinemaDB = this.getAllCinemasFromDB();
    }

    public void insertCinemaIntoDB(String cinemaName, ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDateTime>> showtimes) {
        HashMap<String, ArrayList<Seat>> seats = this.getSeatsFromDB(movies, showtimes);
        ArrayList<Cinema> cinemas = new ArrayList<>();
        Cinema newCinema = new Cinema(cinemaName, movies, showtimes, seats);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(filename);
        if (f.exists())
            cinemas = this.getAllCinemasFromDB();//Read in existing data in db
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

    public HashMap<String, ArrayList<Seat>> getSeatsFromDB(ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDateTime>> showtimes) {
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
            ArrayList<LocalDateTime> showtime = showtimes.get(title);
            for (LocalDateTime sho : showtime) {
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
            cinemas = (ArrayList<Cinema>) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return cinemas;
    }

    public void insertSeatIntoDB(SeatType type, Customer customer, String seatNo) {
    	ArrayList<Seat> seats = new ArrayList<Seat>();
		Seat newSeat = new Seat(type, customer, seatNo);

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(seatFileName);

        if (f.exists())
        	seats = this.getAllSeatsFromDB();//Read in existing data in db
        else {
            System.out.println("File: " + seatFileName + " does not exist");
            System.out.println("Creating new DB");
        }
        seats.add(newSeat);
        try {
        	fos = new FileOutputStream(seatFileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(seats);
			out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Cinema> getCinemaDB() {
        return cinemaDB;
    }

    public static void setCinemaDB(ArrayList<Cinema> cinemaDB) {
        CinemaController.cinemaDB = cinemaDB;
    }

    public ArrayList<Seat> getAllSeatsFromDB() {
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
        return seats;
    }

}
