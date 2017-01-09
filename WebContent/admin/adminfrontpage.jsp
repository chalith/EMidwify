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
<link rel="stylesheet" type="text/css" href="admin/css/startpage.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="admin/js/startpage.js"></script>
<script>
$(document).ready(function(){
	$('#viewprofile').click(function(){
		window.location = "admin/profile.jsp";
	});
	$('#epidemics').click(function(){
		window.location = "admin/addepidemic.jsp";
	});
	$('#diseases').click(function(){
		window.location = "admin/adddisease.jsp";
	});
	$('#vaccine').click(function(){
		window.location = "admin/addvaccine.jsp";
	});
	$('#area').click(function(){
		window.location = "admin/addarea.jsp";
	});
});
</script>
</head>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
			return;
		}
	%>
	<div class="container">
		<div style="width:100%; height:10%; position:relative">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="body" id="container" style="background-color:#DEE6FB;">
				<div class="servicebox">
				
					<div style="display: inline-block; width: 100%; float: left;">
					
					<div class="services" id="viewprofile" style="margin-left: 1.5%;">
						<button><a><img id="viewprofile" src="admin/images/services/viewprofile.png" alt="profile"></a></button>
						<h3 align="center" id="viewprofile">Profile</h3></br>
						<p style="margin-left: 25%;" id="viewprofile">View my profile</p>
					</div>
					
					<div id="register" class="services">
						<button><a><img src="admin/images/services/reg.png" alt="location"></a></button>
						<h3 align="center">Register</h3></br>
						<p style="margin-left: 20%;">Register Supervisor / Midwife</p>
						<div id="registerdrop" class="dropdown-content">
						<table>
						<tr>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href="admin/supervisorregistration.jsp">Supervisor</a>
						</th>
						<th>
						<a style="padding-left: 10%; padding-right: 10%;" href ="admin/midwiferegistration.jsp">Midwife</a>
						</th>
						</tr>
						</table>
						</div>
					</div>
					
					<div id="vaccine" class="services">
						<button><a><img id="vaccine" src="admin/images/services/clinic.png " alt="vaccinetriposha"></a></button>
						<h3 align="center" id="vaccine" >Vaccine</h3></br>
						<p id="vaccine" style="margin-left: 10%;">Add / Edit / Delete Vaccine</p>
					</div>
					
					<div id="epidemics" class="services">
						<button><a><img id="epidemics" src="admin/images/services/epidemics.png " alt="epidemics"></a></button>
						<h3 align="center" id="epidemics">Epidemics</h3></br>
						<p id="epidemics" style="margin-left: 10%;">Add / Edit / Delete Epidemics</p>
					</div>
					
					<div class="services" id="diseases">
						<button><a><img id="diseases" src="admin/images/services/diseases.png" alt="diseases"></a></button>
						<h3 align="center" id="diseases">Diseases</h3></br>
						<p id="diseases" style="margin-left: 5%;">Add / Edit / Delete Diseases</p>
					</div>
					
					</div>
					<div style="display: inline-block; width: 100%; float: left;">
					
					<div class="services" id="area" style="margin-left: 40.5%;">
						<button><a><img id="area" src="admin/images/services/location.png"area.png " alt="area"></a></button>
						<h3 id="area" align="center">Area</h3></br>
						<p id="area" style="margin-left: 3%;">Add / Edit / Delete Area</p>
					</div>
					
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