package com.midwife;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.main.ChildInClinic;
import com.main.Clinic;
import com.main.JDBC;
import com.main.Main;
import com.main.MotherInClinic;

public class ClinicUnvisited {
	ArrayList<MotherInClinic> mothers = null;
	ArrayList<ChildInClinic> children = null;
	ArrayList<MotherInClinic> unvisitedmothers = new ArrayList<MotherInClinic>();
	ArrayList<ChildInClinic> unvisitedchildren = new ArrayList<ChildInClinic>();
	Clinic clinic = null;
	public ClinicUnvisited(String area, String date){
		Main m = new Main();
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
				if(!rs.next()){
					unvisitedmothers.add(mothers.get(i));
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
				if(!rs.next()){
					unvisitedchildren.add(children.get(i));
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
	public ArrayList<MotherInClinic> getUnvisitedMothers(){
		return unvisitedmothers;
	}
	public ArrayList<ChildInClinic> getUnvisitedChildren(){
		return unvisitedchildren;
	}
	
	public int getUnvisitedMotherCount(){
		return mothers.size();
	}
	public int getUnvisitedChildrenCount(){
		return children.size();
	}
	String viewUnvisitedMothers(){
		String view = "";
		if(unvisitedmothers != null){
			for(int i=0;i<unvisitedmothers.size();i++){
				view = view + "<li><p> Mother Name :- "+unvisitedmothers.get(i).name+"</br> Clinic Date :- "+unvisitedmothers.get(i).date+"</br> Age :- "+unvisitedmothers.get(i).age+"</p></li>";
			}
		}
		if(unvisitedchildren != null){
			for(int i=0;i<unvisitedchildren.size();i++){
				view = view + "<li><p> Mother Name :- "+unvisitedchildren.get(i).name+"</br> Clinic Date :- "+unvisitedchildren.get(i).date+"</br> Age :- "+unvisitedchildren.get(i).age+"</p></li>";
			}
		}
		return view;
	}
}
