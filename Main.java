package InterestCalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	private static ArrayList<Transaction> lines;
	private static ArrayList<Transaction> accountTrans;
	static List<Account> list;

	public static void getAllTheAccounts() {
		try {
			File file = new File("D:\\account aggregator workspace\\MySpace\\InterestCalculator\\Account.txt");
			Reader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			list = new ArrayList<>();

			while ((line = bufferedReader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				int a = Integer.parseInt(tokenizer.nextToken());
				String b = tokenizer.nextToken();
				LocalDate c = LocalDate.parse(tokenizer.nextToken(), DateTimeFormatter.ISO_LOCAL_DATE);
				double d = Double.parseDouble(tokenizer.nextToken());
				list.add(new Account(a, b, c, d));
			}

			bufferedReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static ArrayList<Transaction> readTransactionLines() {
		try {
			File file = new File("D:\\account aggregator workspace\\MySpace\\InterestCalculator\\Transaction.txt");
			Reader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			lines = new ArrayList<>();

			while ((line = bufferedReader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				int a = Integer.parseInt(tokenizer.nextToken());
				int b = Integer.parseInt(tokenizer.nextToken());
				LocalDate c = LocalDate.parse(tokenizer.nextToken(), DateTimeFormatter.ISO_LOCAL_DATE);
				String d = tokenizer.nextToken();
				double e = Double.parseDouble(tokenizer.nextToken());

				lines.add(new Transaction(a, b, c, d, e));
			}

			bufferedReader.close();

		} catch (IOException ex) {
			System.out.println(ex);
		}
		return lines;
	}

	public static ArrayList<Transaction> getAccounttransactions(Account a, ArrayList<Transaction> lines) {
		accountTrans = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			if (a.getAccNo() == lines.get(i).getAccNo()) {
				accountTrans.add(lines.get(i));
			}
		}
		return accountTrans;

	}

	public static double getBalanceAsOnDate(Account a, LocalDate givendate, ArrayList<Transaction> newt) {
		double amt = a.getBalance();
		ArrayList<Transaction> al = getAccounttransactions(a, newt);
		Collections.sort(al, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t2.getTdate().compareTo(t1.getTdate());

			}
		});

		for (Transaction t : al) {

			if (t.getTdate().compareTo(givendate) >= 0) {
				if (t.getTtype().equals("W")) {
					amt = amt + t.getTamount();

				} else {
					amt = amt - t.getTamount();
				}
			}

		}

		return amt;

	}

	public static double getMinBalance(Account a, int month, int year) {
		ArrayList<Transaction> at = new ArrayList<Transaction>();
		at = getAccounttransactions(a, lines);
		double min1 = getBalanceAsOnDate(a, LocalDate.of(year, month, 10), at);
		for (Transaction t1 : at) {

			int currentMonth = t1.getTdate().getMonthValue();
			if (month == currentMonth && t1.getTdate().getYear() == year && t1.getTdate().getDayOfMonth() >= 10) {
				double min2 = getBalanceAsOnDate(a, t1.getTdate(), at);
				min1 = Math.min(min1, min2);

			}
		}

		return min1;
	}

	public static double CalInterest(Account a) {
		Period p = Period.between(a.getOpenDate(), LocalDate.now());
		int months = (int) p.toTotalMonths();
		System.out.println(months);
		int currentmonth = LocalDate.now().getMonthValue();
		int Currentyear = LocalDate.now().getYear();
		if (currentmonth == 1) {
			currentmonth = 12;
			Currentyear -= 1;
		} else {
			currentmonth -= 1;
		}

		double interest = 0;
		if (a.accType.equals("cd")) {
			return 0;
		} else if (a.accType.equals("sb") && a.getOpenDate().getDayOfMonth() >= 10
				&& LocalDate.now().getMonthValue() == a.getOpenDate().getMonthValue()) {

			return 0;
		}

		else if (a.accType.equals("sb") && months < 6 && a.getOpenDate().getDayOfMonth() >= 10) {

			for (int i = 0; i < months - 1; i++) {
				if (currentmonth >= 1) {

					double CurrentMonthMinBalance = getMinBalance(a, currentmonth, Currentyear);
					System.out.println(CurrentMonthMinBalance);
					currentmonth = currentmonth - 1;
					double CurrentMonthlyInterest = ((CurrentMonthMinBalance) * (4.5 / 12)) / 1200;
					interest += CurrentMonthlyInterest;

				} else {
					double CurrentMonthMinBalance = getMinBalance(a, currentmonth, Currentyear);
					System.out.println(CurrentMonthMinBalance);
					currentmonth = 12;
					Currentyear -= 1;
					double CurrentMonthlyInterest = ((CurrentMonthMinBalance) * (4.5 / 12)) / 1200;
					interest += CurrentMonthlyInterest;

				}

			}
		}

		else if (a.accType.equals("sb") && months < 6 && a.getOpenDate().getDayOfMonth() < 10) {

			for (int i = 0; i < months; i++) {

				if (currentmonth >= 1) {

					double CurrentMonthMinBalance = getMinBalance(a, currentmonth, Currentyear);
					System.out.println(CurrentMonthMinBalance);
					currentmonth = currentmonth - 1;
					double CurrentMonthlyInterest = ((CurrentMonthMinBalance) * (4.5 / 12)) / 1200;
					interest += CurrentMonthlyInterest;

				}
			}
		}

		else if (a.accType.equals("sb") && months >= 6) {
			for (int i = 0; i < 6; i++) {
				if (currentmonth >= 1) {

					double CurrentMonthMinBalance = getMinBalance(a, currentmonth, Currentyear);
					System.out.println(CurrentMonthMinBalance);
					currentmonth = currentmonth - 1;
					double CurrentMonthlyInterest = ((CurrentMonthMinBalance) * (4.5 / 12)) / 1200;
					interest += CurrentMonthlyInterest;

				} else {
					double CurrentMonthMinBalance = getMinBalance(a, currentmonth, Currentyear);
					System.out.println(CurrentMonthMinBalance);
					currentmonth = 12;
					Currentyear -= 1;
					double CurrentMonthlyInterest = ((CurrentMonthMinBalance) * (4.5 / 12)) / 1200;
					interest += CurrentMonthlyInterest;

				}
			}
		}
		System.out.println(interest);
		return interest;
	}

	public static void main(String[] args) {
		getAllTheAccounts();
		readTransactionLines();

		getAccounttransactions(list.get(0), readTransactionLines());

		CalInterest(list.get(0));

	}
}
