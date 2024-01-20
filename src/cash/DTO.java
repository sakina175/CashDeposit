package cash;

import java.util.HashMap;

public class DTO {
	public int branch_code;
	public String user_id;
	public String password;
	public String brn_name;
	public int brn_cd;
	public int acc_typ;
	public int cust_no;
	public int run_no;
	public int chk_dgt;
	public int batchID;
	public String Day;
	public String Date;
	public String currency_type;
	public String vouch_no;
	public String vouch_type;
	public Double amount;
	
	
	public double getamount() {
		return amount;
	}
	public void setamount(double amount) {
		this.amount = amount;
	}
	public void set_acc(int brn, int actyp, int cust, int run, int chk){
		this.brn_cd = brn;
		this.acc_typ = actyp;
		this.cust_no = cust;
		this.run_no = run;
		this.chk_dgt = chk;
	}
	public String getvouch_no() {
		return vouch_no;
	}
	public void setvouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	public String getvouch_type() {
		return vouch_type;
	}
	public void setvouch_type(String vouch_type) {
		this.vouch_type = vouch_type;
	}
	public int getbranch_code() {
		return branch_code;
	}
	public void setbranch_code(int branch_code) {
		this.branch_code = branch_code;
	}
	public String getDay() {
		return Day;
	}
	public void setDay(String Day) {
		this.Day = Day;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String Date) {
		this.Date = Date;
	}
	public String getbran_name() {
		return brn_name;
	}
	public void setbran_name(String brn_name) {
		this.brn_name = brn_name;
	}
	public String getuser_id() {
		return user_id;
	}
	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public void setbatchID(int id) {
		this.batchID=id;
	}
	public int getbatchID() {
		return batchID;
	}
	public String getCurrency_type() {
		return currency_type;
	}
	public void setCurrency_type(String type) {
		this.currency_type = type;
	}
	public void setaccno(String accountno){
		System.out.print("account o is "+accountno+"with lenght is "+accountno.length());
		this.brn_cd = Integer.parseInt(accountno.substring(0,4));
		this.acc_typ = Integer.parseInt(accountno.substring(5,9));
		this.cust_no = Integer.parseInt(accountno.substring(10,16));
		this.run_no = Integer.parseInt(accountno.substring(17,19));
		this.chk_dgt = Integer.parseInt(accountno.substring(20,21));
	}	
	public HashMap getaccno(){
		HashMap<String,Integer> map=new HashMap<String,Integer>();//Creating HashMap    
		   map.put("brn_cd", this.brn_cd);  //Put elements in Map  
		   map.put("acc_typ", this.acc_typ);
		   map.put("cust_no", this.cust_no);
		   map.put("run_no", this.run_no);
		   map.put("chk_dgt", this.chk_dgt);
		return map;
	}
	public String getaccStr() {
		String accno= String.format("%04d", this.brn_cd) + "-00" +this.acc_typ+ "-" +String.format("%06d", this.cust_no)+ "-" +String.format("%02d", this.run_no)+ "-" +String.format("%01d",this.chk_dgt);
		return accno;
	}
}
