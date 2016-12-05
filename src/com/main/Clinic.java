package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.main.JDBC;

public class Clinic {
	JDBC jdbc = null;
	public String clinicDate = null;
	public String areaCode = null;
	public String venue = null;
	public String time = null;
	ArrayList<ChildInClinic> children = null;
	ArrayList<MotherInClinic> mothers = null;
	public Clinic(String areaCode,String date) {
		this.areaCode = areaCode;
		this.clinicDate = date;
		jdbc = new JDBC();
		try{
			String q = "SELECT * FROM clinics WHERE areaCode = '"+areaCode+"' && clinicDate = '"+clinicDate+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				this.areaCode = rs.getString("areaCode");
				this.clinicDate = rs.getString("clinicDate");;
				this.venue = rs.getString("venue");
				this.time = rs.getString("time");
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
	}
	public Clinic(String areaCode,String date,String venue,String time) {
		this.areaCode = areaCode;
		this.clinicDate = date;
		this.venue = venue;
		this.time = time;
	}
	public ArrayList<ChildInClinic> getChildren(){
		children = new ArrayList<ChildInClinic>();
		jdbc = new JDBC();
		try{
			String q = "SELECT childID,childName,childDateofDelivery FROM child WHERE childDateofDelivery <= '"+clinicDate+"' AND guardianID IN (SELECT guardianID FROM guardian WHERE guardianAreaCode = '"+areaCode+"');";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				String childID = rs.getString("childID");
				String childName = rs.getString("childName");
				String bdate = rs.getString("childDateofDelivery");
				ChildInClinic childInClinic = new ChildInClinic(childName, childID, bdate, clinicDate);
				children.add(childInClinic);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return children;
	}
	public ArrayList<MotherInClinic> getMothers(){
		mothers = new ArrayList<MotherInClinic>();
		jdbc = new JDBC();
		try{
			String q = "SELECT guardianID,guardianName,guardianBDate FROM guardian WHERE guardianBDate <= '"+clinicDate+"' AND guardianAreaCode = '"+areaCode+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				String guardianID = rs.getString("guardianID");
				Main m = new Main();
				if(m.isHave("mother", "guardianID", guardianID)){
					String guardianName = rs.getString("guardianName");
					String bdate = rs.getString("guardianBDate");
					MotherInClinic motherInClinic = new MotherInClinic(guardianName, guardianID, bdate, clinicDate);
					mothers.add(motherInClinic);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mothers;
	}
}
