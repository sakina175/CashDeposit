package cash;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import java.awt.SystemColor;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Acc_Info extends JDialog{
	private static final Logger logger=Logger_Manager.getLogger(Acc_Info.class);
	private JTextField Acc_No;
	private JTextField Currency;
	private JTextField Exchange_Rate;
	private JTextField Acc_Title;
	private JTextField Acc_Type;
	private JTextField Available_Balance;
	private JTextField Local_Equivalent;
	private JTextField Receipt_No;
	private JTextField Amount;
	private JTextField Narration;
	private JTextField CustomerNameField;
	private JTextField IdNo;
	private JTextField Other;
	public Acc_Info ai;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	DTO mydto1 = new DTO();

	public void getScreen(Acc_Info aii){
		this.ai =aii;
		
	}
	public static String getFormattedAmount(double num,int len,int dec) {
		String lcl_frm="0";
		String lcl_frmtd="";
		int i;
		try
		{	
		if(dec>0)
			   {lcl_frm = "0.";
				for(i=0;i<dec;i++)
				   lcl_frm = lcl_frm + "0";
			   }
		if(len>dec)
			   {for(i=0;i<len-dec-1;i++)
				   {
				    if((i+1)%3==0)
				       lcl_frm  = "," + lcl_frm;
				    lcl_frm  = "#" + lcl_frm;
				   }
			   }
			   
		java.text.NumberFormat  nf = (java.text.NumberFormat) (new java.text.DecimalFormat(lcl_frm));
			
		lcl_frmtd = nf.format(num);
		}
		catch (Exception e)
		{
		 System.out.println("Error");
		}
		return lcl_frmtd;
	}	
	
	public static String getUnFormattedAmount(String formatedAmount) {
		
		String amount= formatedAmount=formatedAmount.replace(",", "");		
		String index = String.valueOf(formatedAmount.indexOf("."));
		System.out.println("data in formatting is "+amount.substring(0,Integer.parseInt(index)));
		return amount.substring(0,Integer.parseInt(index));
		
	}
	public Acc_Info(final DTO mydto,  String account_no,final int type,final int brn_1){
		mydto1 = mydto;
		System.out.println ("brn old is "+mydto.getbranch_code()+" type is "+type);
		System.out.println ("batch "+mydto.batchID+" type is "+mydto.getbatchID());
		setResizable(true);
		setBounds(100,100,968,666);
		getContentPane().setBackground(new Color(153, 204, 153));
		getContentPane().setLayout(null);
		
		final JPanel Additional_Info = new JPanel();
		Receipt_No = new JTextField();
		Amount = new JTextField();
		final JRadioButton radioButton = new JRadioButton("A/C Holder");
		
		
		final JRadioButton radioButton_1 = new JRadioButton("Walk-In Customer");
		
		radioButton_1.setSelected(true);
		final JButton receiveBtn = new JButton("Receive Cash/ Print Receipt");
		receiveBtn.setBackground(new Color(204,204,204));
		final Db_Operation db = new Db_Operation();

		final JLabel label_3 = new JLabel("A/C No");
		label_3.setBounds(393, 77, 68, 14);
		label_3.setVisible(false);
		Additional_Info.add(label_3);
		
		final JComboBox AccountNoField = new JComboBox();
		Acc_No = new JTextField();
		Currency = new JTextField();
		Exchange_Rate = new JTextField();
		Acc_Title = new JTextField();
		Available_Balance = new JTextField();
		Local_Equivalent = new JTextField();
		IdNo = new JTextField();
		AccountNoField.setVisible(false);
		
		final JComboBox Purpose = new JComboBox();
		Purpose.setSelectedItem("BANKERS CHEQUE REMITTANCE");
		final JComboBox IdType = new JComboBox();
		String account=db.GetAcc(account_no,mydto1.getbranch_code());
		System.out.print(account);
		String accno= String.format("%04d", mydto1.getbranch_code()) + "-" + account_no;
		final HashMap<String,Integer> map = mydto1.getaccno();
		final HashMap<String,String> map1=db.GetAccountDetails(map.get("brn_cd"), map.get("acc_typ"), map.get("cust_no"), map.get("run_no"), map.get("chk_dgt"));
		Double LocalEquiv = ((Double.parseDouble(map1.get("AvailableBalance"))) * (Double.parseDouble(map1.get("ExchangeRate"))));
		Acc_No.setText(account_no);
		Acc_No.setEditable(false);
		Acc_Title.setText(map1.get("Title"));
		Currency.setText(map1.get("Currency"));
		Available_Balance.setText((getFormattedAmount(Double.parseDouble(map1.get("AvailableBalance")),40,2)));
		Exchange_Rate.setText(getFormattedAmount(Double.parseDouble(map1.get("ExchangeRate")),40,2));
		Local_Equivalent.setText(getFormattedAmount(LocalEquiv,40,2));
		Acc_Title.setEditable(false);
		Currency.setEditable(false);
		Exchange_Rate.setEditable(false);
		Available_Balance.setEditable(false);
		Local_Equivalent.setEditable(false);
		setTitle("Teller - Deposit ");
		JLabel lblAcInformation = new JLabel("A/C Information");
		lblAcInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAcInformation.setBounds(20, 11, 114, 23);
		getContentPane().add(lblAcInformation);
		JLabel lblNewLabel = new JLabel("A/C No.");
		lblNewLabel.setBounds(30, 52, 46, 14);
		getContentPane().add(lblNewLabel);
		if(radioButton_1.isSelected()){AccountNoField.setModel(new DefaultComboBoxModel());}
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(30, 77, 46, 14);
		getContentPane().add(lblTitle);
		
		JLabel lblCurrency = new JLabel("Currency");
		lblCurrency.setBounds(30, 102, 89, 14);	
		getContentPane().add(lblCurrency);
		
		JLabel lblExchangeRate = new JLabel("Exchange Rate");
		lblExchangeRate.setBounds(30, 127, 89, 14);
		getContentPane().add(lblExchangeRate);
		
		Acc_No.setBounds(117, 49, 169, 20);
		getContentPane().add(Acc_No);
		Acc_No.setColumns(10);
		
		Currency.setColumns(10);
		Currency.setBounds(117, 99, 169, 20);
		getContentPane().add(Currency);
		
		Exchange_Rate.setColumns(10);
		Exchange_Rate.setBounds(117, 124, 105, 20);
		getContentPane().add(Exchange_Rate);
		
		Acc_Title.setColumns(10);
		Acc_Title.setBounds(117, 74, 260, 20);
		getContentPane().add(Acc_Title);
		
		JLabel lblacc = new JLabel("A/C Type");
		lblacc.setBounds(296, 52, 61, 14);
		getContentPane().add(lblacc);
		
		JLabel lbl = new JLabel("Available Balance");
		lbl.setBounds(296, 102, 114, 14);
		getContentPane().add(lbl);
		
		JLabel lblLocalEquivalent = new JLabel("Local Equivalent");
		lblLocalEquivalent.setBounds(296, 127, 105, 14);
		getContentPane().add(lblLocalEquivalent);
		
		Available_Balance.setColumns(10);
		Available_Balance.setBounds(408, 99, 144, 20);
		getContentPane().add(Available_Balance);
		
		Local_Equivalent.setColumns(10);
		Local_Equivalent.setBounds(408, 124, 144, 20);
		getContentPane().add(Local_Equivalent);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setBackground(new Color(158, 204, 158));
		panel.setBounds(20, 34, 922, 125);
		getContentPane().add(panel);
		Acc_Type = new JTextField();
		panel.add(Acc_Type);
		Acc_Type.setText(map1.get("Type"));
		Acc_Type.setEditable(false);
		
		Acc_Type.setColumns(10);
		
		JLabel lblReceiptNo = new JLabel("Receipt No.");
		lblReceiptNo.setBounds(30, 181, 68, 14);
		getContentPane().add(lblReceiptNo);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(30, 216, 68, 14);
		getContentPane().add(lblAmount);
		
		JLabel lblNarration = new JLabel("Narration");
		lblNarration.setForeground(Color.BLUE);
		lblNarration.setBounds(30, 247, 68, 14);
		getContentPane().add(lblNarration);
		
		Receipt_No.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if((arg0.getKeyChar() >= '0' && arg0.getKeyChar() <= '9') && (Receipt_No.getText().length()<12) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
					Receipt_No.setEditable(true);
				}
				else{
					Receipt_No.setEditable(false);
				}
			}
		});
		Receipt_No.setColumns(10);
		Receipt_No.setBounds(153, 178, 128, 20);
		getContentPane().add(Receipt_No);
		Amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {

				if(Character.isLetter(arg0.getKeyChar()) || Amount.getText().length()>11 || Character.isSpaceChar(arg0.getKeyChar())){
					arg0.consume();
				}
				try{
					Double.parseDouble(Amount.getText()+arg0.getKeyChar());
				}
				catch(NumberFormatException e){
					arg0.consume();
				
				}
			}
		});
		Amount.setColumns(10);
		Amount.setBounds(153, 213, 151, 20);
		getContentPane().add(Amount);
		
		Narration = new JTextField();
		Narration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(Narration.getText().length()<30 || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
					Narration.setEditable(true);
				}
				else{
					Narration.setEditable(false);
				}
			}
		});
		Narration.setColumns(10);
		Narration.setBounds(153, 244, 169, 20);
		getContentPane().add(Narration);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(SystemColor.control));
		panel_1.setBackground(new Color(158, 204, 158));
		panel_1.setBounds(660, 206, 268, 92);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_4 = new JLabel("Purpose");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(10, 14, 68, 14);
		panel_1.add(label_4);
		Purpose.setBounds(65, 11, 188, 20);
		panel_1.add(Purpose);
		
		final JPanel Other_Panel = new JPanel();
		Other_Panel.setBorder(null);
		Other_Panel.setBackground(new Color(158, 204, 158));
		Other_Panel.setForeground(new Color(153, 204, 153));
		Other_Panel.setBounds(10, 51, 248, 30);
		panel_1.add(Other_Panel);
		Other_Panel.setLayout(null);
		
		JLabel label_5 = new JLabel("Other");
		label_5.setBounds(0, 3, 61, 14);
		Other_Panel.add(label_5);
		
		Other = new JTextField();
		Other.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(Other.getText().length()<20 || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
					Other.setEditable(true);
				}
				else{
					Other.setEditable(false);
				}
			}
		});
		Other.setColumns(10);
		Other.setBounds(56, 0, 187, 20);
		Other_Panel.add(Other);
		Other_Panel.setVisible(false);
		
		receiveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double amount1 = 0;
				if(Amount.getText().length()!=0 ){
				Double amount2 = Double.parseDouble(getUnFormattedAmount(Amount.getText()));
				amount1 = Math.round(amount2 * 100.0) / 100.0;}
				String acc_no = Acc_No.getText();
				String acc_title = Acc_Title.getText();
				String currency = Currency.getText();
				String acc_type = Acc_Type.getText();
				String exchange_rate = Exchange_Rate.getText();
				String available_balance = Available_Balance.getText();
				String local_equivalent = Local_Equivalent.getText();
				String receipt_no = Receipt_No.getText();
				String amount = Amount.getText();
