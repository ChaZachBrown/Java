

public class Question {
	private String question;
	private int id;
	
	public Question( int id, String question){
		this.question = question;
		this.id = id;
	}
	
	
	public String getquestion(){
		return this.question;
	}
	public int getid(){
		return this.id;
	}
	
	
	public void setquestion(String question){
		this.question=question;
	}
	public void setid(int id){
		this.id=id;
	}
}
