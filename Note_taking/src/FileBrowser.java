import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener {
	JLabel label = new JLabel("File list: ");
	JButton newFile = new JButton("New File");
	JButton open = new JButton("Open");
	JTextField newFileTF = new JTextField(10);
	ButtonGroup bg;
	File directory;
	
	public FileBrowser(String dir) {
		directory = new File(dir);
		directory.mkdirs();
		JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3, 1));
		fileList.add(label);
		bg = new ButtonGroup();
		for(File file: directory.listFiles()) {
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP = new JPanel();
		newP.add(newFileTF);
		newP.add(newFile);
		fileList.add(open);
		fileList.add(newP);
		add(fileList);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
