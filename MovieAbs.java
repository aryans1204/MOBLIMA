import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import Cinema;

public abstract class MovieAbs {

// ENUM.java CLASS //
    enum Status {
        ComingSoon,
        Preview,
        NowShowing,
        EndOfShowing
    }
    
    enum Type {
        Blockbuster,
        3D,
        ChristmasSpecial,
        Documentary
    }
 //END of ENUM.java CLASS //
    
 // Movie.java CLASS //
    private int id; //Movie unique ID
	private String title; //Title of the movie
	private MovieType type; //ENUM data as declared above
	private MovieStatus status; //ENUM data as declared above
	private String synopsis; //Information about the movie
	private String director; //Director of the movie
	private ArrayList<String> casts; //List of casts in the movie (Min 2 casts must be added during initialization)
	private String rating;  //Paretnal Ratings for this movie e.g. PG M18 etc.
	private ArrayList<Review> reviews; //list of all the reviews&ratings this movie has received
	private ArrayList<Cinema> cinemas; //list of all cinemas being broadcasted in
	private int runtime;  //Duration of the movie in (MINS)
	private LocalDate movieReleaseDate; //release date of the movie

	//Methods
	//method to obtain the overall rating of the movie; returns a string in the format of e.g. "4.50"
	public String getOverallReview();
	//method to print the movie details. e.g. you can just print the movie object e.g. s.o.p(movie) directly and it will print the movies detail
	public String toString();
	//Converts LocalDate Date and return the string in the format of dd/MM/yyyy
	public String getMovieReleaseDateToString();
	// ONLY parameterized constructor is defined //
	// REST OF THE METHODS WILL BE THE GETTERS AND SETTERS METHOD FOR ALL ATTRIBUTES AS PER NORMAL //
	
 //Movie.java CLASS //
	
 // MovieController.java CLASS //
	//FILENAME attribute to indicate the path for storing/reading of movies.dat(Database)
	private String FILENAME;
	//ONLY parameterized constructor is defined
	MovieController(String fileName);
	//Get attribute fileName
	public String getFileName();
	//Set attribute fileName
	public void setFileName(String fileName);
	
	//Create a new movie object and insert it into the database. //ArrayList<Reviews> & ArrayList<Cinemas> are empty when a movie is just created.
	public void insertMovieToDB(String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
			String rating, int runtime, LocalDate movieReleaseDate);
	
	//Read the whole movies.dat and return all objects stored inside
	public ArrayList<Movie> getAllMoviesFromDB();
	
	//Iterate through all the movies and update the specific object by MovieID with given newValue. option indicates the attribute to be updated
	public boolean updateMovieById(int option, int movieID, Object newValue);
	
	//Iterate through all the movies and remove the specific object by MovieID
	public boolean removeMovieByID(int movieID);
	
	//Obtain the ID of the last movie in the database
	public int getLastID();
	
	//Iterate and print all the movies title and id
	public boolean printAllMoviesTitleWithID();
	
	//Check if user selected ID exist in the database
	public boolean checkIdExist(int movieID);
	
	//Delete the current movies.dat file and reinsert a new one to update the movie.dat with new datas
	public boolean updateExistingFile(ArrayList<Movie> newData);

	//Print the top 5 movies based on the Overall Review
	public boolean printTopFiveMovies();
	
	//Search the movie in the database and return those movies that the input of user matches.
	public ArrayList<Movie> searchMovies(String input);
	
	//Insert a new rating for a movie into the movie database
	public boolean addRating(int movieID, String customerName, int numRating, String comments);
	
	//Insert cinema that is broadcasting the movie into the movie database
	public boolean assignCinemaToMovie(int movieID, Cinema cinema);
 // END of MovieController.java CLASS //
	
 // MovieUI.java CLASS //
//Just a sample UI for u to test out all of the methods above declared in MovieController. 
//All of the methods in UI simply acts as a User Interface and mostly belongs to the STAFF class. 
    
}
