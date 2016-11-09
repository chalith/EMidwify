package com.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String notificationID = (String) request.getParameter("notificationid");
		String notification = "";
		if((notificationID!=null) && notificationID.startsWith("Event")){
			Event event = new Event(notificationID);
			notification = "<center><p>Event</p></center>";
			notification = notification + ("<ul><li> Event - "+event.name+"</li><li> Venue - "+event.venue+"</li><li> Date - "+event.date+"</li><li> Time - "+event.time+"</li><li> Number of guardians - "+event.guardians.size()+"</li></ul>");
		}
		else{
			String id[] = notificationID.split(" <> ");
			String areaCode = id[0];
			String clinicDate = id[1];
			Clinic clinic = new Clinic(areaCode, clinicDate);
			notification = "<center><p>Clinic</p></center>";
			notification = notification + ("<ul><li> Venue - "+clinic.venue+"</li><li> Date - "+clinic.clinicDate+"</li><li> Time - "+clinic.time+"</li><li> Number of guardians - "+clinic.getMothers().size()+"</li><li> Number of babies - "+clinic.getChildren().size()+"</li></ul>");
		}
		out.print(notification);
	}
}
