package src.controllers;

import src.entities.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controller for Movie entity
 *
 * @author xichen
 * @version 1.0
 * @since 2022-11-13
 */

public class MovieController {
    /**
     * filename of movies database
     */
    private String fileName;
    /**
     * class variable to store movie database
     */
    private static ArrayList<Movie> movieDB;

    /**
     * constructor sets the filename and retrieves data from file
     *
     * @param fileName is the location of file
     */

    public MovieController(String fileName) {
        this.fileName = fileName; //Input the directory where your .dat file is located
        movieDB = this.getAllMoviesFromDB();
    }

    /**
     * gets the filename of the movie database
     *
     * @return filename of movie database
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * set filename of database
     *
     * @param fileName is the new file location
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Create a new movie object and insert it into the database
     *
     * @param title            of movie
     * @param type             of movie
     * @param status           of movie
     * @param synopsis         of movie
     * @param director         of movie
     * @param casts            of movie
     * @param rating           of movie
     * @param runtime          of movie
     * @param movieReleaseDate of movie
     * @param totalSales       of movie
     */
    //Create a new movie object and insert it into the database
    public void insertMovieToDB(String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
                                String rating, int runtime, LocalDate movieReleaseDate, int totalSales) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Movie newMovie = new Movie(this.getLastID() + 1, title, type, status, synopsis, director, casts, rating, runtime, movieReleaseDate, totalSales); //Need to update ID to get from db

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);
        if (f.exists())
            movies = this.getAllMoviesFromDB();//Read in existing data in db
        else {
            System.out.println("File: " + fileName + " does not exist");
            System.out.println("Creating new DB");
        }
        movies.add(newMovie);
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(movies);
            out.close();
        } catch (IOException ex) {

        }

    }

    /**
     * Read the whole movies.dat and return all objects stored inside
     *
     * @return movies, an arraylist from the movies.dat file
     */
    //Read the whole movies.dat and return all objects stored inside
    @SuppressWarnings("unchecked")
    public ArrayList<Movie> getAllMoviesFromDB() {
        ArrayList<Movie> movies = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            movies = (ArrayList<Movie>) in.readObject();
            in.close();
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }
        return movies;
    }

    /**
     * Iterate through all the movies and update the object with new values
     *
     * @param option   to choose what attribute of movie to update
     * @param movieID  to choose which movie to update
     * @param newValue is the new value used to update the movie
     * @return true if movie is updated. False if movie was not updated
     */
    //Iterate through all the movies and update the object with new values
    @SuppressWarnings("unchecked")
    public boolean updateMovieById(int option, int movieID, Object newValue) {
        ArrayList<Movie> movies = null;

        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (Movie movie : movies)
                    if (movie.getId() == movieID) {
                        switch (option) {
                            case 1:
                                movie.setTitle((String) newValue);
                                break;
                            case 2:
                                movie.setType((MovieType) newValue);
                                break;

                            case 3:
                                movie.setStatus((MovieStatus) newValue);
                                break;
                            case 4:
                                movie.setSynopsis((String) newValue);
                                break;

                            case 5:
                                movie.setDirector((String) newValue);
                                break;

                            case 6:
                                movie.setCasts((ArrayList<String>) newValue);
                                break;

                            case 7:
                                movie.setRating((String) newValue);
                                break;

                            case 8:
                                movie.setRuntime((int) newValue);
                                break;

                            case 9:
                                movie.setMovieReleaseDate((LocalDate) newValue);
                                break;
                            default:
                                System.out.println("Error occurred during update. Update failed");
                        }
                        break;
                    }
                if (this.updateExistingFile(movies))
                    return true;
            } else
                System.out.println("Movie Listing is empty. Nothing to update");

        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * Iterate through all the movies and remove the object
     *
     * @param movieID is the unique identifier for movie
     * @return true if movie was removed. False otherwise.
     */
    //Iterate through all the movies and remove the object
    public boolean removeMovieByID(int movieID) {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++)
                    if (movies.get(i).getId() == movieID) {
                        movies.remove(i);
                        break;
                    }
                if (this.updateExistingFile(movies))
                    return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * returns the last movie id in the database
     *
     * @return id of last movie
     */
    //Obtain the ID of the last movie in the database
    public int getLastID() {
        ArrayList<Movie> movies = null;
        int id = -1;

        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            id = movies.get(movies.size() - 1).getId();
            return id;
        } else
            System.out.println("File: " + fileName + " does not exist");

        return id;
    }

    /**
     * Iterate and print all the movies title and id
     *
     * @return true if movies have been printed. False if filename was invalid
     */
    //Iterate and print all the movies title and id
    public boolean printAllMoviesTitleWithID() {
        ArrayList<Movie> movies = null;

        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                System.out.println("ID\tTitle");
                System.out.println("--\t-----");
                for (Movie movie : movies)
                    System.out.println(movie.getId() + "\t" + movie.getTitle());
                return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * Check if user selected ID exist in the database
     *
     * @param movieID is the unique identifier of movie
     * @return true if the movie exists
     */
    //Check if user selected ID exist in the database
    public boolean checkIdExist(int movieID) {
        ArrayList<Movie> movies = null;

        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (Movie movie : movies)
                    if (movie.getId() == movieID)
                        return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * Delete the current movies.dat file and reinsert the new one
     *
     * @param newData is the data going to be passed into the new movies database file
     * @return true if operation was successful. False if filename don't exist
     */

    //Delete the current movies.dat file and reinsert the new one
    public boolean updateExistingFile(ArrayList<Movie> newData) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);
        if (f.exists())
            f.delete();
        else
            System.out.println("File: " + fileName + " does not exist");
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(newData);
            out.close();
        } catch (IOException ex) {

            return false;
        }

        return true;
    }

    /**
     * Print the top 5 movies based on the Overall Review
     *
     * @return true if operation was successful. False if filename is invalid
     */
    //Print the top 5 movies based on the Overall Review
    public boolean printTopFiveMovies() {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                List<Movie> listMovies = movies;

                Collections.sort(listMovies, new Comparator<Movie>() {

                    public int compare(Movie o1, Movie o2) {
                        return Double.valueOf(o2.getOverallReview()).compareTo(Double.valueOf(o1.getOverallReview()));
                    }
                });

                ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
                System.out.println("Top 5 Movies by Overall Rating\n");
                System.out.println("Overall Rating\tTitle");
                System.out.println("--------------\t-----");
                for (int i = 0; i < 5; i++)
                    System.out.println("\t" + sortedMovies.get(i).getOverallReview() + "\t" + sortedMovies.get(i).getTitle());

                return true;

            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return false;
    }

    /**
     * Print the top 5 movies based on the Overall Review
     *
     * @return true if operation is done successfully. False if filename invalid.
     */
    //Print the top 5 movies based on the Overall Review
    public boolean printTopFiveMoviesBySales() {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                List<Movie> listMovies = movies;

                Collections.sort(listMovies, new Comparator<Movie>() {
                    public int compare(Movie o1, Movie o2) {
                        return Double.valueOf(o2.getTotalSales()).compareTo(Double.valueOf(o1.getTotalSales()));
                    }
                });

                ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
                System.out.println("Top 5 Movies by Total Sales\n");
                System.out.println("Total Sales\tTitle");
                System.out.println("--------------\t-----");
                for (int i = 0; i < 5; i++)
                    System.out.println("\t" + sortedMovies.get(i).getTotalSales() + "\t" + sortedMovies.get(i).getTitle());

                return true;

            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return false;
    }


    /**
     * Search the movie in the database and return those movies that the input of user matches.
     *
     * @param input is the movie title to search for
     * @return list of movies that matches user search
     */
    //Search the movie in the database and return those movies that the input of user matches.
    public ArrayList<Movie> searchMovies(String input) {
        String regex = "^" + input;
        ArrayList<Movie> movies = null;
        ArrayList<Movie> matchedMovie = new ArrayList<Movie>();
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                for (Movie movie : movies)
                    if (p.matcher(movie.getTitle()).find())
                        matchedMovie.add(movie);
            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return matchedMovie;
    }

    /**
     * Insert a new rating for a movie into the movie database
     *
     * @param movieID      is identifier for movie
     * @param customerName name of customer
     * @param numRating    rating that customer wants to give
     * @param comments     from customer
     * @return true if review was added. False if invalid file name
     */
    //Insert a new rating for a movie into the movie database
    public boolean addRating(int movieID, String customerName, int numRating, String comments) {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++)
                    if (movies.get(i).getId() == movieID) {
                        movies.get(i).getReviews().add(new Review(customerName, numRating, comments));
                        break;
                    }
                if (this.updateExistingFile(movies))
                    return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return false;
    }

    /**
     * Insert a totalSales for a movie into the movie database
     *
     * @param movieID    the id of movie
     * @param totalSales the total profit earned from movie
     * @return true if movie sales is successfully update. False if filename was invalid
     */

    //Insert a totalSales for a movie into the movie database
    public boolean addTotalSales(int movieID, int totalSales) {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++)
                    if (movies.get(i).getId() == movieID) {
                        movies.get(i).setTotalSales(totalSales);
                        break;
                    }
                if (this.updateExistingFile(movies)) return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return false;
    }

    /**
     * Insert cinema that is broadcasting the movie into the movie database
     *
     * @param movieID is unique id of movie
     * @param cinema  is cinema to broadcast the movie
     * @return true if movie sucessfully assigned to cinema. false if filename is invalid.
     */

    //Insert cinema that is broadcasting the movie into the movie database
    public boolean assignCinemaToMovie(int movieID, Cinema cinema) {
        ArrayList<Movie> movies = null;
        File f = new File(fileName);
        if (f.exists()) {
            movies = this.getAllMoviesFromDB();//Read in existing data in db
            if (movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++)
                    if (movies.get(i).getId() == movieID) {
                        movies.get(i).getCinemas().add(cinema);
                        break;
                    }
                if (this.updateExistingFile(movies))
                    return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");
        return false;
    }

    /**
     * returns data of all movies in current static class variable
     *
     * @return movieDB which is current static class variable
     */

    public static ArrayList<Movie> getMovieDB() {
        return movieDB;
    }

    /**
     * set movies in parameter into current class variable
     *
     * @param movies is collection of movies to update to class variable
     */
    public static void setMovieDB(ArrayList<Movie> movies) {
        movieDB = movies;
    }

    /**
     * adds a single movie object to current collection of movies in class variable
     *
     * @param newMovie is the movie to be added
     */
    public static void setMovieDB(Movie newMovie) {
        movieDB.add(newMovie);
    }
}
