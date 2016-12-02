

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AnswerServlet
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String questionID;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		//*******************************************************************
		questionID = request.getParameter("id");
		if(questionID != null){
			QuestionGateway.setquestion(Integer.valueOf(questionID));
		}else{
			questionID = String.valueOf(QuestionGateway.getquestion());
		}
		String question = DBAbstraction.getSpecificQuestionFromDB(Integer.valueOf(questionID));

		//*******************************************************************
		

		out.println("<p>Answers for question: " + question + "</p>");
		DBAbstraction.getAnswerFromDB(Integer.valueOf(questionID));
		if(AnswerGateway.answerList.size() != 0){
			List<Answer> answers = AnswerGateway.answerList;
	        int count = 0;
	        for (Answer a : answers) {
	        	count += 1;
	        //	id=a.getid();
	    	    out.print("<p>" + count + ". " + a.getquestion() + "</a></p>");	        	

	        	     	
	        }
		}
        
        out.println("<form method=\"POST\" action=\"AnswerServlet\">");
        out.println("<p><input type=\"text\" name=\"theAnswer\" size=\"50\"> <input type=\"submit\" value=\"Add Answer\"></p>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String theAnswer = request.getParameter("theAnswer");
        DBAbstraction.addAnswerToDB(Integer.valueOf(questionID), theAnswer);
        
        response.sendRedirect("AnswerServlet");
	}

}
