package com.supervisor;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class SupervisorNotifications {
	ArrayList<Notification> notifications = null;
	public SupervisorNotifications(String mid){
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Clinic> clinics = new ArrayList<Clinic>();
		ArrayList<Notification> midwifenotifications= new ArrayList<Notification>();
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT areaCode FROM area WHERE midwifeID IN (SELECT midwifeID FROM midwife WHERE supervisorID = '"+mid+"');";
			jdbc.st.execute(q);
			ResultSet rs =  jdbc.st.getResultSet();
			while(rs.next()){
				String areaCode = rs.getString("areaCode");
				ClinicDates clinicdates = new ClinicDates(areaCode);
				for(int i=0;i<clinicdates.clinicDates.size();i++){
					clinics.add(new Clinic(areaCode, clinicdates.clinicDates.get(i)[0], clinicdates.clinicDates.get(i)[1], clinicdates.clinicDates.get(i)[2]));
				}
				java.util.Date date = new java.util.Date();
				DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				Date cDate = FormatDate.createDate(frmt.format(date));
				Date fDate = cDate.increase(365);
				while(fDate.isLarge(cDate)){
					Event event = new Event(mid,cDate.year+"-"+cDate.month+"-"+cDate.day,areaCode);
					if(event.id!=null){
						events.add(event);
					}
					cDate = cDate.increase(1);
				}
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
		for(int i=0;i<clinics.size();i++){
			String title = "Clinic";
			String id = clinics.get(i).areaCode+" <> "+clinics.get(i).clinicDate;
			String area = null;
			jdbc = new JDBC();
			try{
				String q1 = "SELECT area FROM area WHERE areaCode = '"+clinics.get(i).areaCode+"';";
				jdbc.st.execute(q1);
				ResultSet rs1 = jdbc.st.getResultSet();
				while(rs1.next()){
					area = rs1.getString("area");
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
			String description = "Area - "+area;
			Notification notification = new Notification(title, id, ndate, description);
			midwifenotifications.add(notification);
		}
		for(int i=0;i<events.size();i++){
			String title = "Event";
			String id = events.get(i).id;
			String ndate = events.get(i).date;
			String area = null;
			jdbc = new JDBC();
			try{
				String q1 = "SELECT area FROM area WHERE areaCode = '"+events.get(i).area+"';";
				jdbc.st.executeQuery(q1);
				ResultSet rs1 = jdbc.st.getResultSet();
				while (rs1.next()) {
					area = rs1.getString("area");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			String description = "<a>Event - "+events.get(i).name + "</a></br><a>Area - " + area + "</a></br><a>Time - " + events.get(i).time + "</a></br><a>Venue - " + events.get(i).venue;
			Notification notification = new Notification(title, id, ndate, description);
			midwifenotifications.add(notification);
		}
		notifications = DateSort.sort(midwifenotifications);
	}
	public ArrayList<Notification> getNotifications(){
		return notifications;
	}
}
