package cash;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.hssf.record.formula.functions.Int;

public class Db_Operation {
	private static final Logger logger=Logger_Manager.getLogger(Db_Operation.class);
	private static final String String = null;
	public Connection lcl_conn = null;
	public Statement  lcl_stmt =null;
	public Db_Operation(){
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			this.lcl_conn = java.sql.DriverManager.getConnection("jdbc:db2://10.51.41.100:50000/SM27392", "db2admin", "admin123/?");
			this.lcl_stmt = this.lcl_conn.createStatement();
			
		}catch(Exception e){
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}	
	}
	
	public String Login(int BranchCode,String UserId,String pwd){
		ResultSet rs;
		ResultSet rsbrn;
		String is_active="";
		String user_id="";
		Map<String, String> user_data = new HashMap<>();
		try {
			rs = this.lcl_stmt.executeQuery("select * from SM27392.Users_tl where User_ID = '"+UserId+"' and Password= '"+pwd+"' and BRN_CD='"+BranchCode+"'");
			if(!rs.next()){
				return "false";
			}
			else {
				do{
					is_active= rs.getString("is_active");
					user_id= rs.getString("User_ID");
					if(is_active.equals("N")){
						return is_active;
					}
					else{
						user_data.put("UserId", user_id);
						java.sql.Timestamp datetime=new java.sql.Timestamp(System.currentTimeMillis());
						java.util.Date date= new java.util.Date();
						String Day= date.toString().substring(0,3);
						int dtoDateYr= 1900+date.getYear();
						String year= Integer.toString(dtoDateYr);
						String FDate = date.toString().substring(4,10);
						String dt= rs.getString("LAST_SIGNON_DT");
						if(dt==null){
							dt="";}
							this.lcl_stmt.executeUpdate("update SM27392.Users_tl set LAST_SIGNON_DT='"+datetime+"' where User_ID='"+ rs.getString("User_ID")+"' ");
						
						return dt;
					}		
				}while(rs.next());
			}
			} catch (SQLException e) {
				logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
				return "false";
		}
	}
	public HashMap <String, ArrayList> getBatchHistory(String userid,int BatchId){
		ResultSet rs;
		String Voucherno = null;
		String Referenceno = null;
		ArrayList<Object> transactions = new ArrayList();
		ArrayList<Object> vouchers = new ArrayList();
		ArrayList<Object> debits = new ArrayList();
		ArrayList<Object> batch_id = new ArrayList();
		HashMap<String,ArrayList> trans_data=new HashMap<String,ArrayList>();
		try {
			rs=this.lcl_stmt.executeQuery("Select trans_id,batch_id from SM27392.Batch_history_tl where user_id ='"+userid+"' and  batch_id='"+BatchId+"'");
			while(rs.next()){
//				vouchers.add(rs.getString("voucher_no"));
				transactions.add(rs.getString("trans_id"));
				batch_id.add(rs.getString("batch_id"));
//				debits.add(rs.getString("dr"));
//				trans_data.put("Vouchers", vouchers);
				trans_data.put("Transactions", transactions);
				trans_data.put("Batch", batch_id);
//				trans_data.put("Debits", debits);
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return trans_data;
	}
	public boolean getTransStatus(String voucher_cd) {
		ResultSet rs;
		try {
			System.out.println("select is_authorized from SM27392.ledgers_tl WHERE voucher_no='"+voucher_cd+"'");
			rs = this.lcl_stmt.executeQuery("select is_authorized from SM27392.ledgers_tl WHERE voucher_no='"+voucher_cd+"'");
			if(rs.next()){
		    String status= rs.getString("is_authorized");
		    System.out.println("status is "+status);
		    if(status.equals("Y"))
		    	return true;
		    }}
		
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return false;
	}
	public String GetAcc(String acc_no,int brn_cd){
		ResultSet rs;
		
//		ArrayList<Object> data = new ArrayList();
		try {
//			System.out.print(brn_cd+"here in db with elnght "+acc_no.length()+" data is "+Integer.parseInt(acc_no)+"type is ");
			rs = this.lcl_stmt.executeQuery("select * from SM27392.CUST_ACC_TL WHERE cust_no='"+Integer.parseInt(acc_no)+"' and brn_cd='"+brn_cd+"'");
			while(rs.next()){
		    String currency_cd4= rs.getString("BRN_CD");
			String currency_cd= rs.getString("ACC_TYP");
			String currency_cd1= rs.getString("CUST_NO");
			String currency_cd2= rs.getString("RUN_NO");
			String currency_name= rs.getString("CHK_DGT");
			System.out.print("data in database is "+String.format("%04d",Integer.parseInt(currency_cd4)) +"-00"+currency_cd + "-"+currency_cd1+"-"+currency_cd2+"-"+currency_name);
			String currency = String.format("%04d",Integer.parseInt(currency_cd4)) +"-00"+currency_cd + "-"+currency_cd1+"-"+currency_cd2+"-"+currency_name;
			if(currency_cd1.equals(acc_no)){
				return currency;
			}
			}
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}
	public ArrayList GetAccs(String acc_no,String currency_type,int brn_cd){
		ResultSet rs;
		String accno=null;
		ArrayList<Object> data = new ArrayList();
		try {
			System.out.print("select * from SM27392.CUST_ACC_TL WHERE currency_cd='"+currency_type+"' and cust_no='"+Integer.parseInt(acc_no)+"' and brn_cd='"+brn_cd+"'");
			rs = this.lcl_stmt.executeQuery("select * from SM27392.CUST_ACC_TL WHERE currency_cd='"+currency_type+"' and cust_no='"+Integer.parseInt(acc_no)+"' and brn_cd='"+brn_cd+"'");
			if(rs.next()){
				 int currency_cd4=Integer.parseInt( rs.getString("BRN_CD"));
					String currency_cd= rs.getString("ACC_TYP");
					String currency_cd1= rs.getString("CUST_NO");
					String currency_cd2= rs.getString("RUN_NO");
					String currency_name= rs.getString("CHK_DGT");
					String currency = String.format("%04d",  currency_cd4)+"-00"+currency_cd + "-"+currency_cd1+"-"+currency_cd2+"-"+currency_name;
					System.out.println("data is in acc no gets"+currency+"and other is "+acc_no);
					data.add(currency);
				while(rs.next()){
				    currency_cd4=Integer.parseInt( rs.getString("BRN_CD"));
					currency_cd= rs.getString("ACC_TYP");
					currency_cd1= rs.getString("CUST_NO");
					currency_cd2= rs.getString("RUN_NO");
					currency_name= rs.getString("CHK_DGT");
					currency = String.format("%04d",  currency_cd4)+"-00"+currency_cd + "-"+currency_cd1+"-"+currency_cd2+"-"+currency_name;
					System.out.println("data is in acc no gets"+currency+"and other is "+acc_no);
					data.add(currency);
				}

				return data;
				
			}
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			
		}
		return null;
	}
	public String GetUserBranch(int brn_cd){
		ResultSet rs;
		String is_filer="";
		try {
			rs = this.lcl_stmt.executeQuery("select brn_name from SM27392.branchs_tl where brn_cd= '"+ brn_cd +"' ");
			while(rs.next()){
			is_filer= rs.getString("brn_name").toString();
			
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return is_filer;
	}
	

	public int GetBranchCode(String brn_name){
		ResultSet rs;
		int brn_cd=0;
		try {
			System.out.println("select brn_cd from SM27392.branchs_tl where brn_name= '"+ brn_name +"' ");
			rs = this.lcl_stmt.executeQuery("select brn_cd from SM27392.branchs_tl where brn_name= '"+ brn_name +"' ");
			while(rs.next()){
			brn_cd = Integer.parseInt(rs.getString("brn_cd"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return brn_cd;
	}
	public String GetBranchName(int brn_cd){
		ResultSet rs;
		String brn_name="",res="";
		try {
			System.out.println("select brn_name from SM27392.branchs_tl where brn_cd= '"+ brn_cd +"' ");
			rs = this.lcl_stmt.executeQuery("select brn_name from SM27392.branchs_tl where brn_cd= '"+ brn_cd +"'");
			if(rs.next()){
			brn_name = rs.getString("brn_name");
			}
			res=brn_name+" - "+String.format("%04d",  brn_cd);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return res;
	}
//	public int 
	
	public String Authentication(String userid, String password){
		ResultSet rs;
		String is_active="";
		String user_id="";
		try {
			rs = this.lcl_stmt.executeQuery("select * from SM27392.users_tl where user_id = '"+userid+"' and password = '"+ password+"'");
			if(!rs.next()){
				return "false";
			}
			else {
				do{
					is_active= rs.getString("is_active");
					user_id= rs.getString("User_ID");
					if(is_active.equals("N")){
						return is_active;
					}
					else{
						java.sql.Timestamp datetime=new java.sql.Timestamp(System.currentTimeMillis());
						String dt= rs.getString("LAST_SIGNON_DT");
						this.lcl_stmt.executeUpdate("update Users_tl set LAST_SIGNON_DT='"+datetime+"' where User_ID='"+ rs.getString("User_ID")+"' ");
						return dt;
					}		 
				}while(rs.next());
			}
			} catch (SQLException e) {
				logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
				
		}
		return "false";
	}
	
	public String Batch_Authentication(String userid, String password,int brn_cd){
		ResultSet rs;
		String user_id="";
		
		try {
			System.out.print(userid+" 13qhdu "+password + " iwj"+brn_cd);
			rs = this.lcl_stmt.executeQuery("select * from SM27392.Users_tl where User_ID = '"+userid+"' and Password= '"+password+"' and BRN_CD='"+brn_cd+"'");
			if(!rs.next()){
//				System.out.println("CANNOT DO THAT");
				return "false";
			}
			else {
					user_id= rs.getString("User_ID");
					int rsbatch=0;
					rsbatch = this.lcl_stmt.executeUpdate("insert into SM27392.batch_tl(USER_ID,STATUS_ID) values('"+user_id+"',1)");
//					System.out.println("sucessssss "+rsbatch);
					if(rsbatch==1){
						ResultSet rsBatchNo = this.lcl_stmt.executeQuery("select max(Batch_ID) as Batch_ID from SM27392.batch_tl where User_ID = '"+userid+"' and Status_id=1");
						if(!rsBatchNo.next()){
							return "UNAUTHORIZE";
						}
						else{
							String batchno= rsBatchNo.getString("Batch_ID");
//							System.out.println("IN ELSE eee "+batchno);
							return batchno;
						}
					}
					return "false";
			}
			} catch (SQLException e) {
				logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
				
		}
		return "false";
	}

	public String batchClose(int batchid,String user_id) {
		int rs;
		try {
			System.out.println("sucessssss "+batchid+ "with user id"+user_id);
			rs=this.lcl_stmt.executeUpdate("update SM27392.batch_tl set STATUS_ID=0 where User_ID='"+user_id+"' and batch_id='"+batchid+"'");
			if(rs>0){
				return "true";
			}
			else{
				return "false";
			}}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			}
		
		return null;
	}
	int getlimit(String userid){
		ResultSet rs;
		try {
			System.out.println("select activity_name from SM27392.ACCESS_RIGHTS_TL ar ,SM27392.Module_tl m, SM27392.ACTIVITY_TL a where  ar.User_ID='"+userid+"'  and m.module_cd=ar.module_cd and ar.activity_id= a.activity_id");
			rs=this.lcl_stmt.executeQuery("select activity_name from SM27392.ACCESS_RIGHTS_TL ar ,SM27392.Module_tl m, SM27392.ACTIVITY_TL a where  ar.User_ID='"+userid+"'  and m.module_cd=ar.module_cd and ar.activity_id= a.activity_id");
			if(rs.next()){
					int res=Integer.parseInt(rs.getString("activity_name"));
				return res;
			}
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			}
		
		return -1;
	}
	public ArrayList AccessRights(String UserId){
		ResultSet rs;
		int limit=0;
		try {
			limit=getlimit(UserId);
			ArrayList<Object> data = new ArrayList();
			rs = this.lcl_stmt.executeQuery("select m.module_name from SM27392.ACCESS_RIGHTS_TL ar inner join SM27392.Module_tl m on m.module_cd=ar.module_cd where  ar.User_ID='"+UserId+"'  ");
			while(rs.next()){
				System.out.println(rs.getString("module_name"));
			data.add(rs.getString("module_name"));
				}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			
		}
		return null;
	}
	public int getActvitycd(String UserId){
		ResultSet rs;
		int code=0;
		try {
			System.out.println("select m.module_cd from SM27392.ACCESS_RIGHTS_TL ar inner join SM27392.Module_tl m on m.module_cd=ar.module_cd where  ar.User_ID='"+UserId+"'  ");
			rs = this.lcl_stmt.executeQuery("select m.module_cd from SM27392.ACCESS_RIGHTS_TL ar inner join SM27392.Module_tl m on m.module_cd=ar.module_cd where  ar.User_ID='"+UserId+"'  ");
			if(rs.next()){
				System.out.println(rs.getString("module_cd"));
				code=Integer.parseInt(rs.getString("module_cd"));
				return code;
				}
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			
		}
		return -1;
	}
	public String GetDayDate(int brn_cd){
		ResultSet rs;
		String branch_day = null;		
		String branch_date = null;

		try {
			rs = this.lcl_stmt.executeQuery("select * from SM27392.date_tl where brn_cd='"+brn_cd+"'");
			if(rs.next()){
			branch_day= rs.getString("BRN_DAY");
			branch_date = rs.getString("BRN_DATE");
			String result = branch_date+" - "+branch_day;
			return result;
			}
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			
		}
		return null;
	}
	public String GetDate(int brn_cd){
		ResultSet rs;
		String branch_day = null;		
		String branch_date = null;

		try {
			rs = this.lcl_stmt.executeQuery("select BRN_DATE from SM27392.date_tl where brn_cd='"+brn_cd+"'");
			while(rs.next()){
			branch_date = rs.getString("BRN_DATE");
			return branch_date;
			}
			return null;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			
		}
		return null;
	}
	public ArrayList GetBranches(int brn_cd){
		ResultSet rs;
		ArrayList<Object> data = new ArrayList();
		String branch = null;
		try {
//			System.out.println("select brn_cd,brn_name from SM27392.branchs_tl where brn_cd = (select brn_cd from SM27392.users_tl where user_id = '"+user_id+"')");
			rs = this.lcl_stmt.executeQuery("select brn_cd,brn_name from SM27392.branchs_tl where brn_cd <> '"+brn_cd+"' ");
			while(rs.next()){
			int branch_code=Integer.parseInt( rs.getString("brn_cd"));
			String branch_name = rs.getString("brn_name");
			branch =String.format("%04d", branch_code) +" - "+branch_name;
			data.add(branch);
				}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public ArrayList GetPurposes(){
		ResultSet rs;
		ArrayList<Object> data = new ArrayList();
		try {
			rs = this.lcl_stmt.executeQuery("select purpose_desc from SM27392.purpose_tl");
			while(rs.next()){
			String purposedesc= rs.getString("purpose_desc");
			data.add(purposedesc);
				}
			System.out.print("swcc "+data);
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public int GetIdentificationID(String IDdesc){
		ResultSet rs;
		int idendesc=0;
		try {
			
			rs = this.lcl_stmt.executeQuery("select id_type from SM27392.ID_type_tl where TYPE_NAME='"+IDdesc+"'");
			if(rs.next()){
			idendesc= Integer.parseInt(rs.getString("id_type"));
				}
			System.out.print("data base have "+idendesc);
			return idendesc;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}

		return -1;
	}
	
	
	public ArrayList GetIdentifications(){
		ResultSet rs;
		ArrayList<Object> data = new ArrayList();
		try {
			rs = this.lcl_stmt.executeQuery("select type_name from SM27392.ID_type_tl");
			while(rs.next()){
			String idendesc= rs.getString("type_name");
			data.add(idendesc);
				}
			System.out.print("data base have "+data);
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public ArrayList GetCurrencies(){
		ResultSet rs;
		ArrayList<Object> data = new ArrayList();
		try {
			rs = this.lcl_stmt.executeQuery("select * from SM27392.currency_tl");
			while(rs.next()){
			String currency_cd= rs.getString("currency_cd");
			String currency_name= rs.getString("currency_name");
			String currency = currency_cd+" - "+currency_name;
			data.add(currency);
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	public int GetAccCurrency(int brncd, int acctyp,int custno, int runno, int chkdgt){
		ResultSet rs;
		int code=0;
		try {
			rs = this.lcl_stmt.executeQuery("select currency_cd from SM27392.cust_acc_tl where brn_cd = '"+brncd+"' and acc_typ = '"+acctyp+"' and cust_no = '"+custno+"' and run_no = '"+runno+"' and chk_dgt = '"+chkdgt+"'");
			if(rs.next()){
			code=Integer.parseInt(rs.getString("currency_cd")) ;
			}
			return code;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return -1;
		}
	}
	public int GetCurrencyCode(String currency_des){
		ResultSet rs;
		int currency_cd = 0;
//		ArrayList<Object> data = new ArrayList();
		
		try {
			rs = this.lcl_stmt.executeQuery("select currency_cd from SM27392.currency_tl where currency_name='"+currency_des+"'");
			if(rs.next()){
			currency_cd=Integer.parseInt(rs.getString("currency_cd"));
			}
			return currency_cd;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return -1;
		}
	}
	public int GetCurrency(String currency_des){
		ResultSet rs;
		int currency_cd = 0;
		try {
			System.out.println("select currency_cd,exchange_rt from SM27392.currency_tl where currency_name='"+currency_des+"'");
			rs = this.lcl_stmt.executeQuery("select currency_cd,exchange_rt from SM27392.currency_tl where currency_name='"+currency_des+"'");
			while(rs.next()){
			currency_cd=Integer.parseInt( rs.getString("currency_cd"));
			String currency_name= rs.getString("exchange_rt");
			
			}
			return currency_cd;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return -1;
		}
	}
	public ArrayList GetAccounts(int brncd, int currency){
		ArrayList<Object> data = new ArrayList();
		ResultSet rs;
		String acc_no="";
		try {
			System.out.println("select brn_cd, acc_typ, cust_no, run_no, chk_dgt  from SM27392.cust_acc_tl, where brn_cd = '"+brncd+"' and currency_cd = '"+currency+"'");
			rs = this.lcl_stmt.executeQuery("select brn_cd, acc_typ, cust_no, run_no, chk_dgt  from SM27392.cust_acc_tl where brn_cd = '"+brncd+"' and currency_cd = '"+currency+"'");
			while(rs.next()){
			String brn_cd = rs.getString("brn_cd");
			String acc_typ = rs.getString("acc_typ");
			String cust_no = rs.getString("cust_no");
			String run_no = rs.getString("run_no");
			String chk_dgt = rs.getString("chk_dgt");
			acc_no += String.format("%04d", Integer.parseInt(acc_typ)) + '-';
			acc_no += String.format("%06d", Integer.parseInt(cust_no)) + '-';
			acc_no += String.format("%02d", Integer.parseInt(run_no)) + '-';
			acc_no += String.format("%01d", Integer.parseInt(chk_dgt));
			data.add(acc_no);
			acc_no="";
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
//	public String Receive_Deposit_Submit(int cust_no){
//		ResultSet rs;
//		String is_filer="";
//		try {
//			System.out.print("select is_filer from SM27392.customer_tl where cust_no = '"+cust_no+"'");
//			rs = this.lcl_stmt.executeQuery("select is_filer from SM27392.customer_tl where cust_no = '"+cust_no+"'");
//			while(rs.next()){
//			is_filer = rs.getString("is_filer").toString();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return is_filer;
//	}
	
	public int Receive_Block_Condition(int brn_cd, int acc_typ, int cust_no, int run_no, int chk_dgt){
		ResultSet rs;
		int blockcond=0;
		try {
			rs = this.lcl_stmt.executeQuery("select cnd_cd from condition_acc_tl where brn_cd = '"+brn_cd+"' and acc_typ = '"+acc_typ+"' and cust_no = '"+cust_no+"' and run_no = '"+run_no+"' and chk_dgt = '"+chk_dgt+"'");
			while(rs.next()){
			blockcond = Integer.parseInt(rs.getString("cnd_cd").toString());
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return blockcond;
	}
	
	public ArrayList GetCustAccounts(int cust_no){
		ArrayList<Object> data = new ArrayList();
		ResultSet rs;
		String acc_no="";
		try {
			rs = this.lcl_stmt.executeQuery("select brn_cd, acc_typ, cust_no, run_no, chk_dgt  from cust_acc_tl where cust_no = '"+cust_no+"'");
			while(rs.next()){
			String brn_cd = rs.getString("brn_cd");
			String acc_typ = rs.getString("acc_typ");
			String Cust_no = rs.getString("cust_no");
			String run_no = rs.getString("run_no");
			String chk_dgt = rs.getString("chk_dgt");
			acc_no += String.format("%04d", Integer.parseInt(acc_typ)) + '-';
			acc_no += String.format("%06d", Integer.parseInt(Cust_no)) + '-';
			acc_no += String.format("%02d", Integer.parseInt(run_no)) + '-';
			acc_no += String.format("%01d", Integer.parseInt(chk_dgt));
			data.add(acc_no);
			acc_no="";
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public HashMap<String, String> GetAccountDetails(int brn_cd, int acc_typ, int cust_no, int run_no, int chk_dgt){
		ResultSet rs;
		String Condition=null;
		HashMap<String,String> map=new HashMap<String,String>();
		try {
			System.out.print("db data is "+brn_cd+" "+cust_no+" "+run_no+" "+chk_dgt);
			rs = this.lcl_stmt.executeQuery("select ac.acc_title,ac.available_balance,at.acc_typ,at.acc_typ_name,cc.currency_cd,cc.currency_name,cc.exchange_rt,cd.cnd_cd,cd.cnd_desc from SM27392.cust_acc_tl ac left outer join SM27392.acc_typ_tl at on ac.acc_typ = at.acc_typ left outer join SM27392.condition_acc_tl acd on ac.brn_cd=acd.brn_cd and ac.run_no=acd.run_no and ac.cust_no=acd.cust_no and ac.chk_dgt=acd.chk_dgt left outer join SM27392.condition_tl cd on acd.cnd_cd=cd.cnd_cd left outer join SM27392.currency_tl cc on ac.currency_cd=cc.currency_cd where ac.BRN_CD = '"+brn_cd+"' AND ac.ACC_TYP ='"+acc_typ+"' AND ac.CUST_NO ='"+cust_no+"' AND ac.RUN_NO = '"+run_no+"' AND ac.CHK_DGT = '"+chk_dgt+"'");
			while(rs.next()){
				String AccountTitle = rs.getString("ACC_TITLE");
				String CurrencyName = rs.getString("CURRENCY_NAME");
				String CurrencyCode = rs.getString("CURRENCY_CD");
				String TypeName = rs.getString("ACC_TYP_NAME");
				String AvailableBalance = (rs.getString("AVAILABLE_BALANCE"));
				String ExchangeRate = rs.getString("EXCHANGE_RT");
				String ConditionCode = rs.getString("cnd_cd");
				String ConditionDesc = rs.getString("cnd_desc");
				if(ConditionCode != null){
				Condition = ConditionDesc + "-" + ConditionCode;}
//				else{
//					Condition=null;
//				}
				String TypeCode = String.format("%04d", Integer.parseInt(rs.getString("ACC_TYP")));
				String Currency = CurrencyName;
				String Type = TypeName + "-" +TypeCode;
				map.put("Title", AccountTitle);
				map.put("Currency", Currency);
				map.put("Type", Type);
				map.put("AvailableBalance", AvailableBalance);
				map.put("ExchangeRate", ExchangeRate);
				map.put("Condition", Condition);
			}
			System.out.print("in database of map     "+map);
			return map;
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return map;
	}
	
	public int fCondition(int brn_cd, int acc_typ, int cust_no, int run_no, int chk_dgt){
		int rs;
		try {
		//	System.out.print("Insert into condition_acc_tl(brn_cd, acc_typ, cust_no, run_no, chk_dgt, cnd_cd) values("+brn_cd+","+acc_typ+","+cust_no+","+run_no+","+chk_dgt+",19)");
			rs = this.lcl_stmt.executeUpdate("Insert into condition_acc_tl(brn_cd, acc_typ, cust_no, run_no, chk_dgt, cnd_cd) values("+brn_cd+","+acc_typ+","+cust_no+","+run_no+","+chk_dgt+",19)");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return 1;
	}
	
	public int DeleteCondition(int brn_cd, int acc_typ, int cust_no, int run_no, int chk_dgt){
		int rs;
		try {
			//System.out.print("delete from condition_acc_tl where brn_cd="+brn_cd+" and acc_typ="+acc_typ+" and cust_no="+cust_no+" and run_no="+run_no+" and chk_dgt="+chk_dgt+"");
			rs = this.lcl_stmt.executeUpdate("delete from condition_acc_tl where brn_cd="+brn_cd+" and acc_typ="+acc_typ+" and cust_no="+cust_no+" and run_no="+run_no+" and chk_dgt="+chk_dgt+"");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return 1;
	}
	
	public HashMap<String, String> GetCustomerName(String idno, String idtype){
		ResultSet rs;
		HashMap<String,String> map=new HashMap<String,String>();
		ArrayList data = new ArrayList();
		String acc_no="";
			try {
				rs = this.lcl_stmt.executeQuery("select cust_acc_tl.brn_cd, cust_acc_tl.acc_typ, cust_acc_tl.cust_no, cust_acc_tl.run_no, cust_acc_tl.chk_dgt, customer_tl.cust_name, cust_identification_tl.identification_no, identification_typ_tl.IDENTIFICATION_TYP_DESC from customer_tl inner join cust_acc_tl on customer_tl.cust_no = cust_acc_tl.cust_no left join cust_identification_tl on customer_tl.cust_no = cust_identification_tl.cust_no inner join identification_typ_tl on identification_typ_tl.IDENTIFICATION_TYP_ID  = cust_identification_tl.IDENTIFICATION_TYP_ID where IDENTIFICATION_TYP_DESC='"+idtype+"' AND IDENTIFICATION_NO='"+idno+"' ");
				if(rs==null){
					return null;
				}
				else{
				while(rs.next()){
					String CustomerName = rs.getString("cust_name");
					map.put("CustomerName", CustomerName);
					}
				}

				return map;
				} catch (SQLException e) {
					logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			}
			return null;
	}
	
	public HashMap<String, ArrayList> GetCustomerAccounts(String idno, String idtype){
		ResultSet rs;
		HashMap<String,ArrayList> map=new HashMap<String,ArrayList>();
		ArrayList data = new ArrayList();
		String acc_no="";
			try {
				rs = this.lcl_stmt.executeQuery("select cust_acc_tl.brn_cd, cust_acc_tl.acc_typ, cust_acc_tl.cust_no, cust_acc_tl.run_no, cust_acc_tl.chk_dgt, customer_tl.cust_name, cust_identification_tl.identification_no, identification_typ_tl.IDENTIFICATION_TYP_DESC from customer_tl inner join cust_acc_tl on customer_tl.cust_no = cust_acc_tl.cust_no left join cust_identification_tl on customer_tl.cust_no = cust_identification_tl.cust_no inner join identification_typ_tl on identification_typ_tl.IDENTIFICATION_TYP_ID  = cust_identification_tl.IDENTIFICATION_TYP_ID where IDENTIFICATION_TYP_DESC='"+idtype+"' AND IDENTIFICATION_NO='"+idno+"' ");
				if(rs==null){
					map=null;
				}
				else{
				while(rs.next()){
					String brn_cd = rs.getString("brn_cd");
					String acc_typ = rs.getString("acc_typ");
					String Cust_no = rs.getString("cust_no");
					String run_no = rs.getString("run_no");
					String chk_dgt = rs.getString("chk_dgt");
					acc_no += String.format("%04d", Integer.parseInt(brn_cd)) + '-';
					acc_no += String.format("%04d", Integer.parseInt(acc_typ)) + '-';
					acc_no += String.format("%06d", Integer.parseInt(Cust_no)) + '-';
					acc_no += String.format("%02d", Integer.parseInt(run_no)) + '-';
					acc_no += String.format("%01d", Integer.parseInt(chk_dgt));
					data.add(acc_no);
					acc_no="";
					map.put("AccountNos", data);
					}}
				} catch (SQLException e) {
					logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			}
		return map;
	}
	public int getPurposeCode(String purpose) {
		int result=0;
		ResultSet rs;
		
		try {
//			this.lcl_conn.setAutoCommit(false);
			rs = this.lcl_stmt.executeQuery("select purpose_id from SM27392.purpose_tl where purpose_desc = '"+purpose.toUpperCase()+"'");
			while(rs.next()){
				result=Integer.parseInt(rs.getString("purpose_id"));
			}
			return result;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	
	public int getMaxVoucher() {
		int result=0;
		ResultSet rs;
		try {
//			this.lcl_conn.setAutoCommit(false);
			rs=this.lcl_stmt.executeQuery("select max(voucher_cd) as voucher_cd from SM27392.voucher_tl");
			System.out.print("select max(voucher_cd) as voucher_cd from SM27392.voucher_tl");
			while(rs.next()){
				result= Integer.parseInt(rs.getString("voucher_cd"));
			}
			
			return result;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	public int getIDCode(String idtype) {
		int result=0;
		ResultSet rs;
		
		try {
//			this.lcl_conn.setAutoCommit(false);
			rs=this.lcl_stmt.executeQuery("select id_type from SM27392.id_type_tl where type_name='"+idtype.toUpperCase()+"'");
			System.out.print("select id_type from SM27392.id_type_tl where type_name='"+idtype.toUpperCase()+"'");
			while(rs.next()){
				result= Integer.parseInt(rs.getString("id_type"));
			}
			
			return result;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	public int getTranCode(String idtype) {
		int result=0;
		ResultSet rs;
		
		try {
//			this.lcl_conn.setAutoCommit(false);
			rs=this.lcl_stmt.executeQuery("select id_type from SM27392.id_type_tl where type_name='"+idtype.toUpperCase()+"'");
			System.out.print("select id_type from SM27392.id_type_tl where type_name='"+idtype.toUpperCase()+"'");
			while(rs.next()){
				result= Integer.parseInt(rs.getString("id_type"));
			}
			
			return result;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	public ArrayList insertvoucher(int batchid,int brncd, int acctyp, int custno, int runno, int chkdgt, String userid, String acc_no, String acc_title, String currency, String acc_type, String exchange_rate, String available_balance, String local_equivalent, String receipt_no, Double amount, String narration, String purpose, String other, String idtype, String idno, String customername, String account,int type,int brn_1){

		Utlis ut=new Utlis();
		ArrayList<Object> data = new ArrayList();
		System.out.println("data dummy is "+acctyp+" account ty"+ acc_type);
		int purposeid = 0;
		String tran_des="";
		Double debit=amount,credit=amount;
		int vouchercd =0,idtypeval = 0,ledger_rs=0,voucher_ins=0,tran_result=0,curr_cd=0;
		String isauthor="",iscancel="",posteddate=GetDate(brncd),authorizeDate=posteddate,authorizeBy=" ";
		String cancelDate=posteddate,cancelBy=" ",maskvoucher="";
		ResultSet user_right_rs;
		
		purposeid=getPurposeCode(purpose);
		idtypeval=getIDCode(idtype);
		
		curr_cd=GetCurrency(currency);
		String gl_acc_no=get_gl_acc(brncd, "coh", curr_cd);
		System.out.println("gl scc is "+gl_acc_no);
		System.out.println("purpose is "+purposeid+"id value is "+idtypeval+" currency cde is "+curr_cd);
		try {
//			this.lcl_conn.setAutoCommit(false);
			System.out.println("select at.activity_name from SM27392.access_rights_tl art,SM27392.activity_tl at where art.user_id='"+userid+"'  and art.activity_id=at.activity_id ");
			user_right_rs=this.lcl_stmt.executeQuery("select at.activity_name from SM27392.access_rights_tl art,SM27392.activity_tl at where art.user_id='"+userid+"'  and art.activity_id=at.activity_id ");
//			user_right_rs.HOLD_CURSORS_OVER_COMMIT();
			if(user_right_rs.next()) {
				Double limit=Double.parseDouble(user_right_rs.getString("activity_name"));
				System.out.println("amount is "+amount+"limit is acivity "+limit);
				if(type==0){
					voucher_ins = this.lcl_stmt.executeUpdate("insert into SM27392.voucher_tl(voucher_typ) values('General Voucher')");

				}
				if(type==1){
					voucher_ins = this.lcl_stmt.executeUpdate("insert into SM27392.voucher_tl(voucher_typ) values('Inter Branch')");

				}	
				if(voucher_ins==1){
					if(limit>=amount && type==0){
						System.out.println("here in condition    11 "+type);
						isauthor="Y";
						authorizeBy=userid;
						authorizeDate=posteddate;
						update_acc_balance( brncd,acctyp,custno,runno,chkdgt,amount,curr_cd);
						
					}
					else {
						System.out.println("here in condition  2 "+type);
						isauthor="N";
					}
					
					System.out.println("limit is witjin  "+limit);
					vouchercd=getMaxVoucher();
					System.out.print("here in condition     "+vouchercd+idtypeval);
					maskvoucher=ut.genrateCode(vouchercd);
					System.out.println("mask voucher is "+maskvoucher);
					data.add(maskvoucher);
					 Random rand = new Random();
					String recp=String.valueOf( rand.nextInt(100000));
					if(maskvoucher!=null){
						
						System.out.println("INSERT INTO SM27392.LEDGERs_TL(VOUCHER_NO ,REFERENCE_NO, VOUCHER_CD, RECEIPT_NO, AMOUNT, NARRATION, PURPOSE_ID, OTHER_DESC, IS_AUTHORIZED, IS_CANCELLED, POSTED_BY, POSTED_DATE, AUTHORIZED_BY, AUTHORIZED_DATE, CANCELLED_BY, CANCELLED_DATE, Depositor_id_type, Depositor_name, Depositor_id_value,BRN_CD,ACC_TYP,CUST_NO,RUN_NO,CHK_DGT,ACC_TITLE) values ('"+maskvoucher+"', '"+receipt_no+"', '"+vouchercd+"',258,'"+amount+"', '"+narration+"',  '"+purposeid+"','"+other+"', '"+isauthor+"', '"+iscancel+"','"+userid+"', '"+posteddate+"', '"+authorizeBy+"', '"+authorizeDate+"','"+cancelBy+"', '"+cancelDate+"','"+idtypeval+" ', '"+customername+" ', '"+idno+"' ,'"+brncd+"' ,'"+acctyp+"' ,'"+custno+"' ,'"+runno+"','"+chkdgt+"','"+acc_title+"' )");
						
						ledger_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.LEDGERs_TL(VOUCHER_NO ,REFERENCE_NO, VOUCHER_CD, RECEIPT_NO, AMOUNT, NARRATION, PURPOSE_ID, OTHER_DESC, IS_AUTHORIZED, IS_CANCELLED, POSTED_BY, POSTED_DATE, AUTHORIZED_BY, AUTHORIZED_DATE, CANCELLED_BY, CANCELLED_DATE, Depositor_id_type, Depositor_name, Depositor_id_value,BRN_CD,ACC_TYP,CUST_NO,RUN_NO,CHK_DGT,ACC_TITLE) values ('"+maskvoucher+"', '"+receipt_no+"', '"+vouchercd+"','"+recp+"','"+amount+"', '"+narration+"',  '"+purposeid+"','"+other+"', '"+isauthor+"', '"+iscancel+"','"+userid+"', '"+posteddate+"', '"+authorizeBy+"', '"+authorizeDate+"','"+cancelBy+"', '"+cancelDate+"','"+idtypeval+" ', '"+customername+" ', '"+idno+"' ,'"+brncd+"' ,'"+acctyp+"' ,'"+custno+"' ,'"+runno+"','"+chkdgt+"','"+acc_title+"' )");
						System.out.println("result set of edger is"+ledger_rs);
						if(ledger_rs==1){
							System.out.println("GOING TO TRANSACTION STATE"+ledger_rs);
//							TRANS_DESC ,VOUCHER_NO,DR,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt,ACC_NO
							
							System.out.println("data is in stat "+tran_des+" "+maskvoucher+" "+debit+" "+credit+" "+authorizeDate+" "+brncd+" "+acctyp+" "+custno+" "+runno+" "+chkdgt+" "+gl_acc_no);
							if(type==0){
								System.out.println("i were  in 0");
								tran_result=insertTransaction(batchid,userid,tran_des,maskvoucher,debit,credit,authorizeDate,brncd,acctyp,custno,runno,chkdgt,gl_acc_no,type,curr_cd);	
							}
							if(type==1){
								System.out.println("i were  in 1");
								tran_result=insertTransaction_ft(batchid,userid,tran_des, maskvoucher, debit, credit, authorizeDate, brncd, acctyp, custno, runno, chkdgt, gl_acc_no, idtypeval,brn_1,curr_cd);
							}
//							data.add(tran_result);
						}
					}
				}
				else{
					logger.info("unable to generate voucher");
				}
			}
			return data;
			} 
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return data;
	}
	public void update_acc_balance(int brncd, int acctyp,int custno, int runno, int chkdgt, Double amount,int curr_cd) {
		int rs,rs1,rs2;
		String gl_acc_no;
		try {
//			int currency_code=GetAccCurrency(brncd,acctyp,custno,runno,chkdgt);
			
//			if(currency_code==840){
//				gl_acc_no=get_gl_acc(brncd,"fx" );
//				System.out.print("update SM27392.gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='"+gl_acc_no+"'");
//				rs1= this.lcl_stmt.executeUpdate("update SM27392.gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='"+gl_acc_no+"'");
//				rs2= this.lcl_stmt.executeUpdate("update SM27392.cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brncd+"' and acc_typ='"+acctyp+"' and cust_no='"+custno+"' and run_no='"+runno+"' and chk_dgt='"+chkdgt+"'");
//				
//				System.out.print("update SM27392.cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brncd+"' and acc_typ='"+acctyp+"' and cust_no='"+custno+"' and run_no='"+runno+"' and chk_dgt='"+chkdgt+"'");
//			}
//			if(currency_code==586){
				System.out.println(brncd);
				System.out.println(curr_cd);
				gl_acc_no=get_gl_acc(brncd,"coh",curr_cd );
				System.out.println(gl_acc_no);
				rs1= this.lcl_stmt.executeUpdate("update SM27392.gl_acc_tl set available_balance = available_balance-'"+amount+"' where acc_no='"+gl_acc_no+"'");
				rs2= this.lcl_stmt.executeUpdate("update SM27392.cust_acc_tl set available_balance=available_balance+'"+amount+"' where brn_cd='"+brncd+"' and acc_typ='"+acctyp+"' and cust_no='"+custno+"' and run_no='"+runno+"' and chk_dgt='"+chkdgt+"'");
				System.out.print("update SM27392.gl_acc_tl set available_balance = available_balance-'"+amount+"' where acc_no='"+gl_acc_no+"'");
				System.out.print("update SM27392.cust_acc_tl set available_balance=available_balance+'"+amount+"' where brn_cd='"+brncd+"' and acc_typ='"+acctyp+"' and cust_no='"+custno+"' and run_no='"+runno+"' and chk_dgt='"+chkdgt+"'");
//			}
//			rs=this.lcl_stmt.executeUpdate("Update cash_Deposit_tl set is_cancelled='Y', cancelled_by ='"+user_id+"', cancelled_date='"+date+"', cancelled_time ='"+time+"' where voucher_no = '"+vouch_no+"'");
			
		} catch (SQLException e) {

			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
	}

	public Vector<String> get_gl_acc_title(int brn_cd,String typ,int currency_cd) {
		String result="",str="MAIN OFFICE",str2="CASH ON HAND";
		ResultSet rs;
		Vector<String> r1 = new Vector<String>();
		try {
//			this.lcl_conn.setAutoCommit(false);
			if(typ=="mo"){
				System.out.println("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='"+str+"' and currency_cd='"+currency_cd+"'");
				rs = this.lcl_stmt.executeQuery("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='"+str+"' and currency_cd='"+currency_cd+"'");
				if(rs.next()){
					r1.add(rs.getString("acc_no"));
					r1.add(rs.getString("acc_title"));
				}
			}
			if(typ=="coh"){
				System.out.println("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='"+str2+"' and currency_cd='"+currency_cd+"'");
				rs = this.lcl_stmt.executeQuery("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='"+str2+"' and currency_cd='"+currency_cd+"'");
				if(rs.next()){
					r1.add(rs.getString("acc_no"));
					r1.add(rs.getString("acc_title"));
				}
			}
			System.out.println("data in db is aa "+r1);
			return r1;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}
	public String get_gl_acc(int brn_cd,String typ,int currency_cd) {
		String result="";
		ResultSet rs;
		try {
//			this.lcl_conn.setAutoCommit(false);
			if(typ=="mo"){
				System.out.println("select acc_no from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='MAIN OFFICE' AND currency_cd='"+currency_cd+"'");
				rs = this.lcl_stmt.executeQuery("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='MAIN OFFICE' AND currency_cd='"+currency_cd+"'");
				if(rs.next()){
					result=rs.getString("acc_no");
				}
			}
			if(typ=="coh"){
				System.out.println("select acc_no from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='CASH ON HAND' AND currency_cd='"+currency_cd+"'");
				rs = this.lcl_stmt.executeQuery("select acc_no from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='CASH ON HAND' AND currency_cd='"+currency_cd+"'");
				if(rs.next()){
					result=rs.getString("acc_no");
				}
			}
//			if(typ=="fx"){
//				System.out.println("select acc_no from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='FOREIGN EXCHANGE'");
//				rs = this.lcl_stmt.executeQuery("select acc_no,acc_title from SM27392.gl_acc_tl where brn_cd = '"+brn_cd+"' AND acc_title='FOREIGN EXCHANGE'");
//				if(rs.next()){
//					result=rs.getString("acc_no");
//				}
//			}
			return result;
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}
	public int getTransID(String voucher_cd,String postedByUser,int batchid) {
		ResultSet rs;
		int trans_id=-1,res=-1;
		Vector<Integer> array = new Vector<>();
		try {
				System.out.println("select trans_id from SM27392.transaction_tl where voucher_no= '"+voucher_cd+"'");
				rs = this.lcl_stmt.executeQuery("select trans_id from SM27392.transaction_tl where voucher_no = '"+voucher_cd+"'");
				while(rs.next()){
					trans_id=Integer.parseInt(rs.getString("trans_id"));
					array.add(trans_id);
					
				}
				System.out.println("array of trans id is  "+array);
				res=insertBatchHistory(array, postedByUser, batchid);
				if(res==1){
					return 1;
				}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			}
		return -1;
	}
	
	public int insertBatchHistory(Vector<Integer> trans_ids,String postedByUser,int batchid) {
		int result=0;
		Iterator<Integer> value = trans_ids.iterator();
		
		try {
			while(value.hasNext()){
				result = this.lcl_stmt.executeUpdate("insert into SM27392.batch_history_tl (batch_id,user_id,trans_id) values ('"+batchid+"','"+postedByUser+"','"+value.next()+"')");
			}
			
			if(result==1){
				return result;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	
	public int insertTransaction(int batchid,String postedByUser,String tran_des,String vouchercd,Double debit,Double credit,String authorizeDate,int brncd,int acc_type,int custno,int runno,int chkdgt,String acc_no,int type,int curr_cd) {
//		TRANS_DESC ,VOUCHER_NO,DR,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt,ACC_NO
		int cred_rs,deb_rs;
		try {
//			this.lcl_conn.setAutoCommit(false);
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brncd+"','"+acc_type+"', '"+custno+"', '"+runno+"','"+chkdgt+"' )");
			cred_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brncd+"','"+acc_type+"', '"+custno+"', '"+runno+"','"+chkdgt+"' )");
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brncd+"', '"+acc_no+"' )");
			deb_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brncd+"', '"+acc_no+"' )");
			System.out.println("credit is "+cred_rs+" debit is "+deb_rs);
		
			if(cred_rs==1 && deb_rs==1){
				int result=getTransID(vouchercd, postedByUser,batchid );
				if(result==1){
					return 1;
				}
			}
			else{
				return -1;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	public int insertTransaction_ft(int batchid,String postedByUser,String tran_des,String vouchercd,Double debit,Double credit,String authorizeDate,int brncd,int acc_type,int custno,int runno,int chkdgt,String acc_no,int type,int brn_1,int curr_cd) {
//		TRANS_DESC ,VOUCHER_NO,DR,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt,ACC_NO
		int cr_rs=0,new_cr_rs=0,dr_rs=0,new_dr_rs=0;
		System.out.println("brn codoes are "+brncd+" other one "+brn_1);
		String new_mo=get_gl_acc(brncd,"mo",curr_cd),b1_coh=get_gl_acc(brn_1,"coh",curr_cd),b1_mo=get_gl_acc(brn_1,"mo",curr_cd);
		try {
			
//			this.lcl_conn.setAutoCommit(false);
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brn_1+"', '"+b1_coh+"' )");
			dr_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brn_1+"', '"+b1_coh+"' )");
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brn_1+"', '"+b1_mo+"'  )");
			cr_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brn_1+"', '"+b1_mo+"'  )");
			
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brncd+"', '"+new_mo+"' )");
			new_cr_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,DR,TRANSACTION_DATE,brn_cd,ACC_NO) values ('"+tran_des+"', '"+vouchercd+"', '"+debit+"', '"+authorizeDate+"', '"+brncd+"', '"+new_mo+"' )");
			System.out.println("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brncd+"','"+acc_type+"', '"+custno+"', '"+runno+"','"+chkdgt+"' )");
			new_dr_rs = this.lcl_stmt.executeUpdate("INSERT INTO SM27392.TRANSACTION_TL(TRANS_DESC ,VOUCHER_NO,CR,TRANSACTION_DATE,brn_cd, acc_typ, cust_no, run_no, chk_dgt) values ('"+tran_des+"', '"+vouchercd+"', '"+credit+"', '"+authorizeDate+"', '"+brncd+"','"+acc_type+"', '"+custno+"', '"+runno+"','"+chkdgt+"' )");
			System.out.println("credit is "+cr_rs+" debit is "+dr_rs+"credit is "+new_cr_rs+" debit is "+new_dr_rs);
			if(new_dr_rs==1 && new_cr_rs==1 && dr_rs==1 && cr_rs==1 ){
				int result=getTransID(vouchercd, postedByUser,batchid );
				if(result==1){
					return 1;
				}
			}
			else{
				return -1;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return -1;
	}
	public HashMap <String, ArrayList> getTransactions(String voucherno){
		ResultSet rs;
		String Voucherno = null;
		String Referenceno = null;
		ArrayList<Object> transactions = new ArrayList();
		ArrayList<Object> vouchers = new ArrayList();
		ArrayList<Object> debits = new ArrayList();
		ArrayList<Object> credits = new ArrayList();
		HashMap<String,ArrayList> trans_data=new HashMap<String,ArrayList>();
		try {
			rs=this.lcl_stmt.executeQuery("Select * from SM27392.transaction_tl where voucher_no ='"+voucherno+"' ");
			while(rs.next()){
				vouchers.add(rs.getString("voucher_no"));
				transactions.add(rs.getString("trans_id"));
				credits.add(rs.getString("cr"));
				debits.add(rs.getString("dr"));
				trans_data.put("Vouchers", vouchers);
				trans_data.put("Transactions", transactions);
				trans_data.put("Credits", credits);
				trans_data.put("Debits", debits);
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		//System.out.print(trans_data);
		return trans_data;
	}
	
	public void authorize(int vouch_no, String user_id){
		Date date=new java.sql.Date(System.currentTimeMillis());
		Time time=new java.sql.Time(System.currentTimeMillis());
		try {
			this.lcl_stmt.executeUpdate("Update cash_Deposit_tl set is_authorized='Y', authorized_by = '"+user_id+"' ,authorized_date = '"+date+"', authorized_time = '"+time+"' where voucher_no='"+vouch_no+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void authorize(String vouchno, String user_id){
		Date date=new java.sql.Date(System.currentTimeMillis());
		Time time=new java.sql.Time(System.currentTimeMillis());
		try {
			this.lcl_stmt.executeUpdate("Update sm27392.ledgers_tl set is_authorized='Y', authorized_by = '"+user_id+"' ,authorized_date = '"+date+"' where voucher_no='"+vouchno+"'");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
	}
	
	
	public HashMap<String, String> getCancelDetails(int vouchno, String user_id){
		ResultSet rs;
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("Record","yes");
		try {
			rs=this.lcl_stmt.executeQuery("select cash_deposit_tl.voucher_no, is_authorized,transaction_tl.trans_desc, transaction_tl.brn_cd, transaction_tl.acc_typ, transaction_tl.cust_no, transaction_tl.run_no, transaction_tl.chk_dgt, posted_date, narration, amount, posted_by, posted_time, authorized_by, authorized_time, cust_acc_tl.currency_cd, cust_acc_tl.acc_title, currency_tl.exchange_rt, currency_tl.currency_name from cash_deposit_tl inner join transaction_tl on cash_deposit_tl.voucher_no = transaction_tl.voucher_no inner join cust_acc_tl on transaction_tl.brn_cd=cust_acc_tl.brn_cd and cust_acc_tl.acc_typ=transaction_tl.acc_typ and cust_Acc_tl.cust_no=transaction_tl.cust_no and cust_acc_tl.run_no=transaction_tl.run_no and cust_Acc_tl.chk_dgt=transaction_tl.chk_dgt inner join currency_tl on currency_tl.currency_cd=cust_acc_tl.currency_cd where cash_deposit_tl.voucher_no = '"+vouchno+"' and cash_deposit_tl.is_cancelled='N'");
			if(!rs.next()){
				map.put("Record", "none");
			}
			else{
				do{
					String voucherno = rs.getString("voucher_no");
					String transdesc = rs.getString("trans_desc");
					String status = rs.getString("is_authorized");
					String brn_cd = rs.getString("brn_cd");
					String acc_typ = rs.getString("acc_typ");
					String Cust_no = rs.getString("cust_no");
					String run_no = rs.getString("run_no");
					String chk_dgt = rs.getString("chk_dgt");
					String currency_cd = rs.getString("currency_cd");
					String currency_name = rs.getString("currency_name");
					String accname = rs.getString("acc_title");
					String posteddate = rs.getString("posted_date");
					String postedby = rs.getString("posted_by");
					String postedtime = rs.getString("posted_time");
					String authorizedby = rs.getString("authorized_by");
					String authorizedtime = rs.getString("authorized_time");
					String exchangerate = rs.getString("exchange_rt");
					String amount = rs.getString("amount");
					String narration = rs.getString("narration");
					String acc_no = "";
					acc_no += String.format("%04d", Integer.parseInt(brn_cd)) + '-';
					acc_no += String.format("%04d", Integer.parseInt(acc_typ)) + '-';
					acc_no += String.format("%06d", Integer.parseInt(Cust_no)) + '-';
					acc_no += String.format("%02d", Integer.parseInt(run_no)) + '-';
					acc_no += String.format("%01d", Integer.parseInt(chk_dgt));
					String currency = currency_name + " - " + currency_cd;
					if(status.equals("Y")){
						status = "AUTHORIZED";
					}else{
						status = "UNAUTHORIZED";
					}
					map.put("VoucherNo",voucherno);
					map.put("transdesc", transdesc);
					map.put("status", status);
					map.put("accno", acc_no);
					map.put("currency", currency);
					map.put("accname", accname);
					map.put("posteddate", posteddate);
					map.put("postedby", postedby);
					map.put("postedtime", postedtime);
					map.put("authorizedby" , authorizedby);
					map.put("authorizedtime", authorizedtime);
					map.put("exchangerate", exchangerate);
					map.put("amount", amount);
					map.put("narration", narration);
				}while(rs.next());
			}
		
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		//System.out.print(map);
		return map;
	}
	
	public int CancelTransaction(String vouch_no, String user_id, int brn_cd, int acc_typ, int cust_no, int run_no, int chk_dgt, Double amount, String currency){
		int rs = 0;
		int rs1 = 0;
		int rs2 = 0;
		//System.out.print(amount);
		//System.out.print(currency);
		Date date=new java.sql.Date(System.currentTimeMillis());
		Time time=new java.sql.Time(System.currentTimeMillis());
		try {
			rs=this.lcl_stmt.executeUpdate("Update cash_Deposit_tl set is_cancelled='Y', cancelled_by ='"+user_id+"', cancelled_date='"+date+"', cancelled_time ='"+time+"' where voucher_no = '"+vouch_no+"'");
			if(currency.equals("US DOLLAR - 840")){
				rs1= this.lcl_stmt.executeUpdate("update gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='A-010-11-01-1001-1'");
				rs2= this.lcl_stmt.executeUpdate("update cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brn_cd+"' and acc_typ='"+acc_typ+"' and cust_no='"+cust_no+"' and run_no='"+run_no+"' and chk_dgt='"+chk_dgt+"'");
				//System.out.print("update gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='A-010-11-01-1001-1'");
				//System.out.print("update cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brn_cd+"' and acc_typ='"+acc_typ+"' and cust_no='"+cust_no+"' and run_no='"+run_no+"' and chk_dgt='"+chk_dgt+"'");
			}else{
				rs1= this.lcl_stmt.executeUpdate("update gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='A-010-11-01-1000-1'");
				rs2= this.lcl_stmt.executeUpdate("update cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brn_cd+"' and acc_typ='"+acc_typ+"' and cust_no='"+cust_no+"' and run_no='"+run_no+"' and chk_dgt='"+chk_dgt+"'");
				//System.out.print("update gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='A-010-11-01-1000-1'");
				//System.out.print("update cust_acc_tl set available_balance=available_balance-'"+amount+"' where brn_cd='"+brn_cd+"' and acc_typ='"+acc_typ+"' and cust_no='"+cust_no+"' and run_no='"+run_no+"' and chk_dgt='"+chk_dgt+"'");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return rs;
	}
	
	public ArrayList Accounts(){
		ArrayList<Object> data = new ArrayList();
		ResultSet rs;
		String acc_no="";
		try {
			rs = this.lcl_stmt.executeQuery("select brn_cd, acc_typ, cust_no, run_no, chk_dgt  from cust_acc_tl");
			while(rs.next()){
			String brn_cd = rs.getString("brn_cd");
			String acc_typ = rs.getString("acc_typ");
			String cust_no = rs.getString("cust_no");
			String run_no = rs.getString("run_no");
			String chk_dgt = rs.getString("chk_dgt");
			acc_no += brn_cd + '-';
			acc_no += acc_typ + '-';
			acc_no += cust_no + '-';
			acc_no += run_no + '-';
			acc_no += chk_dgt;
			data.add(acc_no);
			acc_no="";
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public HashMap<String, String> getdets(String vouchno){
		HashMap<String, String> map= new HashMap<String, String>();
		ResultSet rs;
		
		try {
			rs=this.lcl_stmt.executeQuery("select idtype, idno, customername, customeraccount from cash_Deposit_tl where voucher_no = '"+vouchno+"'");
			while(rs.next()){
				String idtype = rs.getString("idtype");
				String idno = rs.getString("idno");
				String customername = rs.getString("customername");
				String customeraccount = rs.getString("customeraccount");
				map.put("idtype", idtype);
				map.put("idno", idno);
				map.put("customername", customername);
				map.put("customeraccount", customeraccount);
				return map;
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
		
	}

	public Vector<String> GetIDAcc(String id_type_value, String idNo) {
		ResultSet rs;
		int idtyp=GetIdentificationID(id_type_value);
		Vector<String> result=new Vector<String>();
		
		try {
			System.out.println("select c.cust_name,a.brn_cd,a.acc_typ,a.cust_no,a.run_no,a.chk_dgt from sm27392.cust_acc_tl a,sm27392.customer_tl c where c.cust_no=a.cust_no and c.id_type='"+idtyp+"' and C.id_type_value='"+idNo+"'");
			rs=this.lcl_stmt.executeQuery("select c.cust_name,a.brn_cd,a.acc_typ,a.cust_no,a.run_no,a.chk_dgt from sm27392.cust_acc_tl a,sm27392.customer_tl c where c.cust_no=a.cust_no and c.id_type='"+idtyp+"' and C.id_type_value='"+idNo+"'");
			while(rs.next()){
				int brn_cd=Integer.parseInt(rs.getString("brn_cd"));
				int acc_type=Integer.parseInt(rs.getString("acc_typ"));
				int chkdgt=Integer.parseInt(rs.getString("chk_dgt"));
				int runno=Integer.parseInt(rs.getString("run_no"));
				int custno=Integer.parseInt(rs.getString("cust_no"));
//				String customername = rs.getString("cust_name");
				String customeraccount = String.format("%04d", brn_cd) + "-00" +acc_type+ "-" +String.format("%06d", custno)+ "-" +String.format("%02d",runno)+ "-" +String.format("%01d",chkdgt);
				System.out.println("data in set account number is "+customeraccount);
				
				result.add(customeraccount);
				
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}

	public String GetIDAccName(String id_type_value, String idNo) {
		ResultSet rs;
		int idtyp=GetIdentificationID(id_type_value);
		String customername =null;
		
		try {
			System.out.println("select c.cust_name from sm27392.cust_acc_tl a,sm27392.customer_tl c where c.cust_no=a.cust_no and c.id_type='"+idtyp+"' and C.id_type_value='"+idNo+"'");
			rs=this.lcl_stmt.executeQuery("select c.cust_name from sm27392.cust_acc_tl a,sm27392.customer_tl c where c.cust_no=a.cust_no and c.id_type='"+idtyp+"' and C.id_type_value='"+idNo+"'");
			while(rs.next()){
				customername = rs.getString("cust_name");
				System.out.println(" name is "+customername);
				
			}
			return customername;
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}

	public ArrayList<String> Get_Users(int brn_cd, String id){
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.lcl_stmt.executeQuery("select u.user_id from SM27392.users_tl u, SM27392.access_rights_TL ar where u.user_id <> 'AUTH1' AND u.brn_cd = '1001' and ar.user_id=u.user_id and ar.module_cd=1");
			while(rs.next()){
				String user_id = rs.getString("user_id");
				data.add(user_id);
			}
			return data;
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	public ArrayList Get_Vouch(String user,String user_id,String vouch_type){
		ArrayList<String[]> data = new ArrayList();
		ResultSet rs;
		try {
			int limit=getlimit(user);
			System.out.println("select l.voucher_no, l.posted_by, v.voucher_typ from SM27392.ledgers_tl l join SM27392.voucher_tl v on v.voucher_cd = l.voucher_cd where l.is_authorized = 'N' and l.posted_by = '"+user_id+"' AND v.voucher_typ = '"+vouch_type+"'  and l.amount<='"+limit+"'");
			rs = this.lcl_stmt.executeQuery("select l.voucher_no, l.posted_by, v.voucher_typ from SM27392.ledgers_tl l join SM27392.voucher_tl v on v.voucher_cd = l.voucher_cd where l.is_authorized = 'N' and l.posted_by = '"+user_id+"' AND v.voucher_typ = '"+vouch_type+"'  and l.amount<='"+limit+"'");
			//rs = this.lcl_stmt.executeQuery("select l.voucher_no, l.posted_by from SM27392.ledgers_tl l where l.is_authorized = 'N' and l.posted_by = '"+user_id+"'");
			while(rs.next()){
			String voucher_no = rs.getString("voucher_no");
			String posted_by = rs.getString("posted_by");
			String[] var = new String[3];
			var[0] = voucher_no;
			var[1] = posted_by;
			var[2] = vouch_type;
			data.add(var);
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}	
	public ArrayList Get_Tran(String vouch_no){
		ArrayList<String[]> data = new ArrayList();
		ResultSet rv;
		ResultSet rr;
		String acc_no = "";
		try {
			rv = this.lcl_stmt.executeQuery("select t.voucher_no, t.trans_id,b.BRN_NAME,t.acc_no, a.acc_title, t.dr, t.cr,c.currency_name from SM27392.transaction_tl t join SM27392.gl_acc_tl a on t.acc_no = a.acc_no join SM27392.currency_tl c on a.currency_cd = c.currency_cd join SM27392.branchs_tl b on b.brn_cd = t.brn_cd where t.voucher_no = '"+vouch_no+"' order by t.trans_id asc");
			while(rv.next()){
				String voucher_no = rv.getString("voucher_no");
				String trans_id = rv.getString("trans_id");
				acc_no = rv.getString("acc_no");
				String acc_title = rv.getString("acc_title");
				String brn_name = rv.getString("brn_name");
				String currency_name = rv.getString("currency_name");
				String dr = rv.getString("dr");
				String cr = rv.getString("cr");
				String[] var1 = new String[8];
				var1[0] = voucher_no;
				var1[1] = trans_id;
				var1[2] = brn_name;
				var1[3] = acc_no;
				
				var1[5] = currency_name;
				var1[4] = acc_title;
				var1[6] = dr;
				var1[7] = cr;
				data.add(var1);
				acc_no="";
			}
			rr = this.lcl_stmt.executeQuery("select t.voucher_no AS voucher_no, t.trans_id as trans_id,b.brn_name as brn_name, a.brn_cd as brn_cd, a.acc_typ as acc_typ, a.cust_no as cust_no, a.run_no as run_no, a.chk_dgt as chk_dgt,c.currency_name as currency_name, a.acc_title as acc_title, t.dr as dr, t.cr as cr from SM27392.transaction_tl t join SM27392.cust_acc_tl a on t.brn_cd = a.brn_cd and t.acc_typ = a.acc_typ and t.cust_no = a.cust_no and t.run_no = a.run_no and t.chk_dgt = a.chk_dgt join SM27392.currency_tl c on c.currency_cd = a.currency_cd join SM27392.branchs_tl b on b.brn_cd = t.brn_cd where t.voucher_no = '"+vouch_no+"'");
			if (rr.next()){
			String voucher_no = rr.getString("voucher_no");
			String trans_id = rr.getString("trans_id");
			String acc_title = rr.getString("acc_title");
			String brn_name = rr.getString("brn_name");
			String dr = rr.getString("dr");
			String cr = rr.getString("cr");
			String brn_cd = String.format("%04d", Integer.parseInt(rr.getString("brn_cd")));
			String acc_typ = rr.getString("acc_typ");
			String currency_name = rr.getString("currency_name");
			String cust_no = rr.getString("cust_no");
			String run_no = rr.getString("run_no");
			String chk_dgt = rr.getString("chk_dgt");
			acc_no += brn_cd + '-';
			acc_no += acc_typ + '-';
			acc_no += cust_no + '-';
			acc_no += run_no + '-';
			acc_no += chk_dgt;
			String[] var = new String[8];
			var[0] = voucher_no;
			var[1] = trans_id;
			var[2] = brn_name;
			var[3] = acc_no;
			var[5] = currency_name;
			var[4] = acc_title;
			var[6] = dr;
			var[7] = cr;
			data.add(var);
			acc_no = "";
			}
			return data;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	public Double Get_Amount(String vouch_no){
		ResultSet rr;
		Double amount = null;
		try {
			System.out.println(vouch_no);
			rr = this.lcl_stmt.executeQuery("select amount from SM27392.ledgers_tl  where voucher_no = '"+vouch_no+"'");
			while(rr.next()){
				System.out.println("QUERY GOOD");
				amount = Double.parseDouble(rr.getString("amount"));
			}
			return amount;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	public ArrayList Get_Acs(String vouch_no){
		ResultSet rs;
		ArrayList<String> a = new ArrayList();
		try {
			System.out.println(vouch_no);
			rs = this.lcl_stmt.executeQuery("select brn_cd, acc_typ, cust_no, run_no, chk_dgt from SM27392.ledgers_tl  where voucher_no = '"+vouch_no+"'");
			while(rs.next()){
				System.out.println("QUERY GOOD");
				String brn_cd = rs.getString("brn_cd");
				String acc_typ = rs.getString("acc_typ");
				String cust_no = rs.getString("cust_no");
				String run_no = rs.getString("run_no");
				String chk_dgt = rs.getString("chk_dgt");
				a.add(brn_cd);
				a.add(acc_typ);
				a.add(cust_no);
				a.add(run_no);
				a.add(chk_dgt);
			}
			return a;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error"+ e.getMessage(),e);
			return null;
		}
	}

	
	public int Currency_code(String c_type){
		ResultSet rs;
		int a = 0;
		try {
			rs = this.lcl_stmt.executeQuery("select currency_cd from SM27392.currency_tl  where currency_name = '"+c_type+"'");
			while(rs.next()){
				System.out.println("QUERY GOOD");
				a = Integer.parseInt(rs.getString("brn_cd"));

			}
			return a;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error"+ e.getMessage(),e);
			return 0;
		}
	}

	public void update_ib(int brncd, int acctyp, int custno, int runno, int chkdgt, double amount, int curr_cd, int native_brn){
		int rs1, rs2,rs3,rs4;
		String ac1d = "";
		String ac2d = "";
		String ac3c = "";
		try {
			ac1d=get_gl_acc(native_brn,"coh",curr_cd );
			ac2d=get_gl_acc(brncd,"mo",curr_cd );
			ac3c=get_gl_acc(native_brn,"mo",curr_cd );
			rs1= this.lcl_stmt.executeUpdate("update SM27392.gl_acc_tl set available_balance = available_balance-'"+amount+"' where acc_no='"+ac1d+"'");
			rs2= this.lcl_stmt.executeUpdate("update SM27392.gl_acc_tl set available_balance = available_balance-'"+amount+"' where acc_no='"+ac2d+"'");
			rs3= this.lcl_stmt.executeUpdate("update SM27392.gl_acc_tl set available_balance = available_balance+'"+amount+"' where acc_no='"+ac3c+"'");
			rs4= this.lcl_stmt.executeUpdate("update SM27392.cust_acc_tl set available_balance=available_balance+'"+amount+"' where brn_cd='"+brncd+"' and acc_typ='"+acctyp+"' and cust_no='"+custno+"' and run_no='"+runno+"' and chk_dgt='"+chkdgt+"'");
			System.out.println("QUERY GOOD ib");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error"+ e.getMessage(),e);
			
		}
	}
	public List<Integer> tran_get(String vouch_no){
		List<Integer> lst = new ArrayList<Integer>();
		ResultSet rs;
		String v = vouch_no;
		try {
			rs = this.lcl_stmt.executeQuery("select trans_id from SM27392.transaction_tl t join SM27392.ledgers_tl l on t.voucher_no = l.voucher_no where t.voucher_no = '"+v+"'");
			while(rs.next()){
				System.out.println("QUERY GOOD");
				lst.add(Integer.parseInt(rs.getString("trans_id")));
			}
			return lst;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error"+ e.getMessage(),e);
			return null;
		}
	}
	
	public int get_currency(int cust_no){
		ResultSet rs;
		int a = 0;
		try {
			rs = this.lcl_stmt.executeQuery("select currency_cd from SM27392.cust_acc_tl  where cust_no = '"+cust_no+"'");
			if(rs.next()){
				System.out.println("QUERY GOOD");
				a = Integer.parseInt(rs.getString("currency_cd"));

			}
			return a;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error"+ e.getMessage(),e);
			return 0;
		}
	}
	public ArrayList GetVoucherDetails(String vouch_no){
		ArrayList<String> data = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = this.lcl_stmt.executeQuery("select posted_by, posted_date from SM27392.ledgers_tl where voucher_no = '"+vouch_no+"'");
			if(rs.next()){
			String posted_by = rs.getString("posted_by");
			String posted_date = rs.getString("posted_date");
			data.add(posted_by);
			data.add(posted_date);
			System.out.println(data.get(0));
			System.out.println("out");
			}
			return data;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
			return null;
		}
	}
	public String Getauth(String vouch_no){
		ResultSet rs;
		String authorized_by = "";
		try {
			rs = this.lcl_stmt.executeQuery("select authorized_by from SM27392.ledgers_tl where voucher_no = '"+vouch_no+"'");
			if(rs.next()){
			authorized_by = rs.getString("authorized_by");
			}
			return authorized_by;
			}
		catch (SQLException e) {
			logger.log(Level.SEVERE,"DB error"+ e.getMessage(),e);
		}
		return null;
	}
	
}
	
