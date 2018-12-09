import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.sql.Statement;

public class DBConnection {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc:derby:EDITOR;create=true";
	
	static Connection conn;
	
	
	public DBConnection() throws IllegalAccessException, InstantiationException {
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
	

	public static void createSchema() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		try {
//			Class.forName(DRIVER).newInstance();
//			Connection connect = DriverManager.getConnection(JDBC_URL);
			conn.createStatement().execute("Create table APP.Owner( ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), userName VARCHAR(30) UNIQUE NOT NULL, password VARCHAR(30) NOT NULL)");
			conn.createStatement().execute("Create table APP.Note(  ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), content VARCHAR(255) NOT NULL, title VARCHAR(50) NOT NULL, createdDate DATE, alertDate DATE, ownerID INTEGER REFERENCES APP.Owner(ID))");
//			connect.createStatement().execute("Drop table APP.Note");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			System.out.println("Tables already exist!");
		}
		
	}
//	public void insert(String statement) {
//		try {
//			this.conn.createStatement().execute("Insert into "+tableName+" values ("+value+")");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	public void delete(String table) {
//		
//	}
	public void insertUser(User user) throws SQLException {
		 PreparedStatement psInsert;
//	     PreparedStatement psUpdate;
	     ArrayList<Statement> statements = new ArrayList<Statement>();
	     
	     psInsert = conn.prepareStatement("insert into APP.Owner (userName,password) values (?, ?)");
	     statements.add(psInsert);

	     
	     psInsert.setString(1, user.getUserName());
	     psInsert.setString(2, user.getPassword());
	     psInsert.executeUpdate();
	     System.out.println("Inserted "+user.getUserName());
	}
	
	@SuppressWarnings("null")
	public User checkUser(String userName) throws SQLException {
		Statement s = null;
		ResultSet rs;
				
		s = conn.createStatement();
		rs = s.executeQuery("SELECT * FROM APP.Owner WHERE userName = '"+userName+"'");
		
		if (!rs.next()) {
			return null;
        }
		User reUser = new User();
		reUser.setuserId(rs.getInt(1));
		reUser.setUserName(rs.getString(2));
		reUser.setPassword(rs.getString(3));
		s.close();
		rs.close();
		return reUser;
	}
	public ArrayList<Note> browseNoteList(int userId) throws SQLException {
		String new_userId = String.valueOf(userId);
		
		
		Statement s = null;
		ResultSet rs;
		s = conn.createStatement();
		rs = s.executeQuery("SELECT * FROM APP.Note WHERE ownerID = "+userId);
		
		ArrayList<Note> notelist = new ArrayList<Note>();
		if (!rs.next()) {
			System.out.println("There 's no note");
			return null;
		}
		else {
			Note newNote = new Note();
			newNote.setNoteId(rs.getInt(1));
			newNote.setContent(rs.getString(2));
			newNote.setTitle(rs.getString(3));
			newNote.setCreatedDate(rs.getDate(4));
			newNote.setAlertDate(rs.getDate(5));
			newNote.setOwnerId(rs.getInt(6));
			notelist.add(newNote);
		}
		
		
		while(rs.next()){
			Note newNote = new Note();
			newNote.setNoteId(rs.getInt(1));
			newNote.setContent(rs.getString(2));
			newNote.setTitle(rs.getString(3));
			newNote.setCreatedDate(rs.getDate(4));
			newNote.setAlertDate(rs.getDate(5));
			newNote.setOwnerId(rs.getInt(6));
			notelist.add(newNote);
		}
		return notelist;
	}
	public void insertNote(Note note) throws SQLException {
		PreparedStatement psInsert;
//	     PreparedStatement psUpdate;
	    ArrayList<Statement> statements = new ArrayList<Statement>();
	    if (note.getAlertDate()!=null) {
	    	
	    
	    psInsert = conn.prepareStatement("insert into APP.Note (content, title, createdDate, alertDate, ownerID) values (?, ?, ?, ?, ?)");
	    statements.add(psInsert);

	     
	    psInsert.setString(1, note.getContent());
	    psInsert.setString(2, note.getTitle());
	    psInsert.setDate(3, (Date) note.getCreatedDate());
	    psInsert.setDate(4, (Date) note.getAlertDate());
	    psInsert.setInt(5, note.getOwnerId());
	    psInsert.executeUpdate();
	    System.out.println("Note "+note.getTitle()+" inserted!");
	    }
	    else {
	    	psInsert = conn.prepareStatement("insert into APP.Note (content, title, createdDate, ownerID) values (?, ?, ?, ?)");
		    statements.add(psInsert);

		     
		    psInsert.setString(1, note.getContent());
		    psInsert.setString(2, note.getTitle());
		    psInsert.setDate(3, (Date) note.getCreatedDate());
		    psInsert.setInt(4, note.getOwnerId());
		    psInsert.executeUpdate();
		    System.out.println("Note "+note.getTitle()+" inserted!");
	    }
	}
	public void updateNote(Note noteUpdated) throws SQLException {

		PreparedStatement psUpdate;
	    ArrayList<Statement> statements = new ArrayList<Statement>();

	    psUpdate = conn.prepareStatement("update APP.Note set content=?, title=?, alertDate=? where ID=?");
	    statements.add(psUpdate);
	    
	    psUpdate.setString(1, noteUpdated.getContent());
	    psUpdate.setString(2, noteUpdated.getTitle());
	    psUpdate.setDate(3, (Date) noteUpdated.getAlertDate());
	    psUpdate.setInt(4, noteUpdated.getNoteId());
	    psUpdate.executeQuery();
	    System.out.println("Updated");
	}

	public Note selectNote(int noteId) throws SQLException {
		Statement s = null;
		ResultSet rs;
				
		s = conn.createStatement();
		rs = s.executeQuery("SELECT * FROM APP.Note WHERE ID = "+noteId);
		
		if (!rs.next()) {
			return null;
        }
		//ID , content  title  createdDate , alertDate , ownerID 
		Note reUser = new Note();
		reUser.setNoteId(rs.getInt(1));
		reUser.setContent(rs.getString(2));
		reUser.setTitle(rs.getString(3));
		reUser.setCreatedDate(rs.getDate(4));
		reUser.setAlertDate(rs.getDate(5));
		reUser.setOwnerId(rs.getInt(6));
		s.close();
		rs.close();
		return reUser;
	}
	public void shutdown() throws SQLException {
		DriverManager.getConnection("jdbc:derby:EDITOR;shutdown=true");
	}
	public void deleteNote(int noteId) throws SQLException {
		Statement s = null;
				
		s = conn.createStatement();
		s.execute("DELETE FROM APP.Note WHERE ID = "+noteId);
		

	}
}
