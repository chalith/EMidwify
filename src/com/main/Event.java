package com.main;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.util.ArrayList;


public class Event {
	JDBC jdbc = null;
	public String id = null;
	public String name = null;
	public String area = null;
	public String venue = null;
	public String date = null;
	public String time = null;
	public ArrayList<Guardian> guardians = new ArrayList<Guardian>();
	public Event(){
		
	}
	public Event(String eventID){
		jdbc = new JDBC();
		try{
	    	String q = "SELECT * FROM event WHERE eventID='"+eventID+"';";
	    	Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
	        ResultSet rs = st.getResultSet();
	        while(rs.next())
	        {
	    		this.id = rs.getString("eventID");
	    		this.name = rs.getString("eventName");
	    		this.area = rs.getString("areaCode");
	    		this.date = rs.getString("eventDate");
	    		this.time = rs.getString("eventTime");
	    		this.venue = rs.getString("eventVenue");
	    	}
	        q = "SELECT guardianID FROM eventguardians WHERE eventID='"+id+"';";
	        st.executeQuery(q);
	        rs = st.getResultSet();
	        while(rs.next())
	        {
	        	this.addGuardians(rs.getString("guardianID"));
	        }
	    }
	    catch(Exception e) {
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
	public Event(String id,String date){
		jdbc = new JDBC();
		try{
	    	String q = "SELECT * FROM event WHERE eventDate='"+date+"' && eventID IN (SELECT eventID FROM eventguardians WHERE guardianID = '"+id+"');";
	    	Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
	        ResultSet rs = st.getResultSet();
	        while(rs.next())
	        {
	    		this.id = rs.getString("eventID");
	    		this.name = rs.getString("eventName");
	    		this.area = rs.getString("areaCode");
	    		this.date = rs.getString("eventDate");
	    		this.time = rs.getString("eventTime");
	    		this.venue = rs.getString("eventVenue");
	    	}
	        q = "SELECT guardianID FROM eventguardians WHERE eventID='"+id+"';";
	        st.executeQuery(q);
	        rs = st.getResultSet();
	        while(rs.next())
	        {
	        	this.addGuardians(rs.getString("guardianID"));
	        }
	    }
	    catch(Exception e) {
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
	public Event(String midwifeID,String date,String areaCode){
		jdbc = new JDBC();
		try{
	    	String q = "SELECT * FROM event WHERE eventDate = '"+date+"' && areaCode = '"+areaCode+"';";
	    	Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
	        ResultSet rs = st.getResultSet();
	        while(rs.next())
	        {
	        	this.id = rs.getString("eventID");
	        	this.name = rs.getString("eventName");
	        	this.area = rs.getString("areaCode");
	        	this.date = rs.getString("eventDate");
	        	this.time = rs.getString("eventTime");
	        	this.venue = rs.getString("eventVenue");
	    	}
	        q = "SELECT guardianID FROM eventguardians WHERE eventID='"+id+"';";
	        st.executeQuery(q);
	        rs = st.getResultSet();
	        while(rs.next())
	        {
	        	this.addGuardians(rs.getString("guardianID"));
	        }
	    }
	    catch(Exception e) {
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
	
	public void setEvent(String name,String areaCode,String date,String time,String venue){
		this.name = name;
		this.area = areaCode;
		this.venue = venue;
		this.date = date;
		this.time = time;
	}
	public void addGuardians(String id){
		Guardian guardian = new Guardian(id);
		guardians.add(guardian);
	}
}
