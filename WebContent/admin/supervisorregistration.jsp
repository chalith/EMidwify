<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Register - Supervisors</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="admin/css/registration.css">
<link rel="stylesheet" type="text/css" href="admin/css/form.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="admin/js/supervisorregistration.js"></script>
<script src="admin/js/createviewforall.js"></script>
<script>
$(document).ready(function(){
	 $('#nonic').on('change',function(){
		$('#supervisorid').css('display','none');
		$('#supervisornic').css('display','block');
		$('#supervisorid').attr('id','tempnic');
		$('#supervisornic').attr('id','supervisorid');
		$('#tempnic').attr('id','supervisornic');
		$('#supervisorid').attr('name','txtsupervisorid');
		$('#supervisornic').attr('name','txtsupervisornic');
	 });
 });
</script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			out.print("<script>window.location=\"\";</script>");
			return;
		}
		String cDate = (String) session.getAttribute("date");
	%>
<jsp:include page="/alert.jsp" />
<jsp:include page="/error.jsp" />

<div class="container">
	<div style="width:75%; height:10%; position:relative;">
		<jsp:include page="header.jsp"/>
	</div>
	<div class="backbody" id="container" >
		<div class="body">
			<form action="supervisorregistration" method="post" name="supervisorForm">
				<div class="form_content">
				<div class="form_content" style="border:solid; padding-top:4%; padding-bottom: 5%;">
					<div style="float:left; width:50%;">
						<div style="float:left; width:30%;">
							<label>Midwife NIC/ID</label>
						</div>
						<div style="float:left; width:70%;">
							<input style="width:60%;" placeholder="SupervisorNIC" type="text" id="supervisorid" name="txtsupervisorid">
							<input style="width:60%; background:#E6E6E6; display:none;" type="text" id="supervisornic" name="txtsupervisornic"
								value="<%
								Main m = new Main();
								String id = m.generateID("supervisor","supervisor");
								if(id!=null){
									out.println(id);
								}
								%>" readonly/>
						</div>
						<input style="margin-left: 35%;" type="checkbox" id="nonic" name="txtnonic"> Don't Have NIC</br>
					</div>
					<div style="float:left; width:50%;">
						<div style="float:left; width:20%;">
							<label>Full Name</label>
						</div>
						<div style="float:left; width:80%;">
							<input style="width:100%;" type="text" id="fullname" placeholder="Fullname" name="txtfullname">
						</div>
					</div></br>
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Date of Birth</label>
					</div>
					<div style="float:left; width:80%;" id="date_dateofbirth">
						<jsp:include page="/date.jsp" />
					</div>
					<input type="hidden" id="dateofbirth" name="txtdateofbirth">
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Date of Work Started</label>
					</div>
					<div style="float:left; width:80%;" id="date_dateofstart">
						<jsp:include page="/date.jsp" />
					</div>
					<input type="hidden" id="dateofstart" name="txtdateofstart">
				</div></br>
				<div class="form_content" style="margin-bottom: 8%;">
					<div style="float:left; width: 20%;">
						<label>Address</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%; type="text" id="addressline1" placeholder="Line1" name="txtaddressline1"></br>
						<input style="width:40%; type="text" id="addressline2" placeholder="Line2" name="txtaddressline2"></br>
						<input style="width:40%; type="text" id="addressline3" placeholder="Line3" name="txtaddressline3"></br>
						<input style="width:40%; type="text" id="addressline4" placeholder="Line4" name="txtaddressline4">
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 4.5%;">
					<div style="float:left; width: 20%;">
						<label>Telephone Number</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="text" id="tpnumber1" placeholder="Telephone1" name="txttpnumber1" maxlength="10"></br>
						<input style="width:40%;" type="text" id="tpnumber2" placeholder="Telephone2" name="txttpnumber2" maxlength="10"></br>
						<input style="width:40%;" type="text" id="tpnumber3" placeholder="Telephone3" name="txttpnumber3" maxlength="10">
					</div>
				</div></br>
				<div class="form_content">
					<div style="float:left; width: 20%;">
						<label>Email</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="email" id="email" placeholder="Email" name="txtemail">
					</div>
				</div></br>
				</div>
				<div class="form_content" style="float:right;">
					<button type="button" onclick="doSubmit()" id="myform">Register</button>
				</div>
			</form>
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
<div>
<%
	String alert = (String) request.getAttribute("finalAlert");
	if(alert != null){
		out.print(alert);
	}
%>
<script>
	$('body').click(function(){
		$('#alert').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
</script>
</div>
</body>
</html>