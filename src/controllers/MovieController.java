package src.controllers;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import src.entities.*;



public class MovieController {
	private String fileName;
	
	MovieController(String fileName){
		this.fileName = fileName; //Input the directory where your .dat file is located
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	//Create a new movie object and insert it into the database
	public void insertMovieToDB(String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
			String rating, int runtime, LocalDate movieReleaseDate) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Movie newMovie = new Movie(this.getLastID()+1, title, type, status, synopsis, director, casts, rating, runtime, movieReleaseDate); //Need to update ID to get from db
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File f = new File(fileName);
		if(f.exists())
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
			fis = new FileInputStream(fileName);
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
	public boolean updateMovieById(int option, int movieID, Object newValue) {
		ArrayList<Movie> movies = null;
		
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(Movie movie : movies)
					if(movie.getId() == movieID) {
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
			System.out.println("File: " + fileName + " does not exist");
		
		return false;
	}
	
	//Iterate through all the movies and remove the object
	public boolean removeMovieByID(int movieID) {
		ArrayList<Movie> movies = null;
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(int i = 0; i < movies.size(); i++)
					if(movies.get(i).getId() == movieID) {
						movies.remove(i);
						break;
					}
				if(this.updateExistingFile(movies))
					return true;
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		
		return false;
	}
	
	//Obtain the ID of the last movie in the database
	public int getLastID() { 
		ArrayList<Movie> movies = null;
		int id = -1;
		
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			id = movies.get(movies.size()-1).getId();
			return id;
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		
		return id;
	}
	
	//Iterate and print all the movies title and id
	public boolean printAllMoviesTitleWithID() {
		ArrayList<Movie> movies = null;
		
		File f = new File(fileName);
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
			System.out.println("File: " + fileName + " does not exist");
		
		return false;
	}
	
	//Check if user selected ID exist in the database
	public boolean checkIdExist(int movieID) {
		ArrayList<Movie> movies = null;
		
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size()> 0) {
				for(Movie movie: movies)
					if(movie.getId() == movieID)
						return true;
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		
		return false;
	}
	
	//Delete the current movies.dat file and reinsert the new one
	public boolean updateExistingFile(ArrayList<Movie> newData) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File f = new File(fileName);
		if(f.exists())
			f.delete();
		else
			System.out.println("File: " + fileName + " does not exist");
		try {
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(newData);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

	//Print the top 5 movies based on the Overall Review
	public boolean printTopFiveMovies() {
		ArrayList<Movie> movies = null;
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				List<Movie> listMovies = movies;

				Collections.sort(listMovies, new Comparator<Movie>(){

				  public int compare(Movie o1, Movie o2)
				  {
				     return Double.valueOf(o2.getOverallReview()).compareTo(Double.valueOf(o1.getOverallReview()));
				  }
				});
				
				ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
				System.out.println("Top 5 Movies by Overall Rating\n");
				System.out.println("Overall Rating\tTitle");
				System.out.println("--------------\t-----");
				for(int i = 0; i < 5; i ++)
					System.out.println("\t" + sortedMovies.get(i).getOverallReview() + "\t" + sortedMovies.get(i).getTitle());
				
				return true;
				
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		return false;
	}

	//Print the top 5 movies based on the Overall Review
	public boolean printTopFiveMoviesBySales() {
		ArrayList<Movie> movies = null;
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				List<Movie> listMovies = movies;

				Collections.sort(listMovies, new Comparator<Movie>(){
					public int compare(Movie o1, Movie o2)
					{
						return Double.valueOf(o2.getTotalSales()).compareTo(Double.valueOf(o1.getTotalSales()));
					}
				});

				ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
				System.out.println("Top 5 Movies by Total Sales\n");
				System.out.println("Total Sales\tTitle");
				System.out.println("--------------\t-----");
				for(int i = 0; i < 5; i ++)
					System.out.println("\t" + sortedMovies.get(i).getTotalSales() + "\t" + sortedMovies.get(i).getTitle());

				return true;

			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		return false;
	}

	
	//Search the movie in the database and return those movies that the input of user matches.
	public ArrayList<Movie> searchMovies(String input) {
		String regex = "^"+input;
		ArrayList<Movie> movies = null;
		ArrayList<Movie> matchedMovie = new ArrayList<Movie>();
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				for (Movie movie:movies)
				    if (p.matcher(movie.getTitle()).find())
				    	matchedMovie.add(movie);
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		return matchedMovie;
	}
	
	//Insert a new rating for a movie into the movie database
	public boolean addRating(int movieID, String customerName, int numRating, String comments) {
		ArrayList<Movie> movies = null;
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(int i = 0; i < movies.size(); i++)
					if(movies.get(i).getId() == movieID) {
						movies.get(i).getReviews().add(new Review(customerName, numRating, comments));
						break;
					}
				if(this.updateExistingFile(movies))
					return true;
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		return false;
	}

	//Insert a totalSales for a movie into the movie database
	public boolean addTotalSales(int movieID, double totalSales) {
		ArrayList<Movie> movies = null;
		File f = new File(fileName);
		if(f.exists()) {
			movies = this.getAllMoviesFromDB();//Read in existing data in db
			if(movies.size() > 0) {
				for(int i = 0; i < movies.size(); i++)
					if(movies.get(i).getId() == movieID) {
						movies.get(i).setTotalSales(totalSales);
						break;
					}
				if(this.updateExistingFile(movies)) return true;
			}
		}
		else
			System.out.println("File: " + fileName + " does not exist");
		return false;
	}
	
	//Insert cinema that is broadcasting the movie into the movie database
	public boolean assignCinemaToMovie(int movieID, Cinema cinema) {
			ArrayList<Movie> movies = null;
			File f = new File(fileName);
			if(f.exists()) {
				movies = this.getAllMoviesFromDB();//Read in existing data in db
				if(movies.size() > 0) {
					for(int i = 0; i < movies.size(); i++)
						if(movies.get(i).getId() == movieID) {
							movies.get(i).getCinemas().add(cinema);
							break;
						}
					if(this.updateExistingFile(movies))
						return true;
				}
			}
			else
				System.out.println("File: " + fileName + " does not exist");
			return false;
		}
}
