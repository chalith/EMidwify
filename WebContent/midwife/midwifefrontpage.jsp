<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.midwife.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Home</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/startpage.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/startpage.js"></script>
<script>
$(document).ready(function(){
	$('#viewprofile').click(function(){
		window.location = "midwife/midwife.jsp";
	});
	$('#viewviewlocation').click(function(){
		window.location = "midwife/viewlocation.jsp";
	});
	$('#viewcreateevent').click(function(){
		window.location = "midwife/createevent.jsp";
	});
	$('#viewtimeline').click(function(){
		window.location = "midwife/timeline.jsp";
	});
});
</script>
</head>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			out.print("<script>window.location=\"\";</script>");
			return;
		}
	%>
	<div class="container">
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp" />
		</div>
		<div class="body" id="container" style="background-color:#DEE6FB;">
				<div class="servicebox">
				
					<div style="display: inline-block; width: 100%; float: left;">
					
					<div class="services" id="viewprofile">
						<button><a><img id="viewprofile" src="midwife/images/services/viewprofile.png" alt="profile"></a></button>
						<h3 align="center" id="viewprofile">Profile</h3></br>
						<p style="margin-left: 25%;" id="viewprofile">View my profile</p>
					</div>
					<div id="viewtimeline" class="services">
						<button><a><img id="viewtimeline" src="midwife/images/services/timeline.png" alt="message"></a></button>
						<h3 id="viewtimeline" align="center">Timeline</h3></br>
						<p id="viewtimeline" style="margin-left: 23%;">View my timeline</p>
					</div>
					
					<div id="register" class="services">
						<button><a><img src="midwife/images/services/reg.png" alt="location"></a></button>
						<h3 align="center">Register</h3></br>
						<p style="margin-left: 20%;">Register Mother / Child</p>
						<div id="registerdrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="midwife/motherregistration.jsp">Mother</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href ="midwife/childregistration.jsp">Child</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					<!-- <div id="update" class="services">
						<button><a href="#"><img src="midwife/images/services/update.png " alt="even"></a></button>
						<h3 align="center">Update</h3></br>
						<p style="margin-left: 22%;">Update Mother / Child</p>
						<div id="updatedrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="midwife/motherupdate.jsp">Mother</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href ="midwife/childupdate.jsp">Child</a>
						</th>
						</tr>
						</table>
						</div>
					</div> -->
					<div id="clinic" class="services">
						<button><a><img src="midwife/images/services/clinic.png " alt="clinic"></a></button>
						<h3 align="center">Clinic</h3></br>
						<p style="margin-left: 25%;">View clinic details</p>
						<div id="clinicdrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="midwife/motherclinic.jsp">Mother</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="midwife/childclinic.jsp">Child</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					</div>
					<div style="display: inline-block; width: 100%; margin-left: 12%; float: left;">
					
					<div id="message" class="services">
						<button><a>
						<h1 id="messagecount" class="msgnotify" style="display:none;"></h1>
						<img src="midwife/images/services/message.png " alt="message"></a></button>
						<h3 align="center">Send messages</h3></br>
						<p style="margin-left: 10%;">Send messeges to Gurdians / Supervisor</p>
						<div id="messagedrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 5%; padding-right: 5%;" href="midwife/mothermessages.jsp">Mother
							<a id="mothermessagecount"></a>
						</a>
						</th>
						<th>
						<a href="midwife/supervisormessages.jsp">Supervisor
							<a id="supervisormessagecount"></a>
						</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					<div class="services" id="viewviewlocation">
						<button><a><img id="viewviewlocation" src="midwife/images/services/location.png" alt="location"></a></button>
						<h3 align="center" id="viewviewlocation">Location</h3></br>
						<p id="viewviewlocation" style="margin-left: 5%;">View locations I have to visit this week</p>
					</div>
					
					<div class="services" id="viewcreateevent">
						<button><a><img id="viewcreateevent" src="midwife/images/services/event.png " alt="even"></a></button>
						<h3 id="viewcreateevent" align="center">Create Events</h3></br>
						<p id="viewcreateevent" style="margin-left: 3%;">Create special schedules for events</p>
					</div>
					
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