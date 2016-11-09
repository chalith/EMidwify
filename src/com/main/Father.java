package com.main;

import java.util.ArrayList;

public class Father {
	public String fatherID = null;
	public String fatherName = null;
	public String dateofBirth = null;
	public String birthWeight = null;
	public String address = null;
	public String occupation = null;
	public String eduLevel = null;
	public ArrayList<String[]> epidemics = new ArrayList<String[]>();
	public void addEpidemic(String epCode,String  epName,String  epDate,String  epNote){
		String[] epidemic = {epCode, epName, epDate, epNote};
		epidemics.add(epidemic);
	}
}
