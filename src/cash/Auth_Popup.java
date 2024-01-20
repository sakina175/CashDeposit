package cash;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Auth_Popup extends JDialog {
	public Auth_Popup(DTO dto1) {
		
		final DTO mydto = dto1;
		Db_Operation db = new Db_Operation();
		setTitle("Message");
		getContentPane().setBackground(new Color(153, 204, 153));
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(500,400,378,194);
		
		JLabel lblTransactionNos = new JLabel("Transaction Nos.");
		lblTransactionNos.setBounds(27, 27, 105, 14);
		lblTransactionNos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblTransactionNos);
		
		JComboBox comboTran = new JComboBox();
		//comboTran.addItem(mydto.vouch_no);
		//comboTran.setSelectedIndex(0);
		comboTran.setBounds(134, 25, 78, 20);
		getContentPane().add(comboTran);
		System.out.println(mydto.vouch_no);
		for (int t : db.tran_get(mydto.vouch_no)) {
			System.out.println(t);
			comboTran.addItem(t);
		}
		
		JLabel lblHaveBeenPosted = new JLabel("have been posted");
		lblHaveBeenPosted.setBounds(222, 27, 105, 14);
		lblHaveBeenPosted.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblHaveBeenPosted);
		
		JLabel lblVoucherNo = new JLabel("Voucher No."+mydto.vouch_no+"  authorized"  );
		lblVoucherNo.setBounds(82, 73, 179, 14);
		lblVoucherNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblVoucherNo);
		
		JButton Okbtn = new JButton("Ok");
		Okbtn.setBackground(new Color(0,153,153));
		Okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				String message="Transaction Successfully";
//				JOptionPane.showMessageDialog(null,message);
//				dispose();
				Voucher_display vd=new Voucher_display(mydto);
				vd.setVisible(true);
			}
		});
		Okbtn.setBounds(146, 112, 89, 23);
		getContentPane().add(Okbtn);
		

	
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

	}

}
