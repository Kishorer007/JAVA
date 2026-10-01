package in.booking;

public class Booking {

	private String customerName;
	private int row;
	private int[] seats;
	private double amount;

	public Booking(String customerName, int row, int[] seats, double amount) {
		this.customerName = customerName;
		this.row = row;
		this.seats = seats;
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public int getRow() {
		return row;
	}

	public int[] getSeats() {
		return seats;
	}

	public double getAmount() {
		return amount;
	}
}