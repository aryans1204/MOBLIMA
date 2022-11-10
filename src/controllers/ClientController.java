package src.controllers;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.time.*;

import src.entities.*;

public class ClientController {
    private String fileName;
    private String fileNameStaff;

    ClientController(String fileNameCustomer, String fileNameStaff){
        this.fileNameStaff = fileNameStaff;
	this.fileName = fileNameCustomer;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileNameStaff() {
	return fileNameStaff;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void setFileNameStaff(String fileNameStaff) {
	this.fileNameStaff = fileNameStaff;
    }

    //Inserting Customers name, age, username and password into the database
    public void insertCustomerToDB(String name, int age, String username, String password, String email, int mobileNumber){
        ArrayList<Customer> customers = new ArrayList<>();
        Customer newCustomer = new Customer(name, age, username, password, email, mobileNumber);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);
        if(f.exists()) {
            customers = this.getCustomerFromDB();
            customers.add(newCustomer);
        }
        else{
            System.out.println("File: " + fileName + " does not exist");
            System.out.println("Creating new DB");
        }

        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(customers);
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }
    //Inserting Staff into database with username, password and associated cinema
    public void insertStaffToDB(String username, String password, Cinema cinema) {
	ArrayList<Staff> staffs = new ArrayList<>();
        Staff newStaff = new Staff(username, password, cinema);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileNameStaff);
        if(f.exists()) {
            staffs = this.getStaffFromDB();
            staffs.add(newStaff);
        }
        else{
            System.out.println("File: " + fileNameStaff + " does not exist");
            System.out.println("Creating new DB");
        }

        try {
            fos = new FileOutputStream(fileNameStaff);
            out = new ObjectOutputStream(fos);
            out.writeObject(staffs);
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    //Getting all customer's details from the database
    public ArrayList<Customer> getCustomerFromDB(){
        ArrayList<Customer> customers = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try{
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            customers = (ArrayList<Customer>) in.readObject();
            in.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return customers;
    }
    
    public ArrayList<Staff> getStaffFromDB() {	
        ArrayList<Staff> staffs = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try{
            fis = new FileInputStream(fileNameStaff);
            in = new ObjectInputStream(fis);
            staffs = (ArrayList<Staff>) in.readObject();
            in.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return staffs;
    }

    public boolean checkUsernameExist(String username) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if(f.exists()) {
            customers = this.getCustomerFromDB();//Read in existing data in DB
            if(customers.size()> 0) {
                for(Customer customer: customers)
                    if(customer.getUsername() == username)
                        return true;
            }
        }
        else
          System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    public boolean removeCustomer(String name, int age) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if(f.exists()){
            customers = this.getCustomerFromDB();
            for(int i=0; i<customers.size();i++){
                if(customers.get(i).getName() == name && customers.get(i).getAge() == age){
                    customers.remove(i);
                    return true;
                }
            }
            System.out.println("Name not found in database");
        }
        else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }
    
}
