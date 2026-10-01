package in.booking;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Movie movie = new Movie("Leo", 200, 8.5);

		boolean[][] seats = new boolean[5][5];

		Booking[] bookings = new Booking[25];

		int bookingCount = 0;

		while (true) {

			System.out.println("\n======================================");
			System.out.println("       MOVIE TICKET BOOKING SYSTEM");
			System.out.println("======================================");

			System.out.println("Movie  : " + movie.getMovieName());
			System.out.println("Rating : " + movie.getRating());
			System.out.println("Price  : ₹" + movie.getTicketPrice());

			System.out.println("\n1. Show Available Seats");
			System.out.println("2. Book Ticket");
			System.out.println("3. Cancel Ticket");
			System.out.println("4. Exit");

			System.out.print("\nEnter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {

			// SHOW AVAILABLE SEATS

			case 1:

				System.out.println("\n----------- SEAT LAYOUT -----------");

				for (int i = 0; i < seats.length; i++) {

					System.out.print("Row " + (i + 1) + ": ");

					for (int j = 0; j < seats[i].length; j++) {

						if (seats[i][j]) {
							System.out.print("X ");
						} else {
							System.out.print((j + 1) + " ");
						}
					}

					System.out.println();
				}

				System.out.println("\n X = Booked");
				System.out.println("[Number] = Available");

				break;

			// BOOK TICKET

			case 2:

				sc.nextLine();

				System.out.print("\nEnter customer name: ");
				String customerName = sc.nextLine();

				System.out.print("Enter row number (1-5): ");
				int row = sc.nextInt();

				// Check row
				if (row < 1 || row > 5) {

					System.out.println("Invalid row number.");

					break;
				}

				System.out.print("How many seats do you want to book? ");
				int numberOfSeats = sc.nextInt();

				// Check number of seats
				if (numberOfSeats < 1 || numberOfSeats > 5) {

					System.out.println("You can book between 1 and 5 seats.");

					break;
				}

				int[] selectedSeats = new int[numberOfSeats];

				boolean validBooking = true;

				// Select seats

				for (int i = 0; i < numberOfSeats; i++) {

					System.out.print("Enter seat " + (i + 1) + " (1-5): ");

					int seatNumber = sc.nextInt();

					selectedSeats[i] = seatNumber;

					// Check seat number
					if (seatNumber < 1 || seatNumber > 5) {

						System.out.println("Invalid seat number.");

						validBooking = false;

						break;
					}

					// Check already booked
					if (seats[row - 1][seatNumber - 1]) {

						System.out.println("Seat " + seatNumber + " is already booked.");

						validBooking = false;

						break;
					}

					// Check duplicate seat selection

					for (int j = 0; j < i; j++) {

						if (selectedSeats[j] == seatNumber) {

							System.out.println("You selected seat " + seatNumber + " more than once.");

							validBooking = false;

							break;
						}
					}

					if (!validBooking) {
						break;
					}
				}

				// If any problem occurred
				if (!validBooking) {

					System.out.println("Booking cancelled.");

					break;
				}

				// WEEKEND PRICING

				double pricePerSeat = movie.getTicketPrice();

				DayOfWeek today = LocalDate.now().getDayOfWeek();

				if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) {

					pricePerSeat = pricePerSeat + 50;

					System.out.println("\nWeekend pricing applied.");
				}

				// TOTAL PRICE

				double totalPrice = pricePerSeat * numberOfSeats;

				// DISCOUNT COUPON

				sc.nextLine();

				System.out.print("Enter coupon code (or type NONE): ");

				String coupon = sc.nextLine();

				double discount = 0;

				if (coupon.equalsIgnoreCase("MOVIE10")) {

					discount = totalPrice * 0.10;

					System.out.println("10% discount applied.");

				} else if (!coupon.equalsIgnoreCase("NONE")) {

					System.out.println("Invalid coupon. No discount applied.");
				}

				double finalAmount = totalPrice - discount;

				// BOOK ALL SELECTED SEATS

				for (int i = 0; i < numberOfSeats; i++) {

					int seatNumber = selectedSeats[i];

					seats[row - 1][seatNumber - 1] = true;
				}

				// Store booking
				bookings[bookingCount] = new Booking(customerName, row, selectedSeats, finalAmount);

				bookingCount++;

				// DISPLAY BOOKING DETAILS

				System.out.println("\n================================");
				System.out.println("       BOOKING SUCCESSFUL");
				System.out.println("================================");

				System.out.println("Customer : " + customerName);
				System.out.println("Movie    : " + movie.getMovieName());
				System.out.println("Rating   : " + movie.getRating());
				System.out.println("Row      : " + row);

				System.out.print("Seats    : ");

				for (int i = 0; i < numberOfSeats; i++) {

					System.out.print(selectedSeats[i] + " ");
				}

				System.out.println();

				System.out.println("Price/Seat : ₹" + pricePerSeat);
				System.out.println("Total      : ₹" + totalPrice);
				System.out.println("Discount   : ₹" + discount);
				System.out.println("Final Amount: ₹" + finalAmount);

				break;

			// 3. CANCEL TICKET

			case 3:

				System.out.print("\nEnter row number: ");
				int cancelRow = sc.nextInt();

				System.out.print("Enter seat number: ");
				int cancelSeat = sc.nextInt();

				// Check valid row and seat
				if (cancelRow < 1 || cancelRow > 5 || cancelSeat < 1 || cancelSeat > 5) {

					System.out.println("Invalid row or seat.");

					break;
				}

				// Check whether seat is booked
				if (!seats[cancelRow - 1][cancelSeat - 1]) {

					System.out.println("This seat is not booked.");

					break;
				}

				// Make seat available
				seats[cancelRow - 1][cancelSeat - 1] = false;

				// Find booking
				for (int i = 0; i < bookingCount; i++) {

					if (bookings[i] != null && bookings[i].getRow() == cancelRow) {

						int[] bookedSeats = bookings[i].getSeats();

						for (int j = 0; j < bookedSeats.length; j++) {

							if (bookedSeats[j] == cancelSeat) {

								bookedSeats[j] = -1;

								break;
							}
						}
					}
				}

				System.out.println("Seat " + cancelSeat + " in Row " + cancelRow + " cancelled successfully.");

				break;

			// 4. EXIT

			case 4:

				System.out.println("\nThank you for using Movie Ticket Booking System!");

				sc.close();

				return;

			default:

				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}