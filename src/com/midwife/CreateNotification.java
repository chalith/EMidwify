package com.midwife;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Clinic;
import com.main.JDBC;
public class CreateNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		java.util.Date date = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = frmt.format(date);
		JDBC jdbc = new JDBC();
		String notification = "";
		String clinicUnvisied = "";
		try{
			String q = "SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				String areaCode = (String) rs.getString("areaCode");
				String area = (String) rs.getString("area");
				VisitNotifications visitnotification = new VisitNotifications(areaCode);
				String maxClinicDate = null;
				JDBC jdbc2 = new JDBC();
				try{
					String q2 = "SELECT MAX(clinicDate) FROM clinics WHERE areaCode = '"+areaCode+"' && clinicDate < '"+currentDate+"';";
					jdbc2.st.executeQuery(q2);
					ResultSet rs2 = jdbc2.st.getResultSet();
					while(rs2.next()){
						maxClinicDate = rs2.getString(1);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally{
					try {
						jdbc2.conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				ClinicUnvisited clinic = new ClinicUnvisited(areaCode, maxClinicDate);
				clinicUnvisied = clinicUnvisied + "<li><a style=\"color:purple\" href=\"\">Clinics &raquo </a><p> "+clinic.getUnvisitedMotherCount()+" mothers and "+clinic.getUnvisitedChildrenCount()+" children <br> didn't attend for the clinic scheduled on "+maxClinicDate+" in "+area+"  </p></li>";
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
		//ClinicUnvisited unvisited = new ClinicUnvisited(mid);
		notification = clinicUnvisied + notification;
		out.print(notification);
		//System.out.println(notification);
	}
}
