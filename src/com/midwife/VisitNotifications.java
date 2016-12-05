package com.midwife;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import com.main.Child;
import com.main.JDBC;

public class VisitNotifications {
	ArrayList<ChildVisit> visits = null;
	public VisitNotifications(String areaCode){
		visits = new ArrayList<ChildVisit>();
		JDBC jdbc = new JDBC();;
		try{
			String q = "SELECT childID,guardianID,childName,childDateofDelivery FROM child WHERE guardianID IN (SELECT guardianID FROM guardian WHERE guardianAreaCode = '"+areaCode+"');";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				Child child = new Child();
				child.childID = rs.getString("childID");
				child.motherguardianID = rs.getString("guardianID");
				child.childName = rs.getString("childName");
				child.dateofDelivery = rs.getString("childDateofDelivery");
				ChildVisit cvisit = new ChildVisit(child);
				if(cvisit.visitDate != null){
					visits.add(cvisit);
				}
			}
		}catch(Exception e){
			System.out.print(e);
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	public ArrayList<ChildVisit> getVisits(){
		return visits;
	}
	String viewVisits(){
		String view = "";
		if(visits != null){
			for(int i=0;i<visits.size();i++){
				view = view + "<li><a style=\"color:purple;\" href=\"/EMidwify/midwife/viewlocation.jsp\">Visit &raquo </a><p> Date to visit :- "+visits.get(i).visitDate+"</br> Child :- "+visits.get(i).childName+"</br> Guardian :- "+visits.get(i).guardianName+"</br> Address :- "+visits.get(i).address+"</br> Current age :- "+visits.get(i).visitAge+"</br> Visit Type :- "+visits.get(i).visitType+"</br> Location :- "+visits.get(i).location+"</p></li>";
			}
		}
		return view;
	}
}
