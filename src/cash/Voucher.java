package cash;

import javax.swing.JDialog;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

public class Voucher extends JDialog {
	private static final Logger logger=Logger_Manager.getLogger(Voucher.class);	
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
	   int flag1;
	   Db_Operation db = new Db_Operation();
	   HashMap<String,ArrayList> map=new HashMap<String,ArrayList>();
	public Voucher(final Acc_Info ai, ArrayList details, DTO mydto, String currency, String acc_no, String acc_title,int type,int brn_1) {
		flag1=type;
		setResizable(true);
		setModal(true);
		setBounds(500,200,900,341);
		System.out.println("currency cod in voucher is"+currency);
		this.acci = ai;
		this.mydto1 = mydto;
		this.details = details;
		System.out.println ("batch from voucher s  "+mydto.batchID+" type is "+mydto.getbatchID());
		System.out.println("type in voucher is "+type);
		int currency_cd=db.GetCurrencyCode(currency);
		Vector<String> acc_mo = new Vector<String>();
		acc_mo=db.get_gl_acc_title(brn_1, "mo",currency_cd);
		System.out.println("data in eleme is "+acc_mo.get(0)+acc_mo.get(1));
		Vector<String> acc_mo_new=db.get_gl_acc_title(mydto.brn_cd, "mo",currency_cd);
		System.out.println("data in eleme is "+acc_mo_new.get(0)+acc_mo_new.get(1));
		Vector<String> acc_coh=db.get_gl_acc_title(brn_1, "coh",currency_cd);
		System.out.println("data in eleme is "+acc_coh.get(0)+acc_coh.get(1));
		Vector<String> acc_coh_new=db.get_gl_acc_title(mydto.brn_cd, "coh",currency_cd);
		System.out.println("data in eleme is "+acc_coh_new.get(0)+acc_coh_new.get(1));
		
		
//		System.out.println("in voucher "+Integer.parseInt(details.get(0).toString()));
		System.out.println("passed voucher code i "+details.get(0).toString());
		map = db.getTransactions(details.get(0).toString());
		System.out.println("data in map is "+map);
		String ccy="";
		String accno="";
		if(currency.equals("PAKISTANI RUPEES-586")){
			ccy = "PKR";
		}
		else{
			ccy= "USD";
		}
		getContentPane().setBackground(new Color(158,204,158));
		System.out.println("data is function is "+mydto1);
		System.out.println(mydto1.getaccno());
		String accNoStr=mydto1.getaccStr();
		System.out.println("dto value is  is "+accNoStr);
		String brn1=db.GetBranchName(brn_1);
		String brn2=db.GetBranchName(mydto1.brn_cd);
		setTitle("Voucher");
	      String[] columnNames = {"Vouch No", "Trans No","Branch","Account No","A/C Title","CCY","DR","CR"};
	      if(type==0){
	    	  Object[][] data={
		    		  {map.get("Vouchers").get(0), map.get("Transactions").get(0),brn2,acc_coh.get(0), acc_coh.get(1),  ccy,map.get("Credits").get(0), map.get("Debits").get(0)}, 
		    		  			{map.get("Vouchers").get(1), map.get("Transactions").get(1),brn2, accNoStr, acc_title, ccy,map.get("Credits").get(1), map.get("Debits").get(1)}};
		      
		      table = new JTable(data, columnNames) {
		         public boolean editCellAt(int row, int column, java.util.EventObject e) {
		            return false;
		        }
		      };
		      table.getTableHeader().setReorderingAllowed(false);
		      table.getColumnModel().getColumn(2).setPreferredWidth(200);
		      table.getColumnModel().getColumn(3).setPreferredWidth(250);
		      table.setFocusable(false);

			}
			if(type==1){
				
		    	  Object[][] data={
			    		  {map.get("Vouchers").get(0), map.get("Transactions").get(0),brn1,acc_coh.get(0), acc_coh.get(1),  ccy, map.get("Debits").get(0),map.get("Credits").get(0)}, 
			    		  {map.get("Vouchers").get(1), map.get("Transactions").get(1),brn1, acc_mo.get(0), acc_mo.get(1), ccy, map.get("Debits").get(1),map.get("Credits").get(1)},
			    		  {map.get("Vouchers").get(2), map.get("Transactions").get(2),brn2,acc_mo_new.get(0), acc_mo_new.get(1),  ccy, map.get("Debits").get(2),map.get("Credits").get(2)},
			    		  {map.get("Vouchers").get(3), map.get("Transactions").get(3),brn2,accNoStr, acc_title,  ccy, map.get("Debits").get(3),map.get("Credits").get(3)}};
			      
			      table = new JTable(data, columnNames) {
			         public boolean editCellAt(int row, int column, java.util.EventObject e) {
			            return false;
			        }
			      };
			      table.getColumnModel().getColumn(2).setPreferredWidth(200);
			      table.getColumnModel().getColumn(3).setPreferredWidth(250);
			      table.setFocusable(false);

			}
	     	      table.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
//	            if (me.getClickCount() == 2) {     // to detect doble click events
//	               JTable target = (JTable)me.getSource();
//	               int row = target.getSelectedRow(); // select a row
//	               int column = target.getSelectedColumn(); // select a column
//	              JOptionPane.showMessageDialog(null, table.getValueAt(row, column)); // get the value of a row and column.
//	            }
	         }
	      });
	      getContentPane().setLayout(null);
	      
	      
	      scrollPane= new JScrollPane(table);
	      scrollPane.setBounds(10, 11, 862, 169);
	      getContentPane().add(scrollPane);
	      getContentPane().add(getBtnPrin());
	      getContentPane().add(getBtnOk());
	      
	      JPanel panel_1 = new JPanel();
	      panel_1.setBorder(new LineBorder(SystemColor.control));
	      panel_1.setBackground(new Color(158, 204, 158));
	      panel_1.setBounds(10, 209, 862, 58);
	      getContentPane().add(panel_1);
	     
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      setLocationRelativeTo(null);
	      setVisible(true);
		
		//getContentPane().setLayout(null);
		// TODO Auto-generated constructor stub
	      
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setBackground(new Color(0,153,153));
			btnOk.setBounds(69, 226, 104, 23);
			btnOk.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					boolean flag=db.getTransStatus(map.get("Vouchers").get(0).toString());
					System.out.println("flag is "+flag);
					String message="";
					if(flag){
						setModal(false);
						message="Transaction Successfully";
						JOptionPane.showMessageDialog(null,message);
						dispose();
					}
					else{
						setModal(false);
						message="Transaction Need Authorization";
						JOptionPane.showMessageDialog(null,message);
						dispose();
					}
