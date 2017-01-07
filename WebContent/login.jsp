<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/login.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="js/login.js"></script>
<script src="js/alert.js"></script>
<title>Insert title here</title>
<style>
</style>
</head>
<body>
	<div id="login" class="login">
		<a id="loginclose" class="loginclose">X</a>
		<form name="myForm" action="login" method="post">
			<table>
				<tr></tr>
				<tr>
					<td style="width: 45%;">
						<label>User Name</label>
					</td>
					<td style="width: 55%;">
						<input type="text" id="username" placeholder="User Name" name="username">
					</td>
				</tr>
			</table>
			</br>
			<table>
				<tr>
					<td style="width: 45%;">
						<label>Password</label>
					</td>
					<td style="width: 55%;">
						<input type="password" id="password" placeholder="Password" name="password">
					</td>
				</tr>
			</table>
			</br>
			<button type="button" onclick="doSubmit()">Log In</button></br>
		</form>
	</div>
</body>
</html>