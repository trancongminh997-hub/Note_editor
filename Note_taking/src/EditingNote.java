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
	//Set date picker
	SqlDateModel model = new SqlDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	//Set labels and buttons
	JLabel alertL = new JLabel("Alerting Date: ");
	JButton save = new JButton("Save");
	JButton delete = new JButton("Delete");
	JButton back = new JButton("Back");
	JTextField titleTF = new JTextField();
	JTextArea noteArea = new JTextArea(20,40);
	JPanel Writing_note = new JPanel(new GridLayout(4,2));
//	JPanel panel = new JPanel();
	
	public EditingNote(){
		save.addActionListener(this);
		delete.addActionListener(this);
		back.addActionListener(this);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
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
				           .addComponent(noteArea))
				      
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
				           .addComponent(noteArea))
				      
				);
		frame.pack();
		frame.setSize(500,500);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	
	}
//	public static void main(String[] args) {
//
//		Writing_note writingNote = new Writing_note();
//
//	}
}