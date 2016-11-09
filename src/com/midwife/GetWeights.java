package com.midwife;

import java.sql.ResultSet;
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
	public GetWeights(String person, String id, String startDate, String endDate) {
		this.person = person;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
	}
	public ArrayList<JSONObject> getWeights(){
		if(person.equals("mother")){
			weights = new ArrayList<JSONObject>();
			jdbc = new JDBC();
			try{
				String q = "SELECT * FROM motherclinic WHERE motherID = '"+id+"' && clinicDate <= '"+endDate+"' && clinicDate >= '"+startDate+"';";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
			jdbc = new JDBC();
			try{
				String q = "SELECT * FROM childclinic WHERE childID = '"+id+"' && clinicDate <= '"+endDate+"' && clinicDate >= '"+startDate+"';";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
