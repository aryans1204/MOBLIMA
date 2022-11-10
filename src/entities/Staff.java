package src.entities;

import java.util.*;
import java.io.*;
import java.time.*;

public class Staff implements Client, Serializable {
    private String username;
    private String password;
    private boolean auth = false;
    private Cinema cinema;   //associated cinema of the staff. Where the staff works

    public Staff(String username, String password, Cinema cinema) {
	this.username = username;
	this.password = password;
	this.cinema = cinema;
    }
    public static boolean login(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException {
	System.out.println("Enter username: ");
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String username = reader.readLine();
	System.out.println("Enter password: ");
	String password = reader.readLine();
	ArrayList<Staff> staffs = staffDB;

	for (Staff s : staffs) {
		if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
			System.out.println("Authenticated successfully");
			auth = true;
			return true;
		}
	}
	System.out.println("Username or password incorrect. Please try again later");
	return false;
    }

    public boolean createAccount(ArrayList<Staff> staffDB, ArrayList<Customer> customerDB) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tries = 9;  //9 tries before system shuts;
	String tempUsername;
	do {
		System.out.println("Enter username: ");
		String username = reader.readLine();
	    	tempUsername = username;
	    	if (staffDB.contains(tempUsername)) System.out.println("Username already exists, try another one!");
            	if (tries == 0) System.out.println("Too many tries. System quitting now");
            	tries--;
	}
	while (staffDB.contains(tempUsername) && tries != 0);
	if (tries == 0) return false;
	setUsername(tempUsername);
        System.out.println("Enter password: ");
        String password = reader.readLine();
        setPassword(password);
	System.out.println("Account created successfully");
	staffDB.add(this);
        return true;
    }

    public void staffUI(ArrayList<Movie> movieDB){
	if (!auth) return;
	Scanner sc = new Scanner(System.in);

	boolean exit = false;
	while (!exit) {
		System.out.printf("\n\nCinema Staff Selection: \n" +
							"1. Create Movie Listing\n" +
							"2. Update Movie Listing\n" +
							"3. Remove Movie Listing\n" +
							"4. List Movie\n" +
							"5. List Top 5 Movies by Reviews\n" +
							"6. Search Movie\n" +
							"7. Return\n" +
							"8. List Top 5 Movies by TotalSales\n"+
							"9. Configure Ticket Prices for a Movie\n"+
							"Select option: ");
		int option = Integer.valueOf(sc.nextLine());
		switch(option) {
			case 1:
				createMovieListing();
				break;
			case 2:
				updateMovieListing(movieDB);
				break;
			case 3:
				removeMovieListing(movieDB);
				break;
			case 4:
				for (Movie movie: movieDB) {
					System.out.println("Title: " + movie.getTitle());
					System.out.println("Date: " + movie.getMovieReleaseDateToString());
					System.out.println("Type: " + movie.getType());
					System.out.println(movie);
				}
				break;
			case 5:
				List<Movie> listMovies = movieDB;

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

				break;
			case 6:
				System.out.println("Enter Input: ");
				String searchInput = sc.nextLine();
				ArrayList<Movie> matchedMovies = null;
				matchedMovies = mvc.searchMovies(searchInput); //Still using movie controller. Not sure how to go around this.
				for(Movie movie : matchedMovies)
					System.out.println(movie);
				break;
			case 7:
				exit = true;
				break;
			case 8:
				List<Movie> listMoviesSales = movieDB;

				Collections.sort(listMoviesSales, new Comparator<Movie>(){

					public int compare(Movie o1, Movie o2)
					{
						return Double.valueOf(o2.getTotalSales()).compareTo(Double.valueOf(o1.getTotalSales()));
					}
				});

				ArrayList<Movie> sortedMoviesSales = new ArrayList<Movie>(listMoviesSales);
				System.out.println("Top 5 Movies by Total Sales\n");
				System.out.println("Overall Rating\tTitle");
				System.out.println("--------------\t-----");
				for(int i = 0; i < 5; i ++)
				System.out.println("\t" + sortedMoviesSales.get(i).getTotalSales() + "\t" + sortedMoviesSales.get(i).getTitle());
				break;
			case 9:
				System.out.println("Enter Movie title for which you would like to update the prices");
				String title = sc.nextLine();
				System.out.println("Enter the price you want to set for this ticket");
				double price = Double.parseDouble(sc.nextLine());
				this.cinema.setTicketPrice(title, price);
				break;

			default:
				System.out.println("Invalid input!\n Please try again");
		}
	}

	sc.close();
    }
    
    private void createMovieListing() {
	Scanner sc = new Scanner(System.in);
	int runtime, option, id;
	String title, synopsis, director, rating;
	boolean exit = false;
	LocalDate releaseDate = null;
	MovieType type = null;
	MovieStatus status = null;
	ArrayList<String> casts = new ArrayList<String>();
	try {
		System.out.println("\nCREATE MOVIE");

		System.out.println("Enter movie id: ");
		id = sc.nextInt();

		System.out.println("Enter movie title: ");
		title = sc.nextLine();

		while(!exit) {
			System.out.print("\nMovie types: \n" +
					   "	1. 2D\n" +
					   "	2. 3D\n" +
					   "	3. Blockbuster\n\n" +
					   "Select an option: ");

			option = Integer.valueOf(sc.nextLine());

			switch(option) {
				case 1:
					type = MovieType.TWO_D;
					exit = true;
					break;
				case 2:
					type = MovieType.THREE_D;
					exit = true;
					break;
				case 3:
					type = MovieType.BLOCKBUSTER;
					exit = true;
					break;
				default:
					System.out.println("Invalid input!, Please try again");
			}
		}

		exit = false;
		while(!exit) {
			System.out.print("\nMovie showing status: \n" +
					   "	1. Coming Soon\n" +
					   "	2. Preview\n" +
					   "	3. Now Showing\n" +
					   "	4. End of showing\n\n" +
					   "Select an option: ");

			option = Integer.valueOf(sc.nextLine());

			switch(option) {
				case 1:
					status = MovieStatus.COMING_SOON;
					exit = true;
					break;
				case 2:
					status = MovieStatus.PREVIEW;
					exit = true;
					break;
				case 3:
					status = MovieStatus.NOW_SHOWING;
					exit = true;
					break;
				case 4:
					status = MovieStatus.END_OF_SHOWING;
					exit = true;
					break;
				default:
					System.out.println("Wrong input!, Please try again");
			}
		}

		System.out.println("Enter movie's synopsis: ");
		synopsis = sc.nextLine();

		System.out.println("Enter movie's director: ");
		director = sc.nextLine();

		exit = false;
		while(!exit) {
			System.out.println("Enter number of casts (min 2): ");
			option = Integer.valueOf(sc.nextLine());
			if (option < 2) {
				System.out.println("Invalid number, Please enter for more than 2 casts");
				continue;
			}
			for (int i = 0; i < option; i++) {
				System.out.println("Enter name of cast " + (i+1) + ": ");
				casts.add(sc.nextLine());
			}
			exit = true;
		}
		/*
		 * G: Suitable For General Audiences Of All Ages
		PG:  Parental Guidance Suggested. Some Material May Not Be Suitable For Children
		M: Recommended For A Mature Audience
		R: Restricted To Persons Over specifed age Unless Accompanied By A Parent Or Guardian.
		TBC: To Be Classified
		*/
		System.out.println("Enter movie rating (G / PG / M / R / TBC) :");
		rating = sc.nextLine();

		System.out.println("Enter movie duration (mins): ");
		runtime = Integer.valueOf(sc.nextLine());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		exit = false;
		while(!exit) {
			try {
				System.out.println("Enter movie release date (DD/MM/YYYY) : ");
				releaseDate = LocalDate.parse(sc.nextLine(), formatter);
				exit = true;
			}catch(DateTimeParseException e) {
				System.out.println("Invalid date format, Please try again");
			}
		}
		Movie newMovie = new Movie(id, title, type, status, synopsis, director, casts, rating, runtime, releaseDate, 0);
		this.cinema.updateMovies(newMovie);
		System.out.println("Movie List Created.. Going back to previous menu");
	}catch(Exception e) {
		e.printStackTrace();
		System.out.println("Invalid input detected, Please try again");
		return;
	}
    }

    private void updateMovieListing(ArrayList<Movie> movieDB) {
		//Instead of updating release date, give an option to update a showtime, based on a Cinema.
		Scanner sc = new Scanner(System.in);
		int movieId = 0, option;
		boolean exit = false, success = false;

		try {
			System.out.println("\nMOVIE UPDATE");
			int index = 0;
			while(!exit) {
				System.out.println("Enter the Movie title which you need to update: ");
				String title = sc.nextLine();
				for (int i = 0; i < movieDB.size(); i++) {
					if (movieDB.get(i).getTitle() == title) {
						index = i;
						exit = true;
						break;
					}
				}
			}

			System.out.println("Select movie detail to update");
			System.out.print( 	"1. Title\n" +
					"2. Type\n" +
					"3. Status\n" +
					"4. Synopsis\n" +
					"5. Director \n" +
					"6. Cast\n" +
					"7. Rating\n" +
					"8. Duration\n" +
					"9. Showtimes\n\n" +
					"Enter option: ");
			option = Integer.valueOf(sc.nextLine());

			switch(option) {
				case 1:
					System.out.println("Enter new Title:");
					String title = sc.nextLine();
					movieDB.get(index).setTitle(title);
					break;
				case 2:
					MovieType type = null;
					exit = false;
					while(!exit) {
						System.out.print("\nMovie types: \n" +
								   "	1. 2D\n" +
								   "	2. 3D\n" +
								   "	3. Blockbuster\n\n" +
								   "Select new option: ");

						option = Integer.valueOf(sc.nextLine());

						switch(option) {
						case 1:
							type = MovieType.TWO_D;
							exit = true;
							break;
						case 2:
							type = MovieType.THREE_D;
							exit = true;
							break;
						case 3:
							type = MovieType.BLOCKBUSTER;
							exit = true;
							break;
						default:
							System.out.println("Invalid input!, Please try again");
						}
					}
					movieDB.get(index).setType(type);
					break;

				case 3:
					exit = false;
					MovieStatus status = null;
					while(!exit) {
						System.out.print("\nMovie showing status: \n" +
								   "	1. Coming Soon\n" +
								   "	2. Preview\n" +
								   "	3. Now Showing\n" +
								   "	4. End of showing\n\n" +
								   "Select new option: ");

						option = Integer.valueOf(sc.nextLine());

						switch(option) {
						case 1:
							status = MovieStatus.COMING_SOON;
							exit = true;
							break;
						case 2:
							status = MovieStatus.PREVIEW;
							exit = true;
							break;
						case 3:
							status = MovieStatus.NOW_SHOWING;
							exit = true;
							break;
						case 4:
							status = MovieStatus.END_OF_SHOWING;
							//success = mvc.removeMovieByID(movieId);
							exit = true;
							break;
						default:
							System.out.println("Wrong input!, Please try again");
						}
					}
					movieDB.get(index).setStatus(status);
					break;
				case 4:
					System.out.println("Enter new movie's synopsis: ");
					String synopsis = sc.nextLine();
					movieDB.get(index).setSynopsis(synopsis);
					break;

				case 5:
					System.out.println("Enter new movie's director: ");
					String director = sc.nextLine();
					movieDB.get(index).setDirector(director);
					break;

				case 6:
					exit = false;
					ArrayList<String> casts = new ArrayList<String>();
					System.out.println("Past entered casts will be overwritten");
					while(!exit) {
						System.out.println("Enter number of casts (min 2): ");
						option = Integer.valueOf(sc.nextLine());
						if (option < 2) {
							System.out.println("Invalid number, Please enter for more than 2 casts");
							continue;
						}

						for (int i = 0; i < option; i++) {
							System.out.println("Enter name of new cast " + (i+1) + ": ");
							casts.add(sc.nextLine());
						}
						exit = true;
					}
					movieDB.get(index).setCasts(casts);
					break;

				case 7:
					System.out.println("Enter new movie rating (G / PG / M / R / TBC) :");
					String rating = sc.nextLine();
					movieDB.get(index).setRating(rating);
					break;

				case 8:
					System.out.println("Enter new movie duration (mins): ");
					int runtime = Integer.valueOf(sc.nextLine());
					movieDB.get(index).setRuntime(runtime);
					break;

				case 9:
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mma");
					LocalDateTime showtime = null;
					exit = false;
					while(!exit) {
						try {
							System.out.println("Enter new movie release date e.g (Saturday, Jul 14, 2018 14:30PM) : ");
							showtime = LocalDateTime.parse(sc.nextLine(), formatter);
							exit = true;
						}catch(DateTimeParseException e) {
							System.out.println("Invalid date format, Please try again");
						}
					}
					this.cinema.setShowtime(movieDB.get(index).getTitle(), showtime);
					break;
				default:
					System.out.println("Invalid option, returning to previous menu");
					return;
				}
				System.out.println("Movie is successfully updated");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input detected, Please try again");
			return;
		}
	}

	private void removeMovieListing(ArrayList<Movie> movieDB) {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		int movieId = 0;

		try {
			String title;
			System.out.println("Enter the title of the movie you would like to remove today");
			title = sc.nextLine();

			for (Movie movie : movieDB) {
				if (movie.getTitle() == title) movie.setStatus(MovieStatus.END_OF_SHOWING);
				System.out.println("Movie successfully removed");
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input detected, Please try again");
			return;
		}
	}

    //Getters
    public String getUsername() {
	return this.username;
    }

    public String getPassword() {
	return this.password;
    }

    public Cinema getCinema() {
	return this.cinema;
    }

    //Setters
    public void setUsername(String username) {
	this.username = username;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setCinema(Cinema cinema) {
	this.cinema = cinema;
    }

}
