package in.expense;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		ExpenseManager manager = new ExpenseManager();

		int id = 1;

		while (true) {

			System.out.println("\n================================");
			System.out.println("       PERSONAL EXPENSE TRACKER");
			System.out.println("================================");

			System.out.println("1. Add Expense");
			System.out.println("2. Delete Expense");
			System.out.println("3. Display All Expenses");
			System.out.println("4. Calculate Total Expense");
			System.out.println("5. Find Highest Expense");
			System.out.println("6. Category-wise Expense");
			System.out.println("7. Exit");

			System.out.print("\nEnter your choice: ");

			int choice = sc.nextInt();

			switch (choice) {

			case 1:

				sc.nextLine();

				System.out.print("Enter description: ");
				String description = sc.nextLine();

				System.out.print("Enter amount: ");
				double amount = sc.nextDouble();

				System.out.println("\nSelect Category:");

				Category[] categories = Category.values();

				for (int i = 0; i < categories.length; i++) {

					System.out.println((i + 1) + ". " + categories[i]);
				}

				System.out.print("Enter category number: ");
				int categoryChoice = sc.nextInt();

				if (categoryChoice < 1 || categoryChoice > categories.length) {

					System.out.println("Invalid category.");

					break;
				}

				Category category = categories[categoryChoice - 1];

				LocalDate date = LocalDate.now();

				Expense expense = new Expense(id, description, amount, category, date);

				manager.addExpense(expense);

				id++;

				break;

			case 2:

				System.out.print("Enter expense ID to delete: ");

				int deleteId = sc.nextInt();

				manager.deleteExpense(deleteId);

				break;

			case 3:

				manager.displayExpenses();

				break;

			case 4:

				manager.calculateTotal();

				break;

			case 5:

				manager.findHighestExpense();

				break;

			case 6:

				manager.categoryWiseExpense();

				break;

			case 7:

				System.out.println("Thank you for using Expense Tracker.");

				sc.close();

				return;

			default:

				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}