

import java.util.ArrayList;



public class QuestionGateway {
	private static int questionID;
	
	
	public static ArrayList<Question> questionList = new ArrayList<Question>();
	
	
	public static void setquestion(int q){
		questionID = q;
	}
	
	public static int getquestion(){
		return questionID;
	}
}

