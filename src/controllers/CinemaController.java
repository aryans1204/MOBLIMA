/**
 * src.controllers is a group of controllers for operating on customer,staff,holiday,movie things.
 */

package src.controllers;

import src.entities.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for cinema. Reads/writes from cinema  database and manages seats.
 * manages the seats that are occupied by customers and stores them in database.
 *
 * @author xichen
 * @version 1.0
 * @since 2022-11-13
 */


public class CinemaController {
    /**
     * file name for cinema database
     */
    private String filename;
    /**
     * file name for seat database
     */
    private String seatFileName;
    /**
     * class variable for in controller that receives a reference of cinemas from database
     */
    private static ArrayList<Cinema> cinemaDB;

    /**
     * method to set class variable with data from database
     *
     * @param filename     is filename of cinemas database
     * @param seatFileName is filename of seats database
     */
    public CinemaController(String filename, String seatFileName) {
        this.filename = filename;
        this.seatFileName = seatFileName;
        cinemaDB = this.getAllCinemasFromDB();
    }

    /**
     * inserts cinema into class variable
     *
     * @param cinemaName is used to construct a new Cinema object to be added to CinemaDB
     * @param movies     is used to construct a new Cinema object to be added to CinemaDB
     * @param showtimes  is used to construct a new Cinema object to be added to CinemaDB
     */
    public void insertCinemaIntoDB(String cinemaName, ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDateTime>> showtimes) {
        HashMap<String, ArrayList<Seat>> seats = this.getSeatsFromDB(movies, showtimes);
        HashMap<String, ArrayList<Seat>> newSeats = new HashMap<>();
        newSeats = seats;
        ArrayList<Cinema> cinemas = new ArrayList<>();
        Cinema newCinema = new Cinema(cinemaName, movies, showtimes, newSeats);
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
        } catch (IOException ignored) {

        }

    }

    /**
     * gets the showtime, movie title to create a hashmap of seats for each movie, for each showtime
     *
     * @param movies    title used for hashmap of seats
     * @param showtimes for respective movie used for hashmap of seats
     * @return s Hashmap of seats for each movie,showtime.
     */
    public HashMap<String, ArrayList<Seat>> getSeatsFromDB(ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDateTime>> showtimes) {
        ArrayList<Seat> seats = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(seatFileName);
            in = new ObjectInputStream(fis);
            seats = (ArrayList<Seat>) in.readObject();
            in.close();
        } catch (IOException ignored) {

        } catch (ClassNotFoundException ignored) {

        }
        HashMap<String, ArrayList<Seat>> s = new HashMap<>();
        for (Movie movie : movies) {
            String title = movie.getTitle();
            ArrayList<LocalDateTime> showtime = showtimes.get(title);
            for (LocalDateTime sho : showtime) {
                ArrayList<Seat> dummy = new ArrayList<Seat>();
                dummy = (ArrayList<Seat>) seats.clone();
                String key = title + sho.toString();
                s.put(key, dummy);
            }
        }

        return s;
    }

    /**
     * method that retrieves all cinemas from database
     *
     * @return all cinemas from database
     */
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
            
        } catch (ClassNotFoundException ig) {

        }
        return cinemas;
    }

    /**
     * method that inserts newly created seat into database
     *
     * @param type     for seattype
     * @param customer records customer details
     * @param seatNo   records what seat is assigned to
     */

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

        }
    }

    /**
     * method to return class variable
     *
     * @return class variable cinemaDB
     */

    public static ArrayList<Cinema> getCinemaDB() {
        return cinemaDB;
    }

    /**
     * method to set the class vairable
     *
     * @param cinemaDB is set to data from parameter
     */

    public static void setCinemaDB(ArrayList<Cinema> cinemaDB) {
        CinemaController.cinemaDB = cinemaDB;
    }

    /**
     * method to read from database
     *
     * @return ArrayList of seats from database
     */

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

        } catch (ClassNotFoundException ex) {

        }
        return seats;
    }

}
