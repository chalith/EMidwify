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

public class ClinicUnvisited {
	ArrayList<MotherInClinic> mothers = null;
	ArrayList<ChildInClinic> children = null;
	ArrayList<MotherInClinic> unvisitedmothers = new ArrayList<MotherInClinic>();
	ArrayList<ChildInClinic> unvisitedchildren = new ArrayList<ChildInClinic>();
	Clinic clinic = null;
	String date = null;
	String area = null;
	public ClinicUnvisited(String area, String date){
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
	public void sendAlert(){
		JDBC jdbc = new JDBC();
		Main m = new Main();
		
		//notify to midwife
		int umothercount = getUnvisitedMotherCount();
		int uchildcount = getUnvisitedChildrenCount();
		String part1="";
		String part2="";
		if(umothercount>0){
			part1 = umothercount+" mothers";
		}
		if(uchildcount>0){
			part2 = uchildcount+" babies";
		}
		String unvisitedmsg = null;
		if(!(part1.equals("")&&part2.equals(""))){
			unvisitedmsg = part1+"   "+part2+"  didn't attend for the clinic scheduled on "+date+" in "+area;
		}
		if(unvisitedmsg!=null){
			jdbc = new JDBC();
			try{
				String q = "SELECT mobileNumber FROM midwifemobilenumber WHERE midwifeID = (SELECT midwifeID FROM area WHERE areaCode = '"+area+"');";
				Statement st = jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					m.sendSms(rs.getString("mobileNumber"), unvisitedmsg);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
		return unvisitedmothers.size();
	}
	public int getUnvisitedChildrenCount(){
		return unvisitedchildren.size();
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
				view = view + "<li><p> Child Name :- "+unvisitedchildren.get(i).name+"</br> Clinic Date :- "+unvisitedchildren.get(i).date+"</br> Age :- "+unvisitedchildren.get(i).age+"</p></li>";
			}
		}
		return view;
	}
}
