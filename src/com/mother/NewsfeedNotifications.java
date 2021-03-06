package com.mother;

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

/**
 * Servlet implementation class NewsfeedNotifications
 */
@WebServlet("/newsfeednotifications")
public class NewsfeedNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		String notificationString = "";
		MotherNotifications notifications = new MotherNotifications(mid);
		ArrayList<Notification> mnotifications = notifications.getNotifications();
		for(int i=0;i<mnotifications.size();i++){
			java.util.Date date = new java.util.Date();
			DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
			Date cDate = FormatDate.createDate(frmt.format(date));
			Date tndate = FormatDate.createDate(mnotifications.get(i).date);
			if(cDate.getDeference(tndate)<=7){			
				notificationString = notificationString + "<li><p>"+mnotifications.get(i).description+"<br>Date : "+mnotifications.get(i).date+"</p></li>";
			}
		}
		out.print(notificationString);
    }

}
