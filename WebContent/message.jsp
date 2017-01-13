<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/message.css"/>
<script src="js/message.js"></script>
</head>
<body>
	<div class="msgheader" id="msgheader">
		<a id="msgnotify" style="float:right; font-size: 150%; width:40%; text-align:center; background-color:red; color: white; border-radius:50%; position: absolute;">
		</a>
		<img id="msgheader" src="images/services/msg.png" alt="Message" />
	</div>
	<div class="msgcontent" style="border-radius:0%;" id="msgcontent">
		<center><p style="font-size: 110%; color: white;">
		<%	
			String name = (String) request.getAttribute("name");
			if(name!=null){
				out.print(name);
			}
		%>
		</p></center>
		<div class="msgs" id="msgs">
			
		</div>
		<div style="margin-top:2%; height: 21%; width: 100%;">
			<textarea id="message" rows="3";></textarea>
			<button id="sendmessage">Send</button>
		</div>
	</div>
</body>
</html>