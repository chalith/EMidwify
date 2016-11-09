<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.main.Event"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/notification.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="js/notification.js"></script>
</head>
<body>
<div class="notification" id="notification">
	<h1 id="closenotification" class="close">X</h1>
	<div id="notificationcontent">
	</div>
</div>
</body>
</html>