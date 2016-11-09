<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/alert.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/alert.js"></script>

</head>
<body>
	<div id="alert" class="alert_div">
		<h1 id="close" class="close">&#10006</h1>
		<center><h1 id="warning" class="h11">please fill in the data fields</h1></center>
		<center><h1 id="icon" class="h12">!</h1></center>
	</div>
</body>
</html>