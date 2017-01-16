<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>EditInfo - Midwife</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/registration.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/form.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="supervisor/js/editsupervisorinfo.js"></script>
<script src="supervisor/js/createviewforall.js"></script>
<script>
</script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			out.print("<script>window.location=\"\";</script>");
			return;
		}
		String cDate = (String) session.getAttribute("date");
	%>
<jsp:include page="/alert.jsp" />
<jsp:include page="/error.jsp" />

<div class="container">
	<div style="width:75%; height:10%; position:relative;">
		<jsp:include page="header.jsp"/>
	</div>
	<div class="backbody" id="container" >
		<div class="right_sidebar">
			<div class="online_userbar">
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
			<form action="supervisoreditinfo" method="post" name="midwifeeditForm">
				<div class="form_content">
				<div class="form_content" style="border:solid; padding-top:4%; padding-bottom: 5%;">
					<div style="float:left; width:10%;">	
						<img style="float:left; width: 85%; margin-top:-30%; margin-bottom: -5%;" id="motherpicture" src=
							"<%	
								String id = mid;
								String name = "";
								String dob = "";
								String address = "";
								ArrayList<String> tpnumbers = new ArrayList<String>();
								String email = "";
								String starteddate = "";
								String picture = "";
								JDBC jdbc = new JDBC();
								try{
									String q = "SELECT * FROM supervisor WHERE supervisorID = '"+id+"';";
									Statement st = jdbc.conn.createStatement();
									st.executeQuery(q);
									ResultSet rs = st.getResultSet();
									while(rs.next()){
										name = rs.getString(2).trim();
										dob = rs.getString(3).trim();
										address = rs.getString(4).trim();
										email = rs.getString(5).trim();
										starteddate = rs.getString(6).trim();
										picture = rs.getString(7).trim();
									}
									q = "SELECT mobileNumber FROM supervisormobilenumber WHERE supervisorID = '"+id+"';";
									st.executeQuery(q);
									rs = st.getResultSet();
									while(rs.next()){
										tpnumbers.add(rs.getString("mobileNumber").trim());
									}
								}catch(Exception e){
									e.printStackTrace();
								}finally{
									try{
										jdbc.conn.close();
									}catch(Exception e){
										e.printStackTrace();
									}
								}
								
								String midwifepicture = "";
								out.print(picture);
							%>"
						></br>
					</div>
					<div style="float:left; width:45%;">
						<div style="float:left; width:40%;">
							<label>Supervisor NIC/ID</label>
						</div>
						<div style="float:left; width:60%;">
							<input style="width:60%; background:#E6E6E6;" placeholder="MidwifeID" type="text" id="midwifeid" name="txtmidwifeid" value = 
								"<%	
									out.print(id);
								%>" 
							readonly/>
						</div>
					</div>
					<div style="float:left; width:45%;">
						<div style="float:left; width:20%;">
							<label>Full Name</label>
						</div>
						<div style="float:left; width:80%;">
							<input style="width:100%;" type="text" id="fullname" placeholder="Fullname" name="txtfullname" value="<%
									out.print(name);
								%>"/>
						</div>
					</div></br>
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Date of Birth</label>
					</div>
					<div style="float:left; width:80%;" id="date_dateofbirth">
						<input style="width:40%; background:#E6E6E6;" type="text" id="dateofbirth" name="txtdateofbirth" placeholder="DateOFBirth" value=
							"<%
								out.print(dob);
							%>"
						readonly/>
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 8%;">
					<div style="float:left; width: 20%;">
						<label>Address</label>
					</div>
					<div style="float:left; width: 80%;">
						<%
							String s[] = address.split("\n");
							for(int i=0;i<4;i++){
						%>
							<input style="width:40%; type="text" id="addressline<%out.print(i+1);%>" placeholder="Line<%out.print(i+1);%>" name="txtaddressline<%out.print(i+1);%>" value=
									"<%
										if(i<s.length){
											out.print(s[i].trim());
										}
										else{
											out.print("");
										}
									%>"
							></br>
						<% 
							} 
						%>
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 4.5%;">
					<div style="float:left; width: 20%;">
						<label>Telephone Number</label>
					</div>
					<div style="float:left; width: 80%;">
						<%
							for(int i=0;i<3;i++){
						%>
							<input style="width:40%;" type="text" id="tpnumber<%out.print(i+1);%>" placeholder="Telephone<%out.print(i+1);%>" name="txttpnumber<%out.print(i+1);%>" maxlength="10" value=
							"<%
								if(i<tpnumbers.size()){
									out.print(tpnumbers.get(i));
								}
								else{
									out.print("");
								}
							%>"></br>
						<%
							}
						%>
						
					</div>
				</div></br>
				<div class="form_content">
					<div style="float:left; width: 20%;">
						<label>Email</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="email" id="email" placeholder="Email" name="txtemail" value=
							"<%
								out.print(email);
							%>"
						>
					</div>
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Job experience from</label>
					</div>
					<div style="float:left; width:80%;" id="date_starteddate">
						<input style="width:40%; background:#E6E6E6;" type="text" id="starteddate" name="starteddate" placeholder="WorkStartedDate" value=
							"<%
								out.print(starteddate);
							%>"
						readonly/>
					</div>
				</div></br>
				</div>
				<div class="form_content" style="float:right;">
					<button type="button" onclick="doSubmit()" id="myform">Add Changes</button>
				</div>
			</form>
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
<div>
<%
	String alert = (String) request.getAttribute("finalAlert");
	if(alert != null){
		out.print(alert);
	}
	try{
		jdbc.conn.close();
	}
	catch(Exception e){
		e.printStackTrace();
	}
%>
<script>
	$('body').click(function(){
		$('#alert').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
</script>
</div>
</body>
</html>