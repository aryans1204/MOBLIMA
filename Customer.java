import java.util.*;
import java.io.*;

import Client,Cinema,Movie;

public class Customer implements Client, Serializable{

    private int age;
    private String name;
    private boolean auth = false;  //authentication indicator. Whether customer is authenticated or not. All methods must check for authentication.
    public String username;
    public String password;

    //Constructor for Customer object.
    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Customer(String name, int age, String username, String password) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }


    public boolean login(CustomerController c) throws IOException {
        int tries = 9;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter your login username: ");
        tempUsername = reader.readLine();
        System.out.println("Please enter your password: ");
        tempPassword = reader.readLine();

        ArrayList<Customer> customers = null;
        customers = c.getCustomerFromDB();  //Fetch all customers details from DB
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername() == tempUsername && customers.get(i).getPassword() == tempPassword) {
                System.out.println("Authenticated successfully");
                auth = true;
                return true;
            }
        }
        return false;
    }

    public boolean createAccount(CustomerController c) throws IOException{
        int tries = 9;  //9 tries before system shuts;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Enter username: ");
            tempUsername = reader.readLine();
            if (c.checkUsernameExist(tempUsername)) System.out.println("Username already taken");
            if (tries == 0) System.out.println("Too many tries. System quitting now");
            tries--;
        } while (c.checkUsernameExist(tempUsername) && tries != 0);
        if (tries == 0) return false;
        setUsername(tempUsername);
        System.out.println("Enter password: ");
        tempPassword = reader.readLine();
        setPassword(tempPassword);
        c.insertCustomerToDB(name, age, username, password);
        System.out.println("Account created successfully");
        return true;
    }

//    public void checkMovieListings(Cinema cinema) {
//        if (!auth) return;
//        for (Movie movie : cinema.movies) {
//            System.out.println(movie.title);
//            System.out.println(movie.status);
//            System.out.println(movie.synopsis);
//            System.out.println("Director: " + movie.Director + "Cast: " + movie.Cast1 + " " + movie.Cast2);
//            System.out.println(movie.rating);
//            System.out.println("Review                                                          Rating");  //please keep review length to 64 characters
//            System.out.println("=======================================================================");
//            for (for (int j = 0; j < movie.reviews.length; j++)) {
//                System.out.print(movie.reviews[j]);
//                for (int i = 0; i < 66 - movie.reviews[j].length(); i++) System.out.print(" ");
//                System.out.println(movie.ratings[j]);
//            }
//        }
//    }

    public String makeBooking(Cinema cinema, Movie movie) throws Exception {
        if (!auth) return "0xdeadbeef";
        cinema.printLayout(movie, showtime);  //prints layout for movie and showtime at the cinema.
        int frees = cinema.free.get(movie.title).get(movie.showtime);
        if (frees == 0) return "0xdeadbeef";  //0xdeadbeef is code for error occured, that is no free seats
        Ticket ticket = new Ticket(movie, cinema, this);  //creates a booking
        BufferedReader reader = new BufferedReader(new InputStreamreader(System.in));
        do {
            System.out.println("Please enter which seat number you would like: ");
            int seatNo = reader.readLine();
            if (cinema.seats.get(movie.title).get(movie.showtime).get(seatNo) != null)
                SYstem.out.println("Seat is taken. Please choose another seat");
        }
        while (cinema.seats.get(movie.title).get(movie.showtime).get(seatNo) != null);
        cinema.seats.get(movie.title).get(movie.showtime).put(seatNo, this);
        cinema.free.get(movie.title).put(movie.showtime, frees - 1);
        String transactionID = UUID.randomUUID().toString();
        cinema.tID.get(this.username).get(movie.title).put(movie.showtime, transactionID);
        return transactionID;
    }
    public void listMovie(MovieController m) { // List all movies that are "NowShowing"
        if (!auth) return;
        ArrayList<Movie> movies = null;
        movies = m.getAllMoviesFromDB();
        for(int i=0; i<movies.size(); i++){
            if(movies.get(i).getStatus() == MovieStatus.NOW_SHOWING){
                System.out.println("Movie Title: " + movies.get(i).getTitle());
                System.out.println("Director: " + movies.get(i).getDirector());
                System.out.println("Runtime: " + movies.get(i).getRuntime());
                System.out.println("Type: " + movies.get(i).getType() );
                System.out.println("Ratings: " + movies.get(i).getRating());
                System.out.println("Synopsis: " + movies.get(i).getSynopsis());
                System.out.println("Reviews: " + movies.get(i).getReviews());
            }
        }
    }

    public void searchMovie(String movieTitle, MovieController m){ //searches for a Movie in our database and prints out the Review and rating
        if (!auth) return;
        ArrayList<Movie> matchedmovie = null;
        matchedmovie = m.searchMovies(movieTitle);

        if(matchedmovie.size() > 0){ //When movie is successfully searched
            for(int i=0; i<matchedmovie.size(); i++){
                System.out.println("Movie Title: " + movieTitle);
                System.out.println("Director: " + matchedmovie.get(i).getDirector());
                System.out.println("Runtime: " + matchedmovie.get(i).getRuntime());
                System.out.println("Type: " + matchedmovie.get(i).getType() );
                System.out.println("Ratings: " + matchedmovie.get(i).getRating());
                System.out.println("Synopsis: " + matchedmovie.get(i).getSynopsis());
                System.out.println("Reviews: " + matchedmovie.get(i).getReviews());
            }
        }
    }

    public void viewBookings(TicketController t){ //view all past bookings made by the Customer.
        if (!auth) return;
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets = t.getAllTicketsFromDB();  //Get all tickets from DB

        for(int i=0; i< tickets.size();i++){
            if(tickets.get(i).getCustomer().getName() == name && tickets.get(i).getCustomer().getAge() == age){
                System.out.println(tickets.get(i).getCinema());
                System.out.println(tickets.get(i).getMovie());
                System.out.println(tickets.get(i).getShowtime());
                System.out.println(tickets.get(i).getPrice());
            }
        }
    }

    public void checkSeats(Cinema cinema, String showtime) {
        if (!auth) return;
	    cinema.printLayout(this, showtime); //simple wrapper to print layout
    }

    public void listTopFive(String criterion, MovieController m){ //require read access to Movie CRUD if criterion is Rating OR require Ticket CRUD if criterion is sales.
        if (!auth) return;
        //Top 5 by Ratings
        if(criterion == "Rating" || criterion == "rating"){
            m.printTopFiveMovies();
        }

        //Top 5 by Sales
        else{
            m.printTopFiveMoviesBySales();
        }
    }


    //Getters
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }  //method to get the age of the client, as an int.
    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }


    //Setters
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        String customerDetail;
        customerDetail = "Name: " + name + "\n"
                       + "Age: " + age + "\n"
                       + "Username: " + username + "\n"
                       + "Password: " + password;
        return customerDetail;
    }
}
