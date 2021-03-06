<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.ArrayList"%>
<%@page import="com.main.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Clinic - Babies</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/clinic.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
</head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script src="midwife/js/childclinic.js"></script>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			out.print("<script>window.location=\"\";</script>");
			return;
		}
	%>
	
<div>
	<div class="container">
		<div style="width:75%; height:10%; position:relative;">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="backbody">
			<div class="right_sidebar">
				<div class="online_userbar">
					<select name="area" id="area">
						<%
							Areas a = new Areas(mid);
							ArrayList<String[]> areaArr = null;
							areaArr = a.getAreas();
							String areas = "";
							for(int i=0;i<areaArr.size();i++){
								areas = areas+"<option value="+areaArr.get(i)[0]+">("+areaArr.get(i)[0]+") "+areaArr.get(i)[1]+"</option>";
							}
							out.print(areas);
						%>
					</select>
					<div id="onlineusers">
						
					</div>
				</div>
				<div class ="newsfeed clearfix">
					
					<h2>Notifications</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
				<div class="titlebar" id="titlebar">
					<div class="title" id="upcomingtab"><center><h1>Upcoming Clinics</h1></center></div>
					<div class="title" id="pasttab"><center><h1>Past Clinics</h1></center></div>
				</div>
					
				<div class="form" id="upcoming">
					<div class="form_content" style="border-bottom:solid;">
						<center><h1>Clinics to come</h1></center>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							<label>Area</label>
							<select id="clinicareas" style="font-size: 80%; width:53%; background:#34495E; color: white; float: right; margin-right: 20%;">
								<option selected disabled option>Area</option>
								<%
									out.print(areas);
								%>
							</select>
						</div>
						<div style="float:left; width:50%;">
							<label>Scheduled Date</label>
							<select id="clinicdates" style="font-size: 80%; width:53%; background:#34495E; color: white; float: right;margin-right: 5%;">
								<option selected disabled option>Clinic Dates</option>
								
							</select>
						</div></br>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							<label>Venue</label>
							<input id="venue" style=" font-size: 80%; width:53%; float:right; background:#E6E6E6; margin-right: 20% " type="text" id="venue" placeholder="Venue" name="txtvenue" /readonly>
						</div>
						<div style="float:left; width:50%;">
							<label>Time</label>
							<input id="time" style=" font-size: 80%; width:53%; float:right; background:#E6E6E6;margin-right: 5%; " type="text" id="time" placeholder="Time" name="txttime" /readonly>
						</div></br>
					</div>
					<div class="form_content">
						<table id="chlidTable" Style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Age</th>
								<th>Vaccine</th>
								<th>Amount(ml)</th>
							</tr>
						</table>
					</div>
					<div class="form_content">
						<table id="triposhaTable" Style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Triposha Amount</th>
							</tr>
						</table>
					</div>
				</div>
				<div class="form" style="display:none;" id="past">
					<div class="form_content" style="border-bottom:solid;">
						<center><h1>Who didn't attend for the clinic</h1></center>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							<label>Area</label>
							<select id="pastclinicareas" style="font-size: 80%; width:53%; background:#34495E; color: white; float: right; margin-right: 20%;">
								<option selected disabled option>Area</option>
								<%
									out.print(areas);
								%>
							</select>
						</div>
						<div style="float:left; width:50%;">
							<label>Scheduled Date</label>
							<select id="pastclinicdates" style="font-size: 80%; width:53%; background:#34495E; color: white; float: right;margin-right: 5%;">
								<option selected disabled option>Clinic Dates</option>
								
							</select>
						</div></br>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							<label>Venue</label>
							<input id="pastvenue" style=" font-size: 80%; width:53%; float:right; background:#E6E6E6; margin-right: 20% " type="text" placeholder="Venue" name="txtvenue" /readonly>
						</div>
						<div style="float:left; width:50%;">
							<label>Time</label>
							<input id="pasttime" style=" font-size: 80%; width:53%; float:right; background:#E6E6E6;margin-right: 5%; " type="text" placeholder="Time" name="txttime" /readonly>
						</div></br>
					</div>
					<div class="form_content" id="count">
						
					</div>
					<div class="form_content">
						<table id="pastChildTable" style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Age</th>
								<th>Vaccine</th>
								<th>Amount(ml)</th>
							</tr>
						</table>
					</div>
					<div class="form_content">
						<table id="pastTriposhaTable" style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Triposha Amount</th>
							</tr>
						</table>
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
</div>
</body>

</html>