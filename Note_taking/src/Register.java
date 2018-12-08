import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Register extends JPanel implements ActionListener{
	JLabel userL = new JLabel("Choose a Username: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Password");
	JPasswordField passTF = new JPasswordField();
	JLabel passLC = new JLabel("Confirm Password");
	JPasswordField passC = new JPasswordField();
	JButton register = new JButton("Register");
	JButton back = new JButton("Back");
	JLabel mess = new JLabel();
	
	public Register(){
		JPanel loginP = new JPanel();
		loginP.setLayout(new GridLayout(5,2));
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		loginP.add(passLC);
		loginP.add(passC);
		loginP.add(register);
		loginP.add(back);
		loginP.add(mess);
		register.addActionListener(this);
		back.addActionListener(this);
		add(loginP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == register && passTF.getPassword().length >0 && userTF.getText().length() >0 && passC.getPassword().length>0){
			
			String pass = new String(passTF.getPassword());
			String confirm = new String(passC.getPassword());
			
			if(pass.equals(confirm)){
				try {
					DBConnection newConn = new  DBConnection();
					User checkedUser = newConn.checkUser(userTF.getText());
					if(checkedUser == null) {
					
						MessageDigest md = MessageDigest.getInstance("SHA-1");
						String toHash = new String(passTF.getPassword());
						md.update(toHash.getBytes(),0, toHash.length() );
						String hash = null;
						hash = new BigInteger(1,md.digest()).toString(16).substring(0, 29);
						
						newConn.insertUser(new User(userTF.getText(),hash));
						
						Login login = (Login) getParent();
						login.cl.show(login, "login");
					}
					else mess.setText("User already exists");
					
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else mess.setText("Double check password");
		}
		if(e.getSource() == back){
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
	}
}
