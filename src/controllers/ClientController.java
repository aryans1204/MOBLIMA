package src.controllers;

import src.entities.Cinema;
import src.entities.Customer;
import src.entities.Staff;

import java.io.*;
import java.util.ArrayList;

/**
 * class to control reading/writing from database for customer + staff
 *
 * @author Xichen
 * @version 1.0
 * @since 2022-11-13
 */

public class ClientController {
    /**
     * name of file for customer
     */
    private String fileName;
    /**
     * name of file for staff
     */
    private String fileNameStaff;
    /**
     * to store a class variable of array of data from database
     */
    private static ArrayList<Customer> customerDB;
    /**
     * to store a class variable of array of data from database
     */
    private static ArrayList<Staff> staffDB;

    /**
     * constructor will set filenames and read from database
     *
     * @param fileNameCustomer file name for customer database
     * @param fileNameStaff    file name for staff
     */
    public ClientController(String fileNameCustomer, String fileNameStaff) {
        this.fileNameStaff = fileNameStaff;
        this.fileName = fileNameCustomer;
        customerDB = this.getCustomerFromDB();
        staffDB = this.getStaffFromDB();
    }

    /**
     * method returns filename of customer database
     *
     * @return fileName of database
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * returns file name of staff database
     *
     * @return fileNameStaff of the staff database
     */
    public String getFileNameStaff() {
        return fileNameStaff;
    }

    /**
     * sets file name of the customer database
     *
     * @param fileName is the new filename for customer database
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * sets file name of database for staff
     *
     * @param fileNameStaff is new file name for staff database
     */
    public void setFileNameStaff(String fileNameStaff) {
        this.fileNameStaff = fileNameStaff;
    }

    /**
     * Inserting Customers name, age, username and password into the database
     *
     * @param name         of customer
     * @param age          of customer
     * @param username     of customer
     * @param password     of customer
     * @param email        of customer
     * @param mobileNumber of customer
     */
    //Inserting Customers name, age, username and password into the database
    public void insertCustomerToDB(String name, int age, String username, String password, String email, int mobileNumber) {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer newCustomer = new Customer(name, age, username, password, email, mobileNumber);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);
        if (f.exists()) {
            customers = this.getCustomerFromDB();
        } else {
            System.out.println("File: " + fileName + " does not exist");
            System.out.println("Creating new DB");
        }
        customers.add(newCustomer);
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(customers);
            out.close();
        } catch (IOException ex) {

        }

    }

    /**
     * Inserting Staff into database with username, password and associated cinema
     *
     * @param username of Staff
     * @param password of Staff
     * @param cinema   of Staff
     */
    //Inserting Staff into database with username, password and associated cinema
    public void insertStaffToDB(String username, String password, Cinema cinema) {
        ArrayList<Staff> staffs = new ArrayList<>();
        Staff newStaff = new Staff(username, password, cinema);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileNameStaff);
        if (f.exists()) {
            staffs = this.getStaffFromDB();
        } else {
            System.out.println("File: " + fileNameStaff + " does not exist");
            System.out.println("Creating new DB");
        }
        staffs.add(newStaff);
        try {
            fos = new FileOutputStream(fileNameStaff);
            out = new ObjectOutputStream(fos);
            out.writeObject(staffs);
            out.close();
        } catch (IOException ex) {

        }

    }

    /**
     * Getting all customer's details from the database
     *
     * @return database value of customers
     */
    //Getting all customer's details from the database
    public ArrayList<Customer> getCustomerFromDB() {
        ArrayList<Customer> customers = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            customers = (ArrayList<Customer>) in.readObject();
            in.close();
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }
        return customers;
    }

    /**
     * gets values from staff database
     *
     * @return staffs database values.
     */
    public ArrayList<Staff> getStaffFromDB() {
        ArrayList<Staff> staffs = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(fileNameStaff);
            in = new ObjectInputStream(fis);
            staffs = (ArrayList<Staff>) in.readObject();
            in.close();
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {
            
        }
        return staffs;
    }

    /**
     * checks if username is valid
     *
     * @param username input by user
     * @return boolean whether username is valid/invalid
     */
    public boolean checkUsernameExist(String username) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if (f.exists()) {
            customers = customerDB;//Read in existing data in DB
            if (customers.size() > 0) {
                for (Customer customer : customers)
                    if (customer.getUsername() == username)
                        return true;
            }
        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * removes customer from database
     *
     * @param name of customer
     * @param age  of customer
     * @return boolean which indicates whether customer has been removed or not
     */
    public boolean removeCustomer(String name, int age) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if (f.exists()) {
            for (int i = 0; i < customerDB.size(); i++) {
                if (customerDB.get(i).getName() == name && customerDB.get(i).getAge() == age) {
                    customerDB.remove(i);
                    return true;
                }
            }
            System.out.println("Name not found in database");
        } else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    /**
     * returns array of customers from controller's static variable
     *
     * @return customerDB array of customers from controller's static variabl
     */
    public static ArrayList<Customer> getCustomerList() {
        return customerDB;
    }

    /**
     * returns array of Staffs from controller's static variable
     *
     * @return staffDB array of Staffs from controller's static variable
     */
    public static ArrayList<Staff> getStaffList() {
        return staffDB;
    }
}
