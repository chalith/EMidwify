<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.main.Date"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Register - Babies</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/registration.css">
<link rel="stylesheet" type="text/css" href="midwife/css/form.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/editchildinfo.js"></script>
<script src="midwife/js/createviewforall.js"></script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			response.sendRedirect("");
			return;
		}
		String cDate = (String) session.getAttribute("date");
	%>
<div>
	<div class="container">
		<div style="width:75%; height:10%; position:relative;">
			<jsp:include page="header.jsp"/>
		</div>
		<div id="container" class="backbody">
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
			<div class="body">
				<form name="childeditForm" method="post" action="childeditinfo">
					<input id="ceps" type="hidden" name="cepArray">
					<input id="feps" type="hidden" name="fepArray">
					<div class="form_content">
					<div class="form_content" style="border-bottom: dotted white; padding-top:4%; padding-bottom: 5%;">
						<div style="float:left; width:50%;">
							<label>Mother Guardian ID</label>
							<input style="width:40%; background:#E6E6E6;" type="text" id="motherguardianid" placeholder="MotherGuardianID" name="txtmotherguardianid" value=
							"<%
								String id = (String) request.getParameter("id");
								JDBC jdbc = new JDBC();
								String guardianID = "";
								String name = "";
								String dob = "";
								String bweight = "";
								String fatherID = "";
								String notes = "";
								String picture = "";
								String guardianName = null;
								String guardianAreaCode = null;
								String area = null;
								String fname = "";
								String fdob = "";
								String foccupation = "";
								String faddress = "";
								String fedulevel = "";
								String fpicture = "";
								
								try{
									String q = "SELECT * FROM child WHERE childID = '"+id+"';";
									Statement st=jdbc.conn.createStatement();
									st.executeQuery(q);
									ResultSet rs = st.getResultSet();
									while(rs.next()){
										guardianID = rs.getString("guardianID").trim();
										name = rs.getString("childName").trim();
										dob = rs.getString("childDateofDelivery").trim();
										bweight = rs.getString("childBirthWeight").trim();
										fatherID = rs.getString("childFatherID").trim();
										notes = rs.getString("childNotes").trim();
										picture = rs.getString("childPicture").trim();
									}
									q = "SELECT guardianName,guardianAreaCode FROM guardian WHERE guardianID = '"+guardianID+"';";
									st=jdbc.conn.createStatement();
									st.executeQuery(q);
									rs = st.getResultSet();
									while(rs.next()){
										guardianName = rs.getString("guardianName");
										guardianAreaCode = rs.getString("guardianAreaCode");
									}
									q = "SELECT area FROM area WHERE areaCode = '"+guardianAreaCode+"';";
									st.executeQuery(q);
									rs = st.getResultSet();
									while(rs.next()){
										area = rs.getString("area");
									}
									
									q = "SELECT * FROM father WHERE fatherID = '"+fatherID+"';";
									st.executeQuery(q);
									rs = st.getResultSet();
									while(rs.next()){
										fname = rs.getString(2).trim();
										faddress = rs.getString(4).trim();
										fdob = rs.getString(3).trim();
										foccupation = rs.getString(5).trim();
										fedulevel = rs.getString(6).trim();
										fpicture = rs.getString(7);
									}
								}catch(Exception e){
									e.printStackTrace();
								}
								finally{
									try{
										jdbc.conn.close();
									}catch(Exception e){
										e.printStackTrace();
									}
								}
								out.print(guardianID);
							%>"
							readonly/>
						</div>
						<div style="float:left; width:50%;">
							<label>Mother Guardian's Name</label>
							<input style="width:60%; background:#E6E6E6;" type="text" id="motherguardianname" placeholder="MotherGuardian'sName" name="txtmotherguardianname" value=
							"<%
								out.print(guardianName);
							%>"
							readonly/>
						</div></br>
					</div>
					<div class="form_content"></br>
						<div style="float:left; width:10%;">
							<img style="float:left; width: 85%; margin-top:-30%; margin-bottom: -5%;" id="motherpicture" src=
							"<%
								out.print(picture);
							%>"></br>
						</div>
						<div style="float:left; width:45%;">
							<div style="float:left; width:20%;">
								<label>Child ID</label>
							</div>
							<div style="float:left; width:80%;">
								<input style="width:60%; background:#E6E6E6;" type="text" id="childid" name="txtchildid"
									value="<%
										out.print(id);
									%>" readonly/>
							</div>
						</div>
						<div style="float:left; width:45%;">
							<div style="float:left; width:20%;">
								<label>Full Name</label>
							</div>
							<div style="float:left; width:80%;">
								<input style="width:100%;" type="text" id="childname" placeholder="ChildName" name="txtchildname" value=
								"<%
									out.print(name);
								%>"
								>
							</div>
						</div>
					</div></br>
					<div class="form_content">
						<div style="float:left; width:15%;">
							<label>Date of Delivery</label>
						</div>
						<div style="float:left; width:85%;">
							<input style="background:#E6E6E6;" id="dateofdeliver" name="txtdateofchilddelivery" value=
							"<%
								out.print(dob);
							%>"
							readonly/>
						</div>
					</div></br>
					<div class="form_content" style="margin-bottom: 2%;">
						<div style="float:left; width:15%;">
							<label>Birth Weight(kg)</label>
						</div>
						<div style="float:left; width:85%;">
							<input style="background:#E6E6E6; width:20%;" type="text" id="birthweight" placeholder="BirthWeight" name="txtchildbirthweight" value=
							"<%
								out.print(bweight);
							%>"
							readonly/>
						</div>
					</div></br></br>
					<div class="form_content" style="border-style:solid;">
						<div id="childep" class="dropb">
							<h4>If any epidemics </h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="childeps">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Epidemic Code</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="childepidemiccode" placeholder="EpidemicCode" name="txtchildepidemiccode"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Epdemic Name</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="childepidemicname" placeholder="EpidemicName" name="txtchildepidemicname"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date</label>
								</div>
								<div id="date_childepidemicdate" style="float:left; width:85%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="childepidemicdate" name="txtchildepidemicdate">
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Note</label>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="childepidemicnote" placeholder="EpdemicNote" name="txtchildepidemicnote"></textarea></br>
								</div>
							</div></br>
							<div style="margin: 5% 2% 8% 39%;">
								<div class="btn" style="float:left;" onclick="addchildEpidemic()">Add</div>
								<div class="btn" style="float:left;" onclick="removefromTable(childepidemictable)">Remove</div>
							</div>
							<div class="form_content">
								<table id="childepidemictable" style="width:100%;">
									<tr class="thead" id="thead">
										<th>Code</th>
										<th>Name</th>
										<th>Date</th>
										<th>Note</th>
									</tr>
								</table>
							</div>
						</div>								
					</div></br>
					<div class="form_content" style="border-Style:dotted;">
						<h3>Father</h3>
						<div class="form_content" style="border-style:dotted;">
							<h4 style="color:red;">If father is not registered in the system</h4>
							<div class="form_content">
								<div style="float:left; width:50%;">
									<div style="float:left; width:30%;">
										<label>Father NIC/ID</label>
									</div>
									<div style="float:left; width:70%;">
										<input style="background:#E6E6E6; width:60%;" placeholder="FatherNIC" type="text" id="fatherid" name="txtfatherid" value=
										"<%
											out.print(fatherID);	
										%>"
										readonly/>
									</div>
								</div>
								<div style="float:left; width:50%;">
									<div style="float:left; width:20%;">
										<label>Full Name</label>
									</div>
									<div style="float:left; width:80%;">
										<input style="width:100%;" type="text" id="fatherfullname" placeholder="Fullname" name="txtfatherfullname" value=
										"<%
											out.print(fname);
										%>"
										>
									</div>
								</div>
							</div></br></br>
							<div class="form_content">
								<div style="float:left; width:15%;">
									<label>Date of Birth</label>
								</div>
								<input  style="background:#E6E6E6;" placeholder="DateOfBirth" id="fatherdateofbirth" name="txtfatherdateofbirth"  value=
								"<%
									out.print(fdob);
								%>"
								readonly/>
							</div></br>
							<div class="form_content">
								<div style="float:left; width:15%;">
									<label>Occupation</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="occupation" placeholder="Occupation" name="txtoccupation" value=
									"<%
										out.print(foccupation);
									%>">
								</div>
							</div></br>
							<div class="form_content">
								<div style="float:left; width: 15%;">
									<label>Address</label>
								</div>
								<div style="float:left; width:85%;">
									<%
										String s[] =faddress.split("\n");
										for(int i=0;i<4;i++){
									%>
										<input style="width:40%; type="text" id="addressline<%out.print(i+1);%>" placeholder="Line1<%out.print(i+1);%>" name="txtaddressline<%out.print(i+1);%>" value=
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
									%></div>
							</div></br>
							<div class="form_content" style="display:inline-block; width:40%;">
								<div style="float:left; width:20%">
									<label>Education Level</label>
								</div>
								<div style="float:right; width:80%;">
									<input type="radio" id="high" name="edulevel" value="high"
										<%
											if(fedulevel.equals("high")){
												out.print("checked=\"checked\"");
											}
										%>
									>High</br>
									<input type="radio" id="medium" name="edulevel" value="medium"
										<%
											if(fedulevel.equals("medium")){
												out.print("checked=\"checked\"");
											}
										%>
									>Medium</br>
									<input type="radio" id="low" name="edulevel" value="low"
										<%
											if(fedulevel.equals("low")){
												out.print("checked=\"checked\"");
											}
										%>
									>Low</input></br>
								</div>	
							</div>
							<div class="form_content" style="border-style:solid;">
								<div id="fatherep" class="dropb">
									<h4>If any epidemics </h4>
									<img  src="images/icons/dropdown.png" alt="" />
								</div>
								<div class="hide_div" id="fathereps">
									<div class="form_content" style="margin-bottom: 1%;">
										<div style="float:left; width:15%;">
											<label>Epidemic Code</label>
										</div>
										<div style="float:left; width:85%;">
											<input style="width:30%;" type="text" id="fatherepidemiccode" placeholder="EpidemicCode" name="txtfatherepidemiccode"></br>
										</div>
									</div></br>
									<div class="form_content" style="margin-bottom: 1%;">
										<div style="float:left; width:15%;">
											<label>Epdemic Name</label>
										</div>
										<div style="float:left; width:85%;">
											<input style="width:30%;" type="text" id="fatherepidemicname" placeholder="EpidemicName" name="txtfatherepidemicname"></br>
										</div>
									</div></br>
									<div class="form_content" style="margin-bottom: 1%;">
										<div style="float:left; width:15%;">
											<label>Date</label>
										</div>
										<div id="date_fatherepidemicdate" style="float:left; width:85%;">
											<jsp:include page="/date.jsp" /></br>
										</div>
										<input type="hidden" id="fatherepidemicdate" name="txtfatherepidemicdate">
									</div></br>
									<div class="form_content" style="margin-bottom: 1%;">
										<div style="float:left; width:15%;">
											<label>Note</label></br>
										</div>
										<div style="float:left; width:85%;">
											<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="fatherepidemicnote" placeholder="EpdemicNote" name="txtfatherepidemicnote"></textarea></br>
										</div>
									</div></br>
									<div style="margin: 5% 2% 8% 39%;">
										<div class="btn" style="float:left;" onclick="addfatherEpidemic()">Add</div>
										<div class="btn" style="float:left;" onclick="removefromTable(fatherepidemictable)">Remove</div>
									</div>
									<div class="form_content">
										<table id="fatherepidemictable" Style="width:100%;">
											<tr class="thead" id="thead">
												<th>Code</th>
												<th>Name</th>
												<th>Date</th>
												<th>Note</th>
											</tr>
										</table>
									</div>
								</div>								
							</div>
						</div></br>
					</div>
					<div class="form_content" style="border-Style:solid;">
						<div id="motherdeath" class="dropb">
							<h3 style="color:red;">If mother not alive</h3>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="motherdeaths">
							<div class="form_content" style="border-style:dotted;">
								<h4 style="color:red;">If mother registered in the system</h4>
								<div style="float:left; width:15%;">
									<label>Mother ID</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:20%; background:#E6E6E6;" type="text" id="deathmotherid" placeholder="MotherID" name="txtdeathmotherid" value=
									"<% 
										Main m = new Main();
										if(m.isHave("mother","guardianID",guardianID)){
											out.print(guardianID);
										}
										
									%>" readonly/>

								</div>
								<label id="nomotherAlert" style="color:red;"></label>
								</br>
							</div></br>
							<div class="form_content" style="border-style:dotted;">
								<h4 style="color:red;">If mother is not registered in the system</h4>
								<div class="form_content" style="margin-bottom: 1%;">
									<div style="float:left; width:15%;">
										<label>Full Name</label>
									</div>
									<div style="float:left; width:85%;">
										<input style="width:30%;" type="text" id="deathmothername" placeholder="NameOfTheMother" name="txtdeathmothername" value=
										"<%
											if(m.isHave("mother","guardianID",guardianID)){
												out.print(guardianName);
											}
										%>"
										<%
											if(m.isHave("mother","guardianID",guardianID)){
												out.print("readonly");
											}
										%>/></br>
									</div>
								</div></br>
								<div class="form_content" style="margin-bottom: 1%;">
									<div style="float:left; width:15%;">
										<label>Area Code</label>
									</div>
									<div style="float:left; width:85%;">
										<select style="width:20%;" type="text" id="deathmotherareacode" placeholder="AreaCode" name="txtdeathmotherareacode">
												<%
													if(m.isHave("mother","guardianID",guardianID)){
												%>
												<option selected value="<%out.print(guardianAreaCode);%>"><%out.print("("+guardianAreaCode+") "+area);%></option>
												<% 
													}else{
												%>
												<option selected value>Area</option>
												<%
														out.print(areas);
													}
												%>
										</select>
									</div>
								</div></br>
								<div class="form_content" style="margin-bottom: 1%;">
									<div style="float:left; width:15%;">
										<label>Date of death</label>
									</div>
									<div id="date_dateofmotherdeath" style="float:left; width:85%;">
										<jsp:include page="/date.jsp" /></br>
									</div>
									<input type="hidden"  id="dateofmotherdeath" name="txtdateofmotherdeath">
								</div></br>
								<div class="form_content" style="margin-bottom: 3%;">
									<div style="float:left; width:15%;">
										<label>Reason</label>
									</div>
									<div style="float:left; width:85%;">
										<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="resonfordeath" placeholder="Reason" name="txtresonfordeath"></textarea>
									</div>
								</div></br></br>
							</div>
						</div>
					</div>
					<div class="form_content" style="margin-bottom: 8%;">
						<div style="float:left; width:5%;">
							<label>Notes</label>
						</div>
						<div style="float:left; width:95%;">
							<textarea style="width: 90%; height: 90%;" rows="4" cols="80" id="notesforchild" placeholder="Notes" name="txtnotesforchild"><%
								out.print(notes);
							%></textarea>
						</div>
					</div>
					</div>
					<div class="form_content" style="float:right;">
						<button type="button" id="myForm" onclick="doSubmit()">Add Changes</button>
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
</div>
<jsp:include page="/alert.jsp" />
<jsp:include page="/error.jsp" />
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
</body>

</html>