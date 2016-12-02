package mvc.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.models.AnswerGatewayViewModel;
import mvc.models.DBAbstraction;

/**
 * Servlet implementation class AnswerController
 */
@WebServlet("/AnswerController")
public class AnswerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Integer questionID;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			questionID = Integer.parseInt(request.getParameter("id"));
		} catch(Exception e){
			questionID = 0;
		}
		RequestDispatcher rd = null;
		String question = DBAbstraction.getSpecificQuestionFromDB(questionID);
		AnswerGatewayViewModel result = new AnswerGatewayViewModel();
		DBAbstraction.getAnswerFromDB(questionID);
		rd = request.getRequestDispatcher("/Views/AnswerView.jsp");
		request.setAttribute("viewModel", result);
		request.setAttribute("currentQuestion", question);
		rd.forward(request, response);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
        String theAnswer = request.getParameter("theAnswer");
        Integer questionID = Integer.parseInt(request.getParameter("id"));
        DBAbstraction.addAnswerToDB(questionID, theAnswer);

        doGet(request, response);
	}

}
