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
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.SwingConstants;



public class Voucher_display extends JDialog {
	
	//private DefaultTableModel tm;	
	private JLabel vouch_typ;
	private JLabel vouch_no;
	private JLabel vouch_date;
	private JLabel Branch;
	private JLabel posted_by;
	private JLabel tran1_id;
	private JLabel tran2_id;
	private JLabel tran3_id;
	private JLabel tran4_id;
	private JLabel tran1_acc;
	private JLabel tran2_acc;
	private JLabel tran3_acc;
	private JLabel tran4_acc;
	private JLabel tran1_title;
	private JLabel tran2_title;
	private JLabel tran3_title;
	private JLabel tran4_title;
	private JLabel ccy1;
	private JLabel ccy2;
	private JLabel ccy3;
	private JLabel ccy4;
	private JLabel tran1_dr;
	private JLabel tran2_dr;
	private JLabel tran3_dr;
	private JLabel tran4_dr;
	private JLabel tran1_cr;
	private JLabel tran4_cr;
	private JLabel tran2_cr;
	private JLabel tran3_cr;
	private JLabel auth_lbl;
	public Voucher_display(DTO dto1) {
		setModal(true);
		final Db_Operation db = new Db_Operation();
		final DTO mydto = dto1;
		setTitle("View Voucher");
		getContentPane().setBackground(new Color(153, 204, 153));
		getContentPane().setLayout(null);
		setBounds(100,100,803,495);
		JPanel panel = new JPanel();
		panel.setBounds(10, 401, 757, 41);
		panel.setForeground(SystemColor.menu);
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setBackground(new Color(153, 204, 153));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton OkBtn = new JButton("Ok");
		OkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag=db.getTransStatus(mydto.vouch_no);
				System.out.println("flag is "+flag);
				String message="";
				if(flag){
					message="Transaction Successful";
				}
				else{
					message="Authorization Required";
				}
				JOptionPane.showMessageDialog(null,message);
				dispose();
				dispose();
			}
		});
		
		
		OkBtn.setBounds(10, 11, 98, 23);
		panel.add(OkBtn);
		
