package com.midwife.child;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Child;
import com.main.Father;
import com.main.JDBC;
import com.main.Main;
import com.main.MotherDeath;
public class ChildEditInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Child child = null;
		String mguardID = request.getParameter("txtmotherguardianid");
		String mguardName = request.getParameter("txtmotherguardianname");
		String id = request.getParameter("txtchildid");
		String childname = request.getParameter("txtchildname");
		String dateofdelivery = request.getParameter("txtdateofchilddelivery");
		String birthhweight = request.getParameter("txtchildbirthweight");
		String childNotes = request.getParameter("txtnotesforchild");
		
		Father father = null;
		String fatherID = request.getParameter("txtfatherid");
		String fatherName = request.getParameter("txtfatherfullname");
		String fatherDateofBirth = request.getParameter("txtfatherdateofbirth");
		String occupation = request.getParameter("txtoccupation");
		String addressline1 = request.getParameter("txtaddressline1");
		String addressline2 = request.getParameter("txtaddressline2");
		String addressline3 = request.getParameter("txtaddressline3");
		String addressline4 = request.getParameter("txtaddressline4");
		String address = addressline1 + " \n " + addressline2 + " \n " + addressline3 + " \n " + addressline4;
		String edulevel = request.getParameter("edulevel");
		
		MotherDeath motherdeath = null;
		String deathmotherid = request.getParameter("txtdeathmotherid");
		String deathmothername = request.getParameter("txtdeathmothername");
		String deathmotherareacode = request.getParameter("txtdeathmotherareacode");
		String dateofmotherdeath = request.getParameter("txtdateofmotherdeath");
		String reasonfordeath = request.getParameter("txtreasonfordeath");
		
		String registeredfatherID = request.getParameter("txtregisteredfatherid");
		String registeredfatherName = request.getParameter("txtregisteredfathername");
		
		java.util.Date currentDate = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		String cDate = frmt.format(currentDate);
		
		Main m = new Main();
		
		child = new Child();
		child.childID=id;
		child.motherguardianID=m.convertID(mguardID);
		child.childName=childname;
		child.dateofDelivery=dateofdelivery;
		child.birthWeight=birthhweight;
		child.note=childNotes;
		
		//System.out.println("cepArray  "+request.getParameter("cepArray"));
		String epidemics[] = request.getParameter("cepArray").split("~row~");
		for(int i=1;i<epidemics.length;i++){
			String epidemic = epidemics[i];
			String ep[] = epidemic.split("~column~");
			if(ep.length>0){
				String epCode=ep[0].trim();
				String epName=ep[1].trim();
				String epDate=ep[2].trim();
				String epNote=ep[3].trim();
				child.addEpidemic(epCode, epName, epDate, epNote);
			}
		}
		
		if(!fatherName.equals("")){
			father = new Father();
			father.fatherID=m.convertID(fatherID);
			father.fatherName=fatherName;
			father.address=address;
			father.dateofBirth=fatherDateofBirth;
			father.occupation=occupation;
			father.eduLevel=edulevel;
			String fatherepidemics[] = request.getParameter("fepArray").split("~row~");
			for(int i=1;i<fatherepidemics.length;i++){
				String epidemic = fatherepidemics[i];
				//out.println(epidemic);
				String ep[] = epidemic.split("~column~");
				if(ep.length>0){
					String epCode=ep[0].trim();
					String epName=ep[1].trim();
					String epDate=ep[2].trim();
					String epNote=ep[3].trim();
					father.addEpidemic(epCode, epName, epDate, epNote);
				}
			}
			child.father=father;
		}
		if(!dateofmotherdeath.equals("")){
			motherdeath = new MotherDeath();
			if(!deathmotherid.equals("")){
				motherdeath.motherID = m.convertID(deathmotherid);
			}
			motherdeath.motherName = deathmothername;
			motherdeath.motherAreaCode = deathmotherareacode;
			motherdeath.dateofDeath = dateofmotherdeath;
			motherdeath.reason = reasonfordeath;
			child.motherDeath = motherdeath;
		}
		
		if(child != null){
			JDBC jdbc = new JDBC();
	        try{
		        String q="UPDATE child SET guardianID = '"+child.motherguardianID+"',childName = '"+child.childName+"',childNotes = '"+child.note+"' WHERE childID = '"+id+"';";
		        Statement st=jdbc.conn.createStatement();
				st.executeUpdate(q);
	            
	            for(int i=0;i<child.epidemics.size();i++){
		            String q1="INSERT INTO childepidemics (childID,epidemicCode,epidemicName,date,note) VALUES('"+child.childID+"','"+child.epidemics.get(i)[0]+"','"+child.epidemics.get(i)[1]+"','"+child.epidemics.get(i)[2]+"','"+child.epidemics.get(i)[3]+"')";
		            st.executeUpdate(q1);
	            }
	            if(father != null){
	            	String q1="UPDATE father SET fatherAddress = '"+father.address+"', fatherOccupation = '"+father.occupation+"',fatherEdulevel = '"+father.eduLevel+"' WHERE fatherID = '"+father.fatherID+"';";
	            	st.executeUpdate(q1);
	            	for(int i=0;i<father.epidemics.size();i++){
			            q1="INSERT INTO fatherepidemics (fatherID,epidemicCode,epidemicName,date,note) VALUES('"+father.fatherID+"','"+father.epidemics.get(i)[0]+"','"+father.epidemics.get(i)[1]+"','"+father.epidemics.get(i)[2]+"','"+father.epidemics.get(i)[3]+"')";
			            st.executeUpdate(q1);
		            }
	            }
	            if(motherdeath != null){
	            	String q2;
	            	if(motherdeath.motherID!=null){
	            		q2="INSERT INTO motherdeaths (motherID,motherName,motherAreaCode,date,reason) " +
	            		"VALUES('"+motherdeath.motherID+"','"+motherdeath.motherName+"','"+motherdeath.motherAreaCode+"','"+motherdeath.dateofDeath+"','"+motherdeath.reason+"')";
	            	}else{
	            		q2="INSERT INTO motherdeaths (motherName,motherAreaCode,date,reason) " +
	            		"VALUES('"+motherdeath.motherName+"','"+motherdeath.motherAreaCode+"','"+motherdeath.dateofDeath+"','"+motherdeath.reason+"')";	
	            	}
	            	st.executeUpdate(q2);
	            }
	            
	        	request.setAttribute("finalAlert","<script>showsuccessmessage(\"Child updated successfully\")</script>");
	            out.println("successfully added");
	        }catch(Exception e){
	        	System.out.print(e);
	        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	        }finally{
	        	try {
					jdbc.conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        getServletContext().getRequestDispatcher("/midwife/editchildinfo.jsp?id="+id).forward(request, response);
		}
	}
}
