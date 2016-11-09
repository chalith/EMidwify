package com.main;

import java.util.ArrayList;

public class Child {
	public String motherguardianName = null;
	public String motherguardianID = null;
	public String childID = null;
	public String childName = null;
	public String dateofDelivery = null;
	public String birthWeight = null;
	public String note = null;
	public Father father = null;
	public MotherDeath motherDeath = null;
	public ArrayList<String[]> epidemics = new ArrayList<String[]>();
	public void addEpidemic(String epCode,String  epName,String  epDate,String  epNote){
		String[] epidemic = {epCode, epName, epDate, epNote};
		epidemics.add(epidemic);
	}
}
