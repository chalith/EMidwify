package com.midwife;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.catalina.connector.Request;
import org.json.JSONObject;

import com.main.DateSort;
import com.main.Guardian;
import com.main.JDBC;

public class GetWeights {
	String startDate = null;
	String endDate = null;
	ArrayList<JSONObject> weights = null;
	String person = null;
	String id = null;
	JDBC jdbc = null;
	public GetWeights(String person, String id) {
		this.person = person;
		this.id = id;
	}
	public ArrayList<JSONObject> getWeights(){
		jdbc = new JDBC();
		if(person.equals("mother")){
			weights = new ArrayList<JSONObject>();
			try{
				String q = "SELECT guardianDateOfRegistered FROM guardian WHERE guardianID = '"+id+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					startDate = (String) rs.getString("guardianDateOfRegistered");
					startDate = startDate.substring(0, 10);
				}
				java.util.Date date = new java.util.Date();
				DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				endDate = frmt.format(date);
				
				q = "SELECT * FROM motherclinic WHERE motherID = '"+id+"' && clinicDate <= '"+endDate+"' && clinicDate >= '"+startDate+"';";
				rs = st.getResultSet();
				while(rs.next()){
					JSONObject ob = new JSONObject();
					ob.put("date", rs.getString("clinicDate"));
					ob.put("age", rs.getString("age"));
					ob.put("weight", rs.getString("weight"));
					weights.add(ob);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					jdbc.conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			weights = new ArrayList<JSONObject>();
			try{
				String bweight = null;
				String q = "SELECT childDateofDelivery,childBirthWeight FROM child WHERE childID = '"+id+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					bweight = (String) rs.getString("childBirthWeight");
					startDate = (String) rs.getString("childDateofDelivery");
					startDate = startDate.substring(0, 10);
				}
				java.util.Date date = new java.util.Date();
				DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				endDate = frmt.format(date);
				
				JSONObject ob1 = new JSONObject();
				ob1.put("date", startDate);
				ob1.put("age", "1 Day");
				ob1.put("weight", bweight);
				weights.add(ob1);
				
				q = "SELECT * FROM childclinic WHERE childID = '"+id+"' && clinicDate <= '"+endDate+"' && clinicDate > '"+startDate+"';";
				jdbc = new JDBC();
				st.executeQuery(q);
				rs = st.getResultSet();
				while(rs.next()){
					JSONObject ob = new JSONObject();
					ob.put("date", rs.getString("clinicDate"));
					ob.put("age", rs.getString("age"));
					ob.put("weight", rs.getString("weight"));
					weights.add(ob);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					jdbc.conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		weights = DateSort.sortWeight(weights);
		return weights;
	}
}
