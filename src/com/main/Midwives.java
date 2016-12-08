package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Midwives {
	ArrayList<String[]> midwives = null;
	public Midwives() {
		midwives = new ArrayList<String[]>();
		JDBC jdbc = new JDBC();
	    try{
	    	String q = "SELECT midwifeID,name,midwifePicture FROM midwife";
	        jdbc.st.executeQuery(q);
	        ResultSet rs = jdbc.st.getResultSet();
	        String motherOrguardian;
	    	while(rs.next())
	        {
	    		String gid = rs.getString("midwifeID");
	    		String name = rs.getString("name");
	    		String profilePic = rs.getString("midwifePicture");
	    		Main m = new Main();
	    		String s[] = {gid,name,profilePic};
	    		midwives.add(s);
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
	public ArrayList<String[]> getMidwives(){
		return midwives;
	}
}
