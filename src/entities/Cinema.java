package src.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Cinema in the system
 * It contains movies, prices of the movies, showtimes, seats and seat layout
 *
 * @author Aryan
 */
//ide suggested @SuppressWarnings("serial")
@SuppressWarnings("serial")
public class Cinema implements Serializable {

    /**
     * A HashMap for Staff to set prices in this Cinema
     * String for associated movie title and Double for the price
     */
    private HashMap<String, Double> prices = new HashMap<>();  //prices indicator per movie-title for Staff to set

    /**
     * Array list of movies to store all movies in this Cinema
     */
    private ArrayList<Movie> movies;  //array of Movies at this cinema;

    /**
     * HashMap of showtimes for movies in this Cinema
     * String indicates movie title and Array list of LocalDateTime for fixed showtimes
     */
    private HashMap<String, ArrayList<LocalDateTime>> showtimes;

    /**
     * HashMap of seats in this Cinema
     * String indicates movie and showtime while array list of Seat is to store the details of each Seat
     */
    private HashMap<String, ArrayList<Seat>> seats;  //key is the movie title + showtime.toString().

    /**
     * This Cinema's name
     */
    private String cinemaName;

    /**
     * Creates a Cinema with the given attributes
     *
     * @param cinemaName This Cinema's name
     * @param movies     Movies available in this Cinema
     * @param showtimes  All showtimes for each movies in this Cinema
     * @param seats      All seats for each movie in this Cinema
     */
    public Cinema(String cinemaName, ArrayList<Movie> movies, HashMap<String, ArrayList<LocalDateTime>> showtimes, HashMap<String, ArrayList<Seat>> seats) {
        this.movies = movies;
        this.cinemaName = cinemaName;

        //line 18, was this a typo? object was not used.
        this.showtimes = showtimes;
        for (Movie mov : movies) {
            this.prices.put(mov.getTitle(), 0.0);  //0 is a sign that Staff hasn't configured prices yet
        }
        this.seats = seats;
    }

    /**
     * A method to set Ticket price
     *
     * @param title Movie title
     * @param price Price to set
     */
    public void setTicketPrice(String title, double price) {
        this.prices.put(title, price);
    }

    /**
     * A method to add showtimes into this Cinema
     *
     * @param title    Movie title
     * @param showtime Showtime to add
     */
    public void setShowtime(String title, LocalDateTime showtime) {
        ArrayList<LocalDateTime> shows = this.showtimes.get(title);
        shows.add(showtime);
        this.showtimes.put(title, shows);
    }

    /**
     * Changes this name of this Cinema
     *
     * @param name This Cinema's new name
     */
    public void setName(String name) {
        this.cinemaName = name;
    }

    /**
     * Gets the name of this Cinema
     *
     * @return this Cinema's name
     */
    public String getName() {
        return this.cinemaName;
    }

    /**
     * Gets the Ticket price of a Movie in this Cinema
     *
     * @param title Movie title
     * @return the price of the Movie Ticket
     */
    public double getTicketPrice(String title) {
        return this.prices.get(title);
    }

    /**
     * Gets the showtimes in this Cinema
     *
     * @param title Movie title
     * @return the showtime of the Movie
     */
    public ArrayList<LocalDateTime> getShowtimes(String title) {
        return this.showtimes.get(title);
    }

    /**
     * Gets all the movies available in this Cinema
     *
     * @return Array list of Movies
     */
    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    /**
     * A method to update Movies available in this Cinema
     *
     * @param newMovie Movie object
     * @return an updated array list of Movies
     */
    public ArrayList<Movie> updateMovies(Movie newMovie) {
        this.movies.add(newMovie);
        return this.movies;
    }

    /**
     * Sets the seat with associated Movie in this Cinema
     *
     * @param title   Movie title
     * @param newSeat new Seat object
     */
    public void setSeats(String title, Seat newSeat) {
        ArrayList<Seat> s = this.seats.get(title);
        s.add(newSeat);
        this.seats.put(title, s);
    }

    /**
     * Sets the Movies available in this Cinema
     *
     * @param movies Array list of Movies
     */
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Overloads the setSeats method with another parameter of a HashMap
     *
     * @param seats HashMap of associated Movies and showtime and an array list of Seat object
     */
    public void setSeats(HashMap<String, ArrayList<Seat>> seats) {
        this.seats = seats;
    }

