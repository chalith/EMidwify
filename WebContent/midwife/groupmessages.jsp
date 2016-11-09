<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css" href="midwife/css/groupmessages.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/groupmessages.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
	<div class="groupicon" id="groupicon"><center><img src="midwife/images/services/group.png" alt="group"/></center></div>
	<div class="grpcontainer" id="groupmessage">
		<h1 id="grpclose" class="grpclose">&#10006</h1>
		<div class="grpmothers">
			<select name="grpmotherarea" id="grpmotherarea">
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
			<div class="grpmotherbar" id="grpmotherbar">
				
			</div>
			<center><div class="grpbtn" id="add"><center><h1>Add >></h1></center></div></center>
		</div>
		<div class="grpmembers">
			<div class="grpmemberbar" id="grpmemberbar">

			</div>
			<center><div class="grpbtn" id="remove"><center><h1>Remove <a style="font-size: 80%;">&#10007</a></h1></center></div></center>
		</div>
		<div class="grpmsgcontent" id="grpmsgcontent">
			
			<div class="grpmsgs" id="grpmsgs">
				
			</div>
			<div style="margin-top:2%; height: 21%; width: 100%;">
				<textarea id="grpmessage" rows="2";></textarea>
				<button id="grpsendmessage">Send</button>
			</div>
		</div>
	</div>
</body>
</html>