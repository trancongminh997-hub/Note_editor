import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc:derby:EDITOR;create=true";
	
	Connection conn;
	
	
	public DBConnection() {
		try
        {
            Class.forName(DRIVER).newInstance();
            //Get a connection
            conn = DriverManager.getConnection(JDBC_URL); 
            if (this.conn != null) {
				System.out.println("Connection successful!");
//				DriverManager.getConnection("jdbc:derby:NewDB;shutdown=true;");
			}
        }
        catch (Exception except)
        {
            except.printStackTrace();
            System.out.println("Connection failed!");
        }
		try {
			createSchema();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void createSchema() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(DRIVER).newInstance();
			Connection connect = DriverManager.getConnection(JDBC_URL);
			connect.createStatement().execute("Create table APP.Owner( ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userName VARCHAR(30) UNIQUE NOT NULL, password VARCHAR(30) NOT NULL)");
			connect.createStatement().execute("Create table APP.Note(  ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), content VARCHAR(255) NOT NULL, createdDate DATE, alertDate DATE, ownerID INTEGER REFERENCES APP.Owner(ID))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			System.out.println("Tables already exist!");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void insert(String tableName, String value) {
		try {
			this.conn.createStatement().execute("Insert into "+tableName+" values ("+value+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void delete(String table) {
		
	}
}
