package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VaccineAmounts {
	String id = null;
	Date cdate = null;
	Date bDate = null;
	int age = 0;
	String person = null;
	int remindPeriod;
	public ArrayList<String[]> vAmounts = null;
	JDBC jdbc = null;
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
		jdbc = new JDBC();
		String bd = null;
		if(person.equals("child")){
			try{
				String q = "SELECT childDateofDelivery FROM child WHERE childID = '"+id+"';";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
		jdbc = new JDBC();
		if((person.equals("child")) && (age != 0)){
			try{
				String q = "SELECT * FROM childvaccineamounts";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
				while(rs.next()){
					int vaage = Integer.parseInt((String) rs.getString("age"));
					String vCode = (String) rs.getString("vaccineCode");
					String vName = null;
					String vAmount = (String) rs.getString("amount");
					if((((age%vaage)>=vaage-remindPeriod)||((age%vaage)==0))&&(!(checkVaccineGiven(vCode)))){
						if((age%vaage)!=0){
							visitDate = cdate.increase(vaage-(age%vaage));
						}
						else{
							visitDate = cdate;
						}
						JDBC jdbc2 = new JDBC();
						try{
							String q2 = "SELECT vaccineName FROM vaccine WHERE vaccineCode = '"+vCode+"';";
							jdbc2.st.executeQuery(q2);
							ResultSet rs2 = jdbc2.st.getResultSet();
							while(rs2.next()){
								vName = (String) rs2.getString("vaccineName");
							}
							String vDate = visitDate.year+"/"+visitDate.month+"/"+visitDate.day;
							String s[] = {vCode,vName,vAmount,vDate};
							vAmounts.add(s);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							jdbc2.conn.close();
						}
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
		}
		else if((person.equals("mother")) && (age != 0)){
			try{
				String q = "SELECT * FROM mothervaccineamounts";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
				while(rs.next()){
					int vaage = Integer.parseInt((String) rs.getString("age"));
					String vCode = (String) rs.getString("vaccineCode");
					String vName = null;
					String vAmount = (String) rs.getString("amount");
					if((((age%vaage)>=vaage-remindPeriod)||((age%vaage)==0))&&(!(checkVaccineGiven(vCode)))){
						if((age%vaage)!=0){
							visitDate = cdate.increase(vaage-(age%vaage));
						}
						else{
							visitDate = cdate;
						}
						JDBC jdbc2 = new JDBC();
						try{
							String q2 = "SELECT vaccineName FROM vaccine WHERE vaccineCode = '"+vCode+"';";
							jdbc2.st.executeQuery(q2);
							ResultSet rs2 = jdbc2.st.getResultSet();
							while(rs2.next()){
								vName = (String) rs2.getString("vaccineName");
							}
							String vDate = visitDate.year+"/"+visitDate.month+"/"+visitDate.day;
							String s[] = {vCode,vName,vAmount,vDate};
							vAmounts.add(s);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							jdbc2.conn.close();
						}
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
		}
		return vAmounts;
	}
	public String getAmount(String vCode){
		jdbc = new JDBC();
		String amount = null;
		if((person.equals("child")) && (age != 0)){
			try{
				String q = "SELECT amount,age FROM childvaccineamounts WHERE vaccineCode = '"+vCode+"' HAVING age > "+age+"%age";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
		}
		if((person.equals("mother")) && (age != 0)){
			try{
				String q = "SELECT amount,age FROM mothervaccineamounts WHERE vaccineCode = '"+vCode+"' HAVING age > "+age+"%age";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
		}
		return amount;
	}
	public boolean checkVaccineGiven(String vCode){
		jdbc = new JDBC();
		String givenDate = null;
		int period = 0;
		if((person.equals("child")) && (age != 0)){
			try{
				String q1 = "SELECT age FROM childvaccineamounts WHERE vaccineCode = '"+vCode+"'";
				jdbc.st.executeQuery(q1);
				ResultSet rs1 = jdbc.st.getResultSet();
				while(rs1.next()){
					period = Integer.parseInt((String) rs1.getString("age"));
				}
				String q = "SELECT clinicDate FROM childgivenvaccines WHERE ((vaccineCode = '"+vCode+"') && (childID = '"+id+"'))";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
		else if((person.equals("mother")) && (age != 0)){
			try{
				String q1 = "SELECT age FROM mothervaccineamounts WHERE vaccineCode = '"+vCode+"'";
				jdbc.st.executeQuery(q1);
				ResultSet rs1 = jdbc.st.getResultSet();
				while(rs1.next()){
					period = Integer.parseInt((String) rs1.getString("age"));
				}
				String q = "SELECT clinicDate FROM mothergivenvaccines WHERE ((vaccineCode = '"+vCode+"') && (motherID = '"+id+"'))";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
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
