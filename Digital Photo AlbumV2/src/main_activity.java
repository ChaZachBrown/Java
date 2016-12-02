import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
import javax.swing.SwingUtilities;

public class main_activity extends JFrame  implements ActionListener {
	
	
	public static void main (String[] args){
		JFrame frame = new main_activity("Photo Viewer");
		frame.setSize(600, 600);
		frame.setVisible(true);
		logic.connect();
		loadNumberOfPhotosToUI();
		loadImageInfoToUI();
	}
	
	private static byte[] rawImage;

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
	
	//objects in panel 2
	private static JTextField currentPhoto = new JTextField(String.valueOf(logic.imageCounter));
	static JTextArea numberOfPhotosText = new JTextArea(String.valueOf(logic.numberOfPhotos));
	static JButton prev = new JButton("<prev");
	static JButton next = new JButton("next>");
	static JMenuItem delete = new JMenuItem("Delete");
	static JMenuItem saveChanges = new JMenuItem("Save Changes"); 
	static JMenuItem addPhoto = new JMenuItem("Add Photo");
	
	
	//objects in panel 3
	JTextArea description = new JTextArea("Description: ");
	JTextArea date = new JTextArea("Date: ");
	private static JTextField descriptionField = new JTextField("");
	private static JTextField dateField = new JTextField("");
	

	
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
		
		
		disableB();//disabling prev and next button until photos are loading into the program
		
	}
	//end constructor *************************************************************************************************

	
	
	public  void setVisible1(boolean t){
		delete.setVisible(t);
		saveChanges.setVisible(t);
		addPhoto.setVisible(t);
	}
	
	
	public static void disableB() {
		if (logic.imageCounter >= logic.numberOfPhotos) {
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
			saveChanges.setEnabled(true);
		}

		
		if (logic.imageCounter <= 1) {
			prev.setEnabled(false);
		} else {
			prev.setEnabled(true);
		}

		
		if (logic.imageCounter == 0) {
			saveChanges.setEnabled(false);
			delete.setEnabled(false);
		} else {
			saveChanges.setEnabled(true);
			delete.setEnabled(true);
		}
	}
	
	
	public static void loadImageInfoToUI() {
		/*
		 * loads information from the temp photo object into the UI
		 * creates a new thread to load information form databse to UI  does not get locked up
		 */
		if(logic.numberOfPhotos > 0 & logic.imageCounter ==  0){// if there is at least one photo in the database then load the first one
			logic.imageCounter = 1;
		}
		
		
		//creating separate threads to run to that the UI doesn't get locked up **********************************
		Runnable updateUIThread = new Runnable(){

			@Override
			public void run() {
					//scrollPane.repaint();
					ImageIcon image = new ImageIcon(rawImage);
					imageLabel.setIcon(image);
					descriptionField.setText(logic.currentPhoto.getdescription());
					dateField.setText(logic.currentPhoto.getdate());
					currentPhoto.setText(logic.currentPhoto.getposistion());
					logic.imageCounter = Integer.valueOf(logic.currentPhoto.getposistion());
					disableB();
				}
		};

		Runnable databaseThread = new Runnable(){

			@Override
			public void run() {
				rawImage = logic.loadPhotoFromDatabase(logic.imageCounter);
				if (rawImage != null) {
				
					SwingUtilities.invokeLater(updateUIThread);
				}
			}
		};
		Thread seperateThread = new Thread(databaseThread);
		seperateThread.start();
		
		
		//end separate threads *************************************************************************************
	}

	
	public static void loadNumberOfPhotosToUI(){
		logic.loadNumberOfPhotos();//calls method to load number of photos from the database
		numberOfPhotosText.setText(String.valueOf(logic.numberOfPhotos));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		disableB();//disable the next and prev buttons depending on certain conditions
		//When next or prev buttons are pressed
		if(e.getSource() == next){//if next button is pushed
			logic.imageCounter += 1;
			loadImageInfoToUI();
			disableB();
		}
		
		if (e.getSource() == prev){//if prev button is pushed 
			logic.imageCounter -= 1;
			loadImageInfoToUI();
			disableB();
		}
		
		disableB();
		

		
		
		//When menu buttons are pressed
		if(e.getSource() == maintain){//setting delte, save and add photo buttons to be visisle if maintain button is pressed
			setVisible1(true);
		}
		
		if(e.getSource() == browse){//setting delete, save and add Photo buttons to be invisible if the browse button is pressed
			setVisible1(false);
		}
		
		if(e.getSource() == exit){//closing the program if this button is pressed
			System.exit(0);
		}
		
		
	
		//when addPhoto, saveChanges and delete buttons are pressed
		if(e.getSource() == addPhoto){ // will save the photo selected from the file browser and save it in an arraylist
			try {
				logic.loadNewPhotoFromFileToDatabase();
				loadImageInfoToUI();
				numberOfPhotosText.setText((String.valueOf(logic.numberOfPhotos)));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//loadPhotoFromList();
			disableB();
		}
		
		if(e.getSource() == saveChanges){//will save the changes made to the description and date of the current photo
			logic.saveInfoToDatabase(dateField.getText(),descriptionField.getText());
		}
		
		if(e.getSource() == delete){//will delete the current photo from the arraylist
			logic.deletePhotoFromDatabase();
			loadImageInfoToUI();
			loadNumberOfPhotosToUI();
		}
		
		
		
		//when someone types in the currentPhoto textField
		if(e.getSource() == currentPhoto){
			
			if (Integer.valueOf(currentPhoto.getText()) > 0 & Integer.valueOf(currentPhoto.getText()) <= logic.numberOfPhotos ){
				logic.imageCounter = Integer.valueOf(currentPhoto.getText());
				loadImageInfoToUI();
			}
			if(Integer.valueOf(currentPhoto.getText()) < 0){
				logic.imageCounter = 0;
				loadImageInfoToUI();
			}
			if (Integer.valueOf(currentPhoto.getText()) > logic.numberOfPhotos){
				logic.imageCounter = logic.numberOfPhotos;
				currentPhoto.setText(String.valueOf(logic.imageCounter));
				loadImageInfoToUI();
			}
		}	
	}
}
