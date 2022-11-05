import java.util.*;
import java.io.*;

import Client,Cinema,Movie;

public class Customer implements Client, Serializable{
    private String email;
    private int mobileNumber;
    private ClientController c;
    private String name;
    private int age;
    private boolean auth = false;  //authentication indicator. Whether customer is authenticated or not. All methods must check for authentication.
    private String username;
    private String password;
    private ArrayList<ticket> bookings = new ArrayList<>();

    //Constructor for Customer object.
    public Customer(String name, int age, String username, String password, String email, int mobileNumber){
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
	this.email = email;
	this.mobileNumber = mobileNumber;
    }


    public boolean login() throws IOException {
        int tries = 9;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter your login username: ");
        tempUsername = reader.readLine();
        System.out.println("Please enter your password: ");
        tempPassword = reader.readLine();

        ArrayList<Customer> customers = new ArrayList<>();
        customers = c.getCustomerFromDB();  //Fetch all customers details from DB
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(tempUsername) && customers.get(i).getPassword().equals(tempPassword)) {
                System.out.println("Authenticated successfully");
                auth = true;
                return true;
            }
        }
        return false;
    }

    public boolean createAccount() throws IOException{
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
    	System.out.println("Enter your name: ");
	String tempName = reader.readLine();
	setName(tempName);
	System.out.println("Enter email: ");
	String tempEmail = reader.readLine();
	setEmail(tempEmail);
	System.out.println("Enter mobile number: ");
	int tempNumber = Integer.parseInt(reader.readLine());
	setNumber(tempNumber);
	System.out.println("Enter age: ");
	int tempage = Integer.parseInt(reader.readLine());
	setAge(tempage);
        c.insertCustomerToDB(name, age, username, password);
        System.out.println("Account created successfully");
        return true;
    }

    public customerUI(ArrayList<Cinema> cinemaDB, Araylist<Movie> movieDB) throws Exception {
	if (!auth) return;
	boolean exit = false;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	while (!exit) {
		System.out.println("How can we help you today\n" + 
					"1. Search for a Movie\n" +
					"2. List Movies by Cinema\n" +
					"3. Check seat availability of a movie at a cinema for a given showtime\n" +
					"4. Purchase a ticket for a movie\n" + 
					"5. View Booking History\n" +
					"6. List Top 5 Movies by Ticket Sales or Reviewer's rating(as set by Cinema staff)\n" +
					"7. Add a review or rating for a movie\n" +
					"8. Exit");
		int option = Integer.parseInt(reader.readLine());
		switch (option) {
			case 1:
				System.out.println("Enter movie title you woudl like to search about");
				String title = reader.readLine();
				searchMovie(title, moviesDB);
				break;
			case 2:
				System.out.println("Enter the name of the Cinema you would like to search");
				String cinemaName = reader.readLine();
				listMovies(cinemaName, cinemaDB);
				break;
			case 3:
				System.out.println("Enter the name of the Cinema you would like to check seat availability for");
				String cinemaname = reader.readLine();
				System.out.println("Enter the Movie for which you would like to check availability");
				String moviename = reader.readLine();
				 	
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a");
				LocalDate showtime = null;
				boolean e = false;
				while(!e) {
					try {
						System.out.println("Enter showtime e.g (Saturday, Jul 14, 2018 14:31:06 PM) : ");
						showtime = LocalDate.parse(reader.readLine(), formatter);									e = true;
					}catch(DateTimeParseException d) {
						System.out.println("Invalid date format, Please try again");
					}	
				}
				for (Cinema cinema : cinemaDB) {
					if (cinema.getName() == cinemaname) {
						cinema.printLayout(moviename, showtime);
						break;
					}
				}
				break;
			case 4:
				makeBooking(cinemaDB, movieDB);
				break;
			case 5:	
				System.out.println("Your past bookings are available here");
				for (Ticket ticket : this.bookings) {
					System.out.println(ticket.toString());
				}
				break;
			case 6:										
				List<Movie> listMovies = movieDB;
				Collections.sort(listMovies, new Comparator<Movie>(){
					public int compare(Movie o1, Movie o2)
					{
						return Double.valueOf(o2.getTotalSales()).compareTo(Double.valueOf(o1.getTotalSales()));
					}
				});

				ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
				System.out.println("Top 5 Movies by Total Sales\n");
				System.out.println("Overall Rating\tTitle");
				System.out.println("--------------\t-----");
				for(int i = 0; i < 5; i ++)
					System.out.println("\t" + sortedMovies.get(i).getTotalSales() + "\t" + sortedMovies.get(i).getTitle());
    				break;
			case 7:
				addReview(movieDB);
				break;
			case 8:
				exit = true;
				break;
		}
	}
    }				

    public String makeBooking(ArrayList<Cinema> cinemaDB, ArrayList<Movie> movieDB) throws Exception {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Enter movie title to make booking");
	String title = reader.readLine();
	System.out.println("Enter cinema name you would like to make booking at");
	String cinemaName = reader.readLine();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a");
	LocalDate showtime = null;
	boolean e = false;
	while(!e) {
		try {
			System.out.println("Enter showtime e.g (Saturday, Jul 14, 2018 14:31:06 PM) : ");
			showtime = LocalDate.parse(reader.readLine(), formatter);									e = true;
		}catch(DateTimeParseException d) {
			System.out.println("Invalid date format, Please try again");
		}	
	}
	for (Cinema cinema : cinemaDB) {
		if (cinema.getName() == cinemaname) {
			ArrayList<Seat> seats = cinema.getSeats(title+showtime.toString());
			cinema.printLayout(title, showtime);
			System.out.println("Which seat would you like to select?: ");
			//part to be completed after the Cinema representation is completed
		}
	}
    }

    public void listMovie(String cinemaname, ArrayList<Cinema> cinemaDB) {
	for (Cinema cinema: cinemaDB) {
		if (cinema.getName() == cinemaname) {
			ArrayList<Movie> movies = cinema.getMovies();
			for (Movie movie : movies) {
				System.out.println(movie.toString());
			}
			break;
		}
	}
    }
	
    public void listMovie(MovieController m) { // List all movies that are "NowShowing"
        if (!auth) return;
        ArrayList<Movie> movies = new ArrayList<>();
        movies = m.getAllMoviesFromDB();
        for(int i=0; i<movies.size(); i++){
            if(movies.get(i).getStatus().equals(MovieStatus.NOW_SHOWING)){
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

    public void searchMovie(String movieTitle, ArrayList<Movie? moviesDB) {
	for (Movie movie : moviesDB) {
		if (movie.getTitle() == movieTitle) {
			System.out.println(movie.toString());
			break;
		}
	}

    public void searchMovie(String movieTitle, MovieController m){ //searches for a Movie in our database and prints out the Review and rating
        if (!auth) return;
        ArrayList<Movie> matchedmovie = new ArrayList<>();
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
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets = t.getAllTicketsFromDB();  //Get all tickets from DB

        for(int i=0; i< tickets.size();i++){
            if(tickets.get(i).getCustomer().getName().equals(name) && tickets.get(i).getCustomer().getAge().equals(age)){
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
        if(criterion.equals("Rating") || criterion.equals("rating")){
            m.printTopFiveMovies();
        }

        //Top 5 by Sales
        else{
            m.printTopFiveMoviesBySales();
        }
    }

    public void addreview(ArrayList<Movie> movieDB) throws Exception {
	System.out.println("Enter the title of the Movie you would like to add a review for");
	BufferedReader reader = new BufferedReader(new InputStreamReader(Syetm.in));
	String title = reader.readLine();
	int index = 0;
	for (int i = 0; i < movieDB.size(); i++) {
		if (movieDB.get(i).gertTitle() == title) {
			index = i;
			break;
		}
	}
	System.out.println("Enter your rating for the movie, on a scale of 1.0 - 5.0 ");
	int rating = Integer.parseInt(reader.readLine());
	System.out.println("Enter your review for the movie");
	String comment = reader.readLine();
	Review newReview = new Review(this.username, rating, comment);
	movieDB.get(i).addReview(newReview);
	System.out.println("Youre review was added successfully");
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
    
    public int getMobileNumber() {
	return this.mobileNumber;
    }

    public String getEmail() {
	return this.email;
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
 
    public void setEmail(String email) {
	this.email = email;
    }

    public void setNumber(int mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        String customerDetail;
        customerDetail = "Name: " + name + "\n"
                       + "Age: " + age + "\n"
                       + "Username: " + username + "\n"
                       + "Password: " + password;
        return customerDetail;
    }
}
