package com.greeshmitha;
import java.util.*;
class Useraccount{
	private int accountnum;
	private int pin;
	private double balance;
	Useraccount(int acc_num,int pin){
		this.accountnum=acc_num;
		this.pin=pin;
		balance=0;
	}
	public int getaccountnum() {
		return this.accountnum;
	}
	public void credit(double amount) {
		this.balance+=amount;
	}
	public void debit(double amount) {
		this.balance-=amount;
	}
	public double getaccbalance() {
		return this.balance;
	}
	public boolean ispinvalid(int pin) {
		if(this.pin==pin)
			return true;
		else
			return false;
	}
	
}
//Taken account numbers from 00000 to 50000 and for pin numbers add+1 to account number
class Database{
	private Useraccount[] data;
	Database(){
		data=new Useraccount[50001];
		for(int i=0;i<50001;i++) {
			this.data[i]=new Useraccount(i,i+1);
		}
	}
	private Useraccount searchaccount(int num) {
		for(Useraccount i:data) {
			if(i.getaccountnum()==num)
				return i;
		}
		return null;
	}
	public void credit(int num,double amount) {
		searchaccount(num).credit(amount);
	}
	public void debit(int num,double amount) {
		searchaccount(num).debit(amount);
	}
	public double getbalance(int num) {
		return searchaccount(num).getaccbalance();
	}
	public boolean isuservalid(int num,int pin) {
		if(searchaccount(num)!=null) {
			return searchaccount(num).ispinvalid(pin);
		}
		else
			return false;
	}
	
}
class Balanceinquiry{
	private int accnum;
	private Database data;
	public Balanceinquiry(int num,Database bankdata) {
		this.accnum=num;
		this.data=bankdata;
	}
	void execute() {
		double balance=data.getbalance(accnum);
		System.out.println("Your account balance is:");
		System.out.println(balance);
	}
}
//intially we are takin bank is loaded with 500 rupee 100notes
class CashDispenser{
	private static final int COUNT=5000;
	private int count;
	CashDispenser(){
		count=COUNT;
	}
	public boolean iscashavailable(int amount) {
		int cnt=amount/100;
		if(count>=cnt) {
			return true;
		}
		else 
			return false;
			
	}
	public void cashdispensed(int amount) {
	   int cnt=amount/100;
	   count-=cnt;	
	}
}
class Withdrawal{
	Scanner sc=new Scanner(System.in);
	private double balance;
	private int accnum;
	private Database data;
	private CashDispenser cd;
	Withdrawal(int num,Database bankdata,CashDispenser c){
		this.accnum=num;
		this.data=bankdata;
		this.cd=c;
	}
	public void execute() {
		boolean cashdispensed=false;
		while(!cashdispensed) {
			System.out.println("Choose your withdrawal amount:");
			int amount=sc.nextInt();
			int opt;
			System.out.println("Enter 0 if you want to cancel withdrwal OR Enter anynumber to continue:");
			opt=sc.nextInt();
			if(opt!=0) {
				balance=data.getbalance(accnum);
				if(amount<=balance) {
					if(cd.iscashavailable(amount)) {
						data.debit(accnum, amount);
						cashdispensed=true;
						cd.cashdispensed(amount);
						System.out.println("Cash will be dispensed now..Please collect it..");
					}
					else
						System.out.println("Cash is not available in atm");
				}
				else
					System.out.println("Cash is not available in your account..");
			}
			else {
				System.out.println("Cancelling transaction..");
				break;
			}	
		}
	}
}
class Deposit{
	Scanner sc=new Scanner(System.in);
	private int accnum;
	private Database data;
	Deposit(int num,Database bankdata){
		this.accnum=num;
		this.data=bankdata;
	}
	public void execute() {
	double amount;
	System.out.println("Enter ur amount OR enter 0 if you want to cancel deposit:");
	amount=sc.nextDouble();
    if(amount!=0) {
    	System.out.println("Please insert the envolope having amount "+ amount+".");
    	data.credit(accnum, amount);
    	System.out.println("Your envolope has been received");
      }
    else {
    	System.out.println("Cancelling Transaction..");
      }
   }
}


public class ATM_CS {
   public static void main(String[] args) {
	   Scanner sc=new Scanner(System.in);
	   boolean isUservalid=false;
	   CashDispenser cash=new CashDispenser();
	   Database data=new Database();
	   int present_acc=0;
	   while(true) {
		   while(!isUservalid) {
			   int pin,acc_num;
			   System.out.println("Welcome!!!");
			   System.out.println("Enter your account number:");
			   acc_num=sc.nextInt();
			   System.out.println("Enter PIN:");
			   pin=sc.nextInt();
			   isUservalid=data.isuservalid(acc_num, pin);
			   if(isUservalid) {
				   present_acc=acc_num;
			   }
			   else
				   System.out.println("Invalid credentials!!.Please try again..");
			   
		   }
		   System.out.println("Choose ur option:");
		   System.out.println("1.Balance enquiry 2.Withdrawal 3.Deposit (enter either 1/2/3)");
		   System.out.println("Enter 0 if you want to exit");
		   int opt=sc.nextInt();
		   switch(opt) {
		   case 1:
			   Balanceinquiry temp=new Balanceinquiry(present_acc,data);
			   temp.execute();
			   break;
		   case 2:
			   Withdrawal temp1=new Withdrawal(present_acc,data,cash);
			   temp1.execute();
			   break;
		   case 3:
			   Deposit temp2=new Deposit(present_acc,data);
			   temp2.execute();
			   break;  
		   }
		   if(opt==0) {
			   System.out.println("Leaving ATM...Thanks for visiting!!");
			   break;
		   }
	   }
	   
	   
	   
   }
}
