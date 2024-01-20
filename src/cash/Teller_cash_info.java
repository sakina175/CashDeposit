package cash;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

	public class Teller_cash_info extends JDialog{
		private static final Logger logger=Logger_Manager.getLogger(Teller_cash_info.class);
		private DTO dto;
		private JTextField cus_no_field;
		private int brn_cd=0,brn_1=0;
		private ArrayList<Object> currencies = new ArrayList();
		private ArrayList<Object> branches = new ArrayList();
		private ArrayList<Object> acc_detail = new ArrayList();
		private JComboBox acc_cb = new JComboBox();
		private String cust_no=" ",brn_name=" ";
		String currency_ty,Message;
		String currency_type="";
		String accno="";
		String account="";
		public Teller_cash_info(final DTO mydto,final int type) {
			setTitle("Teller ");
			setResizable(false);
			setBounds(500,200,801,442);
			dto=mydto;
			brn_1=dto.getbranch_code();
			System.out.println("dto value in teller cash info is "+dto.batchID+mydto.getbatchID());
			getContentPane().setBackground(new Color(153,204,153));
			getContentPane().setLayout(null);
			
			JLabel lblPassword = new JLabel("Branch:");
			lblPassword.setBounds(22, 20, 76, 34);
			getContentPane().add(lblPassword);
			
			final Db_Operation db = new Db_Operation();
			currencies = db.GetCurrencies();

			final JComboBox brn_cb = new JComboBox();
			brn_cb.setBounds(150, 27, 248, 20);
			getContentPane().add(brn_cb);
			
			 
			if(type==0){
				brn_name = db.GetUserBranch(dto.getbranch_code());
				System.out.println(brn_name + " Branch Name");
				brn_cd= db.GetBranchCode(brn_name);
			      System.out.print("brn is updated as 222"+brn_cd);
			      String brn=String.format("%04d", brn_cd)+" - "+brn_name;
				brn_cb.addItem(brn);
			}
			if(type==1){
				branches=db.GetBranches(dto.getbranch_code());
				for(Object c : branches){
					brn_cb.addItem(c);
				}
				brn_cb.setSelectedIndex(0);
				 brn_name=brn_cb.getSelectedItem().toString();
				 brn_cd=Integer.parseInt(brn_name.substring(0,4));
//				brn_cb.setSelectedItem("KARACHI MAIN");
			}
			brn_cb.addActionListener(new ActionListener() {     
			     @Override
			     public void actionPerformed(ActionEvent e) {
			    	 acc_cb.removeAllItems();
			    	 cus_no_field.setText(null);
			    	 brn_name=brn_cb.getSelectedItem().toString();
			    	 brn_cd=Integer.parseInt(brn_name.substring(0,4));
				      System.out.print("brn is updated as "+brn_cd);
//			    	dto.setCurrency_type(currency_ty);   
			     }
			   });
			brn_cb.setEditable(false);
			final JButton btnSubmit = new JButton("OK");
			btnSubmit.setBackground(new Color(0,153,153));
			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					account=acc_cb.getSelectedItem().toString();
					if(account==null){
						JOptionPane.showMessageDialog(null, "Account not exist");
						cus_no_field.setText(null);
						acc_cb.removeAllItems();
					}
					else{
						accno= String.format("%04d", brn_cd) + "-" + cust_no;
						System.out.print("data in account is "+account+" lenght is "+account.length());
						dto.setaccno(account);
						final HashMap<String,Integer> map = dto.getaccno();
						System.out.print("dat is in ac_info "+map);
						System.out.print("old brNACH CODE IS "+brn_1+" and new one is "+brn_cd);
						final HashMap<String,String> map1=db.GetAccountDetails(brn_cd, map.get("acc_typ"), map.get("cust_no"), map.get("run_no"), map.get("chk_dgt"));
						if(map1.get("Condition")!=null){
							Message=map1.get("Condition");
							JOptionPane.showMessageDialog(null, Message);
							cus_no_field.setText(null);
							acc_cb.removeAllItems();
							btnSubmit.setEnabled(false);
						}
						else{
							Acc_Info ms = new Acc_Info(dto, cust_no,type,brn_1);
//							ms.setSize(585,585);
							ms.setVisible(true);
							setVisible(false);
						}
							
					}
						
					}
					
				
			});
			btnSubmit.setBounds(22, 192, 93, 28);
			getContentPane().add(btnSubmit);
			
			final JComboBox currency_cb = new JComboBox();
//			String currency_type="";
			currency_cb.setBounds(150, 67, 248, 20);	
			getContentPane().add(currency_cb);
			
			for(Object c : currencies){
				currency_cb.addItem(c);
			}
			

			currency_cb.setSelectedIndex(0);
			 currency_type=currency_cb.getSelectedItem().toString();
	    	 currency_ty=currency_type.substring(0,3);
	    	 System.out.println("oter value is Value: " +currency_ty); 
			currency_cb.addActionListener(new ActionListener() {     
			     @Override
			     public void actionPerformed(ActionEvent e) {
			    	 acc_cb.removeAllItems();
			    	 cus_no_field.setText(null);
			    	 currency_type=currency_cb.getSelectedItem().toString();
			    	 currency_ty=currency_type.substring(0,3);
//				      System.out.println("Value: " +currency_ty);   
//			    	dto.setCurrency_type(currency_ty);   
			     }
			   });
			  
