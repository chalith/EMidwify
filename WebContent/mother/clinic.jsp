<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.main.Date"%>
<%@page import="com.mother.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Clinic</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/timeline.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="mother/js/createviewforall.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("/EMidwify");
		}
	%>
<div>
	<jsp:include page="/notification.jsp" />
	
	<div class="container">
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="body">
			<div style="float:left; width:80%; overflow:hidden; background-color:#E2E4F2;">
				<div class="timeline">
					<center><div style="background-color: white; width: 99%; float: left; border-radius:10%; border: solid #3C405B;">
						<h2>Clinics</h2>
					</div></center>
					<table>
					<%	
						JDBC jdbc = new JDBC();
						ArrayList<Clinic> clinics = new ArrayList<Clinic>();
						ArrayList<Notification> motherclinic= new ArrayList<Notification>();
						ArrayList<Notification> notifications = null;
						try{
							String q1 = "SELECT guardianAreaCode FROM guardian WHERE guardianID = '"+mid+"'";
							jdbc.st.execute(q1);
							ResultSet rs1 =  jdbc.st.getResultSet();
							while(rs1.next()){
								String area = rs1.getString("guardianAreaCode");
								ClinicDates clinicdates = new ClinicDates(area);
								for(int i=0;i<clinicdates.clinicDates.size();i++){
									clinics.add(new Clinic(area, clinicdates.clinicDates.get(i)[0], clinicdates.clinicDates.get(i)[1], clinicdates.clinicDates.get(i)[2]));
								}
							}
							for(int i=0;i<clinics.size();i++){
								String title = "Clinic";
								String id = clinics.get(i).areaCode+" <> "+clinics.get(i).clinicDate;
								String area = null;
								try{
									String q = "SELECT area FROM area WHERE areaCode = '"+clinics.get(i).areaCode+"';";
									jdbc.st.execute(q);
									ResultSet rs = jdbc.st.getResultSet();
									while(rs.next()){
										area = rs.getString("area");
									}
								}catch(Exception e){
									e.printStackTrace();
								}
								String ndate = clinics.get(i).clinicDate;
								String description = "Area - "+area;
								Notification notification = new Notification(title, id, ndate, description);
								motherclinic.add(notification);
							}
							motherclinic = DateSort.sort(motherclinic);
						}catch(Exception e){
							try {
								jdbc.conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						String clinicString = "";
						for(int i=0;i<motherclinic.size();i++){
							clinicString = clinicString + "<tr id=\""+motherclinic.get(i).id+"\"><td id=\""+motherclinic.get(i).id+"\">"+motherclinic.get(i).title+"</td><td id=\""+motherclinic.get(i).id+"\">"+motherclinic.get(i).date+"</td><td id=\""+motherclinic.get(i).id+"\">"+motherclinic.get(i).description+"</td></tr>";
						}
						out.print(clinicString);
					%>
					</table>
				</div>
			</div>
			<div class="right_sidebar">
				<div class ="newsfeed clearfix" style="height: 100%;">
					
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