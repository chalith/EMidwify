package com.mother;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.main.Clinic;
import com.main.ClinicDates;
import com.main.Date;
import com.main.DateSort;
import com.main.Event;
import com.main.FormatDate;
import com.main.JDBC;
import com.main.Notification;

public class MotherNotifications {
	ArrayList<Notification> notifications = null;
	public MotherNotifications(String mid){
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Clinic> clinics = new ArrayList<Clinic>();
		ArrayList<Notification> mothernotifications= new ArrayList<Notification>();
		JDBC jdbc = new JDBC();
		ArrayList<String> areas = new ArrayList<String>();
		try{
			String q1 = "SELECT guardianAreaCode FROM guardian WHERE guardianID = '"+mid+"'";
			Statement st=jdbc.conn.createStatement();
			st.execute(q1);
			ResultSet rs1 =  st.getResultSet();
			while(rs1.next()){
				areas.add(rs1.getString("guardianAreaCode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<areas.size();i++){
			ClinicDates clinicdates = new ClinicDates(areas.get(i));
			for(int j=0;j<clinicdates.clinicDates.size();j++){
				clinics.add(new Clinic(areas.get(i), clinicdates.clinicDates.get(j)[0], clinicdates.clinicDates.get(j)[1], clinicdates.clinicDates.get(j)[2]));
			}
			java.util.Date date = new java.util.Date();
			DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
			Date cDate = FormatDate.createDate(frmt.format(date));
			Date fDate = cDate.increase(365);
			while(fDate.isLarge(cDate)){
				Event event = new Event(mid,cDate.year+"-"+cDate.month+"-"+cDate.day,areas.get(i));
				if(event.id!=null){
					events.add(event);
				}
				cDate = cDate.increase(1);
			}
		}	
		for(int i=0;i<clinics.size();i++){
			String title = "Clinic";
			String id = clinics.get(i).areaCode+" <> "+clinics.get(i).clinicDate;
			String area = null;
			jdbc = new JDBC();
			try{
				String q = "SELECT area FROM area WHERE areaCode = '"+clinics.get(i).areaCode+"';";
				Statement st = jdbc.conn.createStatement();
				st.execute(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					area = rs.getString("area");
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
			String ndate = clinics.get(i).clinicDate;
			String description = "<a>Clinic</a></br><a>Area - " + area + "</a></br><a>Time - " + clinics.get(i).time + "</a></br><a>Venue - " + clinics.get(i).venue;
			Notification notification = new Notification(title, id, ndate, description);
			mothernotifications.add(notification);
		}
		for(int i=0;i<events.size();i++){
			String title = "Event";
			String id = events.get(i).id;
			String ndate = events.get(i).date;
			String area = null;
			jdbc = new JDBC();
			try{
				String q = "SELECT area FROM area WHERE areaCode = '"+events.get(i).area+"';";
				Statement st = jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while (rs.next()) {
					area = rs.getString("area");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			String description = "<a>Event - "+events.get(i).name + "</a></br><a>Area - " + area + "</a></br><a>Time - " + events.get(i).time + "</a></br><a>Venue - " + events.get(i).venue;
			Notification notification = new Notification(title, id, ndate, description);
			mothernotifications.add(notification);
		}
		notifications = DateSort.sort(mothernotifications);
	}
	public ArrayList<Notification> getNotifications(){
		return notifications;
	}
}
