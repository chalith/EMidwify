package com.midwife;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.main.Child;
import com.main.Date;
import com.main.JDBC;
import com.main.Main;


public class ChildVisit {
	public String visitDate = null;
	public String childName = null;
	String visitAge = null;
	public String guardianName = null;
	String address = null;
	public String location = null;
	Child child = null;
	public String visitType = null;
	public ChildVisit(Child c){
		child = c;
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date dt = new java.util.Date();
    	Date currentDate = createDate(frmt.format(dt));
    	Date bDate = createDate(child.dateofDelivery);
    	int age = bDate.getDeference(currentDate);
    	visitAge = bDate.getAge(currentDate);
    	Date d = null;
    	if(age%20>20-7){
    		d = currentDate.increase(age%20);
			visitType = "20 days Visit";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
    	}
    	if(age%30>30-10){
    		d = currentDate.increase(age%30);
			visitType = "30 days Visit";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
    	}
    	if(age%365>365-10){
    		d = currentDate.increase(age%365);
			visitType = "365 days Visit";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
    	}
    	
    	/*
    	if(age<=5){
			d = currentDate.increase(5-age);
			visitType = "5 days";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
		}
    	else if((age<=13)&&(age>=8)){
			d = currentDate.increase(13-age);
			visitType = "13 days";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
		}
    	else if((age<=30)&&(age>=25)){
			d = currentDate.increase(30-age);
			visitType = "30 days";
			visitDate = d.year+"/"+d.month+"/"+d.day;
			createVisit();
		}
		*/
	}
	void createVisit(){
		JDBC jdbc = new JDBC();
		Main m = new Main();
		childName = child.childName;
		String guardianID = child.motherguardianID;
		try{
			String q = "SELECT guardianName,guardianAddress,guardianLocation FROM guardian WHERE guardianID = '"+guardianID+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				guardianName = rs.getString("guardianName");
				address = rs.getString("guardianAddress");
				location = rs.getString("guardianLocation");
			}
		}catch(Exception e){
			System.out.print(e);
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	Date createDate(String date){
		String d[] = date.split("-");
		String year = d[0];
		String month = d[1];
		String day = d[2];
		Date da = null;
		try{
			da = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
		}catch(Exception e){
			System.out.print(e);
		}
		return da;
	}
}
