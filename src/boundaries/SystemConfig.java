package src.boundaries;

import src.controllers.CinemaController;
import src.controllers.HolidayController;
import src.entities.Cinema;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Boundary class providing a User Interface for Staff to configure system settings
 *
 * @author Aryan
 */
public class SystemConfig {
    /**
     * User interface for Staff to change Ticket prices for a Movie at a Cinema
     *
     * @param cinema represents the Cinema at which to configureTicketPrices
     * @throws Exception
     */
    public static void configTicketPrices(Cinema cinema) throws Exception {
        System.out.println("Enter Movie title for which you would like to update the prices");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String title = reader.readLine();
        System.out.println("Enter the price you want to set for this ticket");
        double price = Double.parseDouble(reader.readLine());
        cinema.setTicketPrice(title, price);
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        for (int i = 0; i < cinemaDB.size(); i++) {
            if (cinemaDB.get(i).getName().equals(cinema.getName())) {
                cinemaDB.set(i, cinema);
                break;
            }
        }
        CinemaController.setCinemaDB(cinemaDB);
        System.out.println("Ticket price configured successfully");
    }

    /**
     * User Interface for Staff to configure holidays at a Cinema
     *
     * @throws Exception
     */
    public static void configHolidays() throws Exception {
        int choice = 0;
        String date_input;
        LocalDate localDate = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (choice != 3) {
            System.out.println("Print choice: \n" +
                    "1. Add Holiday\n" +
                    "2. Remove holiday\n" +
                    "3. Exit");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            if (choice == 1) {
                while (!quit) {
                    try {
                        System.out.println("Enter the holiday date to add in this pattern: dd/MM/yyyy:");
                        date_input = reader.readLine();
                        localDate = LocalDate.parse(date_input, formatter);
                        quit = true;
                        HolidayController.setHolidays(localDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format, Please try again");
                        e.printStackTrace();
                    }
                }

                System.out.println("Holiday added successfully");
            } else if (choice == 2) {
                while (!quit) {
                    try {
                        System.out.println("Enter the holiday date to remove in this pattern: dd/MM/yyyy:");
                        date_input = reader.readLine();
                        localDate = LocalDate.parse(date_input, formatter);
                        quit = true;
                    } catch (Exception e) {
                        System.out.println("Invalid date format, Please try again");
                    }
                }
                HolidayController.removeHolidays(localDate);
                System.out.println("Holiday removed successfully");
            }
            quit = false;
        }
    }
}
