package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
import com.main.Main;
@WebServlet("/supervisorregistration")
public class SupervisorRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String id = request.getParameter("txtsupervisorid");
		String fullname = request.getParameter("txtfullname");
		String dateofbirth = request.getParameter("txtdateofbirth");
		String starteddate = request.getParameter("txtdateofstart");
		String addressline1 = request.getParameter("txtaddressline1");
		String addressline2 = request.getParameter("txtaddressline2");
		String addressline3 = request.getParameter("txtaddressline3");
		String addressline4 = request.getParameter("txtaddressline4");
		String address = addressline1 + " \n " + addressline2 + " \n " + addressline3 + " \n " + addressline4;
		String tpnumber1 = request.getParameter("txttpnumber1");
		String tpnumber2 = request.getParameter("txttpnumber2");
		String tpnumber3 = request.getParameter("txttpnumber3");
		String email = request.getParameter("txtemail");
		ArrayList<String> tpnumbers=new ArrayList<String>();
		
		java.util.Date currentDate = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		String cDate = frmt.format(currentDate);

		Main m = new Main();
		
		if((fullname.equals(""))||(dateofbirth.equals(""))||(addressline1.equals(""))){	
			String chkparams[]={fullname,dateofbirth,address};
			String setparams[]={"fullnameEmpty","dobEmpty","addressEmpty"};
			String setstring[]={"full name","date of birth","address"};
			for(int i=0;i<chkparams.length;i++){
				if(chkparams[i].equals("")){
					request.setAttribute(setparams[i],"**Enter the "+setstring[i]);
				}
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
		else{
				id=m.convertID(id);
				
				String tpNumbers[]={tpnumber1,tpnumber2,tpnumber3};
	            for(int i=0;i<3;i++){
	            	if(!(tpNumbers[i].equals(""))){
	            		tpnumbers.add(tpNumbers[i]);
	            	}
	            }
				
		}
		JDBC jdbc=new JDBC();
        try{
	        String q1="INSERT INTO supervisor (supervisorID,name,dateOfBirth,address,email,startedDate,supervisorPicture,dateOfRegistered) " +
	        		"VALUES('"+id+"','"+fullname+"','"+dateofbirth+"','"+address+"','"+email+"','"+starteddate+"','supervisor/images/services/father.png','"+cDate+"')";
            jdbc.st.executeUpdate(q1);
            
        	for(int i=0;i<tpnumbers.size();i++){
        		String q2="INSERT INTO supervisormobilenumber (supervisorID,mobileNumber) VALUES('"+id+"','"+tpnumbers.get(i)+"')";
        		jdbc.st.executeUpdate(q2);
        	}
        	request.setAttribute("finalAlert","<script>showsuccessmessage(\"Supervisor registered successfully\")</script>");
        	request.setAttribute("gid", id);
    		getServletContext().getRequestDispatcher("/registeruser.jsp").forward(request,response);
            out.println("successfully added");
        }catch(Exception e){
        	System.out.print(e);
        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
        	getServletContext().getRequestDispatcher("/admin/supervisorregistration.jsp").forward(request,response);
        }finally{
 			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
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
