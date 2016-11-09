package com.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if((session != null) && (request.isRequestedSessionIdValid())){
			Messages messages = new Messages();
			String gid = (String) request.getParameter("gid");
			String mid = (String) session.getAttribute("mid");
			ArrayList<String[]> msgs = messages.ViewMessages(mid, gid);
			String m = "";
			for(int i=0;i<msgs.size();i++){
				if(msgs.get(i)[0].equals(gid)){
					m = m + "<div class=\"receivemessage\"><p>"+msgs.get(i)[1]+"<p></br><a>"+msgs.get(i)[2]+"</a></div>";
				}
				else{
					m = m + "<div class=\"sentmessage\"><p>"+msgs.get(i)[1]+"<p></br><a>"+msgs.get(i)[2]+"</a></div>";
				}
			}
			out.print(m);

		}else{
			response.sendRedirect("/Emidwify");
		}
	}
}
