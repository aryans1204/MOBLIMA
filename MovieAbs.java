import java.util.Date;
public abstract class MovieAbs {
    String title;
    int status;
    String synopsis;
    String Director, Cast1, Cast2;
    double rating;  //overall average rating for this movie
    public String[] reviews; //all the reviews this movie has received
    int[] ratings;  //all the ratings received by the movie ever
    Date[] showtimes;  //date of all the showtimes of the movie

    public abstract void addRating(String review, int rating);  //method to add rating and review of a client, and update average;
}
