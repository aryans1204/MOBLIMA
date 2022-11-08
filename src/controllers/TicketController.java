package src.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import src.entities.*;

//Ticket controller is used to add tickets to tickets database
//Also used to update default ticket price value in prices database


public class TicketController {
	private String fileName;
	private String priceFileName;
	
	
	public TicketController(String ticket_file_name,String prices_file_name){
		fileName = ticket_file_name; //Input the directory where your .dat file is located
		priceFileName = prices_file_name;
	}
	
	public void updatePriceToDB(int moviename_index, int cinema_index,int showtime_index, int price) {
		//do i have to use arraylist? 
		//how do i use arraylist for multidimensional array?
		//will there be problems when putting array into file? Does it need serialisable interface?
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File f = new File(priceFileName);
		int priceList[];
		if(f.exists())
			priceList = this.getAllPricesFromDB();//Read in existing data in db
		else {
			System.out.println("File: " + priceFileName + " does not exist");
			System.out.println("Creating new DB");
			priceList = new int[1000];
		}
		
		//default price of movie is inserted into array
		//default price means an adult, weekday, a normal class cinema, a normal type movie
		priceList[moviename_index]= price;
		try {
			fos = new FileOutputStream(priceFileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(priceList);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void insertTicketToDB(Movie a, Cinema b, Customer c, String d, String TID, String custName, String custEmail, String custMobileNumber) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		//reads the array from the pricelist DB and then inserts the corresponding price
		Ticket newTicket = new Ticket(a,b,c,d, TID, custName, custEmail, custMobileNumber); 
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		//doesnt this new file replace the old one? Why put it before the if statement
		File f = new File(fileName);
		if(f.exists())
			tickets = this.getAllTicketsFromDB();//Read in existing data in db
		else {
			System.out.println("File: " + fileName + " does not exist");
			System.out.println("Creating new DB");
		}
		tickets.add(newTicket);
		try {
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(tickets);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Ticket> getAllTicketsFromDB() {
		ArrayList<Ticket> tickets = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			tickets = (ArrayList<Ticket>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return tickets;
	}
	
	public int[] getAllPricesFromDB() {
		int[] prices = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(priceFileName);
			in = new ObjectInputStream(fis);
			prices = (int[]) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return prices;
	}
	
	

}
