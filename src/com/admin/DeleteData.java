package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
@WebServlet("/deletedata")
public class DeleteData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String table = request.getParameter("table");
		String code = request.getParameter("code");
		try{
			String q = "DELETE FROM "+table+" WHERE "+table+"Code = '"+code+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeUpdate(q);
			out.println("Deleted successfully");
		}
		catch(Exception e){
			e.printStackTrace();
			out.println("Error occured");
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
