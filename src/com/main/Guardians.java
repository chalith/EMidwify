package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Guardians {
	ArrayList<String[]> guardians = null;
	public Guardians(String areaCode) {
		guardians = new ArrayList<String[]>();
		JDBC jdbc = new JDBC();
	    try{
	    	String q = "SELECT guardianID,guardianName,guardianPicture FROM guardian WHERE guardianAreaCode='"+areaCode+"';";
	        jdbc.st.executeQuery(q);
	        ResultSet rs = jdbc.st.getResultSet();
	        String motherOrguardian;
	    	while(rs.next())
	        {
	    		String gid = rs.getString("guardianID");
	    		String name = rs.getString("guardianName");
	    		String profilePic = rs.getString("guardianPicture");
	    		Main m = new Main();
	    		if(m.isHave("mother", "guardianID", gid)){
	    			motherOrguardian = "Mother";
	    		}else{
	    			motherOrguardian = "Guardian";
	    		}
	    		String s[] = {gid,name,profilePic,motherOrguardian};
	    		guardians.add(s);
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
	public ArrayList<String[]> getGuardians(){
		return guardians;
	}
}
