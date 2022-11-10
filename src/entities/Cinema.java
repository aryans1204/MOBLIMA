package src.entities;

import java.util.*;
import java.io.*;
import java.time.*;

//ide suggested @SuppressWarnings("serial")
@SuppressWarnings("serial")
public class Cinema implements Serializable {
    private HashMap<String, Double> prices = new HashMap<>();  //prices indicator per movie-title for Staff to set.
    private ArrayList<Movie>  movies;  //array of Movies at this cinema;
    private HashMap<String, ArrayList<LocalDateTime>> showtimes;
    private HashMap<String, ArrayList<Seat>> seats;  //key is the movie title + showtime.toString().
    private String cinemaName;
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

    public void setTicketPrice(String title, double price) {
	this.prices.put(title, price);
    }

    public void setShowtime(String title, LocalDateTime showtime) {
	ArrayList<LocalDateTime> shows = this.showtimes.get(title);
	shows.add(showtime);
	this.showtimes.put(title, shows);
    }

    public void setName(String name) {
	this.cinemaName = name;
    }

    public String getName() {
	return this.cinemaName;
    }

    public double getTicketPrice(String title) {
	    return this.prices.get(title);
    }

    public ArrayList<LocalDateTime> getShowtimes(String title) {
	    return this.showtimes.get(title);
    }

    public ArrayList<Movie> getMovies() {
	    return this.movies;
    }

    public ArrayList<Movie> updateMovies(Movie newMovie) {
	    this.movies.add(newMovie);
      return this.movies;
    }
    
    public void setSeats(String title, Seat newSeat) {
        ArrayList<Seat> s = this.seats.get(title);
        s.add(newSeat);
        this.seats.put(title, s);
    }
    
    public void setSeats(HashMap<String, ArrayList<Seat>> seats) {
	    this.seats = seats;
    }
    public void setCustomer(Customer customer, String title, int index) {
	this.seats.get(title).get(index).setCustomer(customer);
    }
    public ArrayList<Seat> getSeats(String title) {
	   return this.seats.get(title);
    }
    public Seat getSeat(String title, int index) {
	return this.seats.get(title).get(index);  
    }
  
    public void printLayout(String title, LocalDateTime showtime) { //prints layout of the Cinema based on available seats for the Movie at the particular showtime
        HashMap<String, ArrayList<Seat>> allSeats = this.seats; //Not sure about the local date class
        ArrayList<Seat> seats = new ArrayList<>();
        if(allSeats.containsKey(title+showtime.toString())){
            seats = allSeats.get(title+showtime.toString());
        }
        ArrayList<Integer> purchasedSeats = new ArrayList<>();

        for(int i=0; i<seats.size();i++){
            if(seats.get(i).getCustomer()!=null){ //if seat is purchased by customer
                purchasedSeats.add(i); //Add the index into purchased Seats
            }
        }



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
                if(c % 4==0) { // This sets the isle gap every 4 columns
                    if(gap)
                        System.out.print(padded+labelIsleGap);
                    else
                        System.out.print(padded+labelGap);
                    gap = !gap; //This alternate enabling the isles every 4 columns
                }
                else
                    System.out.print(padded+labelGap);//Fir when there is no isleGap
            }
        }

        gap = true;
        System.out.println();
        //Second for loop for printing the seats
        for (int r = 1; r < row + 1; r++) {
            for (int c = 0; c < col + 1; c++) {
                if(r==8|| r == 9) {
                    if (c >= 1 && c <= 4 || c >= 13 && c <= 16) //If within this range, set seatClass to couple
                        seatClass = 3;
                    else
                        seatClass = 1; //Otherwise set seatClass to Gold
                }
                else if(r==10) {
                    if (c >= 1 && c <= 4 || c >= 13 && c <= 16)
                        seatClass = 3;
                    else
                        seatClass = 2; //Set seatClass to Platinum
                }

                if (c == 0) {
                    System.out.printf(" %c   ", (alpha++)); //For printing the alphabets
                } else {
                    if (purchasedSeats.contains(1-c+((r-1)*col))) //Converts 2D layout to 1D to compare with Customer's index
                        seatStatus=1; //Set the seat status to taken
                    if(c % 4==0) {
                        if(gap)
                            System.out.print(seatLayout[seatClass][seatStatus]+isleGap);
                        else
                            System.out.print(seatLayout[seatClass][seatStatus]+seatGap);
                        gap = !gap;
                    }
                    else
                        System.out.print(seatLayout[seatClass][seatStatus]+seatGap);

                    seatStatus=0; //Prepares to print the next seat (Empty by default)
                }
                //If it is couple seats, check if it is next to an isle
                if(seatClass==3) {
                    if((c+1)%4==0){ //If it is next to an isle, print isleGap and toggle gap
                        System.out.print("    ");
                        gap=!gap;
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
        System.out.printf("\t"+seatLayout[0][0]+"\tAvailable \t\t"+seatLayout[0][1]+"\tTaken\n");
        System.out.println("\tGold Seats");
        System.out.printf("\t"+seatLayout[1][0]+"\tAvailable \t\t"+seatLayout[1][1]+"\tTaken\n");
        System.out.println("\tPlatinum Seats");
        System.out.printf("\t"+seatLayout[2][0]+"\tAvailable \t\t"+seatLayout[2][1]+"\tTaken\n");
        System.out.println("\tCouple Seats");
        System.out.printf("\t"+seatLayout[3][0]+"\tAvailable \t\t"+seatLayout[3][1]+"\tTaken\n");
    }

}	
