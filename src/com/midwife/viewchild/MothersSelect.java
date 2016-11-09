package com.midwife.viewchild;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Guardians;
import com.main.JDBC;
public class MothersSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBC jdbc = new JDBC();
		PrintWriter out = response.getWriter();
		String areaCode = request.getParameter("area");
	    String userBar ="<option disabled selected value>Mother Name</option>";
	    Guardians guardians = new Guardians(areaCode);
	    ArrayList<String[]> guardianArr = guardians.getGuardians();
    	for(int i=0;i<guardianArr.size();i++){
    		String s[] = guardianArr.get(i);
    		userBar = userBar+"<option value="+s[0]+">"+s[1]+" ("+s[0]+")</option>";
    	}
    	try {
			jdbc.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        out.println(userBar);
	}
}
