import java.io.Serializable;
import javax.swing.ImageIcon;

public class photo implements Serializable{
	
	
	private static final long serialVersionUID = 2;
	public  ImageIcon image;
	public  String descriptionS;
	public  String dateS;
	
	
	public photo(ImageIcon l){//constructor 
		this.image = l;
	}
	
	
	//********************SETTERS************************
	public void setImage(ImageIcon l){
		this.image = l;
	}
	
	public void setdescription(String d){
		this.descriptionS = d;
	}
	
	public void setdateS(String d){
		this.dateS = d;
	}
	
	
	//******************GETTERS***************************
	public ImageIcon getImage(){
		return this.image;
	}
	
	public String getdescription(){
		return this.descriptionS;
	}
	
	public String getdate(){
		return this.dateS;
	}
}
