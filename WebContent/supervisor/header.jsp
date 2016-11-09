<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.main.JDBC"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="supervisor/js/header.js"></script>
</head>
<body>
	<%	
		String mid = (String) session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
<div class="header">
	<table style="width: 100%; height: 100%;">
		<tr>
			<th id="picture" style="width: 5%; box-shadow: 1px -2px 2px -0.5px black;">
				<h1 id="changepic" style="font-size: 100%; margin-left:10%; margin-bottom:-68%; width:5%; z-index:20; color: white; opacity:0.7; display: none;">
				Change Picture</h1>
				<img style="width: 96%; height: -5%;" src=<%
				JDBC jdbc = new JDBC();
				String q = "SELECT name,supervisorPicture FROM supervisor WHERE supervisorID = '"+mid+"';";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
				String supervisorpicture = "";
				String uname = "";
				String utype = "";
				while(rs.next()){
					supervisorpicture = rs.getString("supervisorPicture");
					uname = rs.getString("name");
				}
				utype = "Supervisor";
				try{
					jdbc.conn.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				if(supervisorpicture != null){
					out.print(supervisorpicture);
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
			<th style="width: 50%; color: #796CCA;">
				<h3 id="title">
					
				</h3>
			</th>
			<th id="home" style="width: 10%; color:gray; font-size: 100%;">
				<a id="home">Home</a>
			</th>
			<th id="logout" style="width: 10%; color:gray; font-size: 100%;">
				<a id="logout">Log Out</a>
			</th>
		</tr>
	</table>
</div>
<jsp:include page="/pictureupload.jsp" />
</body>
</html>