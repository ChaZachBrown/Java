<%@ page import="mvc.models.QuestionGatewayViewModel, mvc.Question" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questions</title>
</head>
<body>
<h2>Questions</h2>


<p>
			<%
					
			        QuestionGatewayViewModel viewModel = (QuestionGatewayViewModel)request.getAttribute("viewModel");
			        if (viewModel != null) {
			        	for(Question q : viewModel.questionList){
			        		out.println("<tr><td>" + q.getid() + " </td><td><a href='/Program 12/AnswerGatewayViewModel?id="+q.getid() +"'>"+q.getquestion() +"</a></td>");
			        	}
			        }
			        
			        out.println("<form method=\"POST\" action=\"QuestionController\">");
			        out.println("<p><input type=\"text\" name=\"theQuestion\" size=\"50\"> <input type=\"submit\" value=\"Add Question\"></p>");
			        out.println("</form>");
			        out.println("</body></html>");

			%>
			
		
<p>
</body>
</html>