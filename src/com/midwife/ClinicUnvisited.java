package com.midwife;

import java.sql.ResultSet;
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
	public ClinicUnvisited(String mid){
		JDBC jdbc = null;
		Main m = new Main();
		try{
			jdbc = new JDBC();
			String q = "SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs =  jdbc.st.getResultSet();
			while(rs.next()){
				String areacode = rs.getString("areaCode");
				JDBC jdbc1 = new JDBC();
				try{
					String q1 = "SELECT clinicDate FROM motherclinic;";
					jdbc1.st.executeQuery(q1);
					ResultSet rs1 =  jdbc1.st.getResultSet();
					while(rs1.next()){
						String date = rs1.getString("clinicDate");
						clinic = new Clinic(areacode, date);
						mothers = clinic.getMothers();
						for(int i=0;i<mothers.size();i++){
							if(!m.isHave("motherclinic", "motherID", mothers.get(i).id)){
								unvisitedmothers.add(mothers.get(i));
							}
						}
						children = clinic.getChildren();
						for(int i=0;i<children.size();i++){
							if(!m.isHave("childclinic", "childID", children.get(i).id)){
								unvisitedchildren.add(children.get(i));
							}
						}
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						jdbc1.conn.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
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
