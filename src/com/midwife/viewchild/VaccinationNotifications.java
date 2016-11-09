package com.midwife.viewchild;

import java.util.ArrayList;

import com.main.VaccineAmounts;

public class VaccinationNotifications {
	VaccineAmounts vamounts =null;
	String id = null;
	String vaccination = "";
	int remindPeriod;
	public VaccinationNotifications(String id, int remindPeriod) {
		this.remindPeriod = remindPeriod;
		this.id = id;
		vamounts = new VaccineAmounts(id, 7);
		ArrayList<String[]> vAmounts = vamounts.getVaccines();
		for(int i=0;i<vAmounts.size();i++){
			String vCode = vAmounts.get(i)[0];
			String vName = vAmounts.get(i)[1];
			String vAmount = vAmounts.get(i)[2];
			String vDate = vAmounts.get(i)[3];
			vaccination = vaccination + "<li><a href=\"#\">Date  : "+vDate+"</a><p>Vaccine : "+vName+"</p><p>Vaccine Code : "+vCode+"</p><p>Vaccine Amount : "+vAmount+"</p></li>";
		}
	}
	public String getNotification(){
		return vaccination;
	}
}
