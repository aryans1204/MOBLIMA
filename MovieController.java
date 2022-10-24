import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;



public class MovieController {
	public final static String FILENAME = "\\Academic\\NTU\\SC2002\\SC2002Assignment\\src\\movies.dat";
	
	MovieController(){
		
	}
	
	//Create a new movie object and insert it into the database
	public void insertMovieToDB(String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
			String rating, int runtime, LocalDate movieReleaseDate) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Movie newMovie = new Movie(this.getLastID()+1, title, type, status, synopsis, director, casts, rating, runtime, movieReleaseDate); //Need to update ID to get from db
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File f = new File(FILENAME);
		if(f.exists())
			movies = this.getAllMoviesFromDB();//Read in existing data in db
		else {
			System.out.println("File: " + FILENAME + " does not exist");
			System.out.println("Creating new DB");
		}
		movies.add(newMovie);
		try {
			fos = new FileOutputStream(FILENAME);
			out = new ObjectOutputStream(fos);
			out.writeObject(movies);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//Read the whole movies.dat and return all objects stored inside
	@SuppressWarnings("unchecked")
	public ArrayList<Movie> getAllMoviesFromDB() {
		ArrayList<Movie> movies = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(FILENAME);
			in = new ObjectInputStream(fis);
			movies = (ArrayList<Movie>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return movies;
	}
	
	//Iterate through all the movies and update the object with new values
	@SuppressWarnings("unchecked")
	public boolean updateMovieById(int option, int id, Object newValue) {
		ArrayList<Movie> movies = null;
		
		File f = new File(FILENAME);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(Movie movie : movies)
					if(movie.getId() == id) {
						switch(option) {
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
				if(this.updateExistingFile(movies))
					return true;
			}else
				System.out.println("Movie Listing is empty. Nothing to update");
				
		}
		else 
			System.out.println("File: " + FILENAME + " does not exist");
		
		return false;
	}
	
	//Iterate through all the movies and remove the object
	public boolean removeMovieByID(int id) {
		ArrayList<Movie> movies = null;
		File f = new File(FILENAME);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(int i = 0; i < movies.size(); i++)
					if(movies.get(i).getId() == id) {
						movies.remove(i);
						break;
					}
				if(this.updateExistingFile(movies))
					return true;
			}
		}
		else
			System.out.println("File: " + FILENAME + " does not exist");
		
		return false;
	}
	
	//Obtain the ID of the last movie in the database
	public int getLastID() { 
		ArrayList<Movie> movies = null;
		int id = -1;
		
		File f = new File(FILENAME);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			id = movies.get(movies.size()-1).getId();
			return id;
		}
		else
			System.out.println("File: " + FILENAME + " does not exist");
		
		return id;
	}
	
	//Iterate and print all the movies title and id
	public boolean printAllMoviesTitleWithID() {
		ArrayList<Movie> movies = null;
		
		File f = new File(FILENAME);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size()> 0) {
				System.out.println("ID\tTitle");
				System.out.println("--\t-----");
				for(Movie movie: movies)
					System.out.println(movie.getId() + "\t" + movie.getTitle());
				return true;
			}
		}
		else
			System.out.println("File: " + FILENAME + " does not exist");
		
		return false;
	}
	
	//Check if user selected ID exist in the database
	public boolean checkIdExist(int id) {
		ArrayList<Movie> movies = null;
		
		File f = new File(FILENAME);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size()> 0) {
				for(Movie movie: movies)
					if(movie.getId() == id)
						return true;
			}
		}
		else
			System.out.println("File: " + FILENAME + " does not exist");
		
		return false;
	}
	
	//Delete the current movies.dat file and reinsert the new one
	public boolean updateExistingFile(ArrayList<Movie> newData) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File f = new File(FILENAME);
		if(f.exists())
			f.delete();
		else
			System.out.println("File: " + FILENAME + " does not exist");
		try {
			fos = new FileOutputStream(FILENAME);
			out = new ObjectOutputStream(fos);
			out.writeObject(newData);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

}
