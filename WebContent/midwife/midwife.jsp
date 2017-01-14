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
<link rel="stylesheet" type="text/css" href="midwife/css/midwife.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script src="midwife/js/midwife.js"></script>
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
			<div style="float:left; width:80%;">					
				<div class="details" style="display:block;">
<<<<<<< HEAD
					<div class="midwifedetails" style="padding-top:1%; margin:3% 3% 0 3%; border:2px solid;">
						<img style="width:15%; border:solid; border-radius:10%; margin: 2% 0 -2% 0%;" src="<%
=======
					<div class="midwifedetails" style="height:8%;padding-top:1%; margin:3% 3% 0 3%; border:2px solid;">
						<img style="width:10%; border:solid; border-radius:6%; margin: 1% 0% 1% 0%;" src="<%
>>>>>>> 79eced1e358d02e19391b6d54c45a7d0a3376c90
						JDBC jdbc = null;
						String fullname = null;
						String address = null;
						String areacode = "";
						String area = "";
						String dateofbirth = null;
						String age = null;
						String pic = null;
						String dateofstart = null;
						String experience = null;
						String mcount = null;
						String ccount = null;
						ArrayList<String> mobileNumbers=new ArrayList<String>();
						
						try{
							jdbc = new JDBC();
							String q="SELECT * FROM midwife WHERE midwifeID = '"+mid+"';";
							Statement st=jdbc.conn.createStatement();
							st.executeQuery(q);
							ResultSet rs = st.getResultSet();
							while(rs.next()){
								fullname = (String)rs.getString("name");
								address = (String)rs.getString("address");
								dateofbirth = (String)rs.getString("dateOfBirth");
								dateofstart = (String)rs.getString("startedDate");
								pic = (String)rs.getString("midwifePicture");
								
							}
							q="SELECT mobileNumber FROM midwifemobilenumber WHERE midwifeID = '"+mid+"';";
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
					    	Date wDate = FormatDate.createDate(dateofstart);
					    	experience = wDate.getAge(currentDate);
					    	
					    	q="SELECT areaCode,area FROM area WHERE midwifeID = '"+mid+"';";
					    	st.executeQuery(q);
							ResultSet rs2 = st.getResultSet();
							while(rs2.next()){
								if(area.equals("")){
									areacode = (String)rs2.getString("areaCode");
									area = (String)rs2.getString("area");
								}
								else{
									areacode = areacode + " , " + (String)rs2.getString("areaCode");
									area = area + " , " + (String)rs2.getString("area");
								}
								
							}
							q="SELECT COUNT(guardianID) FROM guardian WHERE guardianID IN (SELECT guardianID FROM mother) && guardianAreaCode IN (SELECT areaCode FROM area WHERE midwifeID = '"+mid+"');";
							st.executeQuery(q);
							rs2 = st.getResultSet();
							while(rs2.next()){
								mcount = (String)rs2.getString(1);
							}
							
							String q1="SELECT COUNT(childID) FROM child WHERE guardianID IN (SELECT guardianID FROM guardian WHERE guardianAreaCode IN (SELECT areaCode FROM area WHERE midwifeID = '"+mid+"'));";
							st.executeQuery(q1);
							rs2 = st.getResultSet();
							while(rs2.next()){
								ccount = (String)rs2.getString(1);
							}
							
						}catch(Exception e){
							System.out.println(e);
						}finally{
				        	try {
								jdbc.conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
				        }
						out.print(pic);
						%>" alt="user_photo">
<<<<<<< HEAD
						<h1 style="width:50%; float: right;"><%	
=======
						<h2 style="width:60%; font-size:120%;float:right; margin-right: 20%; margin-top: 4%;"><%	
>>>>>>> 79eced1e358d02e19391b6d54c45a7d0a3376c90
						if(fullname != null){
							out.print(fullname);
						}
						%>
						</h1>
					</div>
					<div class="titlebar" id="titlebar">
						<div class="title" id="editinfotab"><center><h1>Edit Info</h1></center></div>
					</div>
					<div style="display: inline-block; margin-top: 0; width: 84%;" class="midwifedetails" id="details">
						<div  style="font-size: 120%; float:left; width:70%; color:#040C23;">
							<ul>
								<%
								
								if(areacode!=null){
									out.print("<li>Area :-  "+areacode+"</li></br>");
								}
								if(area!=null){
									out.print("<li>Area :-  "+area+"</li></br>");
								}
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
								if(dateofbirth!=null){
									out.print("<li>Date of birth :-  "+dateofbirth+"</li></br>");
								}
								if(age!=null){
									out.print("<li>Age :-  "+age+"</li></br>");
								}
								if(experience!=null){
									out.print("<li>Work Experience :-  "+experience+"</li></br>");
								}
								%>
							</ul>
						</div>
						<div class="members">
							<table style="width: 100%;">
								<tr>
									<td><center><h3>I have <%
											if(mcount.equals("1")){
												out.print(mcount+" mother");
											}
											else{
												out.print(mcount+" mothers");	
											}
										%></h3> 
										<h3> to take care of</h3></center>
									</td>
								</tr>
								<tr>
									<td><center><h3>I have <%
											if(ccount.equals("1")){
												out.print(ccount+" baby");
											}
											else{
												out.print(ccount+" babies");	
											}
										%></h3> 
										<h3> to take care of</h3></center>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
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
					
					<h2>Latest News</h2>
						
					<ul id="notifications">
						
					</ul>
					
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