package cash;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Auth_User extends JDialog {
	public Auth_User(DTO dto1) {
		setTitle("Trade Finance - Select User");
		
		
		getContentPane().setBackground(new Color(153, 204, 153));
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(500,200,430,253);
		//setResizable(false);
		final ArrayList arr = new ArrayList();
		//final DTO mydto1=dto1;
		final DTO mydto = dto1;
		final Db_Operation db = new Db_Operation();
		final JComboBox<String> comboVoucher = new JComboBox<String>();
		final JComboBox<String> comboUser = new JComboBox<String>();
		final JButton btnOk = new JButton("Ok");
		btnOk.setBackground(new Color(0,153,153));
		final JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(0,153,153));
		
		JPanel panel = new JPanel();
		panel.setForeground(SystemColor.menu);
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setBackground(new Color(153, 204, 153));
		panel.setBounds(10, 172, 404, 41);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnOk.setBounds(10, 11, 89, 23);
		panel.add(btnOk);
		btnBack.setBounds(294, 11, 89, 23);
		panel.add(btnBack);
		
		btnOk.setEnabled(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("1");
				String newuser = comboUser.getSelectedItem().toString();
				//System.out.println("2");
				mydto.setvouch_type(comboVoucher.getSelectedItem().toString());
				System.out.println("3");
				Auth_Select av = new Auth_Select(mydto, newuser);
				av.setVisible(true);
				//av.setSize(455,294);
			}
		});
		
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		JLabel lblBranch = new JLabel("User Id");
		lblBranch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBranch.setBounds(35, 63, 93, 28);
		getContentPane().add(lblBranch);
		
		JLabel lblVoucherType = new JLabel("Voucher Type");
		lblVoucherType.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVoucherType.setBounds(27, 117, 112, 22);
		getContentPane().add(lblVoucherType);
		
		getContentPane().setBounds(500,200,801,442);
		
		comboUser.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(comboVoucher.getSelectedIndex() > 0){
					btnOk.setEnabled(true);
				}
				else{
					btnOk.setEnabled(false);
				}
			}
		});
		
		comboVoucher.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(comboUser.getSelectedIndex() >= 0){
					btnOk.setEnabled(true);
				}
				else{
					btnOk.setEnabled(false);
				}
			}
		});
		
		comboVoucher.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(comboUser.getSelectedIndex() >= 0 && comboVoucher.getSelectedIndex() >= 0 ){
					btnOk.setEnabled(true);
				}
				else{
					btnOk.setEnabled(false);
				}
			}
		});
		comboUser.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(comboUser.getSelectedIndex() >= 0 && comboVoucher.getSelectedIndex() >= 0 ){
				btnOk.setEnabled(true);
			}
			else{
				btnOk.setEnabled(false);
			}
		}
		});
		String b = dto1.getuser_id();
		int a = dto1.getbranch_code();
		for (String users : db.Get_Users(a,b)) {
			comboUser.addItem(users);
		}
		comboUser.setSelectedIndex(-1);
		comboUser.setBounds(149, 67, 209, 22);
		getContentPane().add(comboUser);

		
		comboVoucher.addItem("General Voucher");
		comboVoucher.addItem("Inter Branch");
		comboVoucher.setSelectedIndex(-1);
		
		comboVoucher.setBounds(149, 118, 209, 22);
		getContentPane().add(comboVoucher);
	}

}
