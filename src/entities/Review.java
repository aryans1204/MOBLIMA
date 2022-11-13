package src.entities;

import java.io.Serializable;


/**
 * Represents a Review in the system
 * @author JinCheng
 */
@SuppressWarnings("serial")
public class Review implements Serializable {

    /**
     * Review's username
     */
    private String userName;

    /**
     * Review's rating
     */
    private int numRating;

    /**
     * Review's comments
     */
    private String comments;

    /**
     * Creates a Review with the given attributes
     * @param userName  This Review's username
     * @param numRating This Review's rating
     * @param comments  This Review's comments
     */
    //Constructor
    public Review(String userName, int numRating, String comments) {
        this.userName = userName;
        this.numRating = numRating;
        this.comments = comments;
    }

    /**
     * Gets the username of this Review
     * @return  This Review's username
     */
    //Getters
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the rating of this Review
     * @return  This Review's rating
     */
    public int getNumRating() {
        return numRating;
    }

    /**
     * Gets the comments of this Review
     * @return  This Review's comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Changs the username of this Review
     * @param userName  This Review's new username
     */
    //Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Changes the rating of this Review
     * @param numRating This Review's new rating
     */
    public void setNumRating(int numRating) {
        this.numRating = numRating;
    }

    /**
     * Changes the comments of this Review
     * @param comments  This Review's new comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Overrides toString method to store Review details in a specific format
     * @return  a string of Review details
     */
    @Override
    public String toString() {
        String reviewDetails = "";

        reviewDetails = " Username: " + this.getUserName() +
                "\n Rating: " + this.getNumRating() +
                "\n Reviews: " + this.getComments();

        return reviewDetails;
    }
	
}
