package com.midwife;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Guardians;

public class OnlineUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String areaCode = request.getParameter("area");
	    String userBar ="";
	    Guardians guardians = new Guardians(areaCode);
	    ArrayList<String[]> guardianArr = guardians.getGuardians();
    	for(int i=0;i<guardianArr.size();i++){
    		String s[] = guardianArr.get(i);
    		userBar = userBar + "<div class=\"mother\" name=\"loaduser\" id=\""+s[0]+"\">"
    		+"<img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><ul id=\""+s[0]+"\"><a id=\""+s[0]+"\"><h1 id=\""+s[0]+"\">"+s[1]+"    ("+s[3]+")</h1></a></ul></div>";
        }
        out.println(userBar);
        //System.out.println(userBar);
	}
}
