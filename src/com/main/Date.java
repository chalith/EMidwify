package com.main;
public class Date{
	public int year;
	public int month;
	public int day;
	public Date(int year,int month,int day) throws invalidDayException{
		if(!isValid(year,month,day)){
			throw new invalidDayException();
		}
		this.year=year;
		this.month=month;
		this.day=day;
	}
	private boolean isLeapYear(int year){
		if(year%400==0)
			return true;
		if((year%4==0)&&(year%100!=0))
			return true;
		return false;
	}
	private boolean isValid(int year,int month,int day){
		if((year<0)||(month<0)||(day<0))
			return false;
		if(year<1000)
			return false;
		else if(month>12)
			return false;
		else if((month==1)||(month==3)||(month==5)||(month==7)||(month==8)||(month==10)||(month==12)){
			if(day>31)
				return false;
		}
		else if((month==4)||(month==6)||(month==9)||(month==11)){
			if(day>30)
				return false;
		}
		else if(month==2){
			if(isLeapYear(year)){
				if(day>29)
					return false;
			}
			else{
				if(day>28)
					return false;
			}
		}
		return true;
	}
	private int days(int year, int month){
		switch(month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return (isLeapYear(year))?29:28;
		}
		return 0;
	}
	public boolean isLeapYear(){
		return this.isLeapYear(this.year);
	}
	public boolean isLarge(Date d){
		if(d.year<this.year)
			return true;
		else if((d.year==this.year)&&(d.month<this.month))
			return true;
		else if((d.year==this.year)&&(d.month==this.month)&&(d.day<this.day))
			return true;
		return false;
	}
	public int getDeference(Date d){
		Date d2 = null;
		try{
			d2 = new Date(d.year,d.month,d.day);
		}catch(Exception e){
			System.out.println(e);
		}
		int days=0;
		while(d2.isLarge(this)){
			if(d2.day==0){
				d2.month--;
			}
			if(d2.month==0){
				d2.month=12;
				d2.year--;
			}
			if(d2.day==0){
				d2.day=days(d2.year,d2.month);
			}
			//System.out.println(d2.year+"-"+d2.month+"-"+d2.day);
			days++;
			d2.day--;
		}
		return days;
	}
	public Date increase(int days){
		Date d2 = null;
		try{
			d2 = new Date(this.year,this.month,this.day);
		}catch(Exception e){
			System.out.println(e);
		}
		for(int i=0;i<days;i++){
			d2.day++;
			if(d2.day==days(d2.year,d2.month)+1){
				d2.day=1;
				d2.month++;
			}
			if(d2.month==13){
				d2.month=1;
				d2.year++;
			}
		}
		return d2;
	}
	public String getAge(Date d){
		Date d2 = null;
		try{
			d2 = new Date(d.year,d.month,d.day);
		}catch(Exception e){
			System.out.println(e);
		}
		int days=0;
		int months=0;
		int years=0;
		while(d2.isLarge(this)){
			d2.day--;
			if(d2.day==0){
				d2.month--;
			}
			if(d2.month==0){
				d2.month=12;
				d2.year--;
			}
			if(d2.day==0){
				d2.day=days(d2.year,d2.month);
			}
			if(months==12){
				months=0;
				years++;
			}
			if(days==days(d2.year,d2.month)){
				days=0;
				months++;
			}
			days++;
			if(days==days(d2.year,d2.month)){
				days=0;
				months++;
			}
			if(months==12){
				months=0;
				years++;
			}
		}
		String ye = "";
		String mo = "";
		String da = "";
		if(years != 0){
			ye = years+" Years , ";
		}
		if(months != 0){
			mo = months+" Months , ";
		}
		if(days != 0){
			da = days+" Days";
		}
		return ye+mo+da;
	}
	public Date createDate(String date,String seperator){
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
class invalidDayException extends Exception{
	invalidDayException(){
		super("invalid values for date");
	}
}
