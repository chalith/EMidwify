package com.midwife.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Guardians;
import com.main.Messages;
public class LoadGroupMothers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if((session != null) && (request.isRequestedSessionIdValid())){
			String areaCode = request.getParameter("area");
		    String userBar ="";
		    Guardians guardians = new Guardians(areaCode);
		    ArrayList<String[]> guardianArr = guardians.getGuardians();
	    	for(int i=0;i<guardianArr.size();i++){
	    		String s[] = guardianArr.get(i);
	    		userBar = userBar + "<div class=\"mother_bar\" id=\""+s[0]+"\" name=\"div"+s[0]+"\">"
	    		+"<img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><ul id=\""+s[0]+"\"><a id=\""+s[0]+"\"><h1 id=\""+s[0]+"\" name=\"mother_name\">"+s[1]+" ("+s[3]+")</h1></a></ul></div>";
	        }
	        out.println(userBar);
		}
		else{
			response.sendRedirect("index.jsp");
		}
	}
}
