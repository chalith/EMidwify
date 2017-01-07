<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.main.Date"%>
<%@page import="com.mother.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Timeline</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/timeline.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="mother/js/createviewforall.js"></script>
<script src="mother/js/timeline.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
<div>
	<jsp:include page="/notification.jsp" />
	
	<div class="container">
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="body">
			<div style="float:left; width:80%; overflow:hidden; background-color:#E2E4F2;">
				<div class="timeline">
					<center><div style="background-color: white; width: 99%; float: left; border-radius:10%; border: solid #3C405B;">
						<h2>Timeline</h2>
					</div></center>
					<table>
					<%
						String notificationString = "";
						MotherNotifications notifications = new MotherNotifications(mid);
						ArrayList<Notification> mnotifications = notifications.getNotifications();
						for(int i=0;i<mnotifications.size();i++){
							notificationString = notificationString + "<tr id=\""+mnotifications.get(i).id+"\"><td id=\""+mnotifications.get(i).id+"\">"+mnotifications.get(i).title+"</td><td id=\""+mnotifications.get(i).id+"\">"+mnotifications.get(i).date+"</td><td id=\""+mnotifications.get(i).id+"\">"+mnotifications.get(i).description+"</td></tr>";
						}
						out.print(notificationString);
					%>
					</table>
				</div>
			</div>
			<div class="right_sidebar">
				<div class ="newsfeed clearfix" style="height: 100%;">
					
					<h2>Latest News</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
		</div>
		<div class="footer">
			<div style="float:left; width:100%; height:100%;">
				<div style="float:left; margin:20px;">
					Copyrights &copy; Domain Name. All rights Reseved
				</div>
					
				<div style="float:right; margin:20px;">
					website by: FordSlash
				</div>
			</div>
		</div>
	</div>
</div>
</body>

</html>