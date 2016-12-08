package com.supervisor.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Guardians;
import com.main.Messages;
import com.main.Midwives;
@WebServlet("/loadmessagedmidwives")
public class LoadMessagedMidwives extends HttpServlet {
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
	    		Messages message = new Messages();
	    		String count = Integer.toString(message.getUnreadMessageCount(s[0], mid));
	    		if(count.equals("0")){
	    			count = "";
	    		}
	    		userBar = userBar + "<h1 class=\"msgnotify\">"+count+"</h1><div class=\"mother_bar\" id=\""+s[0]+"\">"
	    		+"<img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><ul id=\""+s[0]+"\"><a id=\""+s[0]+"\"><h1 id=\""+s[0]+"\" name=\"mother_name\">"+s[1]+"</h1></a></ul></div>";
	        }
	        out.println(userBar);
		}
		else{
			response.sendRedirect("index.jsp");
		}
    }

}
