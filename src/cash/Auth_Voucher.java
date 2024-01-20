package cash;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;



public class Auth_Voucher extends JDialog {
	
	private JTable table;
	
	private DefaultTableModel tm;	
	public Auth_Voucher(DTO dto1 , final int native_brn) {
		final Db_Operation db = new Db_Operation();
		final DTO mydto = dto1;
		setTitle("Trade Finance - Voucher");
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(153, 204, 153));
//		getContentPane()
		setResizable(false);
		setBounds(300,300,845,464);
		
		JPanel panel = new JPanel();
		panel.setForeground(SystemColor.menu);
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setBackground(new Color(153, 204, 153));
		panel.setBounds(10, 385, 817, 41);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton AuthorizeBtn = new JButton("Authorize");
		AuthorizeBtn.setBackground(new Color(0,153,153));
		AuthorizeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				System.out.println("BTNNN");
				if(mydto.vouch_type.equals("General Voucher")){
					System.out.println("inside if");
					System.out.println(mydto.brn_cd);
					System.out.println(mydto.cust_no);
					System.out.println(mydto.amount);
					//int a = db.Currency_code(mydto.currency_type);
					int a = db.get_currency(mydto.cust_no);
					System.out.println(a);
					db.authorize(mydto.vouch_no, mydto.user_id);
					db.update_acc_balance(mydto.brn_cd,mydto.acc_typ,mydto.cust_no,mydto.run_no,mydto.chk_dgt, mydto.amount,a);
					System.out.println("first query");
					//db.authorize(mydto.vouch_no, mydto.user_id);
					System.out.println("second query");
	
					Auth_Popup av = new Auth_Popup(mydto);
					av.setVisible(true);
					av.setSize(378,194);
					
					
				}
				if(mydto.vouch_type.equals("Inter Branch")){
					System.out.println("inside");
					System.out.println(mydto.brn_cd);
					System.out.println(mydto.cust_no);
					System.out.println(mydto.amount);
					System.out.println(mydto.currency_type);
					//int a = db.Currency_code(mydto.currency_type);
					
					int a = db.get_currency(mydto.cust_no);
					System.out.println(a);
					db.authorize(mydto.vouch_no, mydto.user_id);
					System.out.println("first query");
					db.update_ib(mydto.brn_cd,mydto.acc_typ,mydto.cust_no,mydto.run_no,mydto.chk_dgt, mydto.amount,a,native_brn);
					//System.out.println("first query");
					
					System.out.println("second query");
					
					Auth_Popup av = new Auth_Popup(mydto);
					av.setVisible(true);
					//av.setSize(378,194);
					
					
				}
				
				//db.update_acc_balance(mydto.brn_cd,mydto.acc_typ,mydto.cust_no,mydto.run_no,mydto.chk_dgt,);
				//db.authorize(dto1.vouch_no, dto1.user_id);
			}
		});
		
		AuthorizeBtn.setBounds(10, 11, 98, 23);
		panel.add(AuthorizeBtn);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(0,153,153));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnBack.setBounds(670, 11, 89, 23);
		panel.add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 817, 363);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		table = new JTable(){
			@Override
			public boolean isCellEditable(int arg0,int arg1){
				return false;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
    	
        tm = (DefaultTableModel) table.getModel();
        
        tm.addColumn("Voucher No");
		tm.addColumn("Trans No");
		tm.addColumn("Branch");
		tm.addColumn("Account No");
		tm.addColumn("Account Title");
		tm.addColumn("CCY");
		tm.addColumn("DR");
		tm.addColumn("CR");
		
		ArrayList<String[]> data = new ArrayList();
		
		System.out.println("before conn");
		data = db.Get_Tran(mydto.vouch_no);
		System.out.println("after conn");
		
		System.out.println(data);
		
		for(int i=0 ; i < data.size() ; i++)
			tm.addRow(data.get(i));
		
		JScrollPane scrollVoucher = new JScrollPane(table);
		scrollVoucher.setBounds(0, 0, 816, 367);
		panel_1.add(scrollVoucher);

	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Auth_Voucher av = new Auth_Voucher();
		av.setVisible(true);
		av.setSize(700,400);*/

	}

}
