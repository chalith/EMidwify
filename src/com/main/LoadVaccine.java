package com.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoadVaccine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String vCode = request.getParameter("vcode");
		String vName = "";
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT vaccineName FROM vaccine WHERE vaccineCode = '"+vCode+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				vName = (String) rs.getString("vaccineName");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		out.print(vName);
	}
}
