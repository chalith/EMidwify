package com.midwife;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		JSONArray clinics = new JSONArray();
		try{
			String q = "SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				JSONObject clinic = new JSONObject();
				String maxClinicDate = null;
				String areaCode = (String) rs.getString("areaCode");
				String area = (String) rs.getString("area");
				String q2 = "SELECT MAX(clinicDate) FROM clinics WHERE areaCode = '"+areaCode+"' && clinicDate < '"+currentDate+"';";
				Statement st2=jdbc.conn.createStatement();
				st2.executeQuery(q2);
				ResultSet rs2 = st2.getResultSet();
				while(rs2.next()){
					maxClinicDate = rs2.getString(1);
				}
				clinic.put("areaCode", areaCode);
				clinic.put("area", area);
				clinic.put("maxClinicDate", maxClinicDate);
				clinics.put(clinic);
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
		for(int i=0;i<clinics.length();i++){
			JSONObject cur;
			try {
				cur = clinics.getJSONObject(i);
				String areaCode = (String)cur.get("areaCode");
				String area = (String)cur.get("area");
				String maxClinicDate = (String)cur.get("maxClinicDate");
				VisitNotifications visitnotification = new VisitNotifications((String)cur.get("areaCode"));
				ClinicUnvisited clinic = new ClinicUnvisited(areaCode, maxClinicDate);
				clinicUnvisied = clinicUnvisied + "<li><a style=\"color:purple\" href=\"\">Clinics &raquo </a><p> "+clinic.getUnvisitedMotherCount()+" mothers and "+clinic.getUnvisitedChildrenCount()+" children <br> didn't attend for the clinic scheduled on "+maxClinicDate+" in "+area+"  </p></li>";
				notification = notification + visitnotification.viewVisits();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//ClinicUnvisited unvisited = new ClinicUnvisited(mid);
		notification = clinicUnvisied + notification;
		out.print(notification);
		//System.out.println(notification);
	}
}
