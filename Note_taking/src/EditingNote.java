import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.GroupLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//Date pickle
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

//import net.codejava.swing.DateLabelFormatter;

public class EditingNote extends JApplet implements ActionListener {

	int noteId, ownerId;
	//Set date picker
	SqlDateModel model = new SqlDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	//Set current Date
	Calendar calendar = Calendar.getInstance();
	java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
	//Set labels and buttons
	JLabel alertL = new JLabel("Alerting Date: ");
	JLabel notification = new JLabel();
	JButton save = new JButton("Save");
	JButton delete = new JButton("Delete");
	JButton back = new JButton("Back");
	JTextField titleTF = new JTextField();
	JTextArea noteArea = new JTextArea(20,40);
	JPanel Writing_note = new JPanel(new GridLayout(4,2));
	Container contentPane = getContentPane();
	DBConnection conn = new DBConnection();
	Note note = null;
	@SuppressWarnings("deprecation")
	public EditingNote(int noteId, int ownerId) throws SQLException, IllegalAccessException, InstantiationException{
		this.noteId = noteId;
		this.ownerId = ownerId;
		if(this.noteId!=0) {
			note = conn.selectNote(this.noteId);
			titleTF.setText(note.getTitle());
			noteArea.setText(note.getContent());
			Date alert = (Date) note.getAlertDate();
//			datePicker.getModel().setDate(alert.getYear(), alert.getMonth(), alert.getDay());
		}
		save.addActionListener(this);
		delete.addActionListener(this);
		back.addActionListener(this);
		
		GroupLayout layout = new GroupLayout(contentPane);
		
		contentPane.setLayout(layout);
	
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    		  .addComponent(save)
				    		  .addComponent(alertL)
				    		  .addComponent(datePicker)
				    		  .addComponent(delete)
				    		  .addComponent(back))
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				           .addComponent(titleTF)
				           .addComponent(noteArea)
				           .addComponent(notification))
				      
				);
		layout.setVerticalGroup(
				   layout.createParallelGroup()
				      .addGroup(layout.createSequentialGroup()
				    		  .addComponent(save)
				    		  .addComponent(alertL)
				    		  .addComponent(datePicker)
				    		  .addComponent(delete)
				    		  .addComponent(back))
				      .addGroup(layout.createSequentialGroup()
				           .addComponent(titleTF)
				           .addComponent(noteArea)
				           .addComponent(notification))     
				);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//set Back button
		if(e.getSource() == back){
			Login login = (Login) getParent();
			try {
				login.add(new FileBrowser(this.ownerId),"fb");
			} catch (IllegalAccessException | InstantiationException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			login.cl.show(login, "fb");
		}
		if(e.getSource() == delete) {
			titleTF.setText("");
			noteArea.setText("");
			//set datePicker is null
			datePicker.getModel().setValue(null);
			//Drop note in database
			if(this.noteId != 0) {
				DBConnection conn;
				try {
					conn = new DBConnection();
					conn.deleteNote(this.noteId);
				} catch (IllegalAccessException | InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		//set Delete button
		if(e.getSource() == delete) {
			titleTF.setText("");
			noteArea.setText("");
			//set datePicker is null
			datePicker.getModel().setValue(null);
			if(note != null) {
				try {
					conn.deleteNote(noteId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		//set condition for save note (Update or insert new one)
		if(e.getSource() == save) {
			String getTitleValue = titleTF.getText();
			String getContentValue = noteArea.getText();
			Date getDatePickerValue = (Date) datePicker.getModel().getValue();
			//Check condition of note.
			if(getTitleValue.equals("")||getContentValue.equals("")) {
				notification.setText("Please enter all information of the note.");
			}
			else {
				try {
					if(note == null) {
						if(getDatePickerValue != null) {
							conn.insertNote(new Note(getContentValue,getTitleValue,currentDate,ownerId,getDatePickerValue));
						}
						else {
							conn.insertNote(new Note(getContentValue,getTitleValue,currentDate,ownerId));
						}
					}
					else {
						note.setTitle(getTitleValue);
						note.setContent(getContentValue);
						note.setCreatedDate(currentDate);
						note.setAlertDate(getDatePickerValue);
						conn.updateNote(note);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Login login = (Login) getParent();
				try {
					login.add(new FileBrowser(this.ownerId),"fb");
				} catch (IllegalAccessException | InstantiationException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				login.cl.show(login, "fb");
			}
		}
	}
}