package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Areas {
	ArrayList<String []> areas = null;
	JDBC jdbc = null;
	public Areas(String mid){
		jdbc = new JDBC();
		areas = new ArrayList<String[]>();
		try{
			String q="SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				String s[] = {(String) rs.getString("areaCode"),(String) rs.getString("area")};
				areas.add(s);
			}
		}catch(Exception e){
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
	public Areas(){
		jdbc = new JDBC();
		areas = new ArrayList<String[]>();
		try{
			String q="SELECT areaCode,area FROM area;";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				String s[] = {(String) rs.getString("areaCode"),(String) rs.getString("area")};
				areas.add(s);
			}
		}catch(Exception e){
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
	public ArrayList<String[]> getAreas(){
		return areas;
	}
}
