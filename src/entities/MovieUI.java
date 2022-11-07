import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import java.time.format.DateTimeParseException;

public class MovieUI {
	//Change the fileName path to your own directory path
	public final static String fileName = "D:\\Academic\\NTU\\SC2002\\SC2002Assignment\\src\\movies.dat";
	public static MovieController mvc = new MovieController(fileName);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		boolean exit = false;
		while (!exit) {
			System.out.printf("\n\nCinema Staff Selection: \n" +
								"1. Create Movie Listing\n" +
								"2. Update Movie Listing\n" +
								"3. Remove Movie Listing\n" +
								"4. List Movie\n" +
								"5. Add Ratings\n" +
								"6. List Top 5\n" +
								"7. Search Movie\n" +
								"8. Return\n\n" +
								"Select option: ");
			int option = Integer.valueOf(sc.nextLine());
			switch(option) {
				case 1:
					createMovieListing();
					break;
				case 2:
					updateMovieListing();
					break;
				case 3:
					removeMovieListing();
					break;
				case 4:
					ArrayList<Movie> movies = new ArrayList<Movie>();
					movies = mvc.getAllMoviesFromDB();
					
					for(Movie movie: movies) {
						System.out.println("Id: " + movie.getId());
						System.out.println("Date: " + movie.getMovieReleaseDateToString());
						System.out.println("Type: " + movie.getType());
						System.out.println(movie);
					}
					break;
				case 5:
					System.out.println("Enter movieID: ");
					int movieID = Integer.valueOf(sc.nextLine());
					System.out.println("Enter Customer Name: ");
					String customerName = sc.nextLine();
					System.out.println("Enter Ratings: ");
					int rating = Integer.valueOf(sc.nextLine());
					System.out.println("Enter Comments: ");
					String comments = sc.nextLine();
					mvc.addRating(movieID, customerName, rating, comments);
					break;
				case 6:
					mvc.printTopFiveMovies();
					break;
				case 7:
					System.out.println("Enter Input: ");
					String searchInput = sc.nextLine();
					ArrayList<Movie> matchedMovies = null;
					matchedMovies = mvc.searchMovies(searchInput);
					for(Movie movie : matchedMovies)
						System.out.println(movie);
					break;
				case 8:
					exit = true;
					break;
				default:
					System.out.println("Invalid input!\n Please try again");
			}
		}
		
		sc.close();
		
	}
	public static void createMovieListing() {
		Scanner sc = new Scanner(System.in);
		int runtime, option;
		String title, synopsis, director, rating;
		boolean exit = false;
		LocalDate releaseDate = null;
		MovieType type = null; 
		MovieStatus status = null;
		ArrayList<String> casts = new ArrayList<String>();
		
		try {
			System.out.println("\nCREATE MOVIE");
	
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
			
			mvc.insertMovieToDB(title, type, status, synopsis, director, casts, rating, runtime, releaseDate);
			System.out.println("Movie List Created.. Going back to previous menu");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input detected, Please try again");
			return;
		}
	}

	public static void updateMovieListing() {
		Scanner sc = new Scanner(System.in);
		int movieId = 0, option;
		boolean exit = false, success = false;
		
		try {
			System.out.println("\nMOVIE UPDATE");
			
			while(!exit) {
				if(!mvc.printAllMoviesTitleWithID()){
					System.out.println("The database is empty. Please create movie listing");
					return;
				}
				System.out.println("Select movie to be updated(ID): ");
				movieId = Integer.valueOf(sc.nextLine());
			
				if(!mvc.checkIdExist(movieId)) {
					System.out.println("Movie ID " + movieId + " does not exist. Please try again");
					continue;
				}
				exit = true;
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
									"9. Release Date\n\n" +
									"Enter option: ");
				option = Integer.valueOf(sc.nextLine());
				
				switch(option) {
				case 1:
					System.out.println("Enter new Title:");
					String title = sc.nextLine();
					success = mvc.updateMovieById(1, movieId, title);
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
					success = mvc.updateMovieById(2, movieId, type);
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
							success = mvc.removeMovieByID(movieId);
							exit = true;
							break;
						default:
							System.out.println("Wrong input!, Please try again");
						}
					}
					if(status != MovieStatus.END_OF_SHOWING)
						success = mvc.updateMovieById(3, movieId, status);
					break;
				case 4:
					System.out.println("Enter new movie's synopsis: ");
					String synopsis = sc.nextLine();
					success = mvc.updateMovieById(4, movieId, synopsis);
					break;

				case 5:
					System.out.println("Enter new movie's director: ");
					String director = sc.nextLine();
					success = mvc.updateMovieById(5, movieId, director);
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
					success = mvc.updateMovieById(6, movieId, casts);
					break;

				case 7:
					System.out.println("Enter new movie rating (G / PG / M / R / TBC) :");
					String rating = sc.nextLine();
					success = mvc.updateMovieById(7, movieId, rating);
					break;

				case 8:
					System.out.println("Enter new movie duration (mins): ");
					int runtime = Integer.valueOf(sc.nextLine());
					success = mvc.updateMovieById(8, movieId, runtime);
					break;

				case 9:
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
					LocalDate releaseDate = null;
					exit = false;
					while(!exit) {
						try {
							System.out.println("Enter new movie release date (DD/MM/YYYY) : ");
							releaseDate = LocalDate.parse(sc.nextLine(), formatter);
							exit = true;
						}catch(DateTimeParseException e) {
							System.out.println("Invalid date format, Please try again");
						}
					}
					success = mvc.updateMovieById(9,movieId, releaseDate);
					break;
				default:
					System.out.println("Invalid option, returning to previous menu");
					return;
				}
			if(success)
				System.out.println("Movie is successfully updated");
			else
				System.out.println("Update failed");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input detected, Please try again");
			return;
		}
	}
	
	public static void removeMovieListing() {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		int movieId = 0;
		
		try {
			System.out.println("\nMOVIE DELETE");
			
			while(!exit) {
				if(!mvc.printAllMoviesTitleWithID()){
					System.out.println("The database is empty. Please create movie listing");
					return;
				}
				System.out.println("Select movie to be removed(ID): ");
				movieId = Integer.valueOf(sc.nextLine());
			
				if(!mvc.checkIdExist(movieId)) {
					System.out.println("Movie ID " + movieId + " does not exist. Please try again");
					continue;
				}
				exit = true;
			}
			if(mvc.removeMovieByID(movieId))
				System.out.println("Movie is successfully removed");
			else
				System.out.println("Removal failed");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input detected, Please try again");
			return;
		}
	}
	
}
