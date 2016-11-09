<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Register - User</title>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/registeruser.css">
<script src="midwife/js/registeruser.js"></script>
</head>
<body>
	<div id="container">
	<%
		if(session.getAttribute("mid")==null){
			response.sendRedirect("index.jsp");
		}
	%>
	<form action="registeruser" method="post" name="myForm">
		<div class="form_content">
			<h1 style="color:#323B56; font-size: 250%;">Easy Midwify</h1>
			<table>
				<tr>
					<td>
						<label>User Name</label>
					</td>
					<td>
						<input background:#E6E6E6;" placeholder="UserName" name="txtusername" id="username" value="<% 
								String gid = (String) request.getAttribute("gid");
								if(gid != null){
									out.print(gid);
								}
							%>" readonly/>
					</td>
				</tr>
				<tr>
					<td>
						<label>Password</label>
					</td>
					<td>
						<input placeholder="Password" type="password" name="txtpassword" id="password"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>Confirm Password</label>
					</td>
					<td>
						<input placeholder="CofirmPassword" type="password" name="txtconfirmpassword" id="confirmpassword"/>
					</td>
				</tr>
			</table>
			<button class="btn" type="button" onclick="doSubmit()">Register</button>
		</div>
	</form>
	</div>
	<jsp:include page="/alert.jsp" />
	<jsp:include page="/error.jsp" />			
	<%
		String alert = (String) request.getAttribute("finalAlert");
		if(alert != null){
			out.print(alert);
		}
	%>
</body>
</html>