//				System.out.print(Double.parseDouble(amount));
				String narration = Narration.getText();
				String purpose = Purpose.getSelectedItem().toString();
				String other = Other.getText();
				String idtype = IdType.getSelectedItem().toString();
				String idno = IdNo.getText();
				String customername = CustomerNameField.getText();
				String account="";
				if(AccountNoField.getSelectedItem() != null){
				account = AccountNoField.getSelectedItem().toString();}
				if(AccountNoField.getSelectedItem() != null){
				account = AccountNoField.getSelectedItem().toString();}
				if(Purpose.getSelectedItem().toString().equals("EDUCATION") && (Amount.getText().length()==0  || Receipt_No.getText().length()==0 || amount1==0)){
					JOptionPane.showMessageDialog(null, "Please fill all the fields. 1");
					System.out.print("educ");
				}
				else if(Purpose.getSelectedItem().toString().equals("EDUCATION") && (Amount.getText().length()!=0 || Receipt_No.getText().length()!=0)){
					ArrayList details = db.insertvoucher(mydto.batchID,map.get("brn_cd"), map.get("acc_typ"), map.get("cust_no"), map.get("run_no"), map.get("chk_dgt"),mydto.getuser_id(), acc_no, acc_title, currency, acc_type, exchange_rate, available_balance, local_equivalent, receipt_no, amount1, narration, purpose, other, idtype, idno, customername, account,type,brn_1);
//					
					
					Voucher v= new Voucher(ai, details, mydto1, currency, acc_no, acc_title,type,brn_1);
//					v.setSize(700,330);
					v.setVisible(true);
					v.setModal(false);
					v.dispose();
					dispose();
				}
