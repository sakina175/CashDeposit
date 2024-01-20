package cash;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;

public class Main_Screen extends JDialog{
	private static final Logger logger=Logger_Manager.getLogger(Main_Screen.class);
	Db_Operation db=new Db_Operation();
	DTO mydto1 = new DTO();
	public Main_Screen(ArrayList arr, DTO mydto,int act_id) {
		setResizable(false);
		setBounds(100,100,1124,689);
		mydto1 = mydto;
//		System.out.print(mydto1.branch_code+mydto1.user_id+"pass data is "+mydto);
		final JButton Receive_Deposit_Btn = new JButton("Receive Deposit");
//		final JButton Special_Conditions_Btn = new JButton("Special Conditions");
		JButton Authorization_Btn = new JButton("Authorization");
		final JButton Cancel_Transaction_Btn = new JButton("Cancel Transaction");
//		Cancel_Transaction_Btn.setVisible(false);
		JButton Reports_Btn = new JButton("Reports");
//		Receive_Deposit_Btn.setEnabled(false);
//		Special_Conditions_Btn.setEnabled(false);
		Authorization_Btn.setEnabled(false);
		Cancel_Transaction_Btn.setEnabled(false);
		Reports_Btn.setEnabled(false);
		for(int i=0;i<=arr.size()-1;i++){
			if(arr.get(i).equals("Receive Deposit")){
				Receive_Deposit_Btn.setEnabled(true);
			}
//			else if(arr.get(i).equals("Special Conditions")){
////				Special_Conditions_Btn.setEnabled(true);
//			}
			else if(arr.get(i).equals("Authorization")){
				Authorization_Btn.setEnabled(true);
			}
			else if(arr.get(i).equals("Cancel Transaction")){
				Cancel_Transaction_Btn.setEnabled(true);
			}
			else if(arr.get(i).equals("Reports")){
				Reports_Btn.setEnabled(true);
			}
		}
		
//		if (mydto1.user_id.equals("AUTH1001") || mydto1.user_id.equals("AUTH0004") || mydto1.user_id.equals("AUTH1") ){
		if(act_id==2){
			System.out.println("herere brn is is "+ mydto1.branch_code);
			Authorization_Btn.setEnabled(true);
			Reports_Btn.setEnabled(false);
			Cancel_Transaction_Btn.setEnabled(false);
			Receive_Deposit_Btn.setEnabled(false);
		}
		
		
		setBackground(new Color(30, 144, 255));
		setTitle("ALHABIB Teller Module");
		getContentPane().setBackground(new Color(153, 204, 153));
		getContentPane().setLayout(null);
		
//		final JPopupMenu Receive_Deposit_Menu = new JPopupMenu();
//		addPopup(Receive_Deposit_Btn, Receive_Deposit_Menu);
		
		
		
		final JPopupMenu Receive_Deposit_Menu = new JPopupMenu();
//		addPopup(Receive_Deposit_Btn, Receive_Deposit_Menu);
		
		JMenuItem mntmCash = new JMenuItem("Cash");
		JMenuItem mntminterbranch = new JMenuItem("Inter Branch cash");
		
		mntmCash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Teller_cash_info rr = new Teller_cash_info(mydto1,0);
				rr.setSize(470,260);
				setModal(false);
				rr.setVisible(true);
				System.out.print("herhe i am 1");
				
//				Receive_Deposit rd = new Receive_Deposit(mydto1);
//				rd.setSize(470,260);
//				rd.setVisible(true);
			}
		});
		
		
		mntminterbranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Teller_cash_info rr = new Teller_cash_info(mydto1,1);
				rr.setSize(470,260);
				setModal(false);
				rr.setVisible(true);
				System.out.print("herhe i am 1");
			}
		});
		
		Receive_Deposit_Menu.add(mntmCash);
		Receive_Deposit_Menu.add(mntminterbranch);
		Receive_Deposit_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mydto1.batchID>0){
					
					Receive_Deposit_Menu.show(Receive_Deposit_Btn, Receive_Deposit_Btn.getWidth(),Receive_Deposit_Btn.getHeight()/2);	
					
				}
				else{
					JOptionPane.showMessageDialog(null,"Open Batch First\n"  );
				}
				
			}
		});
		Receive_Deposit_Btn.setBackground(new Color(0,153,153));
		Receive_Deposit_Btn.setBounds(448, 92, 162, 39);
		getContentPane().add(Receive_Deposit_Btn);
		

		Authorization_Btn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Auth_User au = new Auth_User(mydto1);
				System.out.println("auth btn clicked");
				//au.setSize(510,330);
				au.setVisible(true);
			}
		});
		Authorization_Btn.setBackground(new Color(0,153,153));
		Authorization_Btn.setBounds(448, 160, 162, 39);
		getContentPane().add(Authorization_Btn);
		
		
		Reports_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				reports r = new reports(mydto1);
