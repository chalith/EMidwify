package com.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if((session != null) && (request.isRequestedSessionIdValid())){
			String message = (String) request.getParameter("message");
			String gid = (String) request.getParameter("gid");
			String mid = (String) session.getAttribute("mid");
			Messages messages = new Messages();
			messages.sendMessages(mid, message, gid);
		}else{
			response.sendRedirect("/Emidwify");
		}
	}
}
