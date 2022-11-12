package src.entities;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Movie implements Serializable{
	private int id; //Movie unique ID
	private String title;
	private MovieType type;
	private MovieStatus status;
	private String synopsis; //Information about the movie
	private String director;
	private ArrayList<String> casts;
	private String rating;  //Ratings for this movie e.g. PG M18 etc.
	private ArrayList<Review> reviews; //all the reviews this movie has received
	private ArrayList<Cinema> cinemas;
	private int runtime;  //Duration of the movie in (MINS)
	private LocalDate movieReleaseDate;
	private int totalSales = 0;

	/*
	 * No abstract methods atm
	 */


	//Constructor
	public Movie(String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
			String rating, int runtime, LocalDate movieReleaseDate, int totalSales) {
		this.title = title;
		this.type = type;
		this.status = status;
		this.synopsis = synopsis;
		this.director = director;
		this.casts = casts;
		this.rating = rating;
		this.reviews = new ArrayList<Review>();
		this.cinemas = new ArrayList<Cinema>();
		this.runtime = runtime;
		this.movieReleaseDate = movieReleaseDate;
		this.totalSales = totalSales;
	}
	
	public Movie(int id, String title, MovieType type, MovieStatus status, String synopsis, String director, ArrayList<String> casts,
			String rating, int runtime, LocalDate movieReleaseDate, int totalSales) {

		this.id = id;
		this.title = title;
		this.type = type;
		this.status = status;
		this.synopsis = synopsis;
		this.director = director;
		this.casts = casts;
		this.rating = rating;
		this.reviews = new ArrayList<Review>();
		this.cinemas = new ArrayList<Cinema>();
		this.runtime = runtime;
		this.movieReleaseDate = movieReleaseDate;
		this.totalSales = totalSales;
	}

	//Getters
    public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public MovieType getType() {
		return type;
	}
	public MovieStatus getStatus() {
		return status;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public String getDirector() {
		return director;
	}
	public ArrayList<String> getCasts() {
		return casts;
	}
	public String getRating() {
		return rating;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public ArrayList<Cinema> getCinemas() {
		return cinemas;
	}
	public int getRuntime() {
		return runtime;
	}
	public LocalDate getMovieReleaseDate() {
		return movieReleaseDate;
	}
	//Return the dates as String
	public String getMovieReleaseDateToString(){
        return movieReleaseDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }
	public int getTotalSales(){
		return totalSales;
	}


	//Setters
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setType(MovieType type) {
		this.type = type;
	}
	public void setStatus(MovieStatus status) {
		this.status = status;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setCasts(ArrayList<String> casts) {
		this.casts = casts;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	public void addReview(Review review) {
		this.reviews.add(review);
	}
	public void setCinemas(ArrayList<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public void setMovieReleaseDate(LocalDate movieReleaseDate) {
		this.movieReleaseDate = movieReleaseDate;
	}

	public void setTotalSales(int totalSales){
		this.totalSales = getTotalSales() + totalSales;
	}
	
	//Methods
	public String getOverallReview() { //method to obtain the average rating of the movie
		double averageRating, totalRating = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");
		
		if(this.reviews.size() > 0) {
			for(Review review : reviews)
				totalRating+= review.getNumRating();
			
			averageRating = totalRating/this.reviews.size();
			return df.format(averageRating);
		}
		
		return "0";
	}
	
	@Override
	public String toString() {
		String castDetail = "", reviewDetail = "", movieDetail = "";
		for(String cast: this.casts)
			castDetail += cast + ",";
		castDetail = castDetail.substring(0, castDetail.length()-1); // Remove the last appended ,
		
		if(this.reviews.size() > 0) {
			for(Review review: this.reviews) {
				reviewDetail += review.toString() + "\n----------------\n";
			}
		}else
			reviewDetail = "N/A\n";
		
		movieDetail = "Movie Title: " + this.getTitle() + "\n"
					+ "Showing Status: " + this.getStatus().toString() + "\n"
					+ "Synopsis : " + this.getSynopsis() + "\n"
					+ "Director : " + this.getDirector() + "\n"
					+ "Casts : " + castDetail + "\n"
					+ "Overall rating(1-5): " + this.getOverallReview() + "\n"
					+ "Past reviews : \n" + reviewDetail;
		return movieDetail;
	}
	
	
}
