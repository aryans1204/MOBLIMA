package src.entities;

import java.io.Serializable;

/**
 * Represents a Transaction in the system
 * @author JinCheng
 */
@SuppressWarnings("serial")
public class Transaction implements Serializable{

	/**
	 * Transaction's ID
	 */
	private String TID;

	/**
	 * Transaction's Customer name
	 */
	private String custName;

	/**
	 * Transaction's Customer email
	 */
	private String custEmail;

	/**
	 * Transaction Customer mobile number
	 */
	private String custMobileNumber;

	/**
	 * Creates a Transaction with the given attributes
	 * @param TID				This Transaction's ID
	 * @param custName			This Transaction's Customer name
	 * @param custEmail			This Transaction's Customer email
	 * @param custMobileNumber	This Transaction's Customer mobile number
	 */
	public Transaction(String TID, String custName, String custEmail, String custMobileNumber) {
		this.TID = TID;
		this.custName = custName;
		this.custEmail = custEmail;
		this.custMobileNumber = custMobileNumber;
	}

	/**
	 * Gets the ID of this Transaction
	 * @return	This Transaction's ID
	 */
	public String getTID() {
		return TID;
	}

	/**
	 * Gets the Customer name of this Transaction
	 * @return	This Transaction's Customer name
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * Gets the Customer email of this Transaction
	 * @return	This Transaction's Customer email
	 */
	public String getCustEmail() {
		return custEmail;
	}

	/**
	 * Gets the Customer mobile number of this Transaction
	 * @return	This Transaction's Customer mobile number
	 */
	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	/**
	 * Changes the ID of this Transaction
	 * @param tID	This Transaction's new ID
	 */
	public void setTID(String tID) {
		TID = tID;
	}

	/**
	 * Changes the Customer name of this Transaction
	 * @param custName	This Transaction's new Customer name
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * Changes the Customer email of this Transaction
	 * @param custEmail This Transaction's new Customer email
	 */
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	/**
	 * Changes the Customer mobile number of this Transaction
	 * @param custMobileNumber	This Transaction's new Customer mobile number
	 */
	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	/**
	 * Overrides toString method to store Transaction detail in a specific format
	 * @return	a string of Transaction details
	 */
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
