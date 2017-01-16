package com.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/getallunreadmsgcount")
public class GetAllUnreadMsgCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if((session != null) && (request.isRequestedSessionIdValid())){
			Messages messages = new Messages();
			String mid = (String) session.getAttribute("mid");
			String count = Integer.toString(messages.getUnreadMessageCount(mid));
			out.print(count);
		}else{
			response.sendRedirect("index.jsp");
		}
	}
}
