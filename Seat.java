import java.util.*;
import java.io.*;

public class Seat implements Serializable {
    private enum SeatType {
	STANDARD,
	GOLD,
	PLATINUM
    } type;

    private Customer customer;

    public Seat(SeatType type, Customer customer) {
	this.type = type;
	this.customer = customer;
    }
    public SeatType getType() {
	return this.type;
    }
    public Customer getCustomer() {
	return this.customer;
    }
	
}