//				else if((Purpose.getSelectedItem().toString().equals("BANKERS CHEQUE REMITTANCE") || Purpose.getSelectedItem().toString().equals("BUSINESS") || Purpose.getSelectedItem().toString().equals("FAMILY SUPPORT") || Purpose.getSelectedItem().toString().equals("LOAN INSTALLMENT") || Purpose.getSelectedItem().toString().equals("ONLINE PURCHASE") || Purpose.getSelectedItem().toString().equals("TRANSPORT")) && (Amount.getText().length()==0 || Narration.getText().length()==0 || Receipt_No.getText().length()==0) ||  (((radioButton.isSelected()) && (IdNo.getText().length()==0 || CustomerNameField.getText().length()==0 || AccountNoField.getSelectedItem()==null)) || ((radioButton_1.isSelected()) && (IdNo.getText().length()==0 || CustomerNameField.getText().length()==0)) || ((IdType.getSelectedItem().toString().equals("CNIC/SNIC") || IdType.getSelectedItem().toString().equals("NICOP") || IdType.getSelectedItem().toString().equals("POC")) &&(IdNo.getText().length()!=15)) || ((IdType.getSelectedItem().toString().equals("PASSPORT")) &&(IdNo.getText().length()!=10)) ||  ((IdType.getSelectedItem().toString().equals("POR")) &&(IdNo.getText().length()!=13)))){
//					JOptionPane.showMessageDialog(null, "Please fill all the fields.2");
//					System.out.print(Purpose.getSelectedItem().toString()!="EDUCATION");
//					System.out.print("all");
//				}
				else if((Purpose.getSelectedItem().toString().equals("OTHER")) && (Amount.getText().length()==0 || Receipt_No.getText().length()==0 || Other.getText().length()==0) || (((radioButton.isSelected()) && (IdNo.getText().length()==0 || CustomerNameField.getText().length()==0 || AccountNoField.getSelectedItem()==null)) || ((radioButton_1.isSelected()) && (IdNo.getText().length()==0 || CustomerNameField.getText().length()==0)) )){
					JOptionPane.showMessageDialog(null, "Please fill all the fields.");
					System.out.print("other");
				}
				else if(amount1 <=0){
					JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
				}
				else{
					 available_balance = getUnFormattedAmount(Available_Balance.getText());
					if(AccountNoField.getSelectedItem() != null){
					account = AccountNoField.getSelectedItem().toString();}
					ArrayList details = db.insertvoucher(mydto.batchID,map.get("brn_cd"), map.get("acc_typ"), map.get("cust_no"), map.get("run_no"), map.get("chk_dgt"),mydto.getuser_id(), acc_no, acc_title, currency, acc_type, exchange_rate, available_balance, local_equivalent, receipt_no, amount1, narration, purpose, other, idtype, idno, customername, account,type,brn_1);
					Voucher v= new Voucher(ai, details, mydto1, currency, acc_no, acc_title,type,brn_1);
//					v.setModal(false);
//					v.dispose();
////					setModal(true);
//					v.setVisible(true);
//					
//					dispose();
//					
					v.setVisible(true);
					v.setModal(false);
					v.dispose();
					dispose();
				}}
			//	}
			
		});
		
		receiveBtn.setBounds(20, 486, 215, 23);
		getContentPane().add(receiveBtn);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(204,204,204));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
			}
		});
		btnBack.setBounds(589, 486, 89, 23);
		getContentPane().add(btnBack);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(204,204,204));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnExit.setBounds(723, 486, 89, 23);
		getContentPane().add(btnExit);
		
		Additional_Info.setBorder(new LineBorder(SystemColor.control));
		Additional_Info.setBackground(new Color(158, 204, 158));
		Additional_Info.setBounds(10, 338, 833, 113);
		getContentPane().add(Additional_Info);
		Additional_Info.setLayout(null);
	
		buttonGroup.add(radioButton);
		radioButton.setBackground(new Color(158, 204, 158));
		radioButton.setBounds(17, 7, 97, 23);
		Additional_Info.add(radioButton);
		
		buttonGroup.add(radioButton_1);
		radioButton_1.setBackground(new Color(158, 204, 158));
		radioButton_1.setBounds(117, 7, 131, 23);
		Additional_Info.add(radioButton_1);
		
		JLabel label = new JLabel("ID Type");
		label.setBounds(10, 52, 68, 14);
		Additional_Info.add(label);
		IdType.setModel(new DefaultComboBoxModel(db.GetIdentifications().toArray()));
		IdType.setBounds(75, 49, 177, 20);
		Additional_Info.add(IdType);
		
		JLabel label_1 = new JLabel("Customer Name");
		label_1.setBounds(393, 52, 95, 14);
		Additional_Info.add(label_1);
		
		CustomerNameField = new JTextField();
		CustomerNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(((arg0.getKeyChar() >= 'a' && arg0.getKeyChar() <= 'z')|| (arg0.getKeyChar() >= 'A' && arg0.getKeyChar() <= 'Z') || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE) || (arg0.getKeyChar()==KeyEvent.VK_SPACE)) && (CustomerNameField.getText().length()<=17) ){
					CustomerNameField.setEditable(true);
				}
