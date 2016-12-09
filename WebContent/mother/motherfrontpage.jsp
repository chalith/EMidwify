<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.mother.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Home</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="mother/css/startpage.css">
<link rel="stylesheet" type="text/css" href="mother/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="mother/js/startpage.js"></script>
</head>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
	<jsp:include page="/notification.jsp" />
	<div class="container">
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp" />
		</div>
		<div class="body clearfix" id="container" style="background-color:#DEE6FB;">
			<div class="home-col clearfix" >
				<div class="servicebox">
					<div class="services">
						<button><a href="mother/mother.jsp"><img src="mother/images/services/viewprofile.png" alt="profile"></a></button>
						<h3 align="center">Profile</h3></br>
						<p style="margin-left: 25%;">View my profile</p>
					</div>
					
					<div id="view" class="services">
						<button><a href="#"><img src="mother/images/services/view.png" alt="message"></a></button>
						<h3 align="center">View</h3></br>
						<p style="margin-left: 23%;">View Mother / Child</p>
						<div id="viewdrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" onclick="window.open('mother/selectmother.jsp','name','width=400,height=500,left='+(window.innerWidth-400)/2+',top='+(window.innerHeight-500)/2+'')">Mother</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" onclick="window.open('mother/selectchild.jsp','name','width=400,height=500,left='+(window.innerWidth-400)/2+',top='+(window.innerHeight-500)/2+'')">Child</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					<div id="clinic" class="services">
						<button><a href="#"><img src="mother/images/services/clinic.png " alt="clinic"></a></button>
						<h3 align="center">Clinic</h3></br>
						<p style="margin-left: 25%;">View clinic details</p>
						<div id="clinicdrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="mother/motherclinic.jsp">Mother</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="mother/childclinic.jsp">Child</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					<div id="message" class="services">
						<button><a href="mother/midwifemessages.jsp">
						<h1 id="messagecount" class="msgnotify" style="display:none;"></h1>
						<img src="mother/images/services/message.png " alt="message"></a></button>
						<h3 align="center">Send messages</h3></br>
						<p style="margin-left: 10%;">Send messeges to Gurdians / Supervisor</p>
					</div>
					
				</div>	
				<div class="timeline">
					<center><div style="background-color: white; width: 89.5%; float: left; border-radius:10%; border: solid #3C405B;">
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
				
			<div class ="newsfeed" style="height: 100%; width: 20%; float: right; background-color:#AFC6DF;">
				
				<h2>Latest News</h2>
					
				<ul id="notifications">
					
				</ul>
				
			</div></br>
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
	<jsp:include page="/alert.jsp" />
	<jsp:include page="/error.jsp" />
	<%
		String alert = (String) request.getAttribute("warning");
		if(alert != null){
			out.print(alert);
		}
	%>	
</body>
</html>		