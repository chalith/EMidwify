<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<title>Clinic Schedule</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/clinicschedule.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/form.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="supervisor/js/createviewforall.js"></script>
<script src="supervisor/js/clinicschedule.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
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
			<div class="right_sidebar">
				<div class="online_userbar">
					<div id="onlineusers">
						
					</div>
				</div>
			
				<div class ="newsfeed clearfix">
					
					<h2>Latest News</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
				<form name="eventForm" method="post" action="scheduleclinic">
					<div class="form_content" style="border: solid;">
							<center>
								<h1>Schedule</h1>
							</center>
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:20%;">
									<label>Year</label>
								</div>
								<div style="float:left; width:70%;">
									<select id="scheduledyear" style="width: 80%;">
										<option selected value="">Year</option>
										<%	
											for(int i=1900;i<=Integer.parseInt(cDate.substring(0,4));i++){
												out.print("<option>"+i+"</option>");
											}
										%>	
									</select>
								</div>
							</div>
							<div style="width:50%; float: left;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Area Code</label>
								</div>
								<div style="float:left; width:70%;">
									<select name="scheduledarea" id="scheduledarea" style="width: 80%;">
										<option selected value="">Area</option>
										<%
											Areas a = new Areas();
											ArrayList<String[]> areaArr = null;
											areaArr = a.getAreas();
											String areas = "";
											for(int i=0;i<areaArr.size();i++){
												areas = areas+"<option value="+areaArr.get(i)[0]+">("+areaArr.get(i)[0]+") "+areaArr.get(i)[1]+"</option>";
											}
											out.print(areas);
										%>
									</select>
								</div>
							</div>
						</div>
						</br>
						<table id="scheduledclinictbl" Style="width:100%;">
							<tr class="thead" id="thead">
								<th id="thead">Date</th>
								<th id="thead">Venue</th>
								<th id="thead">Time</th>
							</tr>
						</table>
					</div>
					<input type="hidden" id="evnts" name="txtevnts"/>
					<div class="form_content">
					<div class="form_content" style="padding-top:4%; padding-bottom: 5%;">
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:20%;">
									<label>Date</label>
								</div>
								<div id="date_eventdate" style="float:left; width:70%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="eventdate" name="txteventdate"/>
							</div>
							<div style="width:50%; float: left;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Area Code</label>
								</div>
								<div style="float:left; width:70%;">
									<select name="area1" id="area1" style="width: 80%;">
										<option selected disabled option value="">Area</option>
										<%
											out.print(areas);
										%>
									</select>
								</div>
							</div>
						</div>
						</br>
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:20%;">
									<label>Time</label>
								</div>
								<div id="time_eventtime" style="float:left; width:70%;">
									<jsp:include page="/time.jsp" />
								</div>
								<input type="hidden" id="eventtime" name="txteventtime"/>
							</div>
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Venue</label>
								</div>
								<div style="float:left; width:70%;">
									<input style="width:80%;" type="text" id="eventvenue" placeholder="Venue" name="txteventvenue">
								</div>
							</div>
						</div></br>
						<div style="margin: 5% 2% 8% 39%;">
							<div class="btn" style="float:left;" onclick="addEvent()">Add</div>
							<div class="btn" style="float:left;" onclick="removefromTable(eventtable)">Remove</div>
						</div>
						
						<table id="eventtable" Style="width:100%;">
							<tr class="thead" id="thead">
								<th id="thead">Date</th>
								<th id="thead">Area</th>
								<th id="thead">Venue</th>
								<th id="thead">Time</th>
							</tr>
						</table>
					</div>
					</div>
					<div class="form_content" style="float:right;">
						<button type="button" id="myForm" onclick="doSubmit()">Create</button>
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