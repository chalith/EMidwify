<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.main.JDBC"%>
<%@page import="java.sql.*"%>
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
			out.print("<script>window.location=\"\";</script>");
		}
		JDBC jdbc = new JDBC();
		String supervisorpicture = null;
		String uname = null;
		String utype = null;
		try{
			String q = "SELECT name,supervisorPicture FROM supervisor WHERE supervisorID = '"+mid+"';";
			Statement st = jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				supervisorpicture = rs.getString("supervisorPicture");
				uname = rs.getString("name");
			}
			utype = "Supervisor";
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
		if((supervisorpicture == null)||(uname == null)||(utype == null)){
			out.print("<script>window.location=\"\";</script>");
		}
	%>
<div class="header">
	<table style="float:left; width: 25%;">
		<tr>
			<th id="userpicture" style="width: 25%; box-shadow:0px -2px 1px 0px white;">
				<h1 id="changepic" style="font-size: 100%; margin-left:5%; margin-bottom:-59%; z-index:20; color: white; opacity:0.7; display: none;">
				Change Picture</h1>
				<div style="overflow:hidden; max-height:65px; min-height:65px;"><img style="width: 90%; height: -5%;" src=<%
				out.print(supervisorpicture);
				%> alt="supervisor"></div>
			</th>
			<th id="profile" style="width: 75%; color:white; padding: 0 1% 0 1%; font-size: 60%; box-shadow:0px -2px 1px 0px;">
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
	<table style="float:right; width: 20%; height: 100%;">
		<tr style="width: 100%;">
			<th id="home" style="padding: 6% 0 5% 0; width: 10%; color:gray; font-size: 100%;">
				<a style="padding: 5px;" id="home">Home</a>
			</th>
			<th id="logout" style="padding: 6% 0 5% 0; width: 10%; color:gray; font-size: 100%;">
				<a style="padding: 5px;" id="logout">Log Out</a>
			</th>
		</tr>
	</table>
	<div style="margin: 1% 1% 1% 1%; float:right; width: 30%; color: #796CCA;">
		<div id="searchboxshow" style="border-radius:5%; font-size:120%; margin-left:2%; padding:2.5px; float:right; color: white;">
			<img style="width: 80%;" id="searchboxshow" src="midwife/images/services/search.png">
		</div>
		<div id="searchbox" style="box-shadow:0.2px 0.2px 1px 1px inset; border-radius:1%; width:80%; float:right; color: black;">
			<jsp:include page="/search.jsp" />
		</div>
	</div>		
	
</div>
<jsp:include page="/pictureupload.jsp" />
</body>
</html>