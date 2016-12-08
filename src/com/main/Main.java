package com.main;

import java.sql.ResultSet;

public class Main {
	JDBC jdbc = null;
	public boolean isHave(String table ,String column ,String compare){
		boolean re = false;
		jdbc = new JDBC();
		try{
			String q = "SELECT * FROM "+table+" WHERE "+column+" = '"+compare+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			if(rs.next()){
				re = true;
			}
		}catch(Exception e){
			System.out.print(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return re;
	}
	public String generateID(String table, String prefix){
		String ID = null;
		jdbc = new JDBC();
		try{
			String q="SELECT "+table+"ID FROM "+table+" WHERE "+table+"ID NOT LIKE '%v' OR "+table+"ID NOT LIKE '%V'";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			int max = 0;
			while(rs.next()){
				String curstr = rs.getString(1);
				int cur = Integer.parseInt(curstr.substring(prefix.length())); 
				if(max<cur){
					max = cur;
				}
			}
			ID = prefix+Long.toString(max+1);
		}catch(Exception e){
			System.out.print(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ID;
	}
	public String convertID(String id){
		String prefix;
		String sufix;
		String finalid;
		int l = id.length();
		if((id.charAt(l-1)=='v')||(id.charAt(l-1)=='V')){
			prefix = id.substring(0,l-1);
			sufix = id.substring(l-1,l);
			finalid = prefix+sufix.toLowerCase();
		}
		else{
			prefix = id.substring(0, 1);
			sufix = id.substring(1);
			finalid = prefix.toUpperCase()+sufix;
		}
		return finalid;
	}
	public String getPerson(String id){
		String person = null;
		if(isHave("guardian", "guardianID", id)){
			person = "mother";
		}
		else if(isHave("midwife", "midwifeID", id)){
			person = "midwife";
		}
		else if(isHave("supervisor", "supervisorID", id)){
			person = "supervisor";
		}
		return person;
	}
	
}
