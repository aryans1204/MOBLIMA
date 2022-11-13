package src.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a Movie in the system
 * Contains all relevant information of a Movie
 * @author JinCheng
 */
@SuppressWarnings("serial")
public class Movie implements Serializable {

    /**
     * Unique Movie ID
     */
    private int id; //Movie unique ID

    /**
     * Movie's title
     */
    private String title;

    /**
     * Type of this Movie
     */
    private MovieType type;

    /**
     * Status of this Movie
     */
    private MovieStatus status;

    /**
     * Synopsis of this Movie
     */
    private String synopsis; //Information about the movie

    /**
     * Director of this Movie
     */
    private String director;

    /**
     * Array list of casts for this Movie
     */
    private ArrayList<String> casts;

    /**
     * Ratings of this Movie
     */
    private String rating;  //Ratings for this movie e.g. PG M18 etc.

    /**
     * Array list of reviews for this Movie
     */
    private ArrayList<Review> reviews; //all the reviews this movie has received

    /**
     * Array list of Cinemas associated with this Movie
     */
    private ArrayList<Cinema> cinemas;

    /**
     * Runtime of this Movie
     */
    private int runtime;  //Duration of the movie in (MINS)

    /**
     * Release date of this Movie
     */
    private LocalDate movieReleaseDate;

    /**
     * Total number of sales of this Movie
     */
    private int totalSales = 0;


    /**
     * Creates a Movie with the given attributes
     * @param title                 This Movie's title
     * @param type                  This Movie's type
     * @param status                This Movie's status
     * @param synopsis              This Movie's synopsis
     * @param director              This Movie's director
     * @param casts                 This Movie's casts
     * @param rating                This Movie's rating
     * @param runtime               This Movie's runtime
     * @param movieReleaseDate      This Movie's release date
     * @param totalSales            This Movie's total sales
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

    /**
     * Creates a Movie overloaded with the given attributes
     * @param id                    This Movie's ID
     * @param title                 This Movie's title
     * @param type                  This Movie's type
     * @param status                This Movie's status
     * @param synopsis              This Movie's synopsis
     * @param director              This Movie's director
     * @param casts                 This Movie's casts
     * @param rating                This Movie's rating
     * @param runtime               This Movie's runtime
     * @param movieReleaseDate      This Movie's release date
     * @param totalSales            This Movie's total sales
     */
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

    /**
     * Gets the ID of this Movie
     * @return  this Movie's ID
     */
    //Getters
    public int getId() {
        return id;
    }

    /**
     * Gets the title of this Movie
     * @return  this Movie's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the type of this Movie
     * @return  this Movie's type
     */
    public MovieType getType() {
        return type;
    }

    /**
     * Gets the status of this Movie
     * @return  this Movie's status
     */
    public MovieStatus getStatus() {
        return status;
    }

    /**
     * Gets the synopsis of this Movie
     * @return  this Movie's synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Gets the director of this Movie
     * @return  this Movie's director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the casts of this Movie
     * @return  this Movie's casts
     */
    public ArrayList<String> getCasts() {
        return casts;
    }

    /**
     * Gets the rating of this Movie
     * @return  this Movie's rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Gets the reviews of this Movie
     * @return  this Movie's reviews
     */
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    /**
     * Gets the Cinema of this Movie
     * @return  this Movie's Cinema
     */
    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    /**
     * Gets the runtime of this Movie
     * @return  this Movie's runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Gets the release date of this Movie
     * @return  this Movie's release date
     */
    public LocalDate getMovieReleaseDate() {
        return movieReleaseDate;
    }

    /**
     * A method to convert Movie release date to a specific format
     * @return  new Movie release date
     */
    //Return the dates as String
    public String getMovieReleaseDateToString() {
        return movieReleaseDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    /**
     * Gets the total number of sales of this Movie
     * @return  this Movie's total sale
     */
	public int getTotalSales(){
		return totalSales;
	}

    /**
     * Changes the id of this Movie
     * @param id    This Movie's new ID
     */
	//Setters
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Changes the title of this Movie
     * @param title This Movie's new title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Changes the type of this Movie
     * @param type  This Movie's new type
     */
	public void setType(MovieType type) {
		this.type = type;
	}

    /**
     * Changes the status of this Movie
     * @param status    This Movie's new status
     */
	public void setStatus(MovieStatus status) {
		this.status = status;
	}

    /**
     * Changes the synopsis of this Movie
     * @param synopsis  This Movie's new synopsis
     */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

    /**
     * Changes the director of this Movie
     * @param director  This Movie's new director
     */
	public void setDirector(String director) {
		this.director = director;
	}

    /**
     * Changes the casts of this Movie
     * @param casts This Movie's new casts
     */
	public void setCasts(ArrayList<String> casts) {
		this.casts = casts;
	}

    /**
     * Changes the rating of this Movie
     * @param rating    This Movie's new rating
     */
	public void setRating(String rating) {
		this.rating = rating;
	}

    /**
     * Changes the reviews of this Movie
     * @param reviews   This Movie's new reviews
     */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

    /**
     * A method for adding review
     * @param review    Review to be added
     */
	public void addReview(Review review) {
		this.reviews.add(review);
	}

    /**
     * Changes the Cinema associated to this Movie
     * @param cinemas   This Movie's new Cinema
     */
	public void setCinemas(ArrayList<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

    /**
     * Changes the runtime of this Movie
     * @param runtime   This Movie's new runtime
     */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

    /**
     * Changes the release date of this Movie
     * @param movieReleaseDate  This Movie's new release date
     */
	public void setMovieReleaseDate(LocalDate movieReleaseDate) {
		this.movieReleaseDate = movieReleaseDate;
	}

    /**
     * Changes the total sale of this Movie
     * @param totalSales    This Movie's new total sale
     */
	public void setTotalSales(int totalSales){
		this.totalSales = getTotalSales() + totalSales;
	}

    /**
     * A method for calculating the overall reviews
     * @return  string of average review. "N/A" if no reviews
     */
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

    /**
     * Overrides toString method to store Movie detail in a specific format
     * @return  a string of Movie details
     */
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
                    + "Movie Type: " + this.getType() + "\n"
					+ "Showing Status: " + this.getStatus().toString() + "\n"
					+ "Synopsis : " + this.getSynopsis() + "\n"
					+ "Director : " + this.getDirector() + "\n"
					+ "Casts : " + castDetail + "\n"
					+ "Overall rating(1-5): " + this.getOverallReview() + "\n"
					+ "Past reviews : \n" + reviewDetail;
		return movieDetail;
	}
	
}
