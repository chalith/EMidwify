<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.main.JDBC"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="midwife/js/header.js"></script>
</head>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
<div class="header">
	<table style="float:left; width: 30%; height: 100%;">
		<tr>
			<th id="picture" style="width: 5%; box-shadow: 1px -2px 2px -0.5px black;">
				<h1 id="changepic" style="font-size: 100%; margin-left:10%; margin-bottom:-68%; width:5%; z-index:20; color: white; opacity:0.7; display: none;">
				Change Picture</h1>
				<img style="width: 96%; height: -5%;" src=<%
				JDBC jdbc = new JDBC();
				String midwifepicture = "";
				String uname = "";
				String utype = "";
				try{
					String q = "SELECT name,midwifePicture FROM midwife WHERE midwifeID = '"+mid+"';";
					jdbc.st.executeQuery(q);
					ResultSet rs = jdbc.st.getResultSet();
					while(rs.next()){
						midwifepicture = rs.getString("midwifePicture");
						uname = rs.getString("name");
					}
					utype = "Midwife";
				}catch(Exception e){
					e.printStackTrace();
				}
				finally{
					try{
						jdbc.conn.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(midwifepicture != null){
					out.print(midwifepicture);
				}
				%> alt="midwife">
			</th>
			<th id="profile" style="width: 25%; color:white; padding: 0 1% 0 1%; font-size: 70%; box-shadow: 1px -2px 2px -0.5px black;">
				<h2 id="profile" style="float: left;"><% 
				if(uname != null){
					out.print(uname);
				}
				%></h2>
				<h2 id="profile" style="float: right;"><% 
				if(utype != null){
					out.print("("+utype+")");
				}
				%></h2>
			</th>
		</tr>
	</table>
	<div style="margin: 1% 1% 1% 1%; float:left; width: 45%; color: #796CCA;">
		<div id="searchboxshow" style="border-radius:5%; font-size:120%; border: solid; width:5%; float:left; color: white;">
			&#128270
		</div>
		<div id="searchbox" style="border: solid white; padding-right:1.5%; border-radius:1%; display:none; width:90%; float:left; color: black;">
			<jsp:include page="/search.jsp" />
		</div>
	</div>		
	<table style="float:right; width: 20%; height: 100%;">
		<tr style="width: 100%;">
			<th id="home" style="padding: 6% 0 5% 0; width: 10%; color:gray; font-size: 100%;">
				<a id="home">Home</a>
			</th>
			<th id="logout" style="padding: 6% 0 5% 0; width: 10%; color:gray; font-size: 100%;">
				<a id="logout">Log Out</a>
			</th>
		</tr>
	</table>
</div>
<jsp:include page="/pictureupload.jsp" />
</body>
</html>