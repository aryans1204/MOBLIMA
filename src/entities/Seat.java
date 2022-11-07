package entities;

import java.util.*;
import java.io.*;

public class Seat implements Serializable {
    private enum SeatType {
	STANDARD,
	GOLD,
	PLATINUM
    } type;

    private Customer customer;
    private String seatNo;

    public Seat(SeatType type, Customer customer, String seatNo) {
	this.type = type;
	this.customer = customer;
	this.seatNo = seatNo;
    }
    public SeatType getType() {
	return this.type;
    }
    public Customer getCustomer() {
	return this.customer;
    }
    public void setCustomer(Customer customer) {
	this.customer = customer;
    }
    public String getSeatNo() [
	return this.seatNo;
    }
	
}