//		JButton btnBack = new JButton("Back");
//		btnBack.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				setVisible(false);
//			}
//		});
//		btnBack.setBounds(647, 11, 89, 23);
//		panel.add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.BLACK);
		panel_1.setBounds(10, 11, 757, 371);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BANK AL HABIB");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(306, 11, 149, 28);
		panel_1.add(lblNewLabel);
		
		JLabel lblTransactionVoucher = new JLabel("Transaction Voucher");
		lblTransactionVoucher.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTransactionVoucher.setBounds(312, 41, 143, 14);
		panel_1.add(lblTransactionVoucher);
		
		vouch_typ = new JLabel();
		vouch_typ.setHorizontalAlignment(SwingConstants.CENTER);
		vouch_typ.setText(mydto.vouch_type);
		vouch_typ.setForeground(Color.BLACK);
		vouch_typ.setBackground(new Color(240,240,240));
		vouch_typ.setFont(new Font("Tahoma", Font.PLAIN, 15));
		vouch_typ.setBounds(316, 59, 125, 19);
		panel_1.add(vouch_typ);
		//vouch_typ
		
		JLabel lblVoucherNo = new JLabel("Voucher No:");
		lblVoucherNo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVoucherNo.setBounds(10, 94, 90, 14);
		panel_1.add(lblVoucherNo);
		
		vouch_no = new JLabel();
		vouch_no.setText(mydto.vouch_no);
		vouch_no.setForeground(Color.BLACK);
		vouch_no.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//vouch_no
		vouch_no.setBackground(SystemColor.menu);
		vouch_no.setBounds(84, 91, 105, 19);
		panel_1.add(vouch_no);
		
		ArrayList<String[]> data = db.Get_Tran(mydto.vouch_no);
		if (mydto.vouch_type.equals("Inter Branch")){
			tran3_id = new JLabel();
			tran3_id.setText(data.get(2)[1]);
			tran3_id.setForeground(Color.BLACK);
			tran3_id.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran3_id.setBackground(SystemColor.menu);
			tran3_id.setBounds(15, 251, 53, 20);
			panel_1.add(tran3_id);
			
			tran4_id = new JLabel();
			tran4_id.setText(data.get(3)[1]);
			tran4_id.setForeground(Color.BLACK);
			tran4_id.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
			tran4_id.setBackground(SystemColor.menu);
			tran4_id.setBounds(15, 228, 53, 20);
			panel_1.add(tran4_id);
			
			tran3_acc = new JLabel();
			tran3_acc.setText(data.get(2)[3]);
			tran3_acc.setForeground(Color.BLACK);
			tran3_acc.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran3_acc.setBackground(SystemColor.menu);
			tran3_acc.setBounds(70, 228, 189, 20);
			panel_1.add(tran3_acc);
			
			tran4_acc = new JLabel();
			tran4_acc.setText(data.get(3)[3]);
			tran4_acc.setForeground(Color.BLACK);
			tran4_acc.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
			tran4_acc.setBackground(SystemColor.menu);
			tran4_acc.setBounds(70, 251, 186, 20);
			panel_1.add(tran4_acc);
			
			tran3_title = new JLabel();
			tran3_title.setText(data.get(2)[4]);
			tran3_title.setForeground(Color.BLACK);
			tran3_title.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran3_title.setBackground(SystemColor.menu);
			tran3_title.setBounds(269, 228, 194, 20);
			panel_1.add(tran3_title);
			
			tran4_title = new JLabel();
			tran4_title.setText(data.get(3)[4]);
			tran4_title.setForeground(Color.BLACK);
			tran4_title.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran4_title.setBackground(SystemColor.menu);
			tran4_title.setBounds(269, 251, 194, 20);
			panel_1.add(tran4_title);
			
			ccy3 = new JLabel();
			ccy3.setText(data.get(0)[5]);
			ccy3.setForeground(Color.BLACK);
			ccy3.setFont(new Font("Tahoma", Font.PLAIN, 13));
			//ccy1
			ccy3.setBackground(SystemColor.menu);
			ccy3.setBounds(465, 228, 90, 20);
			panel_1.add(ccy3);
			
			ccy4 = new JLabel();
			ccy4.setText(data.get(0)[5]);
			ccy4.setForeground(Color.BLACK);
			ccy4.setFont(new Font("Tahoma", Font.PLAIN, 13));
			//ccy2
			ccy4.setBackground(SystemColor.menu);
			ccy4.setBounds(465, 251, 90, 20);
			panel_1.add(ccy4);
			
			
			
			tran3_dr = new JLabel();
			tran3_dr.setText(data.get(0)[6]);
			tran3_dr.setForeground(Color.BLACK);
			tran3_dr.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran3_dr.setBackground(SystemColor.menu);
			tran3_dr.setBounds(565, 228, 90, 20);
			panel_1.add(tran3_dr);
			
			tran4_dr = new JLabel();
			tran4_dr.setText("    -");
			tran4_dr.setForeground(Color.BLACK);
			tran4_dr.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran4_dr.setBackground(SystemColor.menu);
			tran4_dr.setBounds(565, 251, 90, 20);
			panel_1.add(tran4_dr);
			
			tran3_cr = new JLabel();
			tran3_cr.setText("    - ");
			tran3_cr.setForeground(Color.BLACK);
			tran3_cr.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran3_cr.setBackground(SystemColor.menu);
			tran3_cr.setBounds(651, 228, 90, 20);
			panel_1.add(tran3_cr);
			
			tran4_cr = new JLabel();
			tran4_cr.setText(data.get(0)[6]);
			tran4_cr.setForeground(Color.BLACK);
			tran4_cr.setFont(new Font("Tahoma", Font.PLAIN, 13));

			tran4_cr.setBackground(SystemColor.menu);
			tran4_cr.setBounds(651, 252, 90, 20);
			panel_1.add(tran4_cr);
			
		}
		
		JLabel lblVoucherDate = new JLabel("Voucher Date:");
		lblVoucherDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVoucherDate.setBounds(252, 94, 90, 14);
		panel_1.add(lblVoucherDate);
		ArrayList<String> dt = db.GetVoucherDetails(mydto.vouch_no);
		System.out.println(dt.get(1));
		System.out.println(dt.get(0));
		
		
		
		
		vouch_date = new JLabel();
		vouch_date.setText(dt.get(1));
		vouch_date.setForeground(Color.BLACK);
		vouch_date.setFont(new Font("Tahoma", Font.PLAIN, 13));
		vouch_date.setBackground(SystemColor.menu);
		vouch_date.setBounds(338, 91, 105, 19);
		panel_1.add(vouch_date);
		
		JLabel lblBranch = new JLabel("Branch:");
		lblBranch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBranch.setBounds(565, 94, 53, 14);
		panel_1.add(lblBranch);
		
		Branch = new JLabel();
		Branch.setText(data.get(0)[2]);
		Branch.setForeground(Color.BLACK);
		Branch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//Branch
		Branch.setBackground(SystemColor.menu);
		Branch.setBounds(625, 92, 105, 19);
		panel_1.add(Branch);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setForeground(Color.BLACK);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setBounds(10, 138, 737, 37);
		panel_1.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Trans No");
		lblNewLabel_1.setBounds(6, 10, 55, 20);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblAccountNo = new JLabel("Account No");
		lblAccountNo.setBounds(105, 10, 139, 20);
		panel_2.add(lblAccountNo);
		
		JLabel lblAccountTitle = new JLabel("Account Title");
		lblAccountTitle.setBounds(259, 10, 176, 20);
		panel_2.add(lblAccountTitle);
		
		JLabel lblCcy = new JLabel("CCY");
		lblCcy.setBounds(456, 10, 87, 20);
		panel_2.add(lblCcy);
		
		JLabel lblDebit = new JLabel("Debit");
		lblDebit.setBounds(555, 10, 68, 20);
		panel_2.add(lblDebit);
		
		JLabel lblCredit = new JLabel("Credit");
		lblCredit.setBounds(642, 10, 68, 20);
		panel_2.add(lblCredit);
		
		Panel panel_3 = new Panel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(10, 277, 737, 1);
		panel_1.add(panel_3);
		
		JLabel lblPostedBy = new JLabel("Posted By:");
		lblPostedBy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPostedBy.setBounds(10, 304, 90, 14);
		panel_1.add(lblPostedBy);
		
		JLabel lblAuthorizedBy = new JLabel("Status:");
		lblAuthorizedBy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthorizedBy.setBounds(10, 324, 118, 20);
		panel_1.add(lblAuthorizedBy);
		
		posted_by = new JLabel();
		posted_by.setText(dt.get(0));
		posted_by.setForeground(Color.BLACK);
		posted_by.setFont(new Font("Tahoma", Font.PLAIN, 13));

		posted_by.setBackground(SystemColor.menu);
		posted_by.setBounds(91, 302, 149, 19);
		panel_1.add(posted_by);
		
		tran1_id = new JLabel();
		tran1_id.setText(data.get(0)[1]);
		tran1_id.setForeground(Color.BLACK);
		tran1_id.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran1_id
		tran1_id.setBackground(SystemColor.menu);
		tran1_id.setBounds(15, 182, 50, 20);
		panel_1.add(tran1_id);
		
		tran2_id = new JLabel();
		tran2_id.setText(data.get(1)[1]);
		tran2_id.setForeground(Color.BLACK);
		tran2_id.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran2_id
		tran2_id.setBackground(SystemColor.menu);
		tran2_id.setBounds(15, 205, 50, 20);
		panel_1.add(tran2_id);
		
		
		
		tran1_acc = new JLabel();
		tran1_acc.setText(data.get(0)[3]);
		tran1_acc.setForeground(Color.BLACK);
		tran1_acc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran1_acc
		tran1_acc.setBackground(SystemColor.menu);
		tran1_acc.setBounds(70, 182, 186, 20);
		panel_1.add(tran1_acc);
		
		tran2_acc = new JLabel();
		tran2_acc.setText(data.get(1)[3]);
		tran2_acc.setForeground(Color.BLACK);
		tran2_acc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran2_acc
		tran2_acc.setBackground(SystemColor.menu);
		tran2_acc.setBounds(70, 205, 186, 20);
		panel_1.add(tran2_acc);
		
		
		
		tran1_title = new JLabel();
		tran1_title.setText(data.get(0)[4]);
		tran1_title.setForeground(Color.BLACK);
		tran1_title.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran1_title
		tran1_title.setBackground(SystemColor.menu);
		tran1_title.setBounds(269, 182, 194, 20);
		panel_1.add(tran1_title);
		
		tran2_title = new JLabel();
		tran2_title.setText(data.get(1)[4]);
		tran2_title.setForeground(Color.BLACK);
		tran2_title.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran2_title
		tran2_title.setBackground(SystemColor.menu);
		tran2_title.setBounds(269, 205, 194, 20);
		panel_1.add(tran2_title);
		
		
		
		ccy1 = new JLabel();
		ccy1.setText(data.get(0)[5]);
		ccy1.setForeground(Color.BLACK);
		ccy1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//ccy1
		ccy1.setBackground(SystemColor.menu);
		ccy1.setBounds(465, 182, 90, 20);
		panel_1.add(ccy1);
		
		ccy2 = new JLabel();
		ccy2.setText(data.get(0)[5]);
		ccy2.setForeground(Color.BLACK);
		ccy2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//ccy2
		ccy2.setBackground(SystemColor.menu);
		ccy2.setBounds(465, 205, 90, 20);
		panel_1.add(ccy2);
		
		
		
		tran1_dr = new JLabel();
		tran1_dr.setText(data.get(0)[6]);
		tran1_dr.setForeground(Color.BLACK);
		tran1_dr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//tran1_dr
		tran1_dr.setBackground(SystemColor.menu);
		tran1_dr.setBounds(565, 182, 90, 20);
		panel_1.add(tran1_dr);
		
		tran2_dr = new JLabel();
		tran2_dr.setText("     -");
		tran2_dr.setForeground(Color.BLACK);
		tran2_dr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tran2_dr.setBackground(SystemColor.menu);
		tran2_dr.setBounds(565, 205, 90, 20);
		panel_1.add(tran2_dr);
		
		
		
		tran1_cr = new JLabel();
		tran1_cr.setText("     -");
		tran1_cr.setForeground(Color.BLACK);
		tran1_cr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		tran1_cr.setBackground(SystemColor.menu);
		tran1_cr.setBounds(651, 182, 90, 20);
		panel_1.add(tran1_cr);
		
		
		
		tran2_cr = new JLabel();
		tran2_cr.setText(data.get(0)[6]);
		tran2_cr.setForeground(Color.BLACK);
		tran2_cr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tran2_cr.setBackground(SystemColor.menu);
		tran2_cr.setBounds(651, 205, 90, 20);
		panel_1.add(tran2_cr);
		
		
		
		
		Panel panel_4 = new Panel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(410, 337, 156, 1);
		panel_1.add(panel_4);
		
		Panel panel_5 = new Panel();
		panel_5.setBackground(Color.BLACK);
		panel_5.setBounds(582, 337, 165, 1);
		panel_1.add(panel_5);
		
		JLabel lblAuthorizedSignature = new JLabel("Authorized Signature");
		lblAuthorizedSignature.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthorizedSignature.setBounds(430, 344, 128, 14);
		panel_1.add(lblAuthorizedSignature);
		
		JLabel label = new JLabel("Authorized Signature");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(601, 345, 128, 14);
		panel_1.add(label);
		
		
	
		JLabel status_lbl = new JLabel("");
		status_lbl.setBounds(91, 325, 141, 20);
		status_lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(status_lbl);
		
	
		boolean flag=db.getTransStatus(mydto.vouch_no);
		System.out.println("flag is "+flag);
		String message="";
		if(flag){
			message="Authorized";
			status_lbl.setText(message);
			JLabel auth_by = new JLabel("Authorized By:");
			auth_by.setFont(new Font("Tahoma", Font.PLAIN, 13));
			auth_by.setBounds(10, 350, 100, 20);
			panel_1.add(auth_by);
			
			
			JLabel auth_lbl = new JLabel(db.Getauth(mydto.vouch_no));
			auth_lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
			auth_lbl.setBounds(95, 350, 105, 20);
			panel_1.add(auth_lbl);
			
		}
		else{
			message="Unauthorized";
			status_lbl.setText(message);
		}
		
		
		
        
		
	}
}
