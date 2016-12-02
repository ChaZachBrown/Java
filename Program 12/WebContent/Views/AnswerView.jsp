<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Answers</title>
</head>
<body>
<h2>Answers:</h2>
<p>
<%
		AnswerGatewayViewModel viewModel = (AnswerGatewayViewModel)request.getAttribute("viewModel");
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


%>
<p>
</body>
</html>