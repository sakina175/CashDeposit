package cash;
import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.logging.Logger;
import javax.swing.JPasswordField;

public class Login extends JDialog {
	static Login lg = new Login();
	DTO mydto = new DTO();
	private static final Logger logger=Logger_Manager.getLogger(Login.class);
	private JTextField user_id_field;
	private JTextField password_field;
	private JTextField branch_code_field;
//	private JTextField passwordField;
	public Login() {
		setTitle("Login");
		setResizable(true);
		setBounds(300,200,770,505);
		getContentPane().setForeground(new Color(0, 0, 0));
		getContentPane().setBackground(new Color(0, 153, 153));
		getContentPane().setLayout(null);
		
		final JLabel capslock_lbl = new JLabel(" ");
		capslock_lbl.setBounds(351, 235, 173, 14);
		getContentPane().add(capslock_lbl);
		
		final JButton Sign_In = new JButton("Sign In");
		Sign_In.setEnabled(false);
		
		user_id_field = new JPasswordField();
//		user_id_field.setEchoChar('*');
		user_id_field.setBounds(427, 168, 130, 20);
		getContentPane().add(user_id_field);
		user_id_field.setColumns(10);
		
		password_field = new JPasswordField();
//		password_field.setEchoChar('*');
		user_id_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if((user_id_field.getText().length()<10) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
					user_id_field.setEditable(true);
				}
				else{
					user_id_field.setEditable(false);
				}
				
				if(arg0.getKeyChar()==KeyEvent.VK_CAPS_LOCK){
					capslock_lbl.setText("Caps Lock is On");
				}
				if((user_id_field.getText().length()==0) || (password_field.getText().length()==0) || (branch_code_field.getText().length()==0)){
					Sign_In.setEnabled(false);
				}
				else{
					Sign_In.setEnabled(true);
				}
			}
		});
		password_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if((password_field.getText().length()<10) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
					password_field.setEditable(true);
				}
				else{
					password_field.setEditable(false);
				}
				if(arg0.getKeyChar()==KeyEvent.VK_CAPS_LOCK){
					capslock_lbl.setText("Caps Lock is On");
				}
				if((user_id_field.getText().length()==0) || (password_field.getText().length()==0) || (branch_code_field.getText().length()==0)){
					Sign_In.setEnabled(false);
				}
				else{
					Sign_In.setEnabled(true);
				}
			}
		});
		password_field.setColumns(10);
		password_field.setBounds(352, 204, 207, 20);
		getContentPane().add(password_field);
		
		branch_code_field = new JTextField();
		branch_code_field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String s = branch_code_field.getText();
				if(s.length()<=3 && s.length()>0){
					int len = s.length();
					for(int i=0; i<=3-len ; i++){
						s= '0'+s;
					}
					branch_code_field.setText(s);
				}
			}
		});
		branch_code_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				if((arg0.getKeyChar() >= '0' && arg0.getKeyChar() <= '9') && (branch_code_field.getText().length()<=3) || (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
					branch_code_field.setEditable(true);
				}
				else{
					branch_code_field.setEditable(false);
				}
				if((user_id_field.getText().length()==0) || (password_field.getText().length()==0) || (branch_code_field.getText().length()==0)){
					Sign_In.setEnabled(false);
				}
				else{
					Sign_In.setEnabled(true);
				}
			}}
		);
		branch_code_field.setColumns(10);
		branch_code_field.setBounds(352, 168, 65, 20);
		getContentPane().add(branch_code_field);
		
		JLabel lblNewLabel = new JLabel("AL HABIB BANKING SYSTEM");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 30));
		lblNewLabel.setBounds(140, 36, 452, 38);
		getContentPane().add(lblNewLabel);
		
		Sign_In.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mydto.setbranch_code(Integer.parseInt(branch_code_field.getText()));
				mydto.setuser_id(user_id_field.getText().toString().toUpperCase());
				mydto.setpassword(password_field.getText().toString());
				int branch_code = mydto.getbranch_code();
				String user_id = mydto.getuser_id();
				String password = mydto.getpassword();
				Db_Operation db = new Db_Operation();
				String resultset = db.Login(branch_code, user_id, password);
				String dt= db.GetDayDate(branch_code);
				String Message="";
				System.out.println(resultset);
				if(resultset == "invalid branch"){
					JOptionPane.showMessageDialog(null,"Invalid Branch Code");
					branch_code_field.setText("");
					user_id_field.setText("");
					password_field.setText("");
					branch_code_field.setEditable(true);
					user_id_field.setEditable(true);
					password_field.setEditable(true);
					Sign_In.setEnabled(false);
					
				}
				else if (resultset=="false"){
					JOptionPane.showMessageDialog(null,"Invalid User ID/ Password");
					user_id_field.setText("");
					password_field.setText("");
					branch_code_field.setEditable(true);
					user_id_field.setEditable(true);
					password_field.setEditable(true);
					Sign_In.setEnabled(false);
//					logger.info("\nInvalid User ID/ Password");
				}
				else if (resultset.equals("N")){
					JOptionPane.showMessageDialog(null,"You have to reset your Status ");
					branch_code_field.setText("");
					user_id_field.setText("");
					password_field.setText("");
					branch_code_field.setEditable(true);
					user_id_field.setEditable(true);
					password_field.setEditable(true);
					Sign_In.setEnabled(false);
					logger.info("\nYou have to reset your Status "+dt);
				}
				else{
					if(resultset==""){
						Message="";
					}
					else{
						Message="login \nBranch Date:"+dt;
					}
					JOptionPane.showMessageDialog(null,"Successfull " + Message);
					int act_code=db.getActvitycd(user_id);
					System.out.println("actvity code ia "+act_code);
					java.util.Date date= new java.util.Date();
					String dayDate=db.GetDayDate(branch_code);
					String Day= dayDate.toString().substring(13,dayDate.length());
					int dtoDateYr= 1900+date.getYear();
					String year= Integer.toString(dtoDateYr);
					String FDate = dayDate.toString().substring(0,10);
					System.out.println(Day+" "+FDate+", "+year);
					mydto.setDay(Day);
					mydto.setDate(FDate);
					Main_Screen ms = new Main_Screen(db.AccessRights(user_id), mydto,act_code);
					ms.setVisible(true);
					lg.setVisible(false);
					dispose();
					logger.info("\nyou have been successfullly logged at "+dt);

				}
			}

		});
		Sign_In.setBackground(new Color(255, 255, 255));
		Sign_In.setBounds(337, 271, 112, 29);
		getContentPane().add(Sign_In);
		
		JButton Exit_Login = new JButton("Exit");
		Exit_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
				
			}
		});
		Exit_Login.setBackground(new Color(255, 255, 255));
		Exit_Login.setBounds(459, 271, 112, 29);
		getContentPane().add(Exit_Login);
		
		ImageIcon icon=new ImageIcon("C:/Users/Sbatool.27374/Desktop/logos.png");
		JLabel img_panel = new JLabel(icon);
		img_panel.setBounds(66, 134, 234, 172);
		img_panel.setVisible(true);
		getContentPane().add(img_panel);
	
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "LOGIN", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(new Color(0, 153, 153));
		panel.setBounds(337, 134, 234, 126);
		getContentPane().add(panel);
	}
	
	public static void main(String[] args){
//		lg.setSize(570,400);
		lg.setVisible(true);
	}
}
