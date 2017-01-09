package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
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
		JSONArray resultchildren = new JSONArray();
		try{
			String q = "SELECT childID,childName,childDateofDelivery FROM child WHERE childDateofDelivery <= '"+clinicDate+"' AND guardianID IN (SELECT guardianID FROM guardian WHERE guardianAreaCode = '"+areaCode+"');";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				JSONObject child = new JSONObject();
				child.put("childID", rs.getString("childID"));
				child.put("childName", rs.getString("childName"));
				child.put("bdate", rs.getString("childDateofDelivery"));
				resultchildren.put(child);
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
		for(int j=0;j<resultchildren.length();j++){
			JSONObject cur;
			try {
				cur = (JSONObject)resultchildren.getJSONObject(j);
				ChildInClinic childInClinic = new ChildInClinic((String)cur.get("childName"), (String)cur.get("childID"), (String)cur.get("bdate"), clinicDate);
				children.add(childInClinic);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return children;
	}
	public ArrayList<MotherInClinic> getMothers(){
		mothers = new ArrayList<MotherInClinic>();
		jdbc = new JDBC();
		JSONArray resultmothers = new JSONArray();
		try{
			String q = "SELECT guardianID,guardianName,guardianBDate FROM guardian WHERE guardianBDate <= '"+clinicDate+"' AND guardianAreaCode = '"+areaCode+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				String guardianID = rs.getString("guardianID");
				Main m = new Main();
				if(m.isHave("mother", "guardianID", guardianID)){
					JSONObject mother = new JSONObject();
					mother.put("guardianID", guardianID);
					mother.put("guardianName", rs.getString("guardianName"));
					mother.put("bdate", rs.getString("guardianBDate"));
					resultmothers.put(mother);
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
		for(int j=0;j<resultmothers.length();j++){
			JSONObject cur;
			try {
				cur = (JSONObject)resultmothers.getJSONObject(j);
				MotherInClinic motherInClinic = new MotherInClinic((String)cur.get("guardianName"), (String)cur.get("guardianID"), (String)cur.get("bdate"), clinicDate);
				mothers.add(motherInClinic);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return mothers;
	}
}
