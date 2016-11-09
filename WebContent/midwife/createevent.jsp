<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<title>Create Event</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/createevent.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<link rel="stylesheet" type="text/css" href="midwife/css/form.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script src="midwife/js/createevent.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
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
	<div class="container" id="container">
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
					
					<h2>Latest News</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
				<form name="eventForm" method="post" action="createevent">
					<input type="hidden" id="evnts" name="txtevnts"/>
					<div class="form_content">
					<div class="form_content" style="padding-top:4%; padding-bottom: 5%;">
						<center>
						<div class="form_content">
							<div style="width:50%; float: left;">
								<label>Event Name</label>
								<input style="width:50%;" type="text" id="name" placeholder="EventName" name="txtname"/>
							</div>
							<div style="width:50%; float: left;">
								<label>Area Code</label>
								<select name="area1" id="area1" style="width: 50%;">
									<option selected disabled option value="">Area</option>
									<%
										out.print(areas);
									%>
								</select>
							</div>
						</div>
						</center></br>
						<div class="form_content">
							<div style="float:left; width:33%;">
								<div style="float:left; width:20%;">
									<label>Date</label>
								</div>
								<div id="date_eventdate" style="float:left; width:70%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="eventdate" name="txteventdate"/>
							</div>
							<div style="float:left; width:33%;">
								<div style="float:left; width:20%;">
									<label>Time</label>
								</div>
								<div id="time_eventtime" style="float:left; width:80%;">
									<jsp:include page="/time.jsp" />
								</div>
								<input type="hidden" id="eventtime" name="txteventtime"/>
							</div>
							<div style="float:left; width:33%;">
								<div style="float:left; width:30%;">
									<label style="float:right; margin-right: 5%;">Venue</label>
								</div>
								<div style="float:left; width:70%;">
									<input style="width:100%;" type="text" id="eventvenue" placeholder="Venue" name="txteventvenue">
								</div>
							</div>
						</div></br>
						<div class="form_content" style="display: inline-block; width: 100%;">
							<div style="float:left; width: 50%; background-color: white;">
								<input style="width: 98.5%;" class="search" id="mothersearch" placeholder="Enter the mother name here  &#128270"></input>
								<div id="mothers" class="suggession">
									
								</div>
								<center><div style="display: inline-block; width: 40%;">
									<div class="selectbtn" id="add"><center><h1>Add >></h1></center></div>
									<div class="selectbtn" id="addall"><center><h1>Add all >></h1></center></div>
								</div></center>
							</div>
							<div style="float:left; width: 50%; background-color: white;">
								<div id="selectedmothers"  class="suggession">
					
								</div>
								<center><div style="display: inline-block; width: 50%;">
									<div class="selectbtn" id="remove"><center><h1>Remove <a style="font-size: 80%;">&#10007</a></h1></center></div>
									<div class="selectbtn" id="removeall"><center><h1>Remove all <a style="font-size: 80%;">&#10007</a></h1></center></div>
								</div></center>
							</div>
						</div>
						<div style="margin: 2% 2% 7% 43%;">
							<div class="btn" style="float:left;" onclick="addEvent()">Add</div>
						</div>
						<table id="eventtable" Style="width:100%;">
							<tr class="thead" id="thead">
								<th id="thead">Event Name</th>
								<th id="thead">Area</th>
								<th id="thead">Date</th>
								<th id="thead">Time</th>
								<th id="thead">Venue</th>
								<th id="thead">Guardians</th>
							</tr>
						</table>
					</div>
					</div>
					<div class="form_content" style="float:right;">
						<button type="button" id="myForm" onclick="doSubmit()">Create</button>
					</div>
				</form>
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