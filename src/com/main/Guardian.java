package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.main.JDBC;

public class Guardian {
	JDBC jdbc = null;
	public String id = null;
	public String fullname = null;
	public String dateofbirth = null;
	public String areacode = null;
	public String address = null;
	public String occupation = null;
	public ArrayList<String> tpnumbers = new ArrayList<String>();;
	public String email = null;
	public String nofchildren = null;
	public String edulevel = null;
	public String mothernotes = null;
	public String picture = null;
	public void addTPnumber(String tpnumber){
		tpnumbers.add(tpnumber);
	}
	public Guardian(){
		
	}
	public Guardian(String id){
		this.id = id;
		jdbc = new JDBC();
		try{
	    	String q = "SELECT * FROM guardian WHERE guardianID='"+id+"';";
	    	Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
	        ResultSet rs = st.getResultSet();
	        while(rs.next())
	        {
	    		fullname = rs.getString("guardianName");
	    		dateofbirth = rs.getString("guardianBDate");
	    		address = rs.getString("guardianAddress");
	    		occupation = rs.getString("guardianOccupation");
	    		email = rs.getString("guardianEMail");
	    		nofchildren = rs.getString("guardianNofchildren");
	    		edulevel = rs.getString("guardianEducationLevel");
	    		areacode = rs.getString("guardianAreaCode");
	    		mothernotes = rs.getString("guardianNote");
	    		picture = rs.getString("guardianPicture");
	    	}
	        q = "SELECT guardianMobileNumber FROM guardianmobilenumber WHERE guardianID='"+id+"';";
	        st.executeQuery(q);
	        rs = st.getResultSet();
	        while(rs.next())
	        {
	        	addTPnumber(rs.getString(1));
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
}
