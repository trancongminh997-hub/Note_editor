import javax.swing.*;  
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;

public class FileBrowser extends JApplet implements ActionListener{

	
	int row;
	
	int userId;

	//c1
    JButton newNote = new JButton("New");
    //c2
    JButton calendar = new JButton("Calendar");
    //c3
    JButton logOut = new JButton("Log Out");
    JButton[] noteBut;
    Container cp = getContentPane();
    GroupLayout layout;

    
	public FileBrowser(int userId) throws IllegalAccessException, InstantiationException, SQLException { 
		this.userId = userId;
		ArrayList<Note> notes = loadNotes(userId);
			
		

		layout = new GroupLayout(cp);
	    cp.setLayout(layout) ; //not needed as container default is BorderLayout
	    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ;
	    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ;
	    
	    
//	    JList list = new JList();
//	    list.setFixedCellHeight(40);
//	    list.setFixedCellWidth(370);
//	    list.setSize(370, 350);
	    //c4
	    JPanel jp = new JPanel();
	    if(notes != null) {
	    	this.row = notes.size();
	    	
	    	
	    	   
		    jp.setLayout( new GridLayout(row,1) ) ;
		    jp.setSize(new Dimension(370, 400));
		    
			this.noteBut = new 	JButton[row];
		    for(int i = 0 ; i < row ; i++) {
		    	String title = notes.get(i).getTitle().length()>25 ?   
		    					notes.get(i).getTitle().substring(0, 24) 
		    					: notes.get(i).getTitle();
		    	String date = notes.get(i).getCreatedDate().toString();
		    	noteBut[i] = new JButton("<html><b>0</b><br><i>1</i></html>".replaceAll("0", title).replaceAll("1", date));
		    	
		    	
		    	//noteBut[i].setMinimumSize(new Dimension(350, 40));
		    	
		    	noteBut[i].addActionListener(this);
		    	noteBut[i].setActionCommand(String.valueOf(notes.get(i).getNoteId()));
		    	noteBut[i].setSize(370, 40);//(new Dimension(350, 40));
		    	jp.add(noteBut[i],i);
		    	System.out.println("Add button "+(i+1));
		    }
	    }
//	    list.add(noteBut);
	    JScrollPane js = new JScrollPane(jp,v, h ) ;
	    
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    layout.setVerticalGroup(
	    		   layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	    		      .addGroup(layout.createSequentialGroup()
	    		           .addComponent(newNote)
	    		           .addComponent(calendar)
	    		           .addComponent(logOut))
	    		      .addComponent(js)
	    		);
	    layout.setHorizontalGroup(
	    		   layout.createSequentialGroup()
	    		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    		           .addComponent(newNote)
	    		           .addComponent(calendar)
	    		           .addComponent(logOut))
	    		      .addComponent(js)
	    		);
	    newNote.addActionListener(this);
	    calendar.addActionListener(this);
	    logOut.addActionListener(this);
	
	  }
	@Override
	public void actionPerformed(ActionEvent e)  {
		// TODO Auto-generated method stub
		if (e.getSource() == newNote) {
			//get parent is login 
			Login login = (Login) getParent();
			// add new object is EditingNote()

			try {
				login.add(new EditingNote(0,userId),"EN");
			} catch (IllegalAccessException | InstantiationException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// show object out
			login.cl.show(login, "EN");
		}
		else if (e.getSource() == calendar) {
			System.out.println("Halo "+e.getSource().toString());
		}
		else if (e.getSource() == logOut) {
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
		else{
			String i = e.getActionCommand();
			int noteId = Integer.parseInt(i);
			Login login = (Login) getParent();
			try {
				login.add(new EditingNote(noteId,0),"EN");
			} catch (IllegalAccessException | InstantiationException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			login.cl.show(login, "EN");
			System.out.println("Halo "+e.getSource().toString());
		}
	}  
	public ArrayList<Note> loadNotes(int userId) throws IllegalAccessException, InstantiationException, SQLException {
		DBConnection newConn = new  DBConnection();
		ArrayList<Note> notes = newConn.browseNoteList(userId);
		return notes;
		
	}
} 