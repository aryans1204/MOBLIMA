import java.io.Serializable;

@SuppressWarnings("serial")
public class Review implements Serializable{
	private String userName;
	private int numRating;
	private String comments;
	
	//Constructor
	public Review(String userName, int numRating, String comments) {
		this.userName = userName;
		this.numRating = numRating;
		this.comments = comments;
	}
	
	//Getters
	public String getUserName() {
		return userName;
	}
	public int getNumRating() {
		return numRating;
	}
	public String getComments() {
		return comments;
	}
	
	//Setters
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setNumRating(int numRating) {
		this.numRating = numRating;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		String reviewDetails = "";
		
		reviewDetails = " Username: " + this.getUserName() +
						"\n Rating: " + this.getNumRating() +
						"\n Reviews: " + this.getComments();
		
		return reviewDetails;
	}
	
	
	
	
	
}
