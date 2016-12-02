import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.io.InputStream;
import java.sql.*;

public class logic{

	//global variables
	static Integer imageCounter = 0;//counter for what picture is being displayed
	static Integer numberOfPhotos = 0;//total number of photos 
	private static Connection con;
    private static Statement stmt;
	volatile static photo currentPhoto = new photo();//new photo object to store temp data in

	
	public static void connect() {
		/*
		 * connects to the databse and loads driver
		 */
        String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/zbtf7";
        String userID = "zbtf7";
        String password = "vSf3EdJfbk";
  
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
        try{
        	con = DriverManager.getConnection(url,userID,password);
        	stmt = con.createStatement();
        } catch (SQLException e){
        	System.out.println(e);
        }
	}
	
	
	public static void loadNumberOfPhotos(){
		/*
		 * gets the number of rows from the database and sets its to the numberOfPhotos
		 */
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from phototable");
			if (rs.next()){//if there is anything in the result statement
				numberOfPhotos = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public static void loadNewPhotoFromFileToDatabase() throws Exception{
		/*
		 * opens FileChooser
		 * updates currentnum to +1 for all photos that come after this new one
		 * so that they stay in the correct order
		 * makes preparedStatment to create a new row and set information. date and description fields are defaulted to spaces
		 * increments numberOfPhotos 
		 */
		final JFileChooser fc = new JFileChooser(); 
		int file = fc.showOpenDialog(fc);
		if(file == JFileChooser.APPROVE_OPTION){//if the user chose a file
			File selectedFile = fc.getSelectedFile();//get the file and add it to a file object
			PreparedStatement pstmt = null;
			imageCounter++;
			stmt.executeUpdate("update phototable set currentnum = currentnum+1 where currentnum > " + imageCounter + "-1");
			pstmt = con.prepareStatement("insert into phototable (currentnum, date, description, photo) values (?,?,?,?)");//set the preparedStatment to the connection
			
			Path path = Paths.get(selectedFile.getAbsolutePath());
			byte [] fileData = null;//creating array of bytes to hold image in 
			try {				
				fileData = Files.readAllBytes(path);
				ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
				
				pstmt.setInt(1, imageCounter);//setting the current picture number on the database
				pstmt.setString(2, " ");
				pstmt.setString(3, " ");
				pstmt.setBinaryStream(4,bis, (int) fileData.length);//loading the photo to the database
				pstmt.executeUpdate();
				
				numberOfPhotos++;
			}catch(IOException e){
				e.printStackTrace();
				imageCounter--;//setting the counter back to what it was if there is an error
			}	
		}
	}
	
	
	public static byte[] loadPhotoFromDatabase(int num){
		/*
		 * gets a Result set from the the database
		 * sets the information in currentPhoto photo object to
		 * the information received from the database 
		 * sends back a ByteArray to the caller
		 */
		int c;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM phototable WHERE currentnum = " + num);
			boolean found = rs.next();
			if (found) {//if there is still information in the resultSet rs
		        currentPhoto.setdateS(rs.getString("date"));
		        currentPhoto.setdescription(rs.getString("description"));
		        currentPhoto.setposition(rs.getString("currentnum"));
				InputStream in = rs.getBinaryStream("photo");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while ((c = in.read()) != -1)
					bos.write(c);
				return bos.toByteArray();//sends a ByteArray back to the caller so that the GUI can load the picture 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void saveInfoToDatabase(String date, String description) {
		try {
			con.setAutoCommit(false);
			stmt.executeUpdate("UPDATE phototable SET date = " + '"' + date + '"' + "WHERE currentnum = "
					+ String.valueOf(imageCounter));
			stmt.executeUpdate("UPDATE phototable SET description = " + '"' + description + '"' + "WHERE currentnum = "
					+ String.valueOf(imageCounter));
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void deletePhotoFromDatabase(){
		/*deletes photo from database 
		 * updates the currentnum field from each photo that comes after
		 * decrements the numberOfPhotos and imageCounter as needed  
		 */
		try {
			stmt.executeUpdate("delete from phototable where currentnum = " + imageCounter);
			stmt.executeUpdate("update phototable set currentnum = currentnum -1 where currentnum > " + imageCounter + " -1");
			numberOfPhotos --;
			if(imageCounter > numberOfPhotos){
				imageCounter --;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
	
	