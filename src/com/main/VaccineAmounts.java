package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONObject;

public class VaccineAmounts {
	String id = null;
	Date cdate = null;
	Date bDate = null;
	int age = 0;
	String person = null;
	int remindPeriod;
	public ArrayList<String[]> vAmounts = null;
	public VaccineAmounts(String id, int remindPeriod){
		this.remindPeriod = remindPeriod;
		this.id = id;
		getPerson(this.id);
		java.util.Date date = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		cdate = createDate(frmt.format(date),"-");
		getAge();
	}
	public VaccineAmounts(String id, String currentdate, int remindPeriod){
		this.remindPeriod = remindPeriod;
		this.id = id;
		getPerson(this.id);
		cdate = createDate(currentdate,"/");
		getAge();
	}
	public VaccineAmounts(String id, String currentdate, int age, int remindPeriod){
		this.remindPeriod = remindPeriod;
		this.id = id;
		this.age = age;
		getPerson(this.id);
		cdate = createDate(currentdate,"/");
	}
	private void getPerson(String id){
		//System.out.println(id);
		if(id.startsWith("Guard")){
			person = "mother";
		}
		else if(id.startsWith("Child")){
			person = "child";
		}
		else if(id.charAt(id.length()-1)=='v'){
			person = "mother";
		}
	}
	void getAge(){
		JDBC jdbc = new JDBC();
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
	public ArrayList<String[]> getVaccines(){
		vAmounts = new ArrayList<String[]>();
		Date visitDate = null;
		JDBC jdbc = new JDBC();
		String q=null;
		if((person.equals("child")) && (age != 0)){
			q = "SELECT * FROM childvaccineamounts";	
		}
		else if((person.equals("mother")) && (age != 0)){
			q = "SELECT * FROM mothervaccineamounts";
		}
		try{
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			JSONObject vaccnines = new JSONObject();
			int i=0;
			while(rs.next()){
				JSONObject vaccnine = new JSONObject();	
				vaccnine.put("vage", (String) rs.getString("age"));
				vaccnine.put("vCode", (String) rs.getString("vaccineCode"));
				vaccnine.put("vAmount", (String) rs.getString("amount"));
				vaccnines.put(Integer.toString(i), vaccnine);
				i++;
			}
			for(int j=0;j<i;j++){
				int vaage = Integer.parseInt((String) ((JSONObject) vaccnines.get(Integer.toString(j))).get("vage"));
				String vCode = (String) ((JSONObject) vaccnines.get(Integer.toString(j))).get("vCode");
				String vName = (String) ((JSONObject) vaccnines.get(Integer.toString(j))).get("vCode");
				String vAmount = (String) ((JSONObject) vaccnines.get(Integer.toString(j))).get("vAmount");
				
				if((((age%vaage)>=vaage-remindPeriod)||((age%vaage)==0))&&(!(checkVaccineGiven(vCode)))){
					if((age%vaage)!=0){
						visitDate = cdate.increase(vaage-(age%vaage));
					}
					else{
						visitDate = cdate;
					}
					String q2 = "SELECT vaccineName FROM vaccine WHERE vaccineCode = '"+vCode+"';";
					st.executeQuery(q2);
					ResultSet rs2 = st.getResultSet();
					while(rs2.next()){
						vName = (String) rs2.getString("vaccineName");
					}
					String vDate = visitDate.year+"/"+visitDate.month+"/"+visitDate.day;
					String s[] = {vCode,vName,vAmount,vDate};
					vAmounts.add(s);
				}
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
		return vAmounts;
	}
	public String getAmount(String vCode){
		JDBC jdbc = new JDBC();
		String amount = null;
		String q = null;
		if((person.equals("child")) && (age != 0)){
			q = "SELECT amount,age FROM childvaccineamounts WHERE vaccineCode = '"+vCode+"' HAVING age > "+age+"%age";
		}
		if((person.equals("mother")) && (age != 0)){
			q = "SELECT amount,age FROM mothervaccineamounts WHERE vaccineCode = '"+vCode+"' HAVING age > "+age+"%age";	
		}
		try{
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				 amount = (String) rs.getString("amount");
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
		return amount;
	}
	public boolean checkVaccineGiven(String vCode){
		JDBC jdbc = new JDBC();
		String givenDate = null;
		int period = 0;
		String q1=null;
		String q=null;
		if((person.equals("child")) && (age != 0)){
			q1 = "SELECT age FROM childvaccineamounts WHERE vaccineCode = '"+vCode+"'";
			q = "SELECT clinicDate FROM childgivenvaccines WHERE ((vaccineCode = '"+vCode+"') && (childID = '"+id+"'))";
			return false;
		}
		else if((person.equals("mother")) && (age != 0)){
			q1 = "SELECT age FROM mothervaccineamounts WHERE vaccineCode = '"+vCode+"'";
			q = "SELECT clinicDate FROM mothergivenvaccines WHERE ((vaccineCode = '"+vCode+"') && (motherID = '"+id+"'))";
			return false;
		}
		try{	
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q1);
			ResultSet rs1 = st.getResultSet();
			while(rs1.next()){
				period = Integer.parseInt((String) rs1.getString("age"));
			}
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				givenDate = (String) rs.getString("clinicDate");
				Date gDate = createDate(givenDate,"-");
				int difference = gDate.getDeference(cdate);
				if(difference<=period){
					return true;
				}
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
		return false;
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
}
