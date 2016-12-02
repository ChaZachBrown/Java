package mvc.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.models.DBAbstraction;
import mvc.models.QuestionGatewayViewModel;


/**
 * Servlet implementation class QuestionController
 */
@WebServlet("/QuestionController")
public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    DBAbstraction.connect();//connecting to database

	    
		RequestDispatcher rd = null;
		DBAbstraction.getQuestionsFromDB();
		QuestionGatewayViewModel result = new QuestionGatewayViewModel();
		rd = request.getRequestDispatcher("/Views/QuestionView.jsp");
		request.setAttribute("viewModel", result);
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String theQuestion = request.getParameter("theQuestion");
        DBAbstraction.addQuestionToDB(QuestionGatewayViewModel.getcount()+1, theQuestion);


        doGet(request, response);
	}

}
