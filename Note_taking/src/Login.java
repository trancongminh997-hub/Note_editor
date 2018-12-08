import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//database connect package
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class Login extends JPanel implements ActionListener {
	
//	private static String dbURL = "jdbc:derby:C:\\Users\\Minh Tran Cong\\MyDB;create=true";
    
	
	JLabel userL = new 	JLabel("User name: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Password: ");
	JPasswordField passTF = new JPasswordField();
	JButton login = new JButton("Login");
	JButton register = new JButton("Register");
	
	JPanel loginP = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();// using to swap
	CardLayout cl;
	Login(){
//		set layout in card
		setLayout(new CardLayout());
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		// add action listener to 2 button
		login.addActionListener(this);
		register.addActionListener(this);
		// add two button to panel
		loginP.add(login);
		loginP.add(register);
		
		panel.add(loginP);
		add(panel, "login");
		cl = (CardLayout) getLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == login && passTF.getPassword().length>0 && userTF.getText().length()>0) {
			try {
				// replace by db
				DBConnection newConn = new DBConnection();
				User checkedUser =  newConn.checkUser(userTF.getText());
				
				//based on username, correct password is return in variable pass
				//we hash input password  
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				String toHash = new String(passTF.getPassword());
				md.update(toHash.getBytes(),0, toHash.length() );
				String hash = null;
				hash = new BigInteger(1,md.digest()).toString(16).substring(0, 29);
				
				//and then compare these two
				if(checkedUser != null && checkedUser.getPassword().equals(hash)) {
					//add(new FileBrowser(userTF.getText()),"fb");
					add(new FileBrowser(checkedUser.getuserId()),"fb");
					cl.show(this, "fb");
					System.out.println("You have loged in");
					passTF.setText("");
					userTF.setText("");
				}
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == register) {
			passTF.setText("");
			userTF.setText("");
			add(new Register(), "register");
			cl.show(this, "register");
		}
	}
	public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException {
		JFrame frame = new JFrame("Note-taking editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		frame.setSize(500, 500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
		
		DBConnection newConnection = new DBConnection();
//		User user = new User("phuc","minh");
//		newConnection.insertUser(user);
//		User user2 = newConnection.checkUser("phuc");
//		if(user2 != null) {
//			System.out.println("Tim duoc Phuc roi: "+user2.getPassword());
		}
	}

