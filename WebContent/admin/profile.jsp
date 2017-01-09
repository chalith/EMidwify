<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.main.Date"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>My Profile</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="admin/css/profile.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="admin/js/createviewforall.js"></script>
<script src="admin/js/profile.js"></script>
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
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="body">
			<div style="float:left; width:100%;">					
				<div class="details" style="display:block;">
					<div class="admindetails" style="padding-top:1%; margin:3% 3% 0 3%; border:2px solid;">
						<img style="width:15%; border:solid; border-radius:10%; margin: 2% 0 -2% 0%;" src="<%
						JDBC jdbc = null;
						String fullname = null;
						String address = null;
						String email = null;
						String areacode = "";
						String area = "";
						String dateofbirth = null;
						String age = null;
						String pic = null;
						String dateofstart = null;
						String experience = null;
						String mcount = "";
						String scount = "";
						ArrayList<String> mobileNumbers=new ArrayList<String>();
						
						try{
							jdbc = new JDBC();
							String q="SELECT * FROM admin WHERE adminID = '"+mid+"';";
							Statement st=jdbc.conn.createStatement();
							st.executeQuery(q);
							ResultSet rs = st.getResultSet();
							while(rs.next()){
								fullname = (String)rs.getString("name");
								address = (String)rs.getString("address");
								email = (String)rs.getString("email");
								dateofbirth = (String)rs.getString("dateOfBirth");
								pic = (String)rs.getString("adminPicture");
								
							}
							q="SELECT mobileNumber FROM adminmobilenumber WHERE adminID = '"+mid+"';";
							st.executeQuery(q);
							rs = st.getResultSet();
							while(rs.next()){
								mobileNumbers.add((String)rs.getString("mobileNumber"));
							}
							
							DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
					    	java.util.Date dt = new java.util.Date();
					    	Date currentDate = FormatDate.createDate(frmt.format(dt));
					    	Date bDate = FormatDate.createDate(dateofbirth);
					    	age = bDate.getAge(currentDate);
					    	
					    	
					    	q="SELECT COUNT(midwifeID) FROM midwife";
							st.executeQuery(q);
							ResultSet rs2 = st.getResultSet();
							while(rs2.next()){
								mcount = (String)rs2.getString(1);
							}
							
							String q1="SELECT COUNT(supervisorID) FROM supervisor;";
							st.executeQuery(q1);
							rs2 = st.getResultSet();
							while(rs2.next()){
								scount = (String)rs2.getString(1);
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}finally{
				        	try {
								jdbc.conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
				        }
						out.print(pic);
						%>" alt="user_photo">
						<h1 style="width:50%; float: right;"><%	
						if(fullname != null){
							out.print(fullname);
						}
						%>
						</h1>
					</div>
					<div class="titlebar" id="titlebar">
						<div class="title" id="editinfotab"><center><h1>Edit Info</h1></center></div>
					</div>
					<div style="display: inline-block; margin-top: 0; width: 84%;" class="admindetails" id="details">
						<div  style="font-size: 120%; float:left; width:70%; color:#040C23;">
							<ul>
								<%
								
								if(mid!=null){
									out.print("<li>ID :-  "+mid+"</li></br>");
								}
								if(address!=null){
									out.print("<li>Address :-  "+address+"</li></br>");
								}
								if(mobileNumbers.size()!=0){
									out.print("<li>Mobile numbers :-  ");
									for(int i=0;i<mobileNumbers.size();i++){
										out.print("<li>"+mobileNumbers.get(i)+"</li></br>");
									}
									out.print("</li>");
								}
								if(email!=null){
									out.print("<li>Email Address :-  "+email+"</li></br>");
								}
								if(dateofbirth!=null){
									out.print("<li>Date of birth :-  "+dateofbirth+"</li></br>");
								}
								if(age!=null){
									out.print("<li>Age :-  "+age+"</li></br>");
								}
								%>
							</ul>
						</div>
						<div class="members">
							<table style="width: 100%;">
								<tr>
									<td><center><h3>There is <%
											if(mcount.equals("1")){
												out.print(mcount+" midwife");
											}
											else{
												out.print(mcount+" midwives");	
											}
										%></h3> 
										<h3> in this system</h3></center>
									</td>
								</tr>
								<tr>
									<td><center><h3>There is <%
											if(scount.equals("1")){
												out.print(scount+" supervisor");
											}
											else{
												out.print(scount+" supervisors");	
											}
										%></h3> 
										<h3> in this system</h3></center>
									</td>
								</tr>
							</table>
						</div>
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