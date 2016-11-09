package com.main;

import com.main.*;

public class FormatDate {
	public static Date createDate(String date){
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
