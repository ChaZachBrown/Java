import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class main_activity extends JFrame  implements ActionListener {
	
	
	public static void main (String[] args){
		JFrame frame = new main_activity("Photo Viewer");
		frame.setSize(600, 600);
		frame.setVisible(true);
		logic.deserializePhotoList();
	}
	

	//container objects
	Container mainPane = getContentPane();
	JPanel panel1 = new JPanel(new BorderLayout());//creating a new panel1 to put at the south region of mainPane
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));//creating a new panel2 to put at the south region of panel1
	JPanel panel3 = new JPanel(new GridLayout(2,2));//creating a new panel3 to put at the north region of panel2
	
		
	//menuBar objects
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu view = new JMenu("View");
	 JButton exit = new JButton("Exit");
	static JButton browse = new JButton("Browse");
	static JButton maintain = new JButton("Maintain");
	
	
	//objects in main
	static JLabel imageLabel = new JLabel();
	static JScrollPane scrollPane = new JScrollPane (imageLabel);//creating ScrollPane so people can scroll around a see their full image and putting image in it 
	
	
	//objects in panel2
	static JTextField currentPhoto = new JTextField(String.valueOf(logic.imageCounter));
	static JTextArea numberOfPhotosText = new JTextArea(String.valueOf(logic.numberOfPhotos));
	static JButton prev = new JButton("<prev");
	static JButton next = new JButton("next>");
	static JMenuItem delete = new JMenuItem("Delete");
	static JMenuItem saveChanges = new JMenuItem("Save Changes"); 
	static JMenuItem addPhoto = new JMenuItem("Add Photo");
	
	
	//objects in panel3
	JTextArea description = new JTextArea("Description: ");
	JTextArea date = new JTextArea("Date: ");
	static JTextField descriptionField = new JTextField("");
	static JTextField dateField = new JTextField("");
	
	

	//main_activity constructor ***************************************************************************************
	public main_activity(String title){
		super(title);

		//adding menu Bar codez
		menuBar.add(file);
		menuBar.add(view);
		this.setJMenuBar(menuBar);
		file.add(exit);
		exit.addActionListener(this);
		
		view.add(browse);
		browse.addActionListener(this);
		
		view.add(maintain);
		maintain.addActionListener(this);
		
		
		// adding objects to main
		mainPane.add(panel1, BorderLayout.SOUTH);
		mainPane.add(scrollPane,BorderLayout.CENTER);
		

		//adding panels into other panels
		panel1.add(panel3, BorderLayout.CENTER);
		panel1.add(panel2, BorderLayout.SOUTH);
		
	
		//adding objects to the panel2
		panel2.add(currentPhoto);
		currentPhoto.addActionListener(this);
		panel2.add(numberOfPhotosText);
		numberOfPhotosText.setEditable(false);
		
		panel2.add(prev);
		prev.addActionListener(this);
		
		panel2.add(next);
		next.addActionListener(this);
		
		panel2.add(delete);
		delete.addActionListener(this);
		delete.setVisible(false);//setting delete to be invisible till the maintain mode is enabled
		
		panel2.add(saveChanges);
		saveChanges.addActionListener(this);
		saveChanges.setVisible(false);//setting saveChanges to invisible till the maintain mode is enabled
		
		panel2.add(addPhoto);
		addPhoto.addActionListener(this);
		addPhoto.setVisible(false);//setting addPhoto to invisible till the maintain mode is enabled
		
		
		//adding objects to the panel3
		panel3.add(description);
		description.setEditable(false);
		panel3.add(descriptionField);
		panel3.add(date);
		date.setEditable(false);
		panel3.add(dateField);
		
		
		logic.disableB();//disabling prev and next button until photos are loading into the program
		
	}
	//end constructor ****************************************************************************************************

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		logic.disableB();//disable the next and prev buttons depending on certain conditions
		
		//When next or prev buttons are pressed
		if(e.getSource() == next){//if next button is pushed
			logic.changePhoto(1);
		}
		if (e.getSource() == prev){//if prev button is pushed 
			logic.changePhoto(0);
		}
		logic.disableB();
		
		
		//When menu buttons are pressed
		if(e.getSource() == maintain){//setting delte, save and add photo buttons to be visisle if maintain button is pressed
			logic.setVisible(true);
		}
		if(e.getSource() == browse){//setting delete, save and add Photo buttons to be invisible if the browse button is pressed
			logic.setVisible(false);
		}
		if(e.getSource() == exit){//closing the program if this button is pressed
			System.exit(0);
		}
		
	
		//when delete saveChanges and addPhoto buttons are pressed
		if(e.getSource() == addPhoto){ // will save the photo selected from the file browser and save it in an arraylist
			logic.loadNewPhoto();
		}
		if(e.getSource() == saveChanges){//will save the changes made to the description and date of the current photo
			logic.save();
		}
		if(e.getSource() == delete){//will delete the current photo from the arraylist
			logic.delete();
		}
		
		
		//when someone types in the currentPhoto textField
		if(e.getSource() == currentPhoto){
			logic.goToPhoto();
		}	
	}
}