//					setModal(false);
					
//					setModal(false);	
					
					
				}
			});
		}
		
		return btnOk;
	}
	
	private JButton getBtnPrin() {
		if (btnPrin == null) {
			btnPrin = new JButton("Print");
			btnPrin.setBackground(new Color(0,153,153));
			btnPrin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mydto1.setvouch_no(map.get("Vouchers").get(0).toString());
				
					if( flag1 == 0){
						mydto1.setvouch_type("General Voucher");
					}
					if( flag1 == 1){
						mydto1.setvouch_type("Inter Branch");
					}
					Voucher_display vd=new Voucher_display(mydto1);
					vd.setVisible(true);
					dispose();
//					vd.setSize(800,500);
//					String name="";
//					name+="\t\t\t\t\tGERNEL VOUCHER\n";
//					name+=map.get("Vouchers").get(0).toString();
//					String file_name = "D:/ariba/workspace/Cash_Deposit/src/pdfs/"+name+".pdf";
//					File filename = new File("D:/ariba/workspace/Cash_Deposit/src/pdfs/"+name+".pdf");
//					try {
//						
//			            boolean print = table.print();
//			            if (!print) {
//			                JOptionPane.showMessageDialog(null, "Could not print.");
//			            }
//			        } catch (PrinterException ex) {
//			            JOptionPane.showMessageDialog(null, ex.getMessage());
//			        }
//					Document document = new Document();
//					try {
//						PdfWriter.getInstance(document, new FileOutputStream(file_name));
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (DocumentException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						System.out.print(e);
//					}
//					document.open();
//					
//					Paragraph para = new Paragraph("This is testing");
////					para.setFont(Font.BOLD)
//					para.setAlignment(Element.ALIGN_MIDDLE);
//					
//					
//					try {
//						document.add(para);
//						document.addTitle("TRANSACTION VOUCHER");
//					} catch (DocumentException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						System.out.print(e);
//					}
//					document.close();
//					try {
//						Desktop.getDesktop().open(filename);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			});
			btnPrin.setBounds(713, 226, 110, 23);
		}
		return btnPrin;
	}
}
