package com.midwife.child;

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
public class LoadFather extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String fatherid = request.getParameter("txtregisteredfatherid");
		String data = null;
		JDBC jdbc =null;
		try{
			jdbc = new JDBC();
			String q = "SELECT fatherName FROM father WHERE fatherID = '"+fatherid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			if(rs.next()){
				data = rs.getString("fatherName");
			}
			out.print(data);
			//System.out.println(data);
		}catch(Exception e){
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
