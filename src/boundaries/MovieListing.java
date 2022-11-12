package src.boundaries;

import src.controllers.CinemaController;
import src.controllers.MovieController;
import src.entities.Cinema;
import src.entities.Movie;
import src.entities.MovieStatus;
import src.entities.MovieType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MovieListing {
    public static Movie createMovieListing() {
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

            System.out.println("Enter movie title: ");
            title = sc.nextLine();

            while (!exit) {
                System.out.print("\nMovie types: \n" +
                        "	1. 2D\n" +
                        "	2. 3D\n" +
                        "	3. Blockbuster\n\n" +
                        "Select an option: ");

                option = Integer.parseInt(sc.nextLine());

                switch (option) {
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
            while (!exit) {
                System.out.print("\nMovie showing status: \n" +
                        "	1. Coming Soon\n" +
                        "	2. Preview\n" +
                        "	3. Now Showing\n" +
                        "	4. End of showing\n\n" +
                        "Select an option: ");

                option = Integer.parseInt(sc.nextLine());

                switch (option) {
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
            while (!exit) {
                System.out.println("Enter number of casts (min 2): ");
                option = Integer.parseInt(sc.nextLine());
                if (option < 2) {
                    System.out.println("Invalid number, Please enter for more than 2 casts");
                    continue;
                }
                for (int i = 0; i < option; i++) {
                    System.out.println("Enter name of cast " + (i + 1) + ": ");
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
            runtime = Integer.parseInt(sc.nextLine());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            exit = false;
            while (!exit) {
                try {
                    System.out.println("Enter movie release date (DD/MM/YYYY) : ");
                    releaseDate = LocalDate.parse(sc.nextLine(), formatter);
                    exit = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format, Please try again");
                }
            }
            Movie newMovie = new Movie(title, type, status, synopsis, director, casts, rating, runtime, releaseDate, 0);
            MovieController.setMovieDB(newMovie);
            System.out.println("Movie List Created.. Going back to previous menu");
            return newMovie;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid input detected, Please try again");
            return null;
        }
    }

    public static void updateMovieListing(Cinema cinema) {
        //Instead of updating release date, give an option to update a showtime, based on a Cinema.
        Scanner sc = new Scanner(System.in);
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        int movieId = 0, option;
        boolean exit = false, success = false;

        try {
            System.out.println("\nMOVIE UPDATE");
            int index = 0;
            while (!exit) {
                System.out.println("Enter the Movie title which you need to update: ");
                String title = sc.nextLine();
                for (int i = 0; i < movieDB.size(); i++) {
                    if (movieDB.get(i).getTitle().equals(title)) {
                        index = i;
                        exit = true;
                        break;
                    }
                }
                if (!exit) System.out.println("Title does not exist. Please try again");
            }

            System.out.println("Select movie detail to update");
            System.out.print("1. Title\n" +
                    "2. Type\n" +
                    "3. Status\n" +
                    "4. Synopsis\n" +
                    "5. Director \n" +
                    "6. Cast\n" +
                    "7. Rating\n" +
                    "8. Duration\n" +
                    "9. Showtimes\n\n" +
                    "Enter option: ");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.println("Enter new Title:");
                    String title = sc.nextLine();
                    movieDB.get(index).setTitle(title);
                    MovieController.setMovieDB(movieDB);
                    break;
                case 2:
                    MovieType type = null;
                    exit = false;
                    while (!exit) {
                        System.out.print("\nMovie types: \n" +
                                "	1. 2D\n" +
                                "	2. 3D\n" +
                                "	3. Blockbuster\n\n" +
                                "Select new option: ");

                        option = Integer.parseInt(sc.nextLine());

                        switch (option) {
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
                    MovieController.setMovieDB(movieDB);
                    break;

                case 3:
                    exit = false;
                    MovieStatus status = null;
                    while (!exit) {
                        System.out.print("\nMovie showing status: \n" +
                                "	1. Coming Soon\n" +
                                "	2. Preview\n" +
                                "	3. Now Showing\n" +
                                "	4. End of showing\n\n" +
                                "Select new option: ");

                        option = Integer.parseInt(sc.nextLine());

                        switch (option) {
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
                    MovieController.setMovieDB(movieDB);
                    break;
                case 4:
                    System.out.println("Enter new movie's synopsis: ");
                    String synopsis = sc.nextLine();
                    movieDB.get(index).setSynopsis(synopsis);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 5:
                    System.out.println("Enter new movie's director: ");
                    String director = sc.nextLine();
                    movieDB.get(index).setDirector(director);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 6:
                    exit = false;
                    ArrayList<String> casts = new ArrayList<String>();
                    System.out.println("Past entered casts will be overwritten");
                    while (!exit) {
                        System.out.println("Enter number of casts (min 2): ");
                        option = Integer.parseInt(sc.nextLine());
                        if (option < 2) {
                            System.out.println("Invalid number, Please enter for more than 2 casts");
                            continue;
                        }

                        for (int i = 0; i < option; i++) {
                            System.out.println("Enter name of new cast " + (i + 1) + ": ");
                            casts.add(sc.nextLine());
                        }
                        exit = true;
                    }
                    movieDB.get(index).setCasts(casts);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 7:
                    System.out.println("Enter new movie rating (G / PG / M / R / TBC) :");
                    String rating = sc.nextLine();
                    movieDB.get(index).setRating(rating);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 8:
                    System.out.println("Enter new movie duration (mins): ");
                    int runtime = Integer.parseInt(sc.nextLine());
                    movieDB.get(index).setRuntime(runtime);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 9:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
                    LocalDateTime showtime = null;
                    LocalDateTime newShowTime = null;
                    exit = false;
                    String selectedTitle = movieDB.get(index).getTitle();
                    //Prints showtimes of the movie
                    cinema.printShowtimes(selectedTitle);
                    while (!exit) {
                        try {
                            System.out.println("Enter the showtime you would like to change e.g (20/11/2022 09:00AM): ");
                            showtime = LocalDateTime.parse(sc.nextLine(), formatter);
                            exit = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format, Please try again");
                        }
                    }
                    ArrayList<LocalDateTime> showtimes = cinema.getShowtimes(selectedTitle);
                    for(int i = 0; i < showtimes.size(); i++) {
                    	if(showtimes.get(i).isEqual(showtime)) {
                    		exit = false;
                            while (!exit) {
                                try {
                                    System.out.println("Enter the new showtime e.g (20/11/2022 09:00AM): ");
                                    newShowTime = LocalDateTime.parse(sc.nextLine(), formatter);
                                    exit = true;
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid date format, Please try again");
                                }
                            }
                    		showtimes.set(i, newShowTime);
                    	}
                    }
                    cinema.updateShowtime(selectedTitle, showtimes);
                    ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
                    CinemaController.setCinemaDB(cinemaDB);
                    break;
                default:
                    System.out.println("Invalid option, returning to previous menu");
                    return;
            }
            System.out.println("Movie is successfully updated");

            System.out.println("\nTitle: " + movieDB.get(index).getTitle());
            System.out.println("Type: " + movieDB.get(index).getType());
            System.out.println("Date: " + movieDB.get(index).getMovieReleaseDateToString());
            System.out.println("Duration: " + movieDB.get(index).getRuntime());
            System.out.println(movieDB.get(index));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid input detected, Please try again");
        }
    }

    public static void removeMovieListing() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        try {
            String title;
            System.out.println("Enter the title of the movie you would like to remove today");
            title = sc.nextLine();

            for (int i = 0; i < movieDB.size(); i++) {
                if (movieDB.get(i).getTitle().equals(title)) movieDB.get(i).setStatus(MovieStatus.END_OF_SHOWING);
                System.out.println("Movie successfully removed");
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid input detected, Please try again");
        }
        MovieController.setMovieDB(movieDB);
    }

    public static void listMovie() {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        for (Movie movie : movieDB) {
            if (movie.getStatus() != MovieStatus.END_OF_SHOWING) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Type: " + movie.getType());

                System.out.println("Date: " + movie.getMovieReleaseDateToString());

                System.out.println("Duration: " + movie.getRuntime());
                System.out.println(movie);
            }

        }
    }

    public static void listByReview() {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        ArrayList<Movie> listMovies = movieDB;

//        listMovies.sort((o1, o2) -> Double.valueOf(o2.getOverallReview()).compareTo(Double.valueOf(o1.getOverallReview())));
        Collections.sort(listMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.getOverallReview().compareTo(o1.getOverallReview());
            }
        });
        ArrayList<Movie> sortedMovies = new ArrayList<Movie>(listMovies);
        System.out.println("Top 5 Movies by Overall Rating\n");
        System.out.println("Overall Rating\tTitle");
        System.out.println("--------------\t-----");
        for (int i = 0; i < 5; i++) {
            if (sortedMovies.get(i).getOverallReview().equals("0"))
                System.out.println("\t" + "N/A" + "\t" + sortedMovies.get(i).getTitle());
            else
                System.out.println("\t" + sortedMovies.get(i).getOverallReview() + "\t" + sortedMovies.get(i).getTitle());
        }
    }

    public static void searchMovie() throws IOException {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Movie title you would like to search");
        String title = reader.readLine();
        for (Movie mov : movieDB) {
            if (Objects.equals(mov.getTitle(), title)) {
                System.out.println(mov);
                return;
            }
        }
        System.out.println("Sorry, movie not found. Try again");
    }

    public static void listBySales() {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        List<Movie> listMoviesSales = movieDB;

        listMoviesSales.sort((o1, o2) -> Double.compare(o2.getTotalSales(), o1.getTotalSales()));

        ArrayList<Movie> sortedMoviesSales = new ArrayList<Movie>(listMoviesSales);
        System.out.println("Top 5 Movies by Total Sales\n");
        System.out.println("Overall Rating\tTitle");
        System.out.println("--------------\t-----");
        for (int i = 0; i < 5; i++) {
            System.out.println("\t" + sortedMoviesSales.get(i).getTotalSales() + "\t" + sortedMoviesSales.get(i).getTitle());
        }
    }

    public static void listMoviesByCinema() throws Exception {
        boolean found = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        do {
            System.out.println("Enter the name of the Cinema you would like to search");
            System.out.println("Available Cinemas: ");
            for (Cinema cinema : cinemaDB) {
                System.out.println(cinema.getName());
            }
            String cinemaName = reader.readLine();
            for (Cinema cinema : cinemaDB) {
                if (cinema.getName().equals(cinemaName)) {
                    ArrayList<Movie> movies = cinema.getMovies();
                    for (Movie movie : movies) {
                        System.out.println(movie.toString());
                    }
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("Invalid Cinema Name. Please try again.");
        } while (!found);
    }
}
