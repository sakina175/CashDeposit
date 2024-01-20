package cash;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;



public class Auth_Select extends JDialog {
	
	private JTable table;
	public int status=0;
	private DefaultTableModel tm;
	Db_Operation db = new Db_Operation();
 	
	
	public Auth_Select(DTO dto1, String newuser) {
		setTitle("Trade Finance  Authorize Voucher");
		
	      final DTO mydto = dto1;
	      JPanel panel = new JPanel();
	      panel.setBorder(new LineBorder(SystemColor.control));
	      panel.setBackground(new Color(153, 204, 153));
	      panel.setBounds(10, 226, 700, 54);
	      getContentPane().add(panel);
	      setResizable(false);
	      setBounds(250, 250, 510,277);
	      
	      panel.setLayout(null);
	      
	      JPanel panel_2 = new JPanel();
	      panel.add(panel_2);
	      panel_2.setBorder(new LineBorder(SystemColor.control, 2));
	      panel_2.setBackground(new Color(153, 204, 153));
	      panel_2.setBounds(12, 193, 470, 47);
	      panel_2.setLayout(null);
	      
	      JButton OkBtn = new JButton("Ok");
	      OkBtn.setBackground(new Color(0,153,153));
	      OkBtn.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		//mydto.setuser_id(comboUser.getSelectedItem().toString());
	      		int selectRow = table.getSelectedRow();
	      		if(selectRow != -1){
	      			String a = (String) tm.getValueAt(selectRow, 0);
	      			
	      			//String b = a.substring(0,a.length()-5);
	      			System.out.println("OK1");
	      			mydto.setvouch_no(a);
	      			System.out.println("OK2" + a);
	      			ArrayList<String> x = db.Get_Acs(a);
	      			System.out.println("OK3");
	      			System.out.println(x.get(0));
	      			System.out.println(x.get(1));
	      			System.out.println(x.get(2));
	      			System.out.println(x.get(3));
	      			System.out.println(x.get(4));
	      			//mydto.setbranch_code();
	      			int native_brn = mydto.getbranch_code();
	      			System.out.println("this is brn " +native_brn);
	      			mydto.set_acc(Integer.parseInt(x.get(0)), Integer.parseInt(x.get(1)), Integer.parseInt(x.get(2)), Integer.parseInt(x.get(3)), Integer.parseInt(x.get(4)));
	      			System.out.println("OK4");
	      			Double amount = db.Get_Amount(mydto.vouch_no);
	      			System.out.println(amount);
	      			mydto.setamount(amount);
	      			dispose();
	      			Auth_Voucher av = new Auth_Voucher(mydto, native_brn);
		    		av.setVisible(true);
		    		if(status==1)
		    			dispose();
		    		//av.setSize(850,470);
	      		}
	      		else{
	      			JOptionPane.showMessageDialog(null, "Select Any Voucher");
	      		}
	      	}
	      });
	      OkBtn.setBounds(10, 11, 89, 23);
	      panel_2.add(OkBtn);
	      
	      JButton backbtn = new JButton("Back");
	      backbtn.setBackground(new Color(0,153,153));
	      backbtn.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		setVisible(false);
	      	}
	      });
	      backbtn.setBounds(368, 11, 89, 23);
	      panel_2.add(backbtn);
	        
		    
	      	Db_Operation db = new Db_Operation();
		    
		    JPanel panel_1 = new JPanel();
		    panel_1.setBounds(12, 11, 470, 171);
		    panel.add(panel_1);
		    panel_1.setLayout(null);
		    
		    
		    table = new JTable(){
				@Override
				public boolean isCellEditable(int arg0,int arg1){
					return false;
				}
			};
		    table.getTableHeader().setReorderingAllowed(false);
		    
		    tm = (DefaultTableModel) table.getModel();
		    
		    
		    
		    JScrollPane scrollAuth = new JScrollPane(table);
		    scrollAuth.setBounds(0, 0, 470, 171);
		    panel_1.add(scrollAuth);
	        
	        tm.addColumn("Voucher No");
			tm.addColumn("Post By");
			tm.addColumn("Voucher Type");
	        
			ArrayList<String[]> data = new ArrayList();
			System.out.println("another user id is "+mydto.user_id);
			data = db.Get_Vouch(mydto.user_id,newuser, mydto.vouch_type);
	        
			for(int i=0 ; i < data.size() ; i++)
				tm.addRow(data.get(i));
	        //table.getSelectedRow();
	        
	        
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Auth_Select av = new Auth_Select();
		av.setVisible(true);
		av.setSize(455,294);*/
	}
}
