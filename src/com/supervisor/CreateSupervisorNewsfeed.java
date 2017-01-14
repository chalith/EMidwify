package com.supervisor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Date;
import com.main.FormatDate;
import com.main.Notification;
@WebServlet("/createsupervisornewsfeed")
public class CreateSupervisorNewsfeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		java.util.Date date = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		Date cDate = FormatDate.createDate(frmt.format(date));
		
		String notificationString = "";
		SupervisorNotifications notifications = new SupervisorNotifications(mid);
		ArrayList<Notification> snotifications = notifications.getNotifications();
		for(int i=0;i<snotifications.size();i++){
			String ndate = snotifications.get(i).date;
			Date tndate = FormatDate.createDate(ndate);
			if(cDate.getDeference(tndate)<=4){
				notificationString = notificationString + "<li><p> "+snotifications.get(i).description+" </br> Date - "+snotifications.get(i).date+"</p></li>";
			}	
		}
		out.print(notificationString);
	}

}
