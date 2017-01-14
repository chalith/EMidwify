package com.midwife;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.main.ChildInClinic;
import com.main.Clinic;
import com.main.JDBC;
import com.main.Main;
import com.main.MotherInClinic;

public class ClinicVisited {
	ArrayList<MotherInClinic> mothers = null;
	ArrayList<ChildInClinic> children = null;
	ArrayList<MotherInClinic> visitedmothers = new ArrayList<MotherInClinic>();
	ArrayList<ChildInClinic> visitedchildren = new ArrayList<ChildInClinic>();
	Clinic clinic = null;
	String date = null;
	String area = null;
	public ClinicVisited(String area, String date){
		this.date = date;
		this.area = area;
		clinic = new Clinic(area, date);
		mothers = clinic.getMothers();
		JDBC jdbc = null;
		for(int i=0;i<mothers.size();i++){
			jdbc = new JDBC();
			try{
				String q = "SELECT * FROM motherclinic WHERE motherID = '"+mothers.get(i).id+"' AND clinicDate = '"+date+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs =  st.getResultSet();
				if(rs.next()){
					visitedmothers.add(mothers.get(i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					jdbc.conn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}	
		}
		children = clinic.getChildren();
		for(int i=0;i<children.size();i++){
			jdbc = new JDBC();
			try{
				String q = "SELECT * FROM childclinic WHERE childID = '"+children.get(i).id+"' AND clinicDate = '"+date+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs =  st.getResultSet();
				if(rs.next()){
					visitedchildren.add(children.get(i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					jdbc.conn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void sendAlert(){
		JDBC jdbc = new JDBC();
		Main m = new Main();
		
		//notify to midwife
		int mothercount = getVisitedMotherCount();
		int childcount = getVisitedChildrenCount();
		String part1="";
		String part2="";
		if(mothercount>0){
			part1 = mothercount+" mothers";
		}
		if(childcount>0){
			part2 = childcount+" babies";
		}
		String visitedmsg = null;
		if(!(part1.equals("")&&part2.equals(""))){
			visitedmsg = part1+"   "+part2+"  attended for the clinic scheduled on "+date+" in "+area;
		}
	}
	public ArrayList<MotherInClinic> getVisitedMothers(){
		return visitedmothers;
	}
	public ArrayList<ChildInClinic> getVisitedChildren(){
		return visitedchildren;
	}
	
	public int getVisitedMotherCount(){
		return visitedmothers.size();
	}
	public int getVisitedChildrenCount(){
		return visitedchildren.size();
	}
	String viewVisitedMothers(){
		String view = "";
		if(visitedmothers != null){
			for(int i=0;i<visitedmothers.size();i++){
				view = view + "<li><p> Mother Name :- "+visitedmothers.get(i).name+"</br> Clinic Date :- "+visitedmothers.get(i).date+"</br> Age :- "+visitedmothers.get(i).age+"</p></li>";
			}
		}
		if(visitedchildren != null){
			for(int i=0;i<visitedchildren.size();i++){
				view = view + "<li><p> Child Name :- "+visitedchildren.get(i).name+"</br> Clinic Date :- "+visitedchildren.get(i).date+"</br> Age :- "+visitedchildren.get(i).age+"</p></li>";
			}
		}
		return view;
	}
}
