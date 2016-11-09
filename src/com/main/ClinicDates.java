package com.main;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.main.JDBC;

public class ClinicDates {
	public String areaCode = null;
	public ArrayList<String[]> clinicDates = null;
	public JDBC jdbc = null;
	public ClinicDates(String area) {
		areaCode = area;
		jdbc = new JDBC();
		Date date = new Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		String cDate = frmt.format(date);
		try {
			clinicDates = new ArrayList<String[]>();
			String q = "SELECT clinicDate,venue,time FROM clinics WHERE areaCode = '"+areaCode+"' && clinicDate >= '"+cDate+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while (rs.next()) {
				String d = rs.getString("clinicDate");
				String v = rs.getString("venue");
				String t = rs.getString("time");
				String s[] = {d,v,t};
				clinicDates.add(s);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public String[] getDeatils(String date){
		String s[] = new String[2];
		for(int i=0;i<clinicDates.size();i++){
			if(clinicDates.get(i)[0].equals(date)){
				s[0] = clinicDates.get(i)[1];
				s[1] = clinicDates.get(i)[2];
				break;
			}
		}
		return s;
	}
	public ArrayList<String[]> getDates() {
		return clinicDates;
	}
}
