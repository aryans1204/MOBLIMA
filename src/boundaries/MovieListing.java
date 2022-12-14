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

/**
 * Boundary class providing the Movie Listing related User Interface
 *
 * @author Aryan
 */
public class MovieListing {
    /**
     * User Interface for Staff to create a new movie Listing at a Cinema
     *
     * @param cinemaName represents the name of the Cinema to createMovieListing at
     * @return Movie
     */
    public static Movie createMovieListing(String cinemaName) {
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
            ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
            for (int i = 0; i < cinemaDB.size(); i++) {
                if (cinemaDB.get(i).getName().equals(cinemaName)) {
                    Cinema temp = cinemaDB.get(i);
                    temp.updateMovies(newMovie);
                    cinemaDB.set(i, temp);
                }
            }
            CinemaController.setCinemaDB(cinemaDB);
            System.out.println("Movie List Created.. Going back to previous menu");
            return newMovie;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid input detected, Please try again");
            return null;
        }
    }

    /**
     * user interface for Staff to update a Movie Listing at a Cinema
     *
     * @param cinema represents the particular Cinema to update movie at
     */
    public static void updateMovieListing(Cinema cinema) {
        //Instead of updating release date, give an option to update a showtime, based on a Cinema.
        Scanner sc = new Scanner(System.in);
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        int option;
        boolean exit = false, success = false;
        Movie mov;
        ArrayList<Cinema> cinemaDB;
        ArrayList<Movie> mo;
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
                    mov = movieDB.get(index);
                    mov.setTitle(title);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
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
                    Movie m = movieDB.get(index);
                    m.setType(type);
                    movieDB.set(index, m);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(m);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
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
                    mov = movieDB.get(index);
                    mov.setStatus(status);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
                    break;
                case 4:
                    System.out.println("Enter new movie's synopsis: ");
                    String synopsis = sc.nextLine();
                    mov = movieDB.get(index);
                    mov.setSynopsis(synopsis);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
                    break;

                case 5:
                    System.out.println("Enter new movie's director: ");
                    String director = sc.nextLine();
                    mov = movieDB.get(index);
                    mov.setDirector(director);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
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
                    mov = movieDB.get(index);
                    mov.setCasts(casts);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
                    break;

                case 7:
                    System.out.println("Enter new movie rating (G / PG / M / R / TBC) :");
                    String rating = sc.nextLine();
                    mov = movieDB.get(index);
                    mov.setRating(rating);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    mo = cinema.getMovies();
                    mo.add(mov);
                    cinema.setMovies(mo);
                    cinemaDB = CinemaController.getCinemaDB();
                    for (int i = 0; i < cinemaDB.size(); i++) {
                        if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
                    }
                    break;

                case 8:
                    System.out.println("Enter new movie duration (mins): ");
                    int runtime = Integer.parseInt(sc.nextLine());
                    mov = movieDB.get(index);
                    mov.setRuntime(runtime);
                    movieDB.set(index, mov);
                    MovieController.setMovieDB(movieDB);
                    break;

                case 9:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
                    LocalDateTime showtime = null;
                    LocalDateTime newShowTime = null;
                    exit = false;
                    String selectedTitle = movieDB.get(index).getTitle();
                    int stIndex = 0;
                    //Prints showtimes of the movie
                    cinema.printShowtimes(selectedTitle);
                    ArrayList<LocalDateTime> showtimes = cinema.getShowtimes(selectedTitle);
                    while (!exit) {
                        try {
                            System.out.println("Enter a existing showtime you would like to change e.g (20/11/2022 09:00AM): ");
                            showtime = LocalDateTime.parse(sc.nextLine(), formatter);
                            for (int i = 0; i < showtimes.size(); i++) {
                                if (showtimes.get(i).isEqual(showtime)) {
                                    stIndex = i;
                                    exit = true;
                                    break;
                                }
                            }
                            if (!exit) System.out.println("Showtime does not exist. Please try again");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format, Please try again");
                        }
                    }

                    for (int i = 0; i < showtimes.size(); i++) {
                        if (showtimes.get(i).isEqual(showtime)) {
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
                        }
                    }
                    showtimes.set(stIndex, newShowTime);
                    cinema.updateShowtime(selectedTitle, showtimes);
                    cinemaDB = CinemaController.getCinemaDB();
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

    public static void addShowtime(Cinema cinema) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
        LocalDateTime showtime = null;
        boolean exit = false;
        String title = "";

        while (!exit) {
            System.out.println("Enter the Movie title you want to add showtime to: ");
            title = sc.nextLine();
            for (int i = 0; i < movieDB.size(); i++) {
                if (movieDB.get(i).getTitle().equals(title)) {
                    exit = true;
                    break;
                }
            }
            if (!exit) System.out.println("Title does not exist. Please try again");
        }

        exit = false;
        while (!exit) {
            try {
                System.out.println("Enter the showtime you would like to add e.g (20/11/2022 09:00AM): ");
                showtime = LocalDateTime.parse(sc.nextLine(), formatter);
                exit = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, Please try again");
            }
        }
        ArrayList<LocalDateTime> showtimes = cinema.getShowtimes(title);
        showtimes.add(showtime);
        cinema.updateShowtime(title, showtimes);
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        for (int i = 0; i < cinemaDB.size(); i++) {
            if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
        }
        CinemaController.setCinemaDB(cinemaDB);
        System.out.println("Showtime added successfully");
    }

    public static void removeShowtime(Cinema cinema) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mma");
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        LocalDateTime showtime = null;
        boolean exit = false;
        int index = 0;
        String title = "";

        while (!exit) {
            System.out.println("Enter the Movie title you want to remove showtime from: ");
            title = sc.nextLine();
            for (int i = 0; i < movieDB.size(); i++) {
                if (movieDB.get(i).getTitle().equals(title)) {
                    exit = true;
                    break;
                }
            }
            if (!exit) System.out.println("Title does not exist. Please try again");
        }
        ArrayList<LocalDateTime> showtimes = cinema.getShowtimes(title);

        exit = false;
        while (!exit) {
            try {
                System.out.println("Enter a existing showtime you would like to remove e.g (20/11/2022 09:00AM): ");
                showtime = LocalDateTime.parse(sc.nextLine(), formatter);
                for (int i = 0; i < showtimes.size(); i++) {
                    if (showtimes.get(i).isEqual(showtime)) {
                        index = i;
                        exit = true;
                        break;
                    }
                }
                if (!exit) System.out.println("Showtime does not exist. Please try again");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, Please try again");
            }
        }
        showtimes.remove(index);
        cinema.updateShowtime(title, showtimes);
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        for (int i = 0; i < cinemaDB.size(); i++) {
            if (cinemaDB.get(i).getName().equals(cinema.getName())) cinemaDB.set(i, cinema);
        }
        CinemaController.setCinemaDB(cinemaDB);
        System.out.println("Showtime removed successfully");
    }

    /**
     * User Interface to Remove a Movie listing. This removal happens globally across all Cinemas
     */
    public static void removeMovieListing() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        try {
            String title;
            System.out.println("Enter the title of the movie you would like to remove today");
            title = sc.nextLine();

            for (int i = 0; i < movieDB.size(); i++) {
                if (movieDB.get(i).getTitle().equals(title)) {
                    Movie mov = movieDB.get(i);
                    mov.setStatus(MovieStatus.END_OF_SHOWING);
                    movieDB.set(i, mov);
                    MovieController.setMovieDB(movieDB);
                    ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
                    for (int k = 0; k < cinemaDB.size(); k++) {
                        ArrayList<Movie> moviez = cinemaDB.get(k).getMovies();
                        for (int j = 0; j < moviez.size(); j++) {
                            if (moviez.get(j).getTitle().equals(title)) {
                                Movie movs = moviez.get(j);
                                movs = mov;
                                moviez.set(j, movs);
                                CinemaController.setCinemaDB(cinemaDB);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            System.out.println("Movie successfully removed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid input detected, Please try again");
        }

    }

    /**
     * User Interface to list all Movies present in the database
     */
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

    /**
     * User interface to List all movies ordered by Rating
     */
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

    /**
     * User interface to search for a Movie based on Movie title in the database
     *
     * @throws IOException
     */
    public static void searchMovie() throws IOException {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Movie title you would like to search");
        String title = reader.readLine();
        for (Movie mov : movieDB) {
            if (Objects.equals(mov.getTitle(), title) && mov.getStatus() != MovieStatus.END_OF_SHOWING) {
                System.out.println(mov);
                return;
            }
        }
        System.out.println("Sorry, movie not found. Try again");
    }

    /**
     * User interface tp List Movies ordered by TotalSales
     */
    public static void listBySales() {
        ArrayList<Movie> movieDB = MovieController.getMovieDB();
        ArrayList<Movie> listMoviesSales = movieDB;

        listMoviesSales.sort((o1, o2) -> Double.compare(o2.getTotalSales(), o1.getTotalSales()));
        Collections.sort(listMoviesSales, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return String.valueOf(o2.getTotalSales()).compareTo(String.valueOf(o1.getTotalSales()));
            }
        });
        ArrayList<Movie> sortedMoviesSales = new ArrayList<Movie>(listMoviesSales);
        System.out.println("Top 5 Movies by Total Sales\n");
        System.out.println("Overall Rating\tTitle");
        System.out.println("--------------\t-----");
        for (int i = 0; i < 5; i++) {
            System.out.println("\t" + sortedMoviesSales.get(i).getTotalSales() + "\t" + sortedMoviesSales.get(i).getTitle());
        }
    }

    /**
     * User interface to List Movies by Cinema
     *
     * @throws Exception
     */
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
                        if (movie.getStatus() != MovieStatus.END_OF_SHOWING) {
                            System.out.println(movie.toString());
                            System.out.println("Showtimes: ");
                            cinema.printShowtimes(movie.getTitle());
                        }
                    }
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("Invalid Cinema Name. Please try again.");
        } while (!found);
    }
}
