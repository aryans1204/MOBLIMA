package src.controllers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayController {
    private String fileName;
    private static int sync = 0;
    private static ArrayList<LocalDate> holidays;

    public HolidayController(String fileName) {
        this.fileName = fileName;
        holidays = this.getAllHolidaysFromDB();
    }

    public boolean searchHolidayinDB(LocalDate a) {
        ArrayList<LocalDate> Holidays = getAllHolidaysFromDB();
        for (int i = 0; i < Holidays.size(); i++) {
            if (Holidays.get(i) == a) return true;
        }
        return false;
    }


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

    public String getFileName() {
        return fileName;
    }

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

    public static ArrayList<LocalDate> getHolidays() {
        return holidays;
    }

    public static void setHolidays(ArrayList<LocalDate> holidays) {
        HolidayController.holidays = holidays;
    }

    public static void setHolidays(LocalDate holiday) {
        holidays.add(holiday);
    }

    public static void removeHolidays(LocalDate holiday) {
        holidays.remove(holiday);
    }
}