//				r.setSize(590,395);
//				r.setVisible(true);
			}
		});
		
		final JPopupMenu Cancel_Transaction_Menu = new JPopupMenu();
		addPopup(Cancel_Transaction_Btn, Cancel_Transaction_Menu);
		
		JMenuItem mntmByRefNo = new JMenuItem("By Ref No");
		mntmByRefNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				Cancellation_Search cs = new Cancellation_Search(mydto1);
//				cs.setSize(420,200);
//				cs.setVisible(true);
			}
		});
		Cancel_Transaction_Menu.add(mntmByRefNo);
		Cancel_Transaction_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cancel_Transaction_Menu.show(Cancel_Transaction_Btn, Cancel_Transaction_Btn.getWidth(),Cancel_Transaction_Btn.getHeight()/2);
			}
		});
		Cancel_Transaction_Btn.setBackground(new Color(0,153,153));
		Cancel_Transaction_Btn.setBounds(448, 230, 162, 39);
		getContentPane().add(Cancel_Transaction_Btn);
		
		Reports_Btn.setBackground(new Color(0,153,153));
		Reports_Btn.setBounds(448, 299, 162, 39);
		getContentPane().add(Reports_Btn);
		
		JButton Sign_Off_Btn = new JButton("Sign Off");
		Sign_Off_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Login lg = new Login();
				db.batchClose(mydto1.batchID,mydto1.user_id);
//				lg.setSize(570,400);
				lg.setVisible(true);
				setVisible(false);
				logger.info("\nuser sign off"+mydto1.user_id);
			}
		});
		Sign_Off_Btn.setBackground(new Color(0,153,153));
		Sign_Off_Btn.setBounds(844, 491, 162, 39);
		getContentPane().add(Sign_Off_Btn);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 153));
		panel.setBorder(new LineBorder(SystemColor.control, 2));
		panel.setBounds(162, 639, 956, 21);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel userDetail = new JLabel("New label");
		userDetail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		userDetail.setBounds(10, 0, 119, 21);
		panel.add(userDetail);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(SystemColor.control, 2));
		panel_1.setBackground(new Color(153, 204, 153));
		panel_1.setBounds(0, 639, 162, 21);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Date = new JLabel("New label");
		Date.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Date.setBounds(10, 0, 152, 21);
		panel_1.add(Date);
		
		Date.setText(mydto.getDay()+" - "+ mydto.getDate());
		int brncd=mydto.getbranch_code();
		String branch = Integer.toString(brncd);
		userDetail.setText(branch +" - "+mydto.getuser_id());
		
		final JPopupMenu Batch_Menu = new JPopupMenu();
		JMenuItem mntmOpenBatch = new JMenuItem("Open");
		JMenuItem mntmCloseBatch = new JMenuItem("Close");
		
		
		final JButton btnBatch = new JButton("Batch");
		Batch_Menu.add(mntmOpenBatch);
		Batch_Menu.add(mntmCloseBatch);
		
		mntmOpenBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mydto1.batchID>0){
					JOptionPane.showMessageDialog(null,"Batch Already Opened\n"  );	
				}
				else{
					System.out.print(mydto1.getuser_id()+" qhdu "+mydto1.getbranch_code());
					Password_Popup r = new Password_Popup(mydto1);
//					r.setSize(300,300);
					r.setVisible(true);
//					r.setResizable(true);
				}
			}
		});
		mntmCloseBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mydto1.setbatchID(0);
			}
		});
		btnBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Batch_Menu.show(btnBatch, btnBatch.getWidth(),btnBatch.getHeight()/2);
				
				
			}
		});
		btnBatch.setBackground(new Color(0,153,153));
		btnBatch.setBounds(106, 491, 162, 39);
		getContentPane().add(btnBatch);
	}
	

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
	}
}
