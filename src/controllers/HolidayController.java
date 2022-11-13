package src.controllers;

package src.controllers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
Generate a sample set of data in database before running the app
@author xichen
@version 1.0
@since 2022-11-13
*/

public class HolidayController {
	/**
	 *name of holiday database file
	 */
    private String fileName;
    /** 
     * ensure that class variable is synced with database for every (5 in this case) set number of holidays stored in database
     */
    private static int sync = 0;
    
    /*
     * keeps a list of holidays
     */
    private static ArrayList<LocalDate> holidays = new ArrayList<LocalDate>();

    /**
     * constructor sets filename while retrieving holidays from DB
     * @param fileName for holiday database
     */
    public HolidayController(String fileName) {
        this.fileName = fileName;
        holidays = this.getAllHolidaysFromDB();
    }
    
    /**
     * find a certain date of holiday in the database
     * @param a is the date to search for
     * @return true/false based on the search result
     */

    public boolean searchHolidayinDB(LocalDate a) {
        ArrayList<LocalDate> Holidays = getAllHolidaysFromDB();
        for (int i = 0; i < Holidays.size(); i++) {
            if (Holidays.get(i) == a) return true;
        }
        return false;
    }


    /**
     * method to write holiday into database
     * sync is used here to sync database with the current class variable containing the holidays
     * @param a is the holiday to write in
     */
    public void updateHolidayToDB(ArrayList<LocalDate> a) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);

        if (!f.exists()) {
            System.out.println("File: " + fileName + " does not exist");
            System.out.println("Creating new DB");
        }
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(a);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        sync++;
        if (sync == 5) {
            holidays = this.getAllHolidaysFromDB();
            sync = 0;
        }

    }



    /**
     * method to return holidays from database
     * @return holidays from database
     */
    @SuppressWarnings("unchecked")
    public ArrayList<LocalDate> getAllHolidaysFromDB() {
        ArrayList<LocalDate> holidays = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            holidays = (ArrayList<LocalDate>) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return holidays;
    }

	/*public boolean isAHoliday(LocalDate a) {
		ArrayList<LocalDate> holidays = null;
		holidays = getAllHolidaysFromDB();
		for (int i = 0; i < holidays.size(); i++) {
			if (a == holidays.get(i))
				return true;
		}
		return false;
	}*/

    /**
     * returns filename of the holidays database
     * @return filename of holidays database
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * sets filename for holidays database
     * @param fileName is location of holidays database
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
	 /*public boolean removeHoliday(LocalDate a) {
		 ArrayList<LocalDate> holidays = null;
	     File f = new File(fileName);
	     if(f.exists()){
	            holidays = this.getAllHolidaysFromDB();
	            for(int i=0; i<holidays.size();i++){
	                if(holidays.get(i) == a){
	                    holidays.remove(i);
	                    return true;
	                }
	            }
	            System.out.println("holiday not found in database");
	        }
	        else
	            System.out.println("File: " + fileName + " does not exist");
	        return false;
	   }*/

    /**
     * returns current class variable holidays 
     * @return holidays currently stored in class
     */
    public static ArrayList<LocalDate> getHolidays() {
        return holidays;
    }

    /**
     * sets a new collection of holidays to current class variable
     * @param holidays updates the current class variable to it
     */
    public static void setHolidays(ArrayList<LocalDate> holidays) {
        HolidayController.holidays = holidays;
    }

    /**
     * adding a holiday to the current arraylist of holidays
     * @param holiday is the holiday date to add
     */
    public static void setHolidays(LocalDate holiday) {
        holidays.add(holiday);
    }
    /**
     * removing a holiday from the arraylist
     * @param holiday is the date to remove
     */
    public static void removeHolidays(LocalDate holiday) {
        holidays.remove(holiday);
    }
}
