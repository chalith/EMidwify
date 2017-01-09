package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.Event;
import com.main.JDBC;
@WebServlet("/addarea")
public class AddArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String ars[] = request.getParameter("txtareas").split("~row~");
		try{
			for(int i=1;i<ars.length;i++){
				String ar = ars[i];
				String area[] = ar.split("~column~");
				String q = "INSERT INTO Area (areaCode,area,midwifeID) VALUES ('"+area[0].trim()+"','"+area[1].trim()+"','"+area[2].trim()+"');";
				Statement st=jdbc.conn.createStatement();
				st.executeUpdate(q);
			}
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"Area added successfully\")</script>");
        	getServletContext().getRequestDispatcher("/admin/addarea.jsp").forward(request,response);
            out.println("successfully added");
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	    	getServletContext().getRequestDispatcher("/admin/addarea.jsp").forward(request,response);
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
