package mvc.models;


import java.util.ArrayList;

import mvc.Question;



public class QuestionGatewayViewModel {
	private static int questionID;
	private static int count;
	
	public static ArrayList<Question> questionList = new ArrayList<Question>();
	
	
	public static void setquestion(int q){
		questionID = q;
	}
	
	public static int getquestion(){
		return questionID;
	}
	
	public static void setcount(int c){
		count = c;
	}
	
	public static int getcount(){
		return count;
	}
}

