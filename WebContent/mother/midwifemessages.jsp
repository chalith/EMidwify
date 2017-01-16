<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Messages - Midwife</title>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="mother/css/midwifemessages.css">
<link rel="stylesheet" type="text/css" href="mother/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="mother/js/midwifemessages.js"></script>
<script src="mother/js/createviewforall.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
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
				<div class ="newsfeed clearfix" style="height: 100%;">
					
					<h2>Latest News</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
			<input type="hidden" id="midwifeid" value="<%
				JDBC jdbc = new JDBC();
				String sid = null;
				String midwifeName = null;
				String midwifePicture = null;
				try{
					String q = "SELECT * from midwife WHERE midwifeID = (SELECT midwifeID FROM area WHERE areaCode = (SELECT guardianAreaCode FROM guardian WHERE guardianID = '"+mid+"'));";
					
					Statement st = jdbc.conn.createStatement();
					st.executeQuery(q);
					ResultSet rs = st.getResultSet();
					while (rs.next()) {
						sid = rs.getString("midwifeID");
						midwifeName = rs.getString("name");
						midwifePicture = rs.getString("midwifePicture");
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						jdbc.conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				out.print(sid);
			%>"/>
				<div class="msgcontent" id="msgcontent">
					<div id="sendername" class="sendername" style="font-size: 80%; margin-left: 20%;">
						<div id="receiverii" style="display: none;">
						<table style="bottom: 0;"><tr><td>
						<img style="width: 100%; float: left;" src="<% out.print(midwifePicture); %>"></td><td style="width: 95%;">
						<h3 style="float: left; font-size: 80%;"><% out.print(midwifeName); %>(Midwife)</h3>
						</td></tr></table>
						</div>
					</div>
					<div class="msgs" id="msgs">
					
					</div>
					<div style="margin-top:2%; height: 21%; width: 100%;">
						<textarea id="message" rows="2";></textarea>
						<button id="sendmessage">Send</button>
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
</div>

</body>
</html>