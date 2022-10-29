import java.util.Date;
import Cinema;

public abstract class MovieAbs {
    String title;

    enum Status {
        ComingSoon,
        Preview,
        NowShowing,
        EndOfShowing
    }

    String synopsis;
    String Director, Cast1, Cast2;

    enum ParentalRating {
        U,
        PG13,
        UA,
        A,
        R
    }

    enum Type {
        Blockbuster,
        3D,
        ChristmasSpecial,
        Documentary
    }

    double rating;  //overall average rating for this movie
    public String[] reviews; //all the reviews this movie has received
    int[] ratings;  //all the ratings received by the movie ever
    Cinema[] cinemas; //list of all cinemas being broadcasted in
     
    public abstract void addRating(String review, int rating);  //method to add rating and review of a client, and update average;
    public getParentalRating();  //get the parental rating
    //implement simple wrappers to access the class variables
}
