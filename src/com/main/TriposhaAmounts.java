package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TriposhaAmounts {
	String id = null;
	Date cdate = null;
	String currentDate = null;
	String pastDate = null;
	Date bDate = null;
	int age = 0;
	String person = null;
	public int amount = 0;
	JDBC jdbc = null;
	public TriposhaAmounts(String id){
		this.id = id;
		getPerson(this.id);
		java.util.Date date = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = frmt.format(date);
		cdate = createDate(currentDate,"-");
		getAge();
	}
	public TriposhaAmounts(String id, String currentdate){
		this.id = id;
		this.currentDate = currentdate;
		getPerson(this.id);
		cdate = createDate(currentdate,"-");
		getAge();
	}
	public TriposhaAmounts(String id, String currentdate, int age){
		this.id = id;
		this.age = age;
		this.currentDate = currentdate;
		getPerson(this.id);
	}
	private void getPerson(String id){
		//System.out.println(id);
		if(id.startsWith("Guard")){
			person = "mother";
		}
		else if(id.startsWith("Child")){
			person = "child";
		}else if(id.charAt(id.length()-1)=='v'){
			person = "mother";
		}
	}
	void getAge(){
		jdbc = new JDBC();
		String bd = null;
		if(person.equals("child")){
			try{
				String q = "SELECT childDateofDelivery FROM child WHERE childID = '"+id+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					bd = (String) rs.getString("childDateofDelivery");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else if(person.equals("mother")){
			try{
				String q = "SELECT guardianBDate FROM guardian WHERE guardianID = '"+id+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					bd = (String) rs.getString("guardianBDate");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		bDate = createDate(bd,"-");
		age = bDate.getDeference(cdate);
	}
	public int getAmount(){
		Date visitDate = null;
		jdbc = new JDBC();
		Double maxWeight = Double.parseDouble("0");
		Double minWeight = Double.parseDouble("0");
		Double weight = Double.parseDouble("0");
		if((person.equals("child")) && (age != 0)){
			try{
				String q = "SELECT weight,clinicDate FROM childclinic WHERE childID = '"+id+"' && clinicDate = (SELECT MAX(clinicDate) FROM childclinic WHERE clinicDate < '"+currentDate+"');";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					weight = Double.parseDouble((String) rs.getString("weight"));
					pastDate = (String) rs.getString("clinicDate");
				}
				q = "SELECT * FROM childweightcycle WHERE maxAge > '"+age+"' && minAge <= '"+age+"';";
				st.executeQuery(q);
				rs = st.getResultSet();
				while(rs.next()){
					maxWeight = Double.parseDouble((String) rs.getString("maxWeight"));
					minWeight = Double.parseDouble((String) rs.getString("minWeight"));
				}
				if(weight<minWeight){
					amount = 2;
				}
				else{
					amount = 1;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else if((person.equals("mother")) && (age != 0)){
			try{
				String q = "SELECT weight,clinicDate FROM motherclinic WHERE motherID = '"+id+"' && clinicDate = (SELECT MAX(clinicDate) FROM motherclinic WHERE clinicDate < '"+currentDate+"');";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					weight = Double.parseDouble((String) rs.getString("weight"));
					pastDate = (String) rs.getString("clinicDate");
				}
				q = "SELECT * FROM motherweightcycle WHERE maxAge > '"+age+"' && minAge <= '"+age+"';";
				st.executeQuery(q);
				rs = st.getResultSet();
				while(rs.next()){
					maxWeight = Double.parseDouble((String) rs.getString("maxWeight"));
					minWeight = Double.parseDouble((String) rs.getString("minWeight"));
				}
				if((weight!=0)&&(weight<minWeight)){
					amount = 2;
				}
				else{
					amount = 1;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		amount = amount - checkTriposhaGiven();
		
		if(amount>0){
			return amount;
		}else{
			return 0;
		}
	}
	public int checkTriposhaGiven(){
		jdbc = new JDBC();
		if(pastDate==null){
			return 0;
		}
		int amount = 0;
		try{
			String q1 = "SELECT amount FROM triposha WHERE id = '"+id+"' && date > '"+pastDate+"' && date <= '"+currentDate+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q1);
			ResultSet rs1 = st.getResultSet();
			while(rs1.next()){
				amount = Integer.parseInt((String) rs1.getString("amount"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(amount>0){
			return amount;
		}else{
			return 0;
		}
	}
	Date createDate(String date,String seperator){
		String d[] = new String[3];
		if(date.charAt(4)=='-'){
			 d = date.split("-");
		}
		else if(date.charAt(4)=='/'){
			d = date.split("/");
		}
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
	boolean isLarge(String s1 ,String s2){
		String x[] = s1.split(" , ");
		int y1 = Integer.parseInt(x[0].substring(0, x[0].length()-6));
		int m1 = Integer.parseInt(x[1].substring(0, x[1].length()-6));
		int d1 = Integer.parseInt(x[2].substring(0, x[2].length()-4));
		x = s2.split(" , ");
		int y2 = Integer.parseInt(x[0].substring(0, x[0].length()-6));
		int m2 = Integer.parseInt(x[1].substring(0, x[1].length()-6));
		int d2 = Integer.parseInt(x[2].substring(0, x[2].length()-4));
		
		if(y1>=y2){
			return true;
		}
		else if((y1==y2)&&(m1>=m2)){
			return true;
		}
		else if((y1==y2)&&(m1==m2)&&(d1>=d2)){
			return true;
		}
		return false;
	}
}
