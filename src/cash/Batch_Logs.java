package cash;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;



public class Batch_Logs extends JFrame {
	private static final Logger logger=Logger_Manager.getLogger(Batch_Logs.class);
	
	private JTable table;
	   private JScrollPane scrollPane;
	   private final ButtonGroup buttonGroup = new ButtonGroup();
	   private JButton btnOk;
	   private JButton btnPrin;
	   public Acc_Info acci;
	   ArrayList details;
	   String vouchnos="";
	   DTO mydto1 = new DTO();
	   private JPanel panel;
	   private JPanel panel_2;
	   private JLabel Date;
	   private JLabel userDetail;
	   HashMap<String,ArrayList> map=new HashMap<String,ArrayList>();
	   public Batch_Logs() {
		setResizable(true);
		setBounds(500,200,900,341);
//		System.out.println("currency cod in voucher is"+currency);
		
//		this.acci = ai;
//		this.mydto1 = mydto;
//		this.details = details;
//		System.out.println ("batch from voucher s  "+mydto.batchID+" type is "+mydto.getbatchID());
//		System.out.println("type in voucher is "+type);
		Db_Operation db = new Db_Operation();
		Vector<String> acc_mo = new Vector<String>();
		map=db.getBatchHistory(mydto1.user_id,mydto1.batchID);
//		
//		
////		System.out.println("in voucher "+Integer.parseInt(details.get(0).toString()));
//		System.out.println("passed voucher code i "+details.get(0).toString());
//		map = db.getTransactions(details.get(0).toString());
//		System.out.println("data in map is "+map);
//		String ccy="";
//		String accno="";
//		if(currency.equals("PAKISTANI RUPEES-586")){
//			ccy = "PKR";
//		}
//		else{
//			ccy= "USD";
//		}
		getContentPane().setBackground(new Color(158,204,158));
//		System.out.println("data is function is "+mydto1);
//		System.out.println(mydto1.getaccno());
//		String accNoStr=mydto1.getaccStr();
//		System.out.println("dto value is  is "+accNoStr);
//		String brn1=db.GetBranchName(brn_1);
//		String brn2=db.GetBranchName(mydto1.brn_cd);
		setTitle("History");
	      String[] columnNames = {"Batch ID", "Trans No"};
//	      if(0==0){
	    	  Object[][] data={
		    		  {map.get("Batch").get(0), map.get("Transactions").get(0)}, 
		    		  			{map.get("Batch").get(1), map.get("Transactions").get(1)}};
		      
		      table = new JTable(data, columnNames) {
		         public boolean editCellAt(int row, int column, java.util.EventObject e) {
		            return false;
		        }
		      };
		      table.getColumnModel().getColumn(2).setPreferredWidth(200);
		      table.getColumnModel().getColumn(3).setPreferredWidth(250);
		      table.setFocusable(false);
//
//			}
//			if(1==1){
//				
//		    	  Object[][] data={
//			    		  {map.get("Vouchers").get(0), map.get("Transactions").get(0),brn1,acc_coh.get(0), acc_coh.get(1),  ccy, map.get("Debits").get(0),map.get("Credits").get(0)}, 
//			    		  {map.get("Vouchers").get(1), map.get("Transactions").get(1),brn1, acc_mo.get(0), acc_mo.get(1), ccy, map.get("Debits").get(1),map.get("Credits").get(1)},
//			    		  {map.get("Vouchers").get(2), map.get("Transactions").get(2),brn2,acc_mo_new.get(0), acc_mo_new.get(1),  ccy, map.get("Debits").get(2),map.get("Credits").get(2)},
//			    		  {map.get("Vouchers").get(3), map.get("Transactions").get(3),brn2,accNoStr, acc_title,  ccy, map.get("Debits").get(3),map.get("Credits").get(3)}};
//			      
//			      table = new JTable(data, columnNames) {
//			         public boolean editCellAt(int row, int column, java.util.EventObject e) {
//			            return false;
//			        }
//			      };
//			      table.getColumnModel().getColumn(2).setPreferredWidth(200);
//			      table.getColumnModel().getColumn(3).setPreferredWidth(250);
//			      table.setFocusable(false);
//
//			}
//	     	      table.addMouseListener(new MouseAdapter() {
//	         public void mouseClicked(MouseEvent me) {
////	            if (me.getClickCount() == 2) {     // to detect doble click events
////	               JTable target = (JTable)me.getSource();
////	               int row = target.getSelectedRow(); // select a row
////	               int column = target.getSelectedColumn(); // select a column
////	              JOptionPane.showMessageDialog(null, table.getValueAt(row, column)); // get the value of a row and column.
////	            }
//	         }
//	      });
	      getContentPane().setLayout(null);
	      
	      
	      scrollPane= new JScrollPane(table);
	      scrollPane.setBounds(10, 11, 862, 169);
	      getContentPane().add(scrollPane);
//	      getContentPane().add(getBtnPrin());
	      getContentPane().add(getBtnOk());
	      
	      JPanel panel_1 = new JPanel();
//	      panel_1.setBorder(new LineBorder(SystemColor.control));
	      panel_1.setBackground(new Color(158, 204, 158));
	      panel_1.setBounds(10, 209, 862, 58);
	      getContentPane().add(panel_1);
	      setLocationRelativeTo(null);
	      setVisible(true);
		
		//getContentPane().setLayout(null);
		// TODO Auto-generated constructor stub
	      
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setBackground(new Color(204,204,204));
			btnOk.setBounds(69, 226, 104, 23);
			btnOk.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
//					Dialog2 d = new Dialog2(acci, details, mydto1);
//					d.setSize(370,170);
//					d.setVisible(true);
//					setVisible(false);
				}
			});
		}
		return btnOk;
	}
	

}
