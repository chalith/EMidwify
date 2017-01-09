<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Register - Mothers</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/registration.css">
<link rel="stylesheet" type="text/css" href="midwife/css/form.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/motherregistration.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script>
function displayMap() {
    document.getElementById('mapCanvas').style.display="block";
    initialize();
}
function initialize() {
	var myOptions = {zoom: 14,center: new google.maps.LatLng(0.0, 0.0),mapTypeId: google.maps.MapTypeId.ROADMAP};
    map = new google.maps.Map(document.getElementById("mapCanvas"),myOptions);
}

 $(document).ready(function(){
	 $('#map').click(function(){
		$('#setlocation').toggle("slow");
		document.getElementById("container").style.opacity="0.2";
		displayMap(); 
	 });
	 $('#addlocation').click(function(){
		document.getElementById("location").value = document.getElementById("info").value;
		$('#setlocation').toggle("slow");
		document.getElementById("container").style.opacity="1";
		document.getElementById("setted").innerHTML = "location set";
	 });
	 $('#closelocation').click(function(){
		$('#setlocation').toggle("slow");
		document.getElementById("container").style.opacity="1";
	 });
	 $('#nonic').on('change',function(){
		$('#guardianid').css('display','none');
		$('#guardiannic').css('display','block');
		$('#guardianid').attr('id','tempnic');
		$('#guardiannic').attr('id','guardianid');
		$('#tempnic').attr('id','guardiannic');
		$('#guardianid').attr('name','txtguardianid');
		$('#guardiannic').attr('name','txtguardiannic');
	 });
 });
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
			<form action="motherregistration" method="post" name="motherForm">
				<input type="hidden" name="epArray" id="eps">
				<input type="hidden" name="abArray" id="abs">
				<input type="hidden" name="cdArray" id="cds">
				<div class="form_content">
				<div class="form_content" style="border:solid; padding-top:4%; padding-bottom: 5%;">
					<div style="float:left; width:50%;">
						<div style="float:left; width:30%;">
							<label>Guardian NIC/ID</label>
						</div>
						<div style="float:left; width:70%;">
							<input style="width:60%;" placeholder="GuardianNIC" type="text" id="guardianid" name="txtguardianid">
							<input style="width:60%; background:#E6E6E6; display:none;" type="text" id="guardiannic" name="txtguardiannic"
								value="<%
								Main m = new Main();
								String id = m.generateID("guardian","Guard");
								if(id!=null){
									out.println(id);
								}
								%>" readonly/>
						</div>
						<input style="margin-left: 35%;" type="checkbox" id="nonic" name="txtnonic"> Don't Have NIC</br>
					</div>
					<div style="float:left; width:50%;">
						<div style="float:left; width:20%;">
							<label>Full Name</label>
						</div>
						<div style="float:left; width:80%;">
							<input style="width:100%;" type="text" id="fullname" placeholder="Fullname" name="txtfullname">
						</div>
					</div></br>
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Date of Birth</label>
					</div>
					<div style="float:left; width:80%;" id="date_dateofbirth">
						<jsp:include page="/date.jsp" />
					</div>
					<input type="hidden" id="dateofbirth" name="txtdateofbirth">
				</div></br>
				<div class="form_content">
					<div style="float:left; width:20%;">
						<label>Occupation</label>
					</div>
					<div style="float:left; width:80%;">
						<input style="width:40%;" type="text" id="occupation" placeholder="Occupation" name="txtoccupation">
					</div>
				</div></br>
				<div class="form_content" style="display:inline-block; width:100%; margin-bottom:-2%;">
					<div style="float:left; width:19.5%;">
						<label>Area Code</label>
					</div>
					<div style="float:left; width:80%;">
						<select style="width:39%;" type="text" id="areacode" placeholder="AreaCode" name="txtareacode">
							<option disabled selected value>Area</option>
							<%
								out.print(areas);
							%>
						</select>
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 8%;">
					<div style="float:left; width: 20%;">
						<label>Address</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%; type="text" id="addressline1" placeholder="Line1" name="txtaddressline1"></br>
						<input style="width:40%; type="text" id="addressline2" placeholder="Line2" name="txtaddressline2"></br>
						<input style="width:40%; type="text" id="addressline3" placeholder="Line3" name="txtaddressline3"></br>
						<input style="width:40%; type="text" id="addressline4" placeholder="Line4" name="txtaddressline4">
					</div>
				</div></br>
				<div class="form_content">
					<div style="float:left; width: 20%;">
						<label>Location</label>
					</div>
					<div style="float:left; width: 32.5%;">
						<input style="width: 100%; background:#E6E6E6;"  name="txtlocation" placeholder="Location" id="location" readonly></input>
					</div>
					<div style="float:left; width: 35%;">
						<div class="btn" id="map" style="width: 40%; float:left; margin-left: 10%;">Set Location</div>
						<lable id="setted" style="margin-left: 10%; float:left; color:blue;"></lable>
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 4.5%;">
					<div style="float:left; width: 20%;">
						<label>Telephone Number</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="text" id="tpnumber1" placeholder="Telephone1" name="txttpnumber1" maxlength="10"></br>
						<input style="width:40%;" type="text" id="tpnumber2" placeholder="Telephone2" name="txttpnumber2" maxlength="10"></br>
						<input style="width:40%;" type="text" id="tpnumber3" placeholder="Telephone3" name="txttpnumber3" maxlength="10">
					</div>
				</div></br>
				<div class="form_content">
					<div style="float:left; width: 20%;">
						<label>Email</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="email" id="email" placeholder="Email" name="txtemail">
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 1%;">
					<div style="float:left; width: 20%;">
						<label>Number of Children</label>
					</div>
					<div style="float:left; width: 80%;">
						<input style="width:40%;" type="text" id="nofchildren" placeholder="NumberOfChildren" name="txtnofchildren">
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 4%;">
					<div style="float:left; width: 20%;">
						<label>Education Level</label>
					</div>
					<div style="float:left; width: 80%;">
						<input type="radio" id="high" name="edulevel" value="high">High</br>
						<input type="radio" id="medium" name="edulevel" value="medium">Medium</br>
						<input type="radio" id="low" name="edulevel" value="low">Low</input></br>
					</div>
				</div></br>
				<div class="form_content" style="margin-bottom: 1%;">
					<div style="float:left; width: 20%;">
						<label>Mother or a Guardian</label>
					</div>
					<div style="float:left; width: 20%;">
						<input type="radio" id="mother" name="motherguardian" value="mother">Mother</br>
					</div>
					<div style="float:left; width: 20%;">
						<input type="radio" id="guardian" name="motherguardian" value="guardian">Guardian</br>
					</div>
				</div></br></br>
				<div class="form_content" style="border-Style:dotted;">
					<h3>Mother</h3>
					<h4 style="color:red;">This details required for mothers only</h4>
					<div class="form_content" style="border-style:solid;">
						<div id="motherep" class="dropb">
							<h4>If any epidemics </h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="mothereps">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Epidemic Code</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="epidemiccode" placeholder="EpidemicCode" name="txtepidemiccode"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Epdemic Name</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="epidemicname" placeholder="EpidemicName" name="txtepidemicname"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date</label>
								</div>
								<div style="float:left; width:85%;" id="date_epidemicdate">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="epidemicdate" name="txtepidemicdate"></br>
							</div>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Note</label></br>
								</div>	
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="epidemicnote" placeholder="EpdemicNote" name="txtepidemicnote"></textarea></br>
								</div>
							</div></br>
							<div style="margin: 5% 2% 8% 39%;">
								<div class="btn" style="float:left;" onclick="addEpidemic()">Add</div>
								<div class="btn" style="float:left;" onclick="removefromTable(epidemictable)">Remove</div>
							</div>
							<div class="form_content">
								<table id="epidemictable" Style="width:100%;">
									<tr class="thead" id="thead">
										<th id="thead">Code</th>
										<th id="thead">Name</th>
										<th id="thead">Date</th>
										<th id="thead">Note</th>
									</tr>
								</table>
							</div>								
						</div>
					</div>
					<div class="form_content" style="border-style:solid;">
						<div id="motherab" class="dropb">
							<h4 style="color:red;">If any abortions</h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="motherabs">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date of abortion</label>
								</div>
								<div id="date_dateofabortion" style="float:left; width:85%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="dateofabortion" name="txtdateofabortion"></br>	
							</div>
							<div class="form_content" style="margin-bottom: 4%;">
								<div style="float:left; width:15%;">
									<label>Reason</label>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="reasonforabortion" placeholder="Reason" name="txtreasonforabortion"></textarea></br>
								</div>
							</div></br>
							<div style="margin: 2% 2% 5% 39%;">
								<div class="btn" style="float:left;" onclick="addAbortion()">Add</div>
								<div class="btn" style="float:left;" onclick="removefromTable(abortiontable)">Remove</div>
							</div>
							<div class="form_content">
								<table id="abortiontable" Style="width:100%;">
									<tr class="thead" id="thead">
										<th id="thead">Date</th>
										<th id="thead">Reason</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="form_content" style="border-style:solid;">
						<div id="childdeath" class="dropb">
							<h4 style="color:red;">If any child deaths</h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="childdeaths">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Child's Full Name</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="deathchildname" placeholder="NameOfTheChild" name="txtdeathchildname"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date of death</label>
								</div>
								<div style="float:left; width:85%;" id="date_dateofdeath">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="dateofdeath" name="txtdateofdeath"></br>	
							</div>
							<div class="form_content" style="margin-bottom: 4%;">
								<div style="float:left; width:15%;">
									<label>Reason</label>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="reasonfordeath" placeholder="Reason" name="txtreasonfordeath"></textarea>
								</div>
							</div></br>
							<div style="margin: 2% 2% 5% 39%;">
								<div class="btn" style="float:left;" onclick="addChilddeath()">Add</div>
								<div class="btn" style="float:left;" onclick="removefromTable(childdeathtable)">Remove</div>
							</div>
							<div class="form_content">
								<table id="childdeathtable" Style="width:100%;">
									<tr class="thead" id="thead">
										<th id="thead">Name</th>
										<th id="thead">Date</th>
										<th id="thead">Reason</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="form_content" style="margin-bottom: 7%;">
					<div style="float:left; width:5%;">
						<label>Notes</label></br>
					</div>
					<div style="float:left; width:95%;">
						<textarea style="width: 90%; height: 90%;" rows="4" cols="80" id="notesformother" placeholder="Notes" name="txtnotesformother"></textarea></br>
					</div>
				</div>
				</div>
				<div class="form_content" style="float:right;">
					<button type="button" onclick="doSubmit()" id="myform">Register</button>
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
<jsp:include page="setlocation.jsp" />
<%
	String alert = (String) request.getAttribute("finalAlert");
	if(alert != null){
		out.print(alert);
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