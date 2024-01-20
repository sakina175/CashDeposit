package cash;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class Password_Popup extends JDialog{
	private static final Logger logger=Logger_Manager.getLogger(Password_Popup.class);
	private DTO dto = new DTO();
	private JPasswordField password_tf;
	public Password_Popup(final DTO mydto) {
		setTitle("Password Popup");
		setResizable(true);
		setBounds(500,200,502,154);
		getContentPane().setForeground(new Color(0, 0, 0));
		getContentPane().setBackground(new Color(158, 204, 158));
		getContentPane().setLayout(null);
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(28, 20, 76, 34);
		getContentPane().add(lblPassword);
		
		password_tf = new JPasswordField();
		password_tf.setBounds(95, 27, 208, 20);
		getContentPane().add(password_tf);
		password_tf.setColumns(10);
		
		JButton btnSubmit = new JButton("submit");
		btnSubmit.setForeground(new Color(0, 0, 0));
		btnSubmit.setBackground(new Color(0,153,153));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Message="";
				
				mydto.setpassword(password_tf.getText().toString());
				String password = mydto.getpassword();
				Db_Operation db = new Db_Operation();
				String resultset = db.Batch_Authentication(mydto.getuser_id(), password, mydto.getbranch_code());
				if(resultset=="UNAUTHORIZE" || resultset=="false"){
					Message="Wrong Password";
				}
				else {
					mydto.setbatchID(Integer.parseInt(resultset));
					System.out.println("batch id is in password pop up "+mydto.getbatchID());
					Message="Batch Opened with Batch No: "+resultset;
					logger.info("batch id"+resultset+" for user id is"+mydto.user_id);
				}
				
				JOptionPane.showMessageDialog(null, Message);
				if(Message!="Wrong Password")
				dispose();
				
				}
					
			
		});
		btnSubmit.setBounds(383, 64, 93, 40);
		getContentPane().add(btnSubmit);
//		getContentPane().set
	}

}
