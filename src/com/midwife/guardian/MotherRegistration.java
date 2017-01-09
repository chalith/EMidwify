package com.midwife.guardian;

import java.io.*;


import java.io.PrintWriter;
import com.main.JDBC;
import com.main.Main;
import com.main.Mother;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
public class MotherRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Mother mother = null; 
		String id = request.getParameter("txtguardianid");
		String fullname = request.getParameter("txtfullname");
		String dateofbirth = request.getParameter("txtdateofbirth");
		String areacode = request.getParameter("txtareacode");
		String addressline1 = request.getParameter("txtaddressline1");
		String addressline2 = request.getParameter("txtaddressline2");
		String addressline3 = request.getParameter("txtaddressline3");
		String addressline4 = request.getParameter("txtaddressline4");
		String location = request.getParameter("txtlocation");
		String address = addressline1 + " \n " + addressline2 + " \n " + addressline3 + " \n " + addressline4;
		String occupation = request.getParameter("txtoccupation");
		String tpnumber1 = request.getParameter("txttpnumber1");
		String tpnumber2 = request.getParameter("txttpnumber2");
		String tpnumber3 = request.getParameter("txttpnumber3");
		String email = request.getParameter("txtemail");
		String nofchildren = request.getParameter("txtnofchildren");
		String edulevel = request.getParameter("edulevel");
		String motherguardian = request.getParameter("motherguardian");
		
		String mothernotes = request.getParameter("txtnotesformother");
		
		java.util.Date currentDate = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		String cDate = frmt.format(currentDate);

		Main m = new Main();
		
		//Epidemic epidemic = new Epidemic();
		
		/*try{
			JSONObject jsonObj = new JSONObject(request.getParameter("TableData"));
			String a = request.getParameter("epArray");
			out.print(a);
			out.print("fsdfs");
			Iterator it = jsonObj.keys();
			while(it.hasNext()){
				String key = (String)it.next();
				String val = (String)jsonObj.get(key);
				out.println(key+" -- "+val);
			}
		}catch(Exception e){
			out.println(e);
		}*/
				
		if(nofchildren.equals("")){
			nofchildren="0";
		}
		
		if((fullname.equals(""))||(dateofbirth.equals(""))||(areacode.equals(""))||(addressline1.equals(""))||(edulevel==null)){	
			String chkparams[]={fullname,dateofbirth,areacode,address};
			String setparams[]={"fullnameEmpty","dobEmpty","areacodeEmpty","addressEmpty"};
			String setstring[]={"full name","date of birth","area code","address"};
			for(int i=0;i<chkparams.length;i++){
				if(chkparams[i].equals("")){
					request.setAttribute(setparams[i],"**Enter the "+setstring[i]);
				}
			}
			if(edulevel==null){
				request.setAttribute("edulevelEmpty","**Enter the education level");
			}
		}
		else if(!((tpnumber1.equals("")||isTPNumber(tpnumber1))&&(tpnumber2.equals("")||isTPNumber(tpnumber2))&&(tpnumber3.equals("")||isTPNumber(tpnumber3)))){
			String chkparams[]={tpnumber1,tpnumber2,tpnumber3};
			String setparams[]={"isnotTP1","isnotTP2","isnotTP3"};
			for(int i=0;i<chkparams.length;i++){
				if(!(chkparams[i].equals("")||isTPNumber(chkparams[i]))){
					request.setAttribute(setparams[i],"**Telephone number is invalid");
				}
			}
		}
		else if(!(isInt(nofchildren))){
			request.setAttribute("isnotNofchildren","**Please enter a number");
		}
		else{
				mother = new Mother();
				mother.id=m.convertID(id);
				mother.fullname=fullname;
				mother.address=address;
				mother.email=email;
				mother.dateofbirth=dateofbirth;
				mother.occupation=occupation;
				mother.nofchildren=nofchildren;
				mother.edulevel=edulevel;
				mother.areacode=areacode;
				mother.mothernotes=mothernotes;
				
				String tpNumbers[]={tpnumber1,tpnumber2,tpnumber3};
	            for(int i=0;i<3;i++){
	            	if(!(tpNumbers[i].equals(""))){
	            		mother.addTPnumber(tpNumbers[i]);
	            	}
	            }
				
				String epidemics[] = request.getParameter("epArray").split("~row~");
				for(int i=1;i<epidemics.length;i++){
					String epidemic = epidemics[i];
					//out.println(epidemic);
					String ep[] = epidemic.split("~column~");
					if(ep.length>0){
						String epCode=ep[0].trim();
						String epName=ep[1].trim();
						String epDate=ep[2].trim();
						String epNote=ep[3].trim();
						mother.addEpidemic(epCode, epName, epDate, epNote);
					}
				}
				
				String abortions[] = request.getParameter("abArray").split("~row~");
				for(int i=1;i<abortions.length;i++){
					String abortion = abortions[i];
					//out.println(epidemic);
					String ab[] = abortion.split("~column~");
					if(ab.length>0){
						String abDate=ab[0].trim();
						String abNote=ab[1].trim();
						mother.addAbortion(abDate, abNote);
					}
				}
				
				String childdeaths[] = request.getParameter("cdArray").split("~row~");
				for(int i=1;i<childdeaths.length;i++){
					String childdeath = childdeaths[i];
					//out.println(epidemic);
					String cd[] = childdeath.split("~column~");
					if(cd.length>0){
						String cdName=cd[0].trim();
						String cdDate=cd[1].trim();
						String cdReason=cd[2].trim();
						mother.addChilddeath(cdName, cdDate, cdReason);
					}
				}
		}
		if(mother != null){		
			JDBC jdbc=new JDBC();
	        try{
		        String q1="INSERT INTO guardian (guardianID,guardianName,guardianAddress,guardianLocation,guardianEmail,guardianBDate,guardianOccupation,guardianNofchildren,guardianEducationLevel,guardianAreaCode,guardianNote,guardianDateOfRegistered,guardianPicture) " +
		        		"VALUES('"+mother.id+"','"+mother.fullname+"','"+mother.address+"','"+location+"','"+mother.email+"','"+mother.dateofbirth+"','"+mother.occupation+"','"+mother.nofchildren+"','"+mother.edulevel+"','"+mother.areacode+"','"+mother.mothernotes+"','"+cDate+"','midwife/images/services/profile.png')";
		        Statement st=jdbc.conn.createStatement();
				st.executeUpdate(q1);
	            
            	for(int i=0;i<mother.tpnumbers.size();i++){
            		String q2="INSERT INTO guardianmobilenumber (guardianID,guardianMobileNumber) VALUES('"+mother.id+"','"+mother.tpnumbers.get(i)+"')";
            		st.executeUpdate(q2);
            	}
            	if(motherguardian.equals("mother")){
            		String MID = m.generateID("mother", "Mother");
	            	String q="INSERT INTO mother (guardianID,motherID) VALUES('"+mother.id+"','"+MID+"')";
		            st.executeUpdate(q);
		            for(int i=0;i<mother.epidemics.size();i++){
			            String q2="INSERT INTO motherepidemics (motherID,epidemicCode,epidemicName,date,note) VALUES('"+MID+"','"+mother.epidemics.get(i)[0]+"','"+mother.epidemics.get(i)[1]+"','"+mother.epidemics.get(i)[2]+"','"+mother.epidemics.get(i)[3]+"')";
			            st.executeUpdate(q2);
		            }
		            for(int i=0;i<mother.abortions.size();i++){
			            String q2="INSERT INTO motherabortions (motherID,abortionDate,abortionReason) VALUES('"+MID+"','"+mother.abortions.get(i)[0]+"','"+mother.abortions.get(i)[1]+"')";
			            st.executeUpdate(q2);
			            //out.println("ab");
		            }
		            for(int i=0;i<mother.childdeaths.size();i++){
			            String q2="INSERT INTO motherchilddeaths (motherID,childName,date,reason) VALUES('"+MID+"','"+mother.childdeaths.get(i)[0]+"','"+mother.childdeaths.get(i)[1]+"','"+mother.childdeaths.get(i)[2]+"')";
			            st.executeUpdate(q2);
			            //out.println("cd");
		            }
	        	}
            	request.setAttribute("finalAlert","<script>showsuccessmessage(\"Guardian or Mother registered successfully\")</script>");
            	request.setAttribute("gid", mother.id);
        		getServletContext().getRequestDispatcher("/registeruser.jsp").forward(request,response);
	            out.println("successfully added");
	        }catch(Exception e){
	        	System.out.print(e);
	        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	        	getServletContext().getRequestDispatcher("/midwife/motherregistration.jsp").forward(request,response);
	        }finally{
	 			try {
					jdbc.conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		/*String chkparams[]={id,fullname,dateofbirth,areacode,addressline1,addressline2,addressline3,addressline4,occupation,tpnumber1,tpnumber2,tpnumber3,email,edulevel,epidemiccode,epidemicname,epidemicdate,epidemicnote,abortioncdate,abortionreason};
		String setparams[]={"gid","fullname","dob","areacode","addressline1","addressline2","addressline3","addressline4","occupation","tpnumber1","tpnumber2","tpnumber3","email","edulevel","epidemiccode","epidemicname","epidemicdate","epidemicnote","abortioncdate","abortionreason"};
		for(int i=0;i<chkparams.length;i++){
			request.setAttribute(setparams[i],chkparams[i]);
		}
		if(nofchildren!="0"){
			request.setAttribute("txtnofchildren",nofchildren);
		}*/
	}
	boolean isInt(String integer){
		try{
			int Int = Integer.parseInt(integer);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	boolean isTPNumber(String tpnumber){
		if(tpnumber.length()==10){
			return isInt(tpnumber);
		}
		return false;
	}
}
