package com.midwife.event;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Event;
import com.main.JDBC;
import com.main.Main;
public class CreateEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Event event = new Event();
		String eps[] = request.getParameter("txtevnts").split("~row~");
		JDBC jdbc = new JDBC();
		try{
			for(int i=1;i<eps.length;i++){
				String ep = eps[i];
				String epidemic[] = ep.split("~column~");
				event.setEvent(epidemic[0].trim(), epidemic[1].trim(),epidemic[2].trim(),epidemic[3].trim(),epidemic[4].trim());
				String guardians[] = epidemic[5].split(",");
				for(int j=0;j<guardians.length;j++){
					event.addGuardians(guardians[j].trim());
				}
			}
			Main m = new Main();
			String eventID = m.generateID("event", "Event");
			String q = "INSERT INTO event (eventID,eventName,areaCode,eventDate,eventTime,eventVenue) VALUES ('"+eventID+"','"+event.name+"','"+event.area+"','"+event.date+"','"+event.time+"','"+event.venue+"');";
			Statement st=jdbc.conn.createStatement();
			st.executeUpdate(q);
			for(int i=0;i<event.guardians.size();i++){
				q = "INSERT INTO eventguardians (eventID,guardianID) VALUES ('"+eventID+"','"+event.guardians.get(i).id+"');";
				st.executeUpdate(q);
			}
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"Event created successfully\")</script>");
        	getServletContext().getRequestDispatcher("/midwife/createevent.jsp").forward(request,response);
            out.println("successfully added");
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	    	getServletContext().getRequestDispatcher("/midwife/createevent.jsp").forward(request,response);
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