//				if((arg0.getKeyChar()==KeyEvent.VK_SPACE)){
//					CustomerNameField.setEditable(true);
//				}
				else
				{
					CustomerNameField.setEditable(false);
				}
			}
		});
		CustomerNameField.setColumns(10);
		CustomerNameField.setBounds(539, 49, 169, 20);
		Additional_Info.add(CustomerNameField);
		
		JLabel label_2 = new JLabel("ID No");
		label_2.setBounds(10, 76, 68, 14);
		Additional_Info.add(label_2);
		
		IdNo.setColumns(10);
		IdNo.setBounds(75, 77, 177, 20);
		Additional_Info.add(IdNo);
		IdType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				IdNo.setText(null);
				IdNo.setEditable(true);
				CustomerNameField.setText(null);
				AccountNoField.setModel(new DefaultComboBoxModel());
			}
		});
		IdNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {                                                               
				if(IdType.getSelectedItem().toString().equals("CNIC/SNIC") || IdType.getSelectedItem().toString().equals("NICOP") || IdType.getSelectedItem().toString().equals("POC") ){
					if((arg0.getKeyChar() >= '0' && arg0.getKeyChar() <= '9') && (IdNo.getText().length()<13) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
						IdNo.setEditable(true);
					}
					else {
						IdNo.setEditable(false);
//						HashMap<String,String> namemap = db.GetCustomerName(IdNo.getText(), IdType.getSelectedItem().toString());
//						HashMap<String,ArrayList> accountsmap = db.GetCustomerAccounts(IdNo.getText(), IdType.getSelectedItem().toString());
//						if(namemap.size() != 0 && accountsmap.size() != 0){
//							System.out.println("here i am 1");
//						radioButton.setSelected(true);
//						CustomerNameField.setText(namemap.get("CustomerName"));
//						AccountNoField.setVisible(true);
//						CustomerNameField.setEditable(false);
//						AccountNoField.setModel(new DefaultComboBoxModel(accountsmap.get("AccountNos").toArray()));
//						radioButton_1.setEnabled(false);
//						}
//						else{
//							System.out.println("here i am 22");
//							CustomerNameField.setText(null);
//							CustomerNameField.setEditable(true);
//							AccountNoField.setModel(new DefaultComboBoxModel());
//							radioButton_1.setSelected(true);
//							radioButton_1.setEnabled(true);
//						}
					}
				}
				else if(IdType.getSelectedItem().toString().equals("PASSPORT")){
					if((IdNo.getText().length()<10) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
						IdNo.setEditable(true);
					}
//					else {
//						IdNo.setEditable(false);
//						HashMap<String,String> namemap = db.GetCustomerName(IdNo.getText(), IdType.getSelectedItem().toString());
//						HashMap<String,ArrayList> accountsmap = db.GetCustomerAccounts(IdNo.getText(), IdType.getSelectedItem().toString());
//					//	System.out.print((namemap!=null && accountsmap!=null));
//						if(namemap.size()!=0 && accountsmap.size()!=0){
//						radioButton.setSelected(true);
//						CustomerNameField.setText(namemap.get("CustomerName"));
//						CustomerNameField.setEditable(false);
//						AccountNoField.setModel(new DefaultComboBoxModel(accountsmap.get("AccountNos").toArray()));
//						radioButton_1.setEnabled(false);
//						}
//						else{
//							CustomerNameField.setText(null);
//							CustomerNameField.setEditable(true);
//							AccountNoField.setModel(new DefaultComboBoxModel());
//							radioButton_1.setSelected(true);
//							radioButton_1.setEnabled(true);
//						}
//					}
				}
				else if(IdType.getSelectedItem().toString().equals("POR")){
					if((IdNo.getText().length()<13) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
						IdNo.setEditable(true);
					}
//					else {
//						IdNo.setEditable(false);
//						HashMap<String,String> namemap = db.GetCustomerName(IdNo.getText(), IdType.getSelectedItem().toString());
//						HashMap<String,ArrayList> accountsmap = db.GetCustomerAccounts(IdNo.getText(), IdType.getSelectedItem().toString());
//						if(namemap.size()!=0 && accountsmap.size()!=0){
//						radioButton.setSelected(true);
//						CustomerNameField.setText(namemap.get("CustomerName"));
//						CustomerNameField.setEditable(false);
//						AccountNoField.setModel(new DefaultComboBoxModel(accountsmap.get("AccountNos").toArray()));
//						radioButton_1.setEnabled(false);
//						}
//						else{
//							CustomerNameField.setText(null);
//							CustomerNameField.setEditable(true);
//							AccountNoField.setModel(new DefaultComboBoxModel());
//							radioButton_1.setSelected(true);
//							radioButton_1.setEnabled(true);
//						}
//					}
				}
			}
		});
		IdNo.addFocusListener (new FocusListener() {
			
			public void focusGained(FocusEvent e){

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				System.out.println("focusLost");
				String IdVal = IdNo.getText().trim();
				String id_type=IdType.getSelectedItem().toString();
				Vector<String> result = db.GetIDAcc(id_type,IdVal);
				System.out.println("id value is "+IdVal+" and id type is "+id_type);
				String cust_name=db.GetIDAccName(id_type,IdVal);
				System.out.println("result in return is "+cust_name);
				if(cust_name!=null && result!=null){
					radioButton.setSelected(true);
					CustomerNameField.setText(cust_name);
					
					System.out.println("focusLost"+result);
					AccountNoField.removeAllItems();
					for(Object c : result){
						AccountNoField.addItem(c);
					}
					AccountNoField.setVisible(true);
					label_3.setVisible(true);
				}
				else{
					AccountNoField.setVisible(false);
					label_3.setVisible(false);
					CustomerNameField.setText(null);
					CustomerNameField.setEditable(true);
					AccountNoField.setModel(new DefaultComboBoxModel());
					radioButton_1.setSelected(true);
					radioButton_1.setEnabled(true);
				}
			
				}
			
			
			
		});
