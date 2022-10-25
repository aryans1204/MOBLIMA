import java.util.*;

import Client,Cinema;

public class Staff implements Client {
    private String username;
    private boolean auth = false;
    private Cinema cinema;   //associated cinema of the staff. Where the staff works

    public login(String username, String password, Database db) {
        if (!db.staff_accounts.containsKey(this.username)) return false;

        else if (db.staff_accounts.get(this.username) != password) return false;
        this.auth = true;
        System.out.println("Authenticated succesfully");
        return true;
    }

    public void createAccount(String username, String password, Database db) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tries = 9;  //9 tries before system shuts;
        do {
            System.out.println("Enter username: ");
            String username = reader.readLine();
            if (db.user_accounts.containsKey(username)) System.out.println("Username already taken");
            if (tries == 0) System.out.println("Too many tries. System quitting now");
            tries--;
        }
        while (db.user_accounts.containsKey(username) && tries != 0);
        if (tries == 0) return false;
        System.out.println("Enter password: ");
        String password = reader.readLine();
        db.user_accounts.put(username, password);
        this.username = username;
        System.out.println("Account created successfully");
        return true;
    }

    public void updateMovieSettings(Movie movie) throws Exception {
        if (!auth) return;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        do {
            System.out.println("The following options are available to update Movie:\n" +
                    "1. Change Movie showing status.\n" +
                    "2. Change Movie Parental Rating.\n" +
                    "3. Change Movie Type.\n" +
                    "4. Change Movie ticket price for a given showtime.\n" +
                    "5. Change Movie showtimes.\n" +
                    "6. Quit.\n");
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1: {
                    System.out.println("Enter new status");
                    String st = reader.readLine();
                    try {
                        movie.Status = movie.Status.valueOf(st);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Following is not an available showing status" + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    System.out.println("Enter the new the movie parental rating");
                    String rt = reader.readLine();
                    try {
                        movie.ParentalRating = movie.ParentalRating.valueOf(rt);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Following is not an available parental rating" + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter the new movie type");
                    String t = reader.readLine();
                    try {
                        movie.Type = movie.Type.valueOf(t);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Following is not an available movie type" + e.getMessage());
                    }
                    break;
                }
                case 4: {
                    System.out.println("Enter first ticket price, followed by showtime");
                    int tp = Integer.parseInt(reader.readLine());
                    String show = reader.readLine();
                    movie.prices.put(show, tp);
                    break;
                }
                case 5: {
                    System.out.println("Please enter which showtime you would like to alter");
                    String showt = reader.readLine();
                    for (int i = 0; i < movie.showtimes[this.cinema.code].length; i++) {
                        if (showt == movie.showtimes[this.cinema.code][i]) {
                            movie.showtimes[this.cinema.code][i] = showt;
                            break;
                        }
                    }
                    break;
                }
                case 6:
                    break;
                default: {
                    System.out.println("Please enter a valid choice");
                    break;
                }
            }
        }
        while (choice != 6);
    }

    public void addMovie() throws Exception {
        BufferedReader reader = new BufferedReader(new InputSreamReader(System.in));
        System.out.println("Enter movie title");
        String title = reader.readLine();
        System.out.println("Enter movie status");
        
    }
}
