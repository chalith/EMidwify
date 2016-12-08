package com.supervisor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
import com.main.Main;

/**
 * Servlet implementation class Supervisoreditinfo
 */
@WebServlet("/supervisoreditinfo")
public class Supervisoreditinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
		String id = request.getParameter("txtmidwifeid");
		String fullname = request.getParameter("txtfullname");
		String addressline1 = request.getParameter("txtaddressline1");
		String addressline2 = request.getParameter("txtaddressline2");
		String addressline3 = request.getParameter("txtaddressline3");
		String addressline4 = request.getParameter("txtaddressline4");
		String address = addressline1 + " \n " + addressline2 + " \n " + addressline3 + " \n " + addressline4;
		String tpnumber1 = request.getParameter("txttpnumber1");
		String tpnumber2 = request.getParameter("txttpnumber2");
		String tpnumber3 = request.getParameter("txttpnumber3");
		String email = request.getParameter("txtemail");
		java.util.Date currentDate = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		String cDate = frmt.format(currentDate);
		
		Main m = new Main();
		
		String tpNumbers[]={tpnumber1,tpnumber2,tpnumber3};
        
		JDBC jdbc = new JDBC();
        try{
	        String q1="UPDATE supervisor SET name = '"+fullname+"', address = '"+address+"', email = '"+email+"' WHERE supervisorID = '"+id+"';";
            jdbc.st.executeUpdate(q1);
            
            String q22 = "DELETE FROM supervisormobilenumber WHERE supervisorID = '"+id+"';";
    		jdbc.st.executeUpdate(q22);
        	for(int i=0;i<tpNumbers.length;i++){
        		if(!tpNumbers[i].equals("")){
	        		q22 = "INSERT INTO supervisormobilenumber (supervisorID,mobileNumber) VALUES ('"+id+"','"+tpNumbers[i]+"')";
	        		jdbc.st.executeUpdate(q22);
        		}
        	}
        	request.setAttribute("finalAlert","<script>showsuccessmessage(\"Your details updated successfully\")</script>");
        	out.println("successfully added");
        }catch(Exception e){
        	System.out.print(e);
        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
        }finally{
        	try {
    			jdbc.conn.close();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
        }
        getServletContext().getRequestDispatcher("/supervisor/editsupervisorinfo.jsp").forward(request,response);
    }
}
