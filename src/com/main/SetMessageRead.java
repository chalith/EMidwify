package com.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if((session != null) && (request.isRequestedSessionIdValid())){
			Messages messages = new Messages();
			String gid = (String) request.getParameter("gid");
			String mid = (String) session.getAttribute("mid");
			messages.setMessageRead(gid, mid);
		}else{
			response.sendRedirect("/Emidwify");
		}
	}
}
