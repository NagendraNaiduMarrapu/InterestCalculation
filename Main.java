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
	private ArrayList<Transaction> lines;
	private ArrayList<Transaction> accountTrans;
	List<Account> list;
	private double interest;

	public void getAllTheAccounts() {
		try {
			File file = new File("Account.txt");
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

	public ArrayList<Transaction> readTransactionLines() {
		try {
			File file = new File("C:\\Users\\vamsi pilla\\eclipse-workspace\\myprojects\\InterestCalculator\\Transaction.txt");
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

	public ArrayList<Transaction> getAccounttransactions(Account a, ArrayList<Transaction> lines) {
		accountTrans =new ArrayList<>();
		for(int i=0;i<lines.size();i++) {
			if(a.getAccNo() == lines.get(i).getAccNo()) {
				accountTrans.add(lines.get(i));
			}
		}
		return accountTrans;
		
			
	
	}
	
	
	public double getBalanceAsOnDate(Account a,LocalDate givendate,ArrayList<Transaction> newt) {
		double amt = a.getBalance() ;
		ArrayList<Transaction> al = getAccounttransactions(a, newt);
		Collections.sort(al,new Comparator<Transaction>() {
			public int compare(Transaction t1,Transaction t2) {
				return t2.getTdate().compareTo(t1.getTdate());
				
			}
		});
		
		for(Transaction t : al) {
		
			System.out.println(t.toString());
			if(t.getTdate().compareTo(givendate)>=0) {
			if(t.getTtype().equals("W")) {
				amt = amt+ t.getTamount();
				
			}
			else {
				amt = amt - t.getTamount();
			}
			}
			
		}
		
		return amt;
		
	}
	
	
	public double CalInterest(Account a) {
	 Period p=Period.between(LocalDate.now(),a.getOpenDate());
	 int months=(int) p.toTotalMonths();
	 if(a.accType == "sb" && a.getOpenDate().getDayOfMonth()>=10 && LocalDate.now().getMonthValue()==a.getOpenDate().getMonthValue()){
			return 0;		
	}
     
     else if(a.accType == "sb" && months<6 && a.getOpenDate().getDayOfMonth()>=10) {
    	 for(int i=0;i<months-1;i++) {
    		 
    	 }
     }
	}
	
	
	public double getMinBalance(Account a,int month,int year) {
		ArrayList<Transaction> at = new ArrayList<Transaction>();
		at = getAccounttransactions(a, lines);
		double min = getBalanceAsOnDate(a,LocalDate.of(year, month, 10),at) ;
		
		for(Transaction t1:at) {
			
			int currentMonth = t1.getTdate().getMonthValue();
			if(month == currentMonth && t1.getTdate().getYear() == year && t1.getTdate().getDayOfMonth()>=10) {
				double min2 = getBalanceAsOnDate(a,t1.getTdate(),at);
				min = Math.min(min,min2);
			}
		}
		System.out.println(min);
		return min;
	}

	public static void main(String[] args) {
		Main m = new Main();
		ArrayList<Transaction> alt = new ArrayList<>();
		Account a1 = new Account(101,"sb",LocalDate.of(2023,02,9),50000);
		alt = m.readTransactionLines();
		
		System.out.println(m.getBalanceAsOnDate(a1,LocalDate.of(2023,03,05),alt));
		m.getMinBalance(a1, 4 , 2023);
	}

}


