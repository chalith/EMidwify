package com.supervisor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Messages;
import com.main.Midwives;
@WebServlet("/loadmidwives")
public class LoadMidwives extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if((session != null) && (request.isRequestedSessionIdValid())){
			String mid = (String) session.getAttribute("mid");
			String userBar ="";
			Midwives midwives = new Midwives();
		    ArrayList<String[]> midwifeArr = midwives.getMidwives();
	    	for(int i=0;i<midwifeArr.size();i++){
	    		String s[] = midwifeArr.get(i);
	    		userBar = userBar + "<div class=\"mother\" name=\"loaduser\" id=\""+s[0]+"\">"
	    		+"<img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><ul id=\""+s[0]+"\"><a id=\""+s[0]+"\"><h1 id=\""+s[0]+"\">"+s[1]+"</h1></a></ul></div>";
	        }
	        out.println(userBar);
		}
		else{
			response.sendRedirect("index.jsp");
		}
	}

}
