<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="com.main.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Mother</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="supervisor/css/viewmother.css">
<link rel="stylesheet" type="text/css" href="supervisor/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="supervisor/js/createviewforall.js"></script>
<script src="supervisor/js/viewmother.js"></script>
<script type="text/javascript" src="js/jscharts.js"></script>
<script>
$(window).load(function(){
	loadGraph();
	loadChildren();
	tabActive('#detailstab');
});
//This function is to view weigth graphs
function loadGraph(){
	var id = document.getElementById("guardianid").value;
	var xmlhttp = new XMLHttpRequest();
    var url="viewweights";
    var url=url+"?person=mother&id="+id;
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
				myChart.setIntervalEndY(120);
				myChart.resize(800, 500);
				myChart.setAxisValuesNumberY(40);
				for(var i=0;i<k.length;i++){
					myChart.setLabelX([i, json[k[i]].age]);
					//alert(json[k[i]].date+" "+json[k[i]].age+" "+json[k[i]].weight);
				}
				myChart.draw();
	        }
	        //document.getElementById("notifications").innerHTML = news;
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
			response.sendRedirect("/EMidwify");
		}
	%>
<div>
	<div class="container">
		<div style="width:75%; height:10%; position:relative">
			<jsp:include page="header.jsp"/>
		</div>
		<jsp:include page="/message.jsp" />
		<div class="body">
			<div style="float:left; width:80%;">
			<%
				Guardian guardian = new Guardian();
				String Gid = request.getParameter("guardianid");
				String guardianPic = "";
				String age = null;
				String area = null;
				String guardormother = null;
				String clinicdates = "";
				ArrayList<String> mobileNumbers=new ArrayList<String>();
				JDBC jdbc = new JDBC();
				try{
					String q="SELECT * FROM guardian WHERE guardianID = '"+Gid+"';";
					jdbc.st.executeQuery(q);
					ResultSet rs = jdbc.st.getResultSet();
					while(rs.next()){
						guardian.id = (String)rs.getString("guardianID");
						guardian.fullname = (String)rs.getString("guardianName");
						guardian.address = (String)rs.getString("guardianAddress");
						guardian.areacode = (String)rs.getString("guardianAreaCode");
						guardian.dateofbirth = (String)rs.getString("guardianBDate");
						DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				    	java.util.Date dt = new java.util.Date();
				    	Date currentDate = FormatDate.createDate(frmt.format(dt));
				    	Date bDate = FormatDate.createDate(guardian.dateofbirth);
				    	age = bDate.getAge(currentDate);
				    	
						guardian.email = (String)rs.getString("guardianEmail");
						guardian.nofchildren = (String)rs.getString("guardianNofchildren");
						guardian.occupation = (String)rs.getString("guardianOccupation");
						guardian.edulevel = (String)rs.getString("guardianEducationLevel");
						guardianPic = (String)rs.getString("guardianPicture");
					}
					q="SELECT mobileNumber FROM guardianmobilenumber WHERE guardianID = '"+Gid+"';";
					jdbc.st.executeQuery(q);
					rs = jdbc.st.getResultSet();
					while(rs.next()){
						mobileNumbers.add((String)rs.getString("mobileNumber"));
					}
					
					q = "SELECT area FROM area WHERE areaCode = '"+guardian.areacode+"';";
					jdbc.st.executeQuery(q);
					rs = jdbc.st.getResultSet();
					while(rs.next()){
						area = rs.getString("area");
					}
					String q1 = "SELECT COUNT(childID) FROM child WHERE guardianID = '"+guardian.id+"';";
					jdbc.st.executeQuery(q1);
					ResultSet rs1 = jdbc.st.getResultSet();
					while (rs1.next()) {
						guardian.nofchildren = (String)rs1.getString(1);
					}
					Main m = new Main();
					if(m.isHave("mother", "guardianID", guardian.id)){
						String q2="SELECT DISTINCT(clinicDate) FROM motherclinic WHERE motherID = '"+guardian.id+"';";
						jdbc.st.executeQuery(q2);
						ResultSet rs2 = jdbc.st.getResultSet();
						while(rs2.next()){
							clinicdates = clinicdates+"<option>"+(String)rs2.getString("clinicDate")+"</option>";
						}
						guardormother = "mother";
					}
					else{
						guardormother = "guardian";
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
				%>
				<div class="details" style="display:inline-block;">
					<input type="hidden" id="guardianormother" value="<%
						out.print(guardormother);
					%>"></input>
					<div class="motherdetails" style="padding-top:1%; margin:3% 3% 0 3%; border:2px solid;">
						<img style="width:15%; border:solid; border-radius:10%; margin: 2% 0 -2% 0%;" src="<% out.print(guardianPic); %>" alt="user_photo">
						<h1 style="width:50%; float: right;"><%
							if(guardian.fullname!=null){
								out.print(guardian.fullname);
							}
							else{
								return;
							}
							%>
						</h1>
					</div>
					<%
					if(guardormother.equals("mother")){
						out.print("<div class=\"titlebar\" id=\"titlebar\">");
						out.print("<div class=\"title\" id=\"detailstab\"><center><h1>Details</h1></center></div>");
						out.print("<div class=\"title\" id=\"vaccinationtab\"><center><h1>Attended Clinics</h1></center></div>");
					}
					%>
					</div>
					<div style="display: inline-block; margin-top: 0; width: 84%;" class="motherdetails" id="details">
						<input type="hidden" id="guardianid" value="<%
							if(guardian.id!=null){
								out.print(guardian.id);
							}
							%>">
						<input type="hidden" id="areacode" value="<%
							if(guardian.areacode!=null){
								out.print(guardian.areacode);
							}
							%>">
						<div  style="font-size: 120%; float:left; width:70%; color:#040C23;">
							<ul>
								<%
									if(area!=null){
										out.print("<li>Area :-  "+area+"</li></br>");
									}
									if(guardian.id!=null){
										out.print("<li>ID :-  "+guardian.id+"</li></br>");
									}
									if(guardian.address!=null){
										out.print("<li>Address :-  "+guardian.address+"</li></br>");
									}
									if(mobileNumbers.size()!=0){
										out.print("<li>Mobile numbers :-  ");
										for(int i=0;i<mobileNumbers.size();i++){
											out.print("<li>"+mobileNumbers.get(i)+"</li></br>");
										}
										out.print("</li>");
									}
									if(guardian.email!=null){
										out.print("<li>Email Address :-  "+guardian.email+"</li></br>");
									}
									if(guardian.dateofbirth!=null){
										out.print("<li>Date of birth :-  "+guardian.dateofbirth+"</li></br>");
									}
									if(age!=null){
										out.print("<li>Date of birth :-  "+age+"</li></br>");
									}
									if(guardian.nofchildren!=null){
										out.print("<li>Number of children :-  "+guardian.nofchildren+"</li></br>");
									}
									if(guardian.edulevel!=null){
										out.print("<li>Education Level :-  "+guardian.edulevel+"</li></br>");
									}
								%>
							</ul>
						</div>
						<div class="members" id="showbabies">
							<table style="width: 100%;">
								<tr>
									<td><center><h3>I have <%
											if(guardian.nofchildren.equals("1")){
												out.print(guardian.nofchildren+" baby");
											}
											else{
												out.print(guardian.nofchildren+" babies");	
											}
										%></h3> 
										<h3> to take care of</h3></center>
									</td>
								</tr>
							</table>
							<div id="babies" class="babies">
								
							</div>							
						</div>
					</div>
					<div style="display:none; margin-top: 0%; position:static; width: 84%;" class="motherdetails" id="vaccination">
						<h2>Past clinic details</h2>
						<select class="clinicselect" name="clinicdate" id="clinicdate">
							<option disabled selected value>Clinic Date</option>
							<%
								out.print(clinicdates);
							%>
						</select></br>
						<div  id="clinicupdates" style="display:none; width:100%; font-size: 120%; foreground:blue;">
							<ul>
								<li>Age :-  <a id="motherage"> <a></li></br>
								<li>Weight(kg) :-  <a id="motherweight"> <a></li></br>
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
						<!-- <div>
						<table style="float:left; width: 100%;">
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
						</table>
						</div></br></br></br></br></br></br></br></br></br></br> -->
						<center>
							<!-- <div class="btn" onclick="loadGraph()">Show</div>  -->
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