package com.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.main.Date;
import com.main.TriposhaAmounts;
import com.main.VaccineAmounts;

public class ChildInClinic {
	public VaccineAmounts vaccAmounts = null;
	public ArrayList<String[]> vaccines = null;
	public TriposhaAmounts triAmounts = null;
	public int triposhaAmount = 0;
	public String id = null;
	public String date = null;
	public String age = null;
	public String name = null;
	public ChildInClinic(String name, String id,String bDate, String date) {
		this.name = name;
		this.id = id;
		this.date = date;
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date dt = new java.util.Date();
    	Date currentDate = createDate(frmt.format(dt),"-");
    	Date birthDate = createDate(bDate,"-");
    	this.age = birthDate.getAge(currentDate);
    	
		vaccAmounts = new VaccineAmounts(id, date, 7, birthDate.getDeference(createDate(date,"-")));
		vaccines = vaccAmounts.getVaccines();
		
		triAmounts = new TriposhaAmounts(id,date, birthDate.getDeference(createDate(date,"-")));
		triposhaAmount = triAmounts.getAmount();
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
