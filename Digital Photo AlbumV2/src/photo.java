import java.io.Serializable;
import javax.swing.ImageIcon;

public class photo implements Serializable{
	
	
	private ImageIcon image;
	private String descriptionS;
	private String dateS;
	private String position;
	
	
	public photo(ImageIcon l){//constructor 1
		this.image = l;
	}
	
	public photo(){//constructor 2
		
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
	public void setposition(String p){
		this.position = p;
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
	public String getposistion(){
		return this.position;
	}
}
