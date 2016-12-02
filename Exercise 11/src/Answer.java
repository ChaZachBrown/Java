

public class Answer {
	private String answer;
	private int id;
	private int questionID;
	
	public Answer( int id,int questionID, String answer){
		this.answer = answer;
		this.id = id;
		this.questionID = questionID;
		
	}
	
	
	public String getquestion(){
		return this.answer;
	}
	public int getid(){
		return this.id;
	}
	
	
	public void setquestion(String answer){
		this.answer=answer;
	}
	public void setid(int id){
		this.id=id;
	}

}
