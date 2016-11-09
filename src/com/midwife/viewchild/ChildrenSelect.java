package com.midwife.viewchild;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Children;
import com.main.Guardians;
import com.main.JDBC;
public class ChildrenSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBC jdbc = new JDBC();
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid");
	    String userBar ="";
	    Children children = new Children(gid);
    	ArrayList<String[]> childrenArr= children.getChildren();
    	for(int i=0;i<childrenArr.size();i++){
    		String s[] = childrenArr.get(i);
    		userBar = userBar + "<div class=\"child\" id=\""+s[0]+"\"><img id=\""+s[0]+"\" src=\""+s[3]+"\" alt=\"user_photo\"><a id=\""+s[0]+"\">"+s[1]+"</a></div>";
    	}
    	try {
			jdbc.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        out.println(userBar);
	}
}
