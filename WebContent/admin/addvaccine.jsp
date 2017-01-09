<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<title>Add Vaccine</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="admin/css/addvaccine.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<link rel="stylesheet" type="text/css" href="admin/css/form.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="admin/js/addvaccine.js"></script>
<script src="admin/js/createviewforall.js"></script>
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
<%
	String alert = (String) request.getAttribute("finalAlert");
	if(alert != null){
		out.print(alert);
	}
%>
<div>
<div class="container">
		<div style="width:75%; height:10%; position:relative;">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="backbody" id="container">
			<div class="body">
				<form name="vaccineForm" method="post" action="addvaccine">
					<div class="form_content" style="border: solid;">
						<center>
							<h1>Vaccines</h1>
						</center>
						<div class="area">
							<table id="vaccinetbl" Style="width:100%;">
								<tr class="thead" id="thead">
									<th id="thead">VaccineCode</th>
									<th id="thead">VaccineName</th>
									<th id="thead">Description</th>
								</tr>
							</table>
						</div>
					</div>
					<input type="hidden" id="vaccines" name="txtvaccines"/>
					<div class="form_content">
					<div class="form_content" style="padding-top:4%; padding-bottom: 5%;">
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">VaccineCode</label>
								</div>
								<div style="float:right; width:70%;">
									<input style="width:80%; background:#E6E6E6; " type="text" id="vaccinecode" placeholder="VaccineCode" name="txtvaccinecode"
									value="<%
										Main m = new Main();
										String id = m.generateCode("vaccine","v");
										if(id!=null){
											out.println(id);
										}
									%>" readonly="readonly"/>
								</div>
							</div>
							<div style="float:right; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">VaccineName</label>
								</div>
								<div style="float:left; width:70%;">
									<input style="width:80%;" type="text" id="vaccinename" placeholder="VaccineName" name="txtvaccinename">
								</div>
							</div>
							
						</div>
						<div class="form_content">
							<div style="width:80%; float: center;">
								<div style="float:left; width:20%;">
									<label style="float:left; margin-right: 5%;">Description</label>
								</div>
								<div style="float:left; width:80%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="description" placeholder="Description" name="txtdescription"></textarea></br>
								</div>
							</div>
						</div>
						</br>
						<div style="margin: 5% 2% 8% 39%;">
							<div class="btn" style="float:left;" onclick="addVaccineToAdd()">Add</div>
							<div class="btn" style="float:left;" onclick="removefromTable(vaccinetoaddtbl)">Remove</div>
						</div>
						<div class="area">
							<table id="vaccinetoaddtbl" Style="width:100%;">
								<tr class="thead" id="thead">
									<th id="thead">VaccineCode</th>
									<th id="thead">VaccineName</th>
									<th id="thead">Description</th>
								</tr>
							</table>
						</div>
					</div>
					</div>
					<div class="form_content" style="float:right;">
						<button type="button" id="myForm" onclick="doSubmit()">Add</button>
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
</div>
</body>

</html>