//			Object currency=comboBox_1.getSelectedItem();
//			System.out.print("currency is "+currency);
			cus_no_field = new JTextField();
			cus_no_field.setBounds(150, 98, 93, 20);
			getContentPane().add(cus_no_field);
			cus_no_field.setColumns(6);
			cus_no_field.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e){
					cus_no_field.setEditable(true);
					System.out.println("focus gained of cus o");
					
				}
				@Override
				public void focusLost(FocusEvent arg0) {
					String s = cus_no_field.getText();
					if(s.length()<=5 && s.length()>0){
						int len = s.length();
						for(int i=0; i<=5-len ; i++){
							s= '0'+s;
						}
						cus_no_field.setText(s);
					}
				}
			});
			cus_no_field.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent arg0) {
//					acc_cb.
					if((arg0.getKeyChar() >= '0' && arg0.getKeyChar() <= '9') && (cus_no_field.getText().length()<=5) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
						cus_no_field.setEditable(true);
					}
					else{
						cus_no_field.setEditable(false);
					}
//					if((brn_cb.getSelectedItem()==null) || (cus_no_field.getText().length()==0) || (currency_cb.getSelectedItem()==null) || (acc_cb.getSelectedItem()==null)){
//						btnSubmit.setEnabled(false);
//					}
//					else{
//						btnSubmit.setEnabled(true);
//					}
				}}
			);
			
			
			final JRadioButton rdbtnAcNo = new JRadioButton("A/C No");
			rdbtnAcNo.setBackground(new Color(153,204,153));
			rdbtnAcNo.setBounds(274, 97, 73, 23);
			getContentPane().add(rdbtnAcNo);
			rdbtnAcNo.setSelected(true);
			boolean isSelected= rdbtnAcNo.isSelected();
			
			final JRadioButton rdbtnIban = new JRadioButton("IBAN");
			rdbtnIban.setBackground(new Color(153,204,153));
			rdbtnIban.setBounds(349, 97, 59, 23);
			getContentPane().add(rdbtnIban);
			rdbtnIban.setSelected(false);
			isSelected= rdbtnIban.isSelected();
			rdbtnIban.setEnabled(false);
			btnSubmit.setEnabled(false);
			
			ButtonGroup btg1 = new ButtonGroup();
			btg1.add(rdbtnAcNo);
			btg1.add(rdbtnIban);
			
			
			acc_cb.setBounds(150, 129, 248, 20);
			getContentPane().add(acc_cb);
			
			for(Object c : acc_detail){
				acc_cb.addItem(c);
			}
			
			JButton btnExit = new JButton("Exit");
			btnExit.setBackground(new Color(204,204,204));
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
				}
			});
			btnExit.setBackground(new Color(0,153,153));
			btnExit.setBounds(315, 192, 93, 28);
			getContentPane().add(btnExit);
			
			JLabel lblCurrency = new JLabel("Currency:");
			lblCurrency.setBounds(22, 60, 76, 34);
			getContentPane().add(lblCurrency);
			
			JLabel lblCustomerNo = new JLabel("Customer no:");
			lblCustomerNo.setBounds(22, 91, 76, 34);
			getContentPane().add(lblCustomerNo);
			
			JLabel lblAcNo = new JLabel("A/C No:");
			lblAcNo.setBounds(22, 122, 76, 34);
			getContentPane().add(lblAcNo);
			
			cus_no_field.addFocusListener (new FocusListener() {
				
				public void focusGained(FocusEvent e){
				}

				@Override
				public void focusLost(FocusEvent e) {
					acc_cb.removeAllItems();
					// TODO Auto-generated method stub
					System.out.print("focusLost");
					System.out.println("here in111 1"+brn_cb.getSelectedItem()+ " " +cus_no_field.getText().length()+" "+currency_cb.getSelectedItem()+" "+acc_cb.getSelectedItem());
					
					
					String ss = cus_no_field.getText().trim();
//					acc_detail.clear();
					if(!ss.isEmpty()){
						int input = Integer.parseInt(ss);
//						acc_detail = db.GetCusAccs(ss);
						int custo_no=Integer.parseInt(cus_no_field.getText().toString());
						cust_no = cus_no_field.getText().toString();
//						dto.setCurrency_type(currency_`ty);
						System.out.print("in teller cash module  "+brn_cd);
						acc_detail = db.GetAccs(cust_no,currency_ty,brn_cd);
						
						if(acc_detail!=null){
							for(Object c : acc_detail){
								acc_cb.addItem(c);
							}
							acc_cb.setSelectedIndex(0);
							account=acc_cb.getSelectedItem().toString();
							
							if((brn_cb.getSelectedItem()==null) || (cus_no_field.getText().length()==0) || (currency_cb.getSelectedItem()==null) || (acc_cb.getSelectedItem()==null)){
								System.out.println("here in 1"+brn_cb.getSelectedItem()+ " " +cus_no_field.getText().length()+" "+currency_cb.getSelectedItem()+" "+acc_cb.getSelectedItem());
								btnSubmit.setEnabled(false);
							}
							else{
								System.out.println("here in 423");
								btnSubmit.setEnabled(true);
							}
						}
						
					}
					
				}
				
				
			});
		}

		public void getBranchName(){
			Db_Operation db = new Db_Operation();
			brn_name = db.GetUserBranch(dto.getbranch_code());
			System.out.println(brn_name + " Branch Name");
		}
	}
