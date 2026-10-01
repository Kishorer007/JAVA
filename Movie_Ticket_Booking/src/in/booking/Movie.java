package in.booking;

public class Movie {

	private String movieName;
	private double ticketPrice;
	private double rating;

	public Movie(String movieName, double ticketPrice, double rating) {
		this.movieName = movieName;
		this.ticketPrice = ticketPrice;
		this.rating = rating;
	}

	public String getMovieName() {
		return movieName;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public double getRating() {
		return rating;
	}
}