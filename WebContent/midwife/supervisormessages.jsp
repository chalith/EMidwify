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
<title>Messages - Supervisor</title>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/supervisormessages.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/supervisormessages.js"></script>
<script src="midwife/js/createviewforall.js"></script>
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
			<input type="hidden" id="supervisorid" value="<%
				JDBC jdbc = new JDBC();
				String sid = null;
				try{
					String q = "SELECT supervisorID from midwife WHERE midwifeID = '"+mid+"';";
					Statement st=jdbc.conn.createStatement();
					st.executeQuery(q);
					ResultSet rs = st.getResultSet();
					while (rs.next()) {
						sid = rs.getString("supervisorID");
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
					<div id="sendername" class="sendername" style="font-size: 80%; margin-left: 20%;"></div>
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