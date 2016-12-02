package mvc.models;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mvc.Answer;
import mvc.Question;



public class DBAbstraction {
	
	private static Connection con;
    private static Statement stmt;
	
    
    
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
	
	
	public static void addQuestionToDB(int id, String q){
		/*
		 * adds questions to the DB
		 */
		try {
			stmt.executeUpdate("INSERT into questions (q_id,question) VALUES("+id+",'"+q+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addAnswerToDB(int id, String a){
		try{
			stmt.executeUpdate("INSERT into answers (a_id,answer) VALUES("+id+",'"+a+"')");

		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getQuestionsFromDB(){
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions");
			QuestionGatewayViewModel.questionList.clear();

			while(rs.next()){
				Question q = new Question(Integer.valueOf(rs.getString("q_id")), rs.getString("question"));
				QuestionGatewayViewModel.questionList.add(q);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String getSpecificQuestionFromDB(int i){
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE q_id ="+i);
			if(rs.next()){
			return String.valueOf(rs.getString("question"));
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fsdfsdsdfs");
			return null;
		}
	}
	
	public static void getAnswerFromDB(int i){
		try{
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM answers Where a_id = "+i);
			AnswerGatewayViewModel.answerList.clear();
			
			while(rs.next()){
				Answer a = new Answer(Integer.valueOf(rs.getString("a_id")), rs.getString("answer"));
				AnswerGatewayViewModel.answerList.add(a);
				

			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
