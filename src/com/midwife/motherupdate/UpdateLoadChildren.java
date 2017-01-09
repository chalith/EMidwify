package com.midwife.motherupdate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
public class UpdateLoadChildren extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String id = request.getParameter("gid");
	    String children ="<option disabled selected value>Child Name</option>";
	    JDBC jdbc = new JDBC();;
	    try{
	    	//out.println(areaCode);
	    	String q = "SELECT childID,childName FROM child WHERE guardianID='"+id+"';";
	    	Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
	        ResultSet rs = st.getResultSet();
	    	while(rs.next())
	        {
	    		String cid = rs.getString("childID");
	    		String name = rs.getString("childName");
	    		children = children + "<option id=\""+cid+"\" name=\"child\">"+name+"</option>";
	        }
	        out.println(children);
	        //System.out.println(children);
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
