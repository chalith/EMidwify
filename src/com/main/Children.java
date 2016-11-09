package com.main;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;


public class Children {
	JDBC jdbc = null;
	ArrayList<String[]> children = null;
	public Children(String gid) {
		children = new ArrayList<String[]>();
		try{
			jdbc = new JDBC();
			String q = "SELECT childID,childName,childDateofDelivery,childPicture FROM child WHERE guardianID = '"+gid+"';";
	        jdbc.st.executeQuery(q);
	        ResultSet rs = jdbc.st.getResultSet();
	        while(rs.next()){
	    		String cid = rs.getString("childID");
	    		String cpic = rs.getString("childPicture");
	    		String cname = rs.getString("childName");
	    		String bdate = rs.getString("childDateofDelivery");
	    		String s[] = {cid,cname,bdate,cpic};
	    		children.add(s);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}	
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	public Children(String areaCode,String whatever) {
		children = new ArrayList<String[]>();
	    try{
	    	jdbc = new JDBC();
	    	String q = "SELECT childID,childName,childPicture FROM child WHERE guardianID IN (SELECT guardianID FROM guardian WHERE guardianAreaCode = '"+areaCode+"' );";
	        jdbc.st.executeQuery(q);
	        ResultSet rs = jdbc.st.getResultSet();
	    	while(rs.next())
	        {
	    		String cid = rs.getString("childID");
	    		String name = rs.getString("childName");
	    		String profilePic = rs.getString("childPicture");
	    		String s[] = {cid,name,profilePic};
	    		children.add(s);
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
	public ArrayList<String[]> getChildren(){
		return children;
	}
}
