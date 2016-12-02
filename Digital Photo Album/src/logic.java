import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class logic{


	//global variables
	static Integer imageCounter = 0;//counter for what picture is being displayed
	static Integer numberOfPhotos = 0;//total number of photos 
	static ArrayList<photo> photoList = new ArrayList<photo>();
	
	
	//method to disable buttons if the parameters are hit
	public static void disableB() {
		if (imageCounter >= numberOfPhotos) {
			main_activity.next.setEnabled(false);
		} else {
			main_activity.next.setEnabled(true);
			main_activity.saveChanges.setEnabled(true);
		}

		if (imageCounter <= 1) {
			main_activity.prev.setEnabled(false);
		} else {
			main_activity.prev.setEnabled(true);
		}
		
		if (imageCounter == 0){
			main_activity.saveChanges.setEnabled(false);
			main_activity.delete.setEnabled(false);
		}else{
			main_activity.saveChanges.setEnabled(true);
			main_activity.delete.setEnabled(true);
		}
	}

	//method to loadPhoto and add it to the Arraylist of Photo objects
	/*This method prompts to user with a file view to choose a file from
	 * then takes that file and tries to put it into a burredImage object
	 * then sets the image as an imageIcon then sets the imageIcon to an imageLabel to put into a scrollPane 
	 * then makes a new Photo object and adds the image the user selected to it using the default constructor
	 * then adds the new photo object to an arrayList of all the photo objects
	 * then increments the current photo and numberOfPhoto counters and loads them to the GUI
	 * then loads the photo from photolist into the scrollpane
	 * lastly it checks to see if the buttons still need to be disabled
	 */
	public static void loadNewPhoto(){
		final JFileChooser fc = new JFileChooser(); 
		int file = fc.showOpenDialog(fc);
		if(file == JFileChooser.APPROVE_OPTION){//if the user chose a file
			File selectedFile = fc.getSelectedFile();//get the file and add it to a file object
		    try {
				BufferedImage image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
				ImageIcon imageIcon = new ImageIcon(image);
				photo newPhoto  = new photo(imageIcon); // add the file path to a new photo object
			    photoList.add(imageCounter, newPhoto);//add the new photo object to the arraylist
			    imageCounter++;
			    main_activity.currentPhoto.setText(String.valueOf(imageCounter));//setting textField(currentPhoto) to the value of imageCounter
			    numberOfPhotos++;
			    main_activity.numberOfPhotosText.setText(String.valueOf(numberOfPhotos));
			    loadPhotoFromList();
			} catch (IOException e) {
				System.out.println("No file");
				e.printStackTrace();
			}
		}
		disableB();
		serializePhotoList();
	}
	
	
	//method to go to next Photo or previous photo
	public static void changePhoto(int i){
		if (i == 1){//go to next photo
			imageCounter ++;
			main_activity.currentPhoto.setText(String.valueOf(imageCounter));//setting textField(currentPhoto) to the value of imageCounter
			//loading next image in arraylist to be displayed
			loadPhotoFromList();
		}
		if(i == 0){//go to previous photo
			imageCounter --;
			main_activity.currentPhoto.setText(String.valueOf(imageCounter));//setting textField(currentPhoto) to the value of imageCounter
			//loading previous image in arraylist to be displayed
			loadPhotoFromList();
		}
	}
	
	
	//method that loads a photo from the photoList and displays the photo, description the scrollPane
	public static void loadPhotoFromList(){
		main_activity.imageLabel.setIcon(photoList.get(imageCounter -1).getImage());//adding imageIcon to JLabel
		System.out.println(photoList.get(imageCounter -1).getImage().getIconWidth());
		main_activity.scrollPane.repaint();
		main_activity.descriptionField.setText(photoList.get(imageCounter -1).getdescription());
		main_activity.dateField.setText(photoList.get(imageCounter -1).getdate());
		
	}
	
	
	//method to save the changes made in description and date for a photo 
	public static void save(){
		photoList.get(imageCounter -1).setdescription(main_activity.descriptionField.getText());
		photoList.get(imageCounter -1).setdateS(main_activity.dateField.getText());
		for (photo p : photoList){ //debugging++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	System.out.println(p.getImage());
	    	System.out.println(p.getdescription());
	    	System.out.println(p.getdate());
	    	System.out.println(p.getImage().getIconWidth());
	    }
		serializePhotoList();
	}
	
	
	//method to delete the current photo from the arrayList
	public static void delete(){
			
			photoList.remove(imageCounter -1 );
			main_activity.scrollPane.repaint();
			if (imageCounter-1 == photoList.size()){
				imageCounter--;
			}
			main_activity.currentPhoto.setText(String.valueOf(imageCounter));
			numberOfPhotos --;
			main_activity.numberOfPhotosText.setText(String.valueOf(numberOfPhotos));
			/*main_activity.imageIcon.setImage(photoList.get(imageCounter -1).image);//converting BufferedImage to ImageIcon
			main_activity.imageLabel.setIcon(main_activity.imageIcon);//adding imageIcon to JLabel
			main_activity.scrollPane.repaint();*/
			if(imageCounter != 0){
				loadPhotoFromList();

			}
			disableB();
			serializePhotoList();
		}
	
	
	//method to set these buttons to be visible
	public static void setVisible(boolean t){
		main_activity.delete.setVisible(t);
		main_activity.saveChanges.setVisible(t);
		main_activity.addPhoto.setVisible(t);
	}
	
	
	//method to go to a specified photo number
	public static void goToPhoto(){
		int i = Integer.parseInt(String.valueOf(main_activity.currentPhoto.getText()));
		if (i > numberOfPhotos){//if the user tries to access a photo number that is passed the total number of photos then it will go to the last one
			imageCounter = photoList.size();
			main_activity.currentPhoto.setText(String.valueOf(imageCounter));//setting textField(currentPhoto) to the value of imageCounter
			loadPhotoFromList();
		} else{//changes photo to the one the user entered
			imageCounter = i;
			main_activity.currentPhoto.setText(String.valueOf(imageCounter));//setting textField(currentPhoto) to the value of imageCounter
			loadPhotoFromList();
		}
	}

	
	//method to serialize the photoList
	public static void serializePhotoList(){
		try{
			FileOutputStream fos = new FileOutputStream("photolistfile");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(photoList);
			oos.close();
			fos.close();
		} catch (IOException ioe){
			ioe.printStackTrace();
			System.out.println("error");
		}
	}
	
	
	//method to de-serialize the photoList 
	@SuppressWarnings("unchecked")
	public static void deserializePhotoList(){
		try{
			FileInputStream fis = new FileInputStream("photolistfile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			photoList = (ArrayList<photo>) ois.readObject();
			ois.close();
			fis.close();
			if (photoList.size() != 0) {//if there is nothing in the photoList from the last run then dont load anything
				numberOfPhotos = photoList.size();
				imageCounter++;
				main_activity.numberOfPhotosText.setText(String.valueOf(numberOfPhotos));
				main_activity.currentPhoto.setText(String.valueOf(imageCounter));
				loadPhotoFromList();
				disableB();
			}
			} catch (IOException ioe){
				ioe.printStackTrace();
				return;
			} catch (ClassNotFoundException c){
				System.out.println("Class not found");
				c.printStackTrace();
				return;
			}
		
	}
}


