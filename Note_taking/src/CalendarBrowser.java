import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
 
public class CalendarBrowser extends JApplet implements ActionListener {
	int userId;
	 DefaultTableModel model;
	 Calendar cal = new GregorianCalendar();
	 JLabel label;
	 JTable table;
	 
	 Container cp = getContentPane();
	 GroupLayout layout;
	 
	 JButton back = new JButton("Back");
	 ArrayList<Note> notes;
	 JScrollPane js;
	 JPanel calPanel;
	 int years, months, days; 	 
	 CalendarBrowser(int userId) throws IllegalAccessException, InstantiationException, SQLException {
		 
		 this.userId=userId;
//		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		 this.setTitle("Swing Calandar");
		 layout = new GroupLayout(cp);
		 cp.setLayout(layout) ; //not needed as container default is BorderLayout
		 
//		 this.setSize(300,200);
//		 this.setLayout(new BorderLayout());
//		 this.setVisible(true);
 
		 calPanel = new JPanel();
		 calPanel.setLayout(new BorderLayout());
		 
	    label = new JLabel();
	    label.setHorizontalAlignment(SwingConstants.CENTER);
 
	    JButton b1 = new JButton("<< Prev");
	    b1.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, -1);
	        updateMonth();
	      }
	    });
 
	    JButton b2 = new JButton("Next >>");
	    b2.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, +1);
	        updateMonth();
	      }
	    });
 
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.add(b1,BorderLayout.WEST);
	    panel.add(label,BorderLayout.CENTER);
	    panel.add(b2,BorderLayout.EAST);
 
 
	    String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	    model = new DefaultTableModel(null,columns);
	    table = new JTable(model);
	    JScrollPane pane = new JScrollPane(table);
 
	    calPanel.add(panel,BorderLayout.NORTH);
	    calPanel.add(pane,BorderLayout.CENTER);
	    calPanel.setSize(500, 250);
	    this.updateMonth();
	    //
	    table.addMouseListener(new java.awt.event.MouseAdapter() {
	    	@Override
	    	public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = table.rowAtPoint(evt.getPoint());
	            int col;
	            if (evt.getPoint() == null ) {
	            	col = -1;
	            }
	            else {
	            	col = table.columnAtPoint(evt.getPoint());
	            }
	            if(col!=(-1) && table.getValueAt(row, col)!= null) {
	                int value = Integer.parseInt(table.getValueAt(row, col).toString());
//	                String[] month_year = label.getText().split(" ");
	                days = value;
	                if (row >= 0 && col >= 0) {
	                	System.out.println("Click: Day "+days+", month "+months+", year "+years);
	                }
	                String selectedDate = ""+years+"-"+months+"-"+days;
	                System.out.println("Parse to util.Date: "+selectedDate.toString());
	                try {
						renderJs(userId, selectedDate);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	    	}
	    	}
	    );
  
	    // 
	    back.addActionListener(this);
	    //
	    js = new JScrollPane();
	    renderJs(this.userId, "2018-12-15");
	    
//	    layout.setHorizontalGroup(
//	    		layout.createSequentialGroup()
//	    			.addComponent(calPanel)
//	    			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//	    		           .addComponent(js)
//	    		           .addComponent(back))
//	    			);
//	    layout.setVerticalGroup(
//	    		layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//	    			.addComponent(calPanel)
//	    			.addGroup(layout.createSequentialGroup()
//	    					.addComponent(js)
//	    					.addComponent(back)));
	}
	public void renderJs(int userId, String selectedDate) throws IllegalAccessException, InstantiationException, SQLException {
		
		notes = loadNotes(userId, selectedDate);
		JPanel jp = new JPanel();
		BoxLayout boxlayout = new BoxLayout(jp, BoxLayout.Y_AXIS);

		JButton[] noteBut;
		int row;
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ;
		
	    if(notes != null) {
	    	row = notes.size();
	    	
	    	System.out.println("Size "+row);
	    	   
		    jp.setLayout(boxlayout); // new GridLayout(row,1) ) ;
		    jp.setSize(new Dimension(370, 400));
		    
			noteBut = new 	JButton[row];
		    for(int i = 0 ; i < row ; i++) {
		    	String title = notes.get(i).getTitle().length()>25 ?   
		    					notes.get(i).getTitle().substring(0, 24) 
		    					: notes.get(i).getTitle();
		    	String date = notes.get(i).getCreatedDate().toString();
		    	noteBut[i] = new JButton("<html><b>0</b><br><i>1</i></html>".replaceAll("0", title).replaceAll("1", date));

		    	noteBut[i].setActionCommand(String.valueOf(notes.get(i).getNoteId()));
		    	noteBut[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
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
				});
		    	noteBut[i].setSize(370, 40);//(new Dimension(350, 40));
		    	jp.add(noteBut[i],i);
		    	System.out.println("Add button "+(i+1));
		    }
	    }
	    js= new JScrollPane(jp,v, h ) ;
	    layout.setHorizontalGroup(
	    		layout.createSequentialGroup()
	    			.addComponent(js)
	    			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    		           .addComponent(calPanel)
	    		           .addComponent(back))
	    			);
//	    layout.setHorizontalGroup(
//	    		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//	    		.addComponent(calPanel)
//	    		.addComponent(js)
//	    		.addComponent(back));
	    
	    layout.setVerticalGroup(
	    		layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	    			.addComponent(js)
	    			.addGroup(layout.createSequentialGroup()
	    					.addComponent(calPanel)
	    					.addComponent(back)));
//	    layout.setVerticalGroup(
//	    		layout.createSequentialGroup()
//	    		.addComponent(calPanel)
//	    		.addComponent(js)
//	    		.addComponent(back));
//	    js.repaint();
//	    cp.revalidate();
//	    cp.repaint();
	}
	void updateMonth() {
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	 
	    String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
	    int month_int = cal.get(Calendar.MONTH)+1;
	    this.months = month_int; // month selected
	    int year = cal.get(Calendar.YEAR);
	    this.years = year; //year selected
	    label.setText(month_int + " " + year);
	 
	    int startDay = cal.get(Calendar.DAY_OF_WEEK);
	    int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
	 
	    model.setRowCount(0);
	    model.setRowCount(weeks);
	 
	    int i = startDay-1;
	    for(int day=1;day<=numberOfDays;day++){
	      model.setValueAt(day, i/7 , i%7 );    
	      i = i + 1;
	    }
    
	}
	void updateHightlight() {
	   
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == back) {
			Login login = (Login) getParent();
			try {
				login.add(new FileBrowser(userId),"fb_back");
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
			login.cl.show(login, "fb_back");
		}
	}
	public ArrayList<Note> loadNotes(int userId, String selectedDate) throws IllegalAccessException, InstantiationException, SQLException {
		DBConnection newConn = new  DBConnection();
		ArrayList<Note> notes = newConn.browseAlertNoteList(userId, selectedDate); //new java.sql.Date(selectedDate.getTime()));
		return notes;
		
	}
 
}