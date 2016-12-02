

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class QuestionsServlet
 */
@WebServlet("/QuestionsServlet")
public class QuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int count = 0;

    /**
     * Default constructor. 
     */
    public QuestionsServlet() {
        // TODO Auto-generated constructor stub

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    DBAbstraction.connect();//connecting to database

	    response.setContentType("text/html");
	    response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Questions</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Questions</h1>");
        DBAbstraction.getQuestionsFromDB();
        List<Question> questions = QuestionGateway.questionList;
        
        for (Question q : questions) {
        	
        	count= q.getid();
	        out.print("<p>" + count + ". <a href=\"AnswerServlet?id="+count+"\">" + q.getquestion() + "</a></p>");	        	
        }
        out.println("<form method=\"POST\" action=\"QuestionsServlet\">");
        out.println("<p><input type=\"text\" name=\"theQuestion\" size=\"50\"> <input type=\"submit\" value=\"Add Question\"></p>");
        out.println("</form>");
        out.println("</body></html>");
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	        String theQuestion = request.getParameter("theQuestion");
	        DBAbstraction.addQuestionToDB(count+1, theQuestion);


	        response.sendRedirect("QuestionsServlet");
	}

}