    /**
     * Sets the Customer to a Seat whenever a booking is done in this Cinema
     *
     * @param customer Customer Object
     * @param title    Movie title
     * @param index    Seat index
     * @return True if Customer is successfully set, False if unsuccessful
     */
    public boolean setCustomer(Customer customer, String title, int index) {
        if (this.seats.containsKey(title) && this.seats.get(title) != null) {
            try {
                this.seats.get(title).get(index).setCustomer(customer);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The seatNo is invalid, please try again");
                return false;
            }
        } else {
            System.out.println("The showtime or the movie title may not exist in our database");
            return false;
        }
        return true;
    }

    /**
     * Gets the array list of Seat of associated Movie title in this Cinema
     *
     * @param title Movie title
     * @return Array list of Seat object
     */
    public ArrayList<Seat> getSeats(String title) {
        return this.seats.get(title);
    }

    /**
     * Overloaded method of getSeat() with other parameters with different return type
     *
     * @param title Movie title
     * @param index Seat number
     * @return Seat object
     */
    public Seat getSeat(String title, int index) {
        return this.seats.get(title).get(index);
    }

    /**
     * A method to update showtimes of a Movie in this Cinema
     *
     * @param title    Movie title
     * @param showtime Updated array list of showtime
     */
    public void updateShowtime(String title, ArrayList<LocalDateTime> showtime) {
        this.showtimes.replace(title, showtime);
    }

    /**
     * A method to print available showtimes of a Movie in this Cinema
     *
     * @param title Movie title
     */
    public void printShowtimes(String title) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
        ArrayList<LocalDateTime> showtimes = this.showtimes.get(title);
        int i = 0;
        System.out.println("The showtimes for movie: " + title);
        if (showtimes.size() == 35) {
            for (i = 0; i < showtimes.size() - 1; i += 5) {
                if (showtimes.get(i) != null && showtimes.get(i + 1) != null && showtimes.get(i + 2) != null && showtimes.get(i + 3) != null && showtimes.get(i + 4) != null)
                    System.out.println(formatter.format(showtimes.get(i)) + "\t" + formatter.format(showtimes.get(i + 1)) + "\t" + formatter.format(showtimes.get(i + 2)) + "\t" + formatter.format(showtimes.get(i + 3)) + "\t" + formatter.format(showtimes.get(i + 4)));
            }
        } else {
            int size = (showtimes.size() / 5) * 5;
            for (i = 0; i < size - 1; i += 5) {
                if (showtimes.get(i) != null && showtimes.get(i + 1) != null && showtimes.get(i + 2) != null && showtimes.get(i + 3) != null && showtimes.get(i + 4) != null)
                    System.out.println(formatter.format(showtimes.get(i)) + "\t" + formatter.format(showtimes.get(i + 1)) + "\t" + formatter.format(showtimes.get(i + 2)) + "\t" + formatter.format(showtimes.get(i + 3)) + "\t" + formatter.format(showtimes.get(i + 4)));
            }
            int subSize = showtimes.size() % 5;
            for (i = 0; i < subSize; i++) {
                System.out.print(formatter.format(showtimes.get(i + size)) + "\t");
            }
        }

        System.out.println();
    }

    /**
     * Prints the seat layout of a Movie in this Cinema
     *
     * @param title    Movie title
     * @param showtime Showtime of the Movie
     */
    public void printLayout(String title, LocalDateTime showtime) { //prints layout of the Cinema based on available seats for the Movie at the particular showtime
        HashMap<String, ArrayList<Seat>> allSeats = this.seats; //Not sure about the local date class
        ArrayList<Seat> seats = new ArrayList<>();
        if (allSeats.containsKey(title + showtime.toString())) {
            seats = allSeats.get(title + showtime.toString());
        }
        ArrayList<Integer> purchasedSeats = new ArrayList<>();

        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getCustomer() != null) { //if seat is purchased by customer
                purchasedSeats.add(i); //Add the index into purchased Seats
            }
        }
        System.out.println(purchasedSeats);

