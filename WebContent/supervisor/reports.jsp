<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.ArrayList"%>
<%@page import="com.main.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Reports</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/reports.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
</head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="supervisor/js/createviewforall.js"></script>
<script src="supervisor/js/reports.js"></script>
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
					<div id="onlineusers">
						
					</div>
				</div>
				<div class ="newsfeed clearfix">
					
					<h2>Notification</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
			<form action="ReportServlet" >
			
				<div class="form" id="past">
					<div class="form_content" style="border-bottom:solid;">
						<center><h1>Past clinics</h1></center>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							<label>Area</label>
							<select id="clinicareas" name="area" style="margin-left:13%; font-size: 80%; width:50%; background:#34495E; color: white;">
								<option selected disabled option>Area</option>
								<%
									out.print(areas);
								%>
							</select>
						</div>
						<div style="float:left; width:50%;">
							<label>Scheduled Date</label>
							<select id="clinicdates" name="date" style="font-size: 80%; width:50%; background:#34495E; color: white;">
								<option selected disabled option>Clinic Dates</option>
								
							</select>
						</div></br>
					</div>
					<div class="form_content" style="font-size: 120%;">
						<div style="float:left; width:50%;">
							
							<input type="hidden" id="venue" style="font-size: 80%; width:60%; background:#E6E6E6;" type="text" placeholder="Venue" name="txtvenue" /readonly>
						</div>
						<div style="float:left; width:50%;">
							
							<input  type="hidden" id="time" style="margin-left:10%; font-size: 80%; width:60%; background:#E6E6E6;" type="text" placeholder="Time" name="txttime" /readonly>
						</div></br>
					</div>
					<div class="form_content" style="display: inline-block; width: 100%;">
						<div class="form_content" id="ccount" style="width: 45%; float: left;">
							
						</div>
						<div class="form_content" id="mcount" style="width: 45%; float: right;">
							
						</div>
					</div>
					<div class="form_content">
						<table id="childTable" style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Age</th>
								<th>Vaccine</th>
								<th>Amount(ml)</th>
							</tr>
						</table>
					</div>
					<div class="form_content">
						<table id="motherTable" style="width:100%; display:none;">
							<tr>
								<th>Child</th>
								<th>Age</th>
								<th>Vaccine</th>
								<th>Amount(ml)</th>
							</tr>
						</table>
					</div>
					<div class="form_content" style="display: inline-block; width: 100%;">
						<table id="childtriposhaTable" style="width:45%; float:left; display:none;">
							<tr>
								<th>Child</th>
								<th>Triposha Amount</th>
							</tr>
						</table>
						<table id="mothertriposhaTable" style="width:45%; float:right; display:none;">
							<tr>
								<th>Mother</th>
								<th>Triposha Amount</th>
							</tr>
						</table>
					</div>
				</div>
				
				
				<input style="float:right;margin-right: 4%; margin-bottom: 3%; background-color: #518187; height:20%;" type= "submit" value="Download as PDF">
				
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