package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mother.MotherNotifications;

public class DailyJob implements Runnable {

    @Override
    public void run() {
    	//to send sms notifications daily
    	ArrayList<String> motherids = new ArrayList<String>();
    	ArrayList<String> midwifeids = new ArrayList<String>();
    	
    	JDBC jdbc = new JDBC();
    	try{
	    	String q = "SELECT guardianID FROM guardian;";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				motherids.add(rs.getString("guardianID"));
			}
			q = "SELECT midwifeID FROM midwife;";
			st=jdbc.conn.createStatement();
			st.executeQuery(q);
			rs = st.getResultSet();
			while(rs.next()){
				midwifeids.add(rs.getString("midwifeID"));
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				jdbc.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	for(int i=0;i<motherids.size();i++){
    		new MotherNotifications(motherids.get(i));
    	}
    	for(int i=0;i<midwifeids.size();i++){
    		new GetMidwifeNotifications(midwifeids.get(i));
    	}
    }

}