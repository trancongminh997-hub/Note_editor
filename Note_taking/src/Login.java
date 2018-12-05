import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		if(e.getSource() == login) {
			try {
				// replace by db
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String pass = null;
				String line = input.readLine();
				while(line != null) {
					StringTokenizer st = new StringTokenizer(line);
					if(userTF.getText().equals(st.nextToken()))
						pass = st.nextToken();
					line = input.readLine();
				}
				input.close();
				
				//based on username, correct password is return in variable pass
				//we hash input password  
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < byteData.length; i++)
					sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
				//and then compare these two
				if(pass.equals(sb.toString())) {
					add(new FileBrowser(userTF.getText()),"fb");
					cl.show(this, "fb");
					System.out.println("You have loged in");
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == register) {
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
		User user = new User("minh","minh");
//		newConnection.insertUser(user);
		User user2 = newConnection.checkUser("minh");
		if(user2 != null) {
			System.out.println("Tim duoc Minh roi: "+user2.getUserName());
		}
	}
	
//	private static void createConnection()
//    {
//        try
//        {
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//            //Get a connection
//            conn = DriverManager.getConnection(dbURL); 
//        }
//        catch (Exception except)
//        {
//            except.printStackTrace();
//        }
//    }
//    
//    private static void insertRestaurants(int id, String restName, String cityName)
//    {
//        try
//        {
//            stmt = conn.createStatement();
//            stmt.execute("insert into " + tableName + " values (" +
//                    id + ",'" + restName + "','" + cityName +"')");
//            stmt.close();
//        }
//        catch (SQLException sqlExcept)
//        {
//            sqlExcept.printStackTrace();
//        }
//    }
//    
//    private static void selectRestaurants()
//    {
//        try
//        {
//            stmt = conn.createStatement();
//            ResultSet results = stmt.executeQuery("select * from " + tableName);
//            ResultSetMetaData rsmd = results.getMetaData();
//            int numberCols = rsmd.getColumnCount();
//            for (int i=1; i<=numberCols; i++)
//            {
//                //print Column Names
//                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
//            }
//
//            System.out.println("\n-------------------------------------------------");
//
//            while(results.next())
//            {
//                int id = results.getInt(1);
//                String restName = results.getString(2);
//                String cityName = results.getString(3);
//                System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
//            }
//            results.close();
//            stmt.close();
//        }
//        catch (SQLException sqlExcept)
//        {
//            sqlExcept.printStackTrace();
//        }
//    }
//    
//    private static void shutdown()
//    {
//        try
//        {
//            if (stmt != null)
//            {
//                stmt.close();
//            }
//            if (conn != null)
//            {
//                DriverManager.getConnection(dbURL + ";shutdown=true");
//                conn.close();
//            }           
//        }
//        catch (SQLException sqlExcept)
//        {
//            
//        }
//
//    }
}