//        int seatPurchased = //Need to pass in the index of the purchased;

        int alpha = 65;
        boolean gap = true;
        String[][] seatLayout = {
                {"<  >", "<-->"},//0: Standard
                {"(  )", "(--)"},//1: Gold
                {"{  }", "{--}"},//2: Platinum
                {"[       ]", "[-------]"}//3: Couple
        };
        int seatClass = 0;
        int seatStatus = 0; // Will toggle betweel 0 and 1 (0=Empty, 1=Taken)

        //Just some variables for formatting
        String labelGap = "   ";
        String labelIsleGap = "       ";
        String seatGap = " ";
        String isleGap = "     ";

        //Row and col fixed otherwise the formatting will be messed up
        int row = 10;
        int col = 16;

        System.out.println("Seat Layout");
        System.out.println();
        System.out.println("                                             SCREEN");
        //First for loop prints the column labels only
        for (int c = 0; c < col + 1; c++) { //For printing the col numbers
            String padded = String.format("%02d", c); //Pad single digits with 0 at the front
            if (c == 0) {
                System.out.print("      ");
            } else {
                if (c % 4 == 0) { // This sets the isle gap every 4 columns
                    if (gap)
                        System.out.print(padded + labelIsleGap);
                    else
                        System.out.print(padded + labelGap);
                    gap = !gap; //This alternate enabling the isles every 4 columns
                } else
                    System.out.print(padded + labelGap);//Fir when there is no isleGap
            }
        }

        gap = true;
        System.out.println();
        //Second for loop for printing the seats
        for (int r = 1; r < row + 1; r++) {
            for (int c = 0; c < col + 1; c++) {
                if (r == 8 || r == 9) {
                    if (c >= 1 && c <= 4 || c >= 13 && c <= 16) //If within this range, set seatClass to couple
                        seatClass = 3;
                    else
                        seatClass = 1; //Otherwise set seatClass to Gold
                } else if (r == 10) {
                    if (c >= 1 && c <= 4 || c >= 13 && c <= 16)
                        seatClass = 3;
                    else
                        seatClass = 2; //Set seatClass to Platinum
                }

                if (c == 0) {
                    System.out.printf(" %c   ", (alpha++)); //For printing the alphabets
                } else {
                    if (purchasedSeats.contains(c + ((r - 1) * col))) //Converts 2D layout to 1D to compare with Customer's index
                        seatStatus = 1; //Set the seat status to taken
                    if (c % 4 == 0) {
                        if (gap)
                            System.out.print(seatLayout[seatClass][seatStatus] + isleGap);
                        else
                            System.out.print(seatLayout[seatClass][seatStatus] + seatGap);
                        gap = !gap;
                    } else
                        System.out.print(seatLayout[seatClass][seatStatus] + seatGap);

                    seatStatus = 0; //Prepares to print the next seat (Empty by default)
                }
                //If it is couple seats, check if it is next to an isle
                if (seatClass == 3) {
                    if ((c + 1) % 4 == 0) { //If it is next to an isle, print isleGap and toggle gap
                        System.out.print("    ");
                        gap = !gap;
                    }
                    c += 1; //Skip a column since couple seats contains 2 seats
                }
            }
            System.out.println(); //Next row
        }
        System.out.println("                                            ENTRANCE");
        System.out.println("--------------------------------------------------------------------------------------------");
        //Printing Legends
        System.out.println();
        System.out.println("\tLegends");
        System.out.println("\tStandard Seats");
        System.out.printf("\t" + seatLayout[0][0] + "\tAvailable \t\t" + seatLayout[0][1] + "\tTaken\n");
        System.out.println("\tGold Seats");
        System.out.printf("\t" + seatLayout[1][0] + "\tAvailable \t\t" + seatLayout[1][1] + "\tTaken\n");
        System.out.println("\tPlatinum Seats");
        System.out.printf("\t" + seatLayout[2][0] + "\tAvailable \t\t" + seatLayout[2][1] + "\tTaken\n");
        System.out.println("\tCouple Seats");
        System.out.printf("\t" + seatLayout[3][0] + "\tAvailable \t\t" + seatLayout[3][1] + "\tTaken\n");
    }

}
