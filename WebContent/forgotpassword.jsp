<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css" href="css/forgotpword.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="js/forgotpword.js"></script>
<script>
	
function validatePassword(password) {
	if(password.length<6){
		alert("Password length must be grater than 5");
		return false;
	}
	var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
	if(password.match(passw)){
		return true;
	}
	else{
		alert("Password must be combination of upper case lower case and digit");
		return false;
	}
}
function doResetSubmit() {
	var password = document.getElementById("resetpassword");
	var confirmpassword = document.getElementById("resetrepassword");
	if(password.value == ""){
		showalert("Please enter a password");
		setError(password);
	}
	else if (!validatePassword(password.value)){
		setError(password);
	}
	else if(confirmpassword.value == ""){
		showalert("Please enter the password again");
		setError(confirmpassword);
	}
	else if (password.value != confirmpassword.value) {
		setError(confirmpassword);
		setError(password);
		showalert("Two passwords must be equal");
	}
	else{
		document.forms["myResetForm"].submit();
		document.forms["myResetForm"].reset();
	}
}
</script>
<title>Forgot Password</title>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid!=null){
			out.print("<script>close.window(); open.window(\"/\",\"_self\"); </script>");
		}
	
	%>
	<div id="reset" class="reset">
		<a id="resetclose" class="resetclose">X</a>
		
		<form name="myResetForm" action="resetpassword">
			<table>
				<tr>
					<td style="width: 45%;">
						<label>Enter The Username</label>
					</td>
					<td style="width: 55%;">
						<input type="text" id="resetusername" placeholder="User Name" name="resetusername">
					</td>
				</tr>
			</table>
			<button type="button" onclick="sendCode()">Send Code</button>
			<table></br>
				<tr>
					<td style="width: 55%;">
						<label>Enter The Code You Received</label>
					</td>
					<td style="width: 45%;">
						<input type="text" id="code" placeholder="Code" name="code">
					</td>
				</tr>
			</table>
			<button type="button" onclick="resetPword()">Submit</button>
			</br></br>
			<div id="resetpword" class="resetpword">
				<table>
					<tr>
						<td style="width: 45%;">
							<label>Password</label>
						</td>
						<td style="width: 55%;">
							<input type="password" id="resetpassword" placeholder="Password" name="resetpassword">
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td style="width: 45%;">
							<label>Confirm Password</label>
						</td>
						<td style="width: 55%;">
							<input type="password" id="resetrepassword" placeholder="Password" name="resetrepassword">
						</td>
					</tr>
				</table>
				<button type="button" onclick="doResetSubmit()">Reset</button>
			</div>
		</form>
	</div>
</body>
</html>