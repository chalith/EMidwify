package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.midwife.ClinicUnvisited;
import com.midwife.VisitNotifications;

public class GetMidwifeNotifications {
	String notification = "";
	String clinicUnvisied = "";
	String visitNotify = "";
	public GetMidwifeNotifications(String mid){
		java.util.Date date = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = frmt.format(date);
		JDBC jdbc = new JDBC();
		JSONArray clinics = new JSONArray();
		try{
			String q = "SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				JSONObject clinic = new JSONObject();
				String areaCode = (String) rs.getString("areaCode");
				String area = (String) rs.getString("area");
				clinic.put("areaCode", areaCode);
				clinic.put("area", area);
				clinics.put(clinic);
			}
			for(int i=0;i<clinics.length();i++){
				JSONObject cur;
				try {
					cur = clinics.getJSONObject(i);
					String areaCode = (String)cur.get("areaCode");
					String area = (String)cur.get("area");
					String maxClinicDate = null;
					String q2 = "SELECT MAX(clinicDate) FROM clinics WHERE areaCode = '"+areaCode+"' && clinicDate < '"+currentDate+"';";
					Statement st2=jdbc.conn.createStatement();
					st2.executeQuery(q2);
					ResultSet rs2 = st2.getResultSet();
					while(rs2.next()){
						maxClinicDate = rs2.getString(1);
					}
					
					VisitNotifications visitnotification = new VisitNotifications((String)cur.get("areaCode"));
					if(maxClinicDate!=null){
						ClinicUnvisited clinic = new ClinicUnvisited(areaCode, maxClinicDate);
						int uchildcount=clinic.getUnvisitedChildrenCount();
						int umothercount=clinic.getUnvisitedMotherCount();
						String part1="";
						String part2="";
						if(umothercount>0){
							part1 = umothercount+" mothers";
						}
						if(uchildcount>0){
							part2 = uchildcount+" babies";
						}
						if(!(part1.equals("")&&part2.equals(""))){
							clinicUnvisied = clinicUnvisied + "<li><a style=\"color:purple\" href=\"\">Clinics &raquo </a><p>"+part1+"   "+part2+"<br> didn't attend for the clinic scheduled on "+maxClinicDate+" in "+area+"  </p></li>";
							clinic.sendAlert();
						}
					}
					visitNotify = visitnotification.viewVisits();
					notification = notification + visitNotify;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	}
	public String getNotifications(){
		return notification;
	}
	public String getUnvisitedNotifications(){
		return clinicUnvisied;
	}
	public String getVistNotifications(){
		return visitNotify;
	}
}
