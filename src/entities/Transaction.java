package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transaction implements Serializable{
	private String TID;
	private String custName;
	private String custEmail;
	private String custMobileNumber;
	
	public Transaction(String TID, String custName, String custEmail, String custMobileNumber) {
		this.TID = TID;
		this.custName = custName;
		this.custEmail = custEmail;
		this.custMobileNumber = custMobileNumber;
	}
	
	public String getTID() {
		return TID;
	}
	public String getCustName() {
		return custName;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public String getCustMobileNumber() {
		return custMobileNumber;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}
	
	@Override
	public String toString() {
		String transactionDetails = "";
		
		transactionDetails = " TransactionID: " + this.getTID() +
						"\n Name: " + this.getCustName() +
						"\n Email: " + this.getCustEmail() +
						"\n Number: " + this.getCustMobileNumber();
		
		return transactionDetails;
	}
	
}
