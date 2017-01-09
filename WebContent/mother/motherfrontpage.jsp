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
<script src="mother/js/createviewforall.js"></script>
<script>
$(document).ready(function(){
	$('#viewprofile').click(function(){
		window.location = "mother/guardian.jsp";
	});
	$('#clinic').click(function(){
		window.location = "mother/clinic.jsp";
	});
	$('#message').click(function(){
		window.location = "mother/midwifemessages.jsp";
	});
	$('#viewtimeline').click(function(){
		window.location = "mother/timeline.jsp";
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
		<input type="hidden" id="guardianid" value="<%
			if(mid!=null){
				out.print(mid);
			}
			%>">
						
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp" />
		</div>
		<div class="body" id="container" style="background-color:#DEE6FB;">
				<div class="servicebox">
				
					<div style="display: inline-block; width: 100%; float: left;">
					
					<div class="services" id="viewprofile">
						<button><a><img id="viewprofile" src="mother/images/services/viewprofile.png" alt="profile"></a></button>
						<h3 align="center" id="viewprofile">Profile</h3></br>
						<p style="margin-left: 25%;" id="viewprofile">View my profile</p>
					</div>
					<div id="viewtimeline" class="services">
						<button><a><img id="viewtimeline" src="mother/images/services/timeline.png" alt="message"></a></button>
						<h3 id="viewtimeline" align="center">Timeline</h3></br>
						<p id="viewtimeline" style="margin-left: 23%;">View my timeline</p>
					</div>
					
					<div id="clinic" class="services">
						<button><a><img src="mother/images/services/clinic.png " alt="clinic"></a></button>
						<h3 align="center">Clinic</h3></br>
						<p style="margin-left: 25%;">View clinic details</p>
					</div>
					
					<div id="message" class="services">
						<button><a>
						<h1 id="messagecount" class="msgnotify" style="display:none;"></h1>
						<img src="mother/images/services/message.png " alt="message"></a></button>
						<h3 align="center">Send messages</h3></br>
						<p style="margin-left: 10%;">Send messeges to Midwife</p>
					</div>
						
					</div>
					<div style="border: solid white; margin-top: 22%;">
						<h1 style="text-align: center;color: white; margin: 0 -5px 0 -5px;">My Babies</h1>
						<div id="babies" class="babies">
									
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