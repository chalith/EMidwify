package com.midwife;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.JDBC;
public class CreateNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		JDBC jdbc = new JDBC();
		String notification = "";
		try{
			String q = "SELECT areaCode FROM area WHERE midwifeID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				VisitNotifications visitnotification = new VisitNotifications((String) rs.getString("areaCode"));
				notification = notification + visitnotification.viewVisits();
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
		out.print(notification);
		//System.out.println(notification);
	}
}
