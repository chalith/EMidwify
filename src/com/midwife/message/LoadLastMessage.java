package com.midwife.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Messages;
public class LoadLastMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if((session != null) && (request.isRequestedSessionIdValid())){
			Messages messages = new Messages();
			String gid = (String) request.getParameter("gid");
			String mid = (String) session.getAttribute("mid");
			ArrayList<String[]> msgs = messages.ViewMessages(mid, gid);
			String m = "<div class=\"sentmessage\"><p>"+msgs.get(msgs.size()-1)[1]+"<p></br><a>"+msgs.get(msgs.size()-1)[2]+"</a></div>";
			out.print(m);

		}else{
			response.sendRedirect("index.jsp");
		}
	}
}
