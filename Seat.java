import java.util.*;
import java.io.*;

public class Seat implements Serializable {
    private enum SeatType {
	STANDARD,
	GOLD,
	PLATINUM
    } type;

    private Customer customer;

    pubic Seat(SeatType type, Customer customer) {
	this.type = type;
	this.customer = customer;
    }
	
}
