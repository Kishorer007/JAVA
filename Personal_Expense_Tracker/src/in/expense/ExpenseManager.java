package in.expense;

import java.util.*;

public class ExpenseManager {

	private ArrayList<Expense> expenses = new ArrayList<>();

	public void addExpense(Expense expense) {

		expenses.add(expense);

		System.out.println("Expense added successfully.");
	}

	public void deleteExpense(int id) {

		boolean removed = expenses.removeIf(expense -> expense.getId() == id);

		if (removed) {
			System.out.println("Expense deleted successfully.");
		} else {
			System.out.println("Expense ID not found.");
		}
	}

	public void displayExpenses() {

		if (expenses.isEmpty()) {

			System.out.println("No expenses available.");

			return;
		}

		System.out.println("\n------------- ALL EXPENSES -------------");

		for (Expense expense : expenses) {

			System.out.println("ID          : " + expense.getId() + "\nDescription : " + expense.getDescription()
					+ "\nAmount      : ₹" + expense.getAmount() + "\nCategory    : " + expense.getCategory()
					+ "\nDate        : " + expense.getDate());

			System.out.println("----------------------------------------");
		}
	}

	public void calculateTotal() {

		double total = expenses.stream().mapToDouble(Expense::getAmount).sum();

		System.out.println("Total Expense: ₹" + total);
	}

	public void findHighestExpense() {

		if (expenses.isEmpty()) {

			System.out.println("No expenses available.");

			return;
		}

		Expense highest = expenses.stream().max((e1, e2) -> Double.compare(e1.getAmount(), e2.getAmount()))
				.orElse(null);

		System.out.println("\n--------- HIGHEST EXPENSE ---------");

		System.out.println("Description : " + highest.getDescription());
		System.out.println("Amount      : ₹" + highest.getAmount());
		System.out.println("Category    : " + highest.getCategory());
		System.out.println("Date        : " + highest.getDate());
	}

	public void categoryWiseExpense() {

		HashMap<Category, Double> categoryMap = new HashMap<>();

		for (Expense expense : expenses) {

			categoryMap.put(expense.getCategory(),
					categoryMap.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
		}

		System.out.println("\n------- CATEGORY-WISE EXPENSE -------");

		for (Map.Entry<Category, Double> entry : categoryMap.entrySet()) {

			System.out.println(entry.getKey() + " : ₹" + entry.getValue());
		}
	}
}