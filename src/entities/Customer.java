package src.entities;

import java.util.*;
import java.io.*;
import java.time.format.*;
import java.time.*;


public class Customer implements Client, Serializable{
    private String email;
    private int mobileNumber;
    private String name;
    private int age;
    private boolean auth = false;  //authentication indicator. Whether customer is authenticated or not. All methods must check for authentication.
    private String username;
    private String password;
    private ArrayList<Ticket> bookings = new ArrayList<>();

    //Constructor for Customer object.
    public Customer(String name, int age, String username, String password, String email, int mobileNumber){
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
	this.email = email;
	this.mobileNumber = mobileNumber;
    }


    public boolean login(ArrayList<Object> customerDB) throws IOException {
        int tries = 9;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter your login username: ");
        tempUsername = reader.readLine();
        System.out.println("Please enter your password: ");
        tempPassword = reader.readLine();

        ArrayList<Object> customers = new ArrayList<>();
        customers = customerDB;  //Fetch all customers details from DB
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(tempUsername) && customers.get(i).getPassword().equals(tempPassword)) {
                System.out.println("Authenticated successfully");
                auth = true;
                return true;
            }
        }
        return false;
    }

    public boolean createAccount(ArrayList<Object> customerDB) throws IOException{
        int tries = 9;  //9 tries before system shuts;
        String tempUsername;
        String tempPassword;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Enter username: ");
            tempUsername = reader.readLine();
            if (customerDB.contains(tempUsername)) System.out.println("Username already taken");
            if (tries == 0) System.out.println("Too many tries. System quitting now");
            tries--;
        } while (customerDB.contains(tempUsername) && tries != 0);
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
        customerDB.add(this);
        System.out.println("Account created successfully");
        return true;
    }

    public void customerUI(ArrayList<Cinema> cinemaDB, ArrayList<Movie> movieDB) throws Exception {
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
				System.out.println("Enter movie title you would like to search about");
				String title = reader.readLine();
				searchMovie(title, movieDB);
				break;
			case 2:
				System.out.println("Enter the name of the Cinema you would like to search");
				String cinemaName = reader.readLine();
				listMovie(cinemaName, cinemaDB);
				break;
			case 3:
				checkSeatAvailability(cinemaDB, movieDB);
				break;
			case 4:
				makeBooking(cinemaDB, movieDB);
				break;
			case 5:
				viewBookings();
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

    private String makeBooking(ArrayList<Cinema> cinemaDB, ArrayList<Movie> movieDB) throws Exception {
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
			showtime = LocalDate.parse(reader.readLine(), formatter);
			e = true;
		}catch(DateTimeParseException d) {
			System.out.println("Invalid date format, Please try again");
		}
	}
	for (Cinema cinema : cinemaDB) {
		if (cinema.getName() == cinemaName) {
			ArrayList<Seat> seats = cinema.getSeats(title+showtime.toString());
			cinema.printLayout(title, showtime);
			String seatNo;
			do {
				System.out.println("Which seat would you like to select?: Enter the alphabet along with the column number like so (C6)");
				seatNo = reader.readLine();
			} while (seatNo.charAt(0) > 'J' || seatNo.charAt(0) < 'A');
			int row = seatNo.charAt(0) % 65;
			int col = Integer.parseInt(seatNo.substring(1));
			int index = row*16+col;
			cinema.setCustomer(this, title+showtime.toString(), index);
			Movie mov=null;
		      	for (Movie movie : movieDB) {
				if (movie.getTitle() == title) {
					mov = movie;
					break;
				}
			}
			String TID = Integer.toString(cinema.getName().hashCode()%1000) + showtime.toString();
			Ticket newTicket = new Ticket(mov, cinema, this, showtime, cinema.getSeat(title+showtime.toString(), index), TID, this.username, this.email, this.mobileNumber);
			this.bookings.add(newTicket);
			System.out.println("The price of your ticket is: " + newTicket.getPrice());
			break;
		}
	}
		return "Ticket booked successfully. A confirmation has been sent to the provided email and phone number";

    }

    private void listMovie(String cinemaname, ArrayList<Cinema> cinemaDB) {
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

    private void checkSeatAvailability(ArrayList<Cinema> cinemaDB, ArrayList<Movie> movieDB) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
			showtime = LocalDate.parse(reader.readLine(), formatter);
			e = true;
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
	}


    private void searchMovie(String movieTitle, ArrayList<Movie> moviesDB) {
		for (Movie movie : moviesDB) {
			if (movie.getTitle() == movieTitle) {
				System.out.println(movie.toString());
				break;
			}
		}
	}

    private void viewBookings(){
		System.out.println("Your past bookings are available here");
		for (Ticket ticket : this.bookings) {
			System.out.println(ticket.toString());
		}
    }

    private void checkSeats(Cinema cinema, String showtime) {
        if (!auth) return;
	    cinema.printLayout(this, showtime); //simple wrapper to print layout
    }


    private void addReview(ArrayList<Movie> movieDB) throws Exception {
	System.out.println("Enter the title of the Movie you would like to add a review for");
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String title = reader.readLine();
	int index = 0;
	for (int i = 0; i < movieDB.size(); i++) {
		if (movieDB.get(i).getTitle() == title) {
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
	System.out.println("You're review was added successfully");
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
}
