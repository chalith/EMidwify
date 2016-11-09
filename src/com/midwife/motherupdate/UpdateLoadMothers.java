package com.midwife.motherupdate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
import com.main.Main;
public class UpdateLoadMothers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String areaCode = request.getParameter("area");
	    String userBar ="<option disabled selected value>Mother Name</option>";
	    JDBC jdbc = new JDBC();;
	    try{
	    	//out.println(areaCode);
	    	String q = "SELECT guardianID,guardianName FROM guardian WHERE guardianAreaCode='"+areaCode+"';";
	        jdbc.st.executeQuery(q);
	        ResultSet rs = jdbc.st.getResultSet();
	    	while(rs.next())
	        {
	    		String gid = rs.getString("guardianID");
	    		String name = rs.getString("guardianName");
	    		Main m = new Main();
	    		if(m.isHave("mother", "guardianID", gid)){
	    			//System.out.print("a");
	    			userBar = userBar + "<option id=\""+gid+"\" name=\"mother\">"+name+" ( "+gid+" )</option>";
	    		}
	        }
	        out.println(userBar);
	        //System.out.println(userBar);
	    }
	    catch(Exception e) {
	        System.out.println(e);
	    }
	    finally{
		    try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    }
	}
}
