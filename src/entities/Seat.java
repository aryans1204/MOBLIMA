package src.entities;

import java.util.*;
import java.io.*;

/**
 * Represents a Seat a Cinema
 * @author Aryan
 */
public class Seat implements Serializable {
    /**
     * Type of this Seat
     */
    public SeatType type;
    /**
     * Customer assigned to this Seat
     */
    private Customer customer;
    /**
     * Seat number of this Seat
     */
    private String seatNo;

    /**
     * Creates Seat with the given attributes
     * @param type      This Seat's type
     * @param customer  This Seat's Customer
     * @param seatNo    This Seat's number
     */
    public Seat(SeatType type, Customer customer, String seatNo) {
	this.type = type;
	this.customer = customer;
	this.seatNo = seatNo;
    }

    /**
     * Gets the type of this Seat
     * @return  This Seat's type
     */
    public SeatType getType() {
	return this.type;
    }

    /**
     * Gets the Customer assigned to this Seat
     * @return  This Seat's Customer
     */
    public Customer getCustomer() {
	return this.customer;
    }

    /**
     * Changes the Customer assigned to this Seat
     * @param customer  This Seat's new Customer
     */
    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    /**
     * Gets the seat number of this Seat
     * @return  This Seat's number
     */
    public String getSeatNo() {
	return this.seatNo;
    }
	
}
