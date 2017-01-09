<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.midwife.viewchild.*"%>
<%@page import="com.main.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Baby</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/viewchild.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="supervisor/js/createviewforall.js"></script>
<script src="supervisor/js/viewchild.js"></script>
<script type="text/javascript" src="js/jscharts.js"></script>
<script>
$(window).load(function(){
	loadGraph();
});
function loadGraph(){
	var id = document.getElementById("childid").value;
	var xmlhttp = new XMLHttpRequest();
    var url="viewweights";
    var url=url+"?person=child&id="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var json = JSON.parse(xmlhttp.responseText);
	        var myData = new Array();
	        var k = Object.keys(json);
	        //alert(k.length);
			for(var i=0;i<k.length;i++){
				myData.push([i, parseFloat(json[k[i]].weight)]);
				//alert(json[k[i]].date+" "+json[k[i]].age+" "+json[k[i]].weight);
			}
	        //var myData = new Array([1, 20], [2, 10], [3, 30], [4, 10], [5, 5]);
	        if(k.length>1){
				var myChart = new JSChart('chartcontainer', 'line');
				myChart.setDataArray(myData);
				myChart.setAxisNameX("Date");
				myChart.setAxisNameY("Weight");
				myChart.setShowXValues(false);
				myChart.setIntervalStartY(0);
				myChart.setIntervalEndY(20);
				myChart.resize(800, 500);
				myChart.setAxisValuesNumberY(20);
				for(var i=0;i<k.length;i++){
					myChart.setLabelX([i, json[k[i]].age]);
					//alert(json[k[i]].date+" "+json[k[i]].age+" "+json[k[i]].weight);
				}
				myChart.draw();
		        //document.getElementById("notifications").innerHTML = news;
		
	       	}
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
</script>
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
					<div class="childdetails" style="padding-top:1%; margin:3% 3% 0 3%; border:2px solid;">
						<img style="width:15%; border:solid; border-radius:10%; margin: 2% 0 -2% 0%;" src="<%
							JDBC jdbc = new JDBC();
						Child child = new Child();;
						String childPic = "";
						MotherDeath motherdeath = null;
						String cid = request.getParameter("childid");
						String clinicdates = "";
						String areaCode = null;
						String age = null;
						String area = null;
						String mothername = null;
						String motherpic = null;
						String fathername = null;
						String fatherpic = null;
						try{
							String q1="SELECT * FROM child WHERE childID = '"+cid+"';";
							Statement st = jdbc.conn.createStatement();
							st.executeQuery(q1);
							ResultSet rs = st.getResultSet();
							while(rs.next()){
								//System.out.print((String)rs.getString("childName"));
								child.childID = cid;
								child.childName = (String)rs.getString("childName");
								child.dateofDelivery = (String)rs.getString("childDateofDelivery");
								child.birthWeight = (String)rs.getString("childBirthWeight");
								child.motherguardianID = (String)rs.getString("guardianID");
								childPic = (String)rs.getString("childPicture");
								String fatherID = (String)rs.getString("childFatherID");
								if(fatherID != null){
									child.father = new Father();
									child.father.fatherID = fatherID;
								}
							}
							String q2="SELECT DISTINCT(clinicDate) FROM childclinic WHERE childID = '"+cid+"';";
							st.executeQuery(q2);
							ResultSet rs2 = st.getResultSet();
							while(rs2.next()){
								clinicdates = clinicdates+"<option>"+(String)rs2.getString("clinicDate")+"</option>";
							}
							
							q1="SELECT guardianAreaCode,guardianName,guardianPicture FROM guardian WHERE guardianID = '"+child.motherguardianID+"';";
							st.executeQuery(q1);
							rs = st.getResultSet();
							String guardianPic = "";
							while(rs.next()){
								child.motherguardianName = (String)rs.getString("guardianName");
								areaCode = (String)rs.getString("guardianAreaCode");
								guardianPic = (String)rs.getString("guardianPicture");
							}
							q1 = "SELECT area FROM area WHERE areaCode = '"+areaCode+"';";
							st.executeQuery(q1);
							rs = st.getResultSet();
							while(rs.next()){
								area = rs.getString("area");
							}
							
							q1="SELECT motherID FROM mother WHERE guardianID = '"+child.motherguardianID+"';";
							st.executeQuery(q1);
							rs2 = st.getResultSet();
							while(rs2.next()){
								//request.setAttribute("mid",(String)rs2.getString("motherID"));
								mothername = child.motherguardianName;
								motherpic = guardianPic;
							}
							if(child.father!=null){
								q1="SELECT fatherName,fatherPicture FROM father WHERE fatherID = '"+child.father.fatherID+"';";
								st.executeQuery(q1);
								ResultSet rs3 = st.getResultSet();
								while(rs3.next()){
									fathername = (String)rs3.getString("fatherName");
									fatherpic = (String)rs3.getString("fatherPicture");
								}
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
						DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				    	java.util.Date dt = new java.util.Date();
				    	Date currentDate = FormatDate.createDate(frmt.format(dt));
				    	Date bDate = FormatDate.createDate(child.dateofDelivery);
				    	age = bDate.getAge(currentDate);
				    	
						out.print(childPic);
						%>" alt="user_photo">
						<h1 style="width:50%; float: right;"><%
							if(child.childName!=null){
								out.print(child.childName);
							}
							else{
								return;
							}
						%>
						</h1>
					</div>
					<div class="titlebar" id="titlebar">
						<div class="title" id="detailstab"><center><h1>Details</h1></center></div>
						<div class="title" id="vaccinationtab"><center><h1>Attended Clinics</h1></center></div>
					</div>
					<div style="display: inline-block; margin-top: 0; width: 84%;" class="childdetails" id="details">
						<input type="hidden" id="childid" value="<%
							if(cid!=null){
								out.print(cid);
							}
							%>">
						<input type="hidden" id="guardianid" value="<%
							if(child.motherguardianID!=null){
								out.print(child.motherguardianID);
							}
							%>">
						<input type="hidden" id="areacode" value="<%
							if(areaCode!=null){
								out.print(areaCode);
							}
							%>">
						<div  style="font-size: 120%; float:left; width:70%; color:#040C23;">
							<ul>
								<%
									if(area!=null){
										out.print("<li>Area :-  "+area+"</a></li></br>");
									}
									if(cid!=null){
										out.print("<li>Child ID :-  "+cid+"</li></br>");
									}
									if(child.dateofDelivery!=null){
										out.print("<li>Date of Delivery :-  "+child.dateofDelivery+"</li></br>");
									}
									if(age!=null){
										out.print("<li>Age :-  "+age+"</li></br>");
									}
									if(child.birthWeight!=null){
										out.print("<li>Birth Weight :-  "+child.birthWeight+"</li></br>");
									}
									if(child.motherguardianID!=null){
										out.print("<li>Guardian ID :-  "+child.motherguardianID+"</li></br>");
									}
									if(child.motherguardianName!=null){
										out.print("<li>Guardian Name :-  "+child.motherguardianName+"</li></br>");
									}
									if(mothername!=null){
										out.print("<li>Mother Name :-  "+mothername+"</li></br>");
									}
									if(child.father!=null){
										out.print("<li>Father ID :-  "+child.father.fatherID+"</li></br>");
									}
									if(fathername!=null){
										out.print("<li>Father Name :-  "+fathername+"</li></br>");
									}
								%>
							</ul>
						</div>
						<div class="members" id="parent">
							<table style="width: 100%;">
								<%
									if(mothername!=null){
										out.print("<tr><td><div class=\"parents\" id=\""+child.motherguardianID+"\"><img id=\""+child.motherguardianID+"\" src=\""+motherpic+"\"><h1 id=\""+child.motherguardianID+"\">  Mother : </br>"+child.motherguardianName+"</h1></div></td></tr>");
									}
									if(fathername!=null){
										out.print("<tr><td><div class=\"parents\" id=\""+child.father.fatherID+"\"><img id=\""+child.father.fatherID+"\" src=\""+fatherpic+"\"><h1 id=\""+child.father.fatherID+"\">  Father : </br>"+fathername+"</h1></div></td></tr>");
									}
								%>
							</table>							
						</div>
					</div>
					<div style="display:none; margin-top: 0; width: 84%;" class="childdetails" id="vaccination">
						<h2>Past clinic details</h2>
						<select class="clinicselect" name="clinicdate" id="clinicdate">
							<option disabled selected value>Clinic Date</option>
							<%
								out.print(clinicdates);
							%>
						</select></br>
						<div  id="clinicupdates" style="display:none; width:100%; font-size: 120%; foreground:blue;">
							<ul>
								<li>Age :-  <a id="childage"> <a></li></br>
								<li>Weight(kg) :-  <a id="childweight"> <a></li></br>
								<li>Given Triposha Amount(pckts) :-  <a id="tamount"> <a></li></br>
							</ul>
							<table class="vaccinetable" id="vaccinetable">
								<tr>
									<th>Vaccine Code</th>
									<th>Vaccine Name</th>
									<th>Vaccine Dose</th>
								</tr>
							</table>
						</div>
						<h2 style="width: 100%;">Weight graphs</h2>
						<!-- <table style="float:left; width: 100%;">
							<tr>
								<th>
									<div style="float:left; width:15%; width: 100%;">
										<label>Start date</label>
									</div>
								</th>
								<th>
									<div style="float:left; width:15%; width: 100%;">
										<label>End date</label>
									</div>
								</th>
							</tr>
							<tr>
								<td>
									<center>
										<div style="float:left; width:85%;">
										<div id="date_startdate" style="float:left; width: 100%; height: 5%;">
											<jsp:include page="/date.jsp" />
										</div>
										<input type="hidden" id="startdate" name="txtstartdate">
									</center>
								</div>
								</td>
								<td>
									<center>
										<div style="float:left; width:85%;">
										<div id="date_enddate" style="float:left; width: 100%; height: 5%;">
											<jsp:include page="/date.jsp" />
										</div>
										<input type="hidden" id="enddate" name="txtenddate">
									</center>
								</td>
							</tr>
						</table></br></br></br></br></br></br></br></br></br></br> -->
						<center>
							<!-- <div class="btn" onclick="loadGraph()" style="height: 5%;">Show</div> -->
							<div style="display: inline-block;">
								<div style="float:left; width: 100%;" id="chartcontainer">
								
								</div>
								<div style="float:left; background-color:black;">
								</div>
							</div>
						</center>
					</div>
				</div>
			</div>
			<div class="right_sidebar">
				<div class ="vaccination">
					<h2>Vaccination Dates</h2>
					<ul>
						<%
							VaccinationNotifications vnotifications = new VaccinationNotifications(child.childID, 7);						
							if(vnotifications.getNotification() != null){
								out.print(vnotifications.getNotification());
							}
						%>
					</ul>
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