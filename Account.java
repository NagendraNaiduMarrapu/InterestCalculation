package InterestCalculator;

import java.time.LocalDate;
import java.util.List;

public class Account {
	List<Account> list;
	int accNo;
	String accType;
	LocalDate openDate;
	double balance;

	public Account(int accNo, String accType, LocalDate openDate, double balance) {
		this.accNo = accNo;
		this.accType = accType;
		this.openDate = openDate;
		this.balance = balance;
	}

	public int getAccNo() {
		return accNo;
	}

	public String getAccType() {
		return accType;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public double getBalance() {
		return balance;
	}



}