//		final JComboBox AccountNoField = new JComboBox();
		AccountNoField.setBounds(539, 80, 167, 20);
		Additional_Info.add(AccountNoField);
		
		
		Amount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!(Amount.getText().equals(""))){
				Amount.setText(getFormattedAmount(Double.parseDouble(Amount.getText()),40,2));
				String Amount1 = getUnFormattedAmount(Amount.getText());
				String ExchangeRate1=Exchange_Rate.getText();
				Double rate = Double.parseDouble(ExchangeRate1);
				Double Amount = Double.parseDouble(Amount1);
				Double TotalAmount = rate * Amount;
				if(TotalAmount>50000 && !(Purpose.getSelectedItem().toString().equals("EDUCATION"))){
					Additional_Info.setVisible(true);
			}
				}}});
		Purpose.setModel(new DefaultComboBoxModel(db.GetPurposes().toArray()));
		
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AccountNoField.setVisible(false);
				label_3.setVisible(false);
				IdNo.setText(null);
				CustomerNameField.setText(null);
			}
		});
		
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AccountNoField.setVisible(true);
				AccountNoField.removeAllItems();
				label_3.setVisible(true);
			}
		});
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(SystemColor.control, 2));
		panel_2.setBackground(new Color(158, 204, 158));
		panel_2.setBounds(0, 606, 163, 21);
		getContentPane().add(panel_2);
		
		JLabel Date = new JLabel("<dynamic> - <dynamic>");
		Date.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Date.setBounds(10, 0, 153, 21);
		panel_2.add(Date);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new LineBorder(SystemColor.control, 2));
		panel_4.setBackground(new Color(158, 204, 158));
		panel_4.setBounds(164, 606, 788, 21);
		getContentPane().add(panel_4);
		
		JLabel userDetail = new JLabel("0 - <dynamic>");
		userDetail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		userDetail.setBounds(10, 0, 119, 21);
		panel_4.add(userDetail);
		Purpose.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(Purpose.getSelectedItem().toString().equals("EDUCATION")){
					Additional_Info.setVisible(false);
					Other_Panel.setVisible(false);
				}
				else if(Purpose.getSelectedItem().toString().equals("OTHER")){
					Other_Panel.setVisible(true);
					CustomerNameField.setText(null);
					IdNo.setText(null);
					IdNo.setEditable(true);
					CustomerNameField.setEditable(true);
					AccountNoField.setModel(new DefaultComboBoxModel());
					Additional_Info.setVisible(true);
				}
				else{
					Other_Panel.setVisible(false);
					CustomerNameField.setText(null);
					IdNo.setText(null);
					IdNo.setEditable(true);
					CustomerNameField.setEditable(true);
					AccountNoField.setModel(new DefaultComboBoxModel());
					Additional_Info.setVisible(true);
				}
			}
		});
//		radioButton_1.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent arg0) {
//				if(radioButton_1.isSelected()){
//					accHolder.setVisible(false);
//				}
//				else{
//					accHolder.setVisible(true);
//				}
//			}
//		});
//		
		Date.setText(mydto.getDay()+" - "+ mydto.getDate());
		int brncd=mydto.getbranch_code();
		String branch = Integer.toString(brncd);
		userDetail.setText(branch +" - "+mydto.getuser_id());
		
		JPanel panel_3 = new JPanel();
		panel_3.setForeground(SystemColor.menu);
		panel_3.setBorder(new LineBorder(SystemColor.control));
		panel_3.setBackground(new Color(158, 204, 158));
		panel_3.setBounds(10, 476, 833, 44);
		getContentPane().add(panel_3);
	}
}
