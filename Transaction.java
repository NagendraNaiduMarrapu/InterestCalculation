package InterestCalculator;

import java.time.LocalDate;

public class Transaction {
	private int Tid;
	private int accNo;
	private LocalDate Tdate;
	private String Ttype;
	private double Tamount;

	public Transaction(int Tid, int accNo, LocalDate Tdate, String Ttype, double Tamount) {
		this.Tid = Tid;
		this.accNo = accNo;
		this.Tdate = Tdate;
		this.Ttype = Ttype;
		this.Tamount = Tamount;
	}

	public int getTid() {
		return Tid;
	}

	public int getAccNo() {
		return accNo;
	}

	public LocalDate getTdate() {
		return Tdate;
	}

	public String getTtype() {
		return Ttype;
	}

	public double getTamount() {
		return Tamount;
	}

	@Override
	public String toString() {
		return "Transaction [Tid=" + Tid + ", accNo=" + accNo + ", Tdate=" + Tdate + ", Ttype=" + Ttype + ", Tamount="
				+ Tamount + "]";
	}
	
	

}
