package com.midwife.location;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.main.JDBC;
import com.midwife.ChildVisit;
import com.midwife.VisitNotifications;
public class ViewLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		JDBC jdbc = new JDBC();
		JSONObject ob = new JSONObject();
		try{
			String q = "SELECT areaCode FROM area WHERE midwifeID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			int j = 0;
			while(rs.next()){
				VisitNotifications visitnotification = new VisitNotifications((String) rs.getString("areaCode"));
				ArrayList<ChildVisit> cvisits = visitnotification.getVisits();
				if(cvisits != null){
					for(int i=0;i<cvisits.size();i++){
						JSONObject ob1 = new JSONObject();
						ob1.put("location", cvisits.get(i).location);
						ob1.put("visittype", cvisits.get(i).visitType);
						ob1.put("date", cvisits.get(i).visitDate);
						ob1.put("child", cvisits.get(i).childName);
						ob1.put("guardian", cvisits.get(i).guardianName);
						ob.put(j+""+i, ob1);
					}
				}
				j++;
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
		out.print(ob);
	}
}
