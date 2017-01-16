<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Update - Mothers Clinic Details</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/update.css">
<link rel="stylesheet" type="text/css" href="midwife/css/form.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script src="midwife/js/motherupdate.js"></script>
<script>
function loadClinics(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("tempareacode").value;
	var url="loadclinicdates"; 
	url=url+"?area="+area;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("clinictdate").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
$(window).load(function(){
	loadClinics();
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
<div>
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
					
					<h2>Notifications</h2>
						
					<ul id="notifications">
						
					</ul>
					
				</div>
			</div>
			<div class="body">
				<form name="mupdateForm" action="updatemother" method="post">
					<input name="txtmotherid" id="motherid" type="hidden">
					<input name="txtmotherage" id="motherage" type="hidden">
					<input name="txttriposha" id="triposha" type="hidden">
					<input name="txtvaccine" id="vaccine" type="hidden">
					<input name="txtdisease" id="disease" type="hidden">
					<input name="txtepidemic" id="epidemic" type="hidden">
					<input name="txtchilddeath" id="childdeath" type="hidden">
					<div class="form_content">
					<% 
						String guardianid = (String)request.getParameter("id");
						String areaCode = (String)request.getParameter("areacode");					
					%>
					<input type="hidden" id="tempguardianid" value="<%
						if((guardianid!=null)){
							out.print(guardianid);
						}
						%>">
					<input type="hidden" id="tempareacode" value="<%
						if((areaCode!=null)){
							out.print(areaCode);
						}
						%>">
					<div class="form_content" style="border:solid; display: <%
						if((guardianid==null)&&(areaCode==null)){
							out.print("block");
						}else{
							out.print("none");
						}
					%>">
						<div style="float:left; width:50%;">
							<div style="float:left; width:15%;">
								<label>Area</label>
							</div>
							<div style="float:left; width:85%;">
								<select name="motherareas" id="motherareas"  style="width:80%;">
									<option disabled selected value>Area</option>
									<%
										out.print(areas);
									%>
								</select>
							</div>
						</div>
						<div style="float:left; width:50%;">
							<div style="float:left; width:30%;">
								<label>Mother Name</label>
							</div>
							<div style="float:left; width:70%;">
								<select name="mothers" id="mothers" style="width:100%;">
									<option disabled selected value>Mother Name</option>
								</select>
							</div>
						</div></br>
					</div></br>
					<div class="form_content" id="view" style="width:95.4%; height:10%; display:none; border-Style:dotted; border-color:gray;">
						<div style="float:left; width:40%;">
							<h2 id="mothername"></h2>
						</div>
						<div style="float:left; width:15%; height:90%; margin-left:45%;">
							<img style="width: 100%; height: 100%;" id="motherpicture" src="">
						</div>
					</div>
					<div class="form_content">
						<div class="form_content" style="display:inline-block; width:100%;">
							<div style="float:left; width:40%; padding-left:10%;">
								<div style="float:left; width:30%;">
									<label>Weight(kg)</label>
								</div>
								<div style="float:left; width:70%;">
									<input style="width:60%;" type="text" id="motherweight" placeholder="Weight" name="txtmotherweight">
								</div>
							</div>
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">	
									<label>Clinic Date</label>
								</div>
								<select id="clinictdate" name="txtclinicdate" style="width:50%;">
									<option selected disabled option>Clinic Dates</option>
									
								</select>
								
								<!-- <div id="date_clinictdate" style="float:left; width:70%;">
									<jsp:include page="/date.jsp" />	
								</div>
								<input type="hidden" id="clinictdate" name="txtclinicdate">  -->
							</div>
						</div>
					</div></br>
					<div class="form_content" style="border-Style:dotted;">
						<h3>Triposha</h3>
						<div class="form_content" style="margin-bottom: 1%; width: 100%;">
							<div style="float:left; width:40%; padding-left:10%;">
								<div style="float:left; width:30%;">
									<label>Amount(pckts)</label>
								</div>
								<input style="width:43%;" id="trposhaamount" name="txttriposhaamount" value="<%
									TriposhaAmounts tamount = new TriposhaAmounts(guardianid);
									out.print(tamount.getAmount());
								%>"/>
							</div>
						</div></br>
						<div style="margin: 2% 2% 7% 39%;">
							<div class="btn" style="float:left;" onclick="addTriposha()">Add</div>
							<div class="btn" style="float:left;" onclick="removefromTable(triposhatable)">Remove</div>
						</div>
						<table id="triposhatable" Style="width:100%;">
							<tr class="thead" id="thead">
								<th id="thead">Amount(pckts)</th>
							</tr>
						</table>
					</div></br>
					<div class="form_content" style="border-Style:dotted;">
						<h3>Vaccine</h3>
						<div class="form_content" style="margin-bottom: 1%;">
							<div style="float:left; width:15%;">
								<label>Vaccine Code</label>
							</div>
							<div style="float:left; width:85%;">
								<input style="width:30%;" type="text" id="vaccinecode" placeholder="VaccineCode" name="txtvaccinecode">
							</div>
						</div>
						<div class="form_content" style="margin-bottom: 1%;">
							<div style="float:left; width:15%;">
								<label>Vaccine</label>
							</div>
							<div style="float:left; width:85%;">
								<input style="width:30%;" type="text" id="vaccineName" placeholder="Vaccine" name="txtvaccinename">
							</div>
						</div>
						<div class="form_content" style="margin-bottom: 1%;">
							<div style="float:left; width:15%;">
								<label>Dose(ml)</label>
							</div>
							<div style="float:left; width:85%;">
								<input style="width:30%;" type="text" id="vaccineamount" placeholder="Dose" name="txtvaccineamount">
							</div>
						</div></br>
						<div style="margin: 2% 2% 7% 39%;">
							<div class="btn" style="float:left;" onclick="addVaccine()">Add</div>
							<div class="btn" style="float:left;" onclick="removefromTable(vaccinetable)">Remove</div>
						</div>
						<table id="vaccinetable" Style="width:100%;">
							<tr class="thead" id="thead">
								<th id="thead">Vaccine Code</th>
								<th id="thead">Vaccine</th>
								<th id="thead">Dose(ml)</th>
							</tr>
						</table>
					</div></br>
					<div class="form_content" style="border-style:dotted;">
						<div id="motherds" class="dropb">
							<h4>If any diseases </h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="motherdss">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Diseases Code</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="diseasecode" placeholder="DiseaseCode" name="txtdiseasecode"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Diseases Name</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="width:30%;" type="text" id="diseasename" placeholder="DiseaseName" name="txtdiseasename"></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date</label>
								</div>
								<div id="date_diseasedate" style="float:left; width:85%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="diseasedate" name="txtdiseasedate">	
							</div></br>
							<div class="form_content" style="margin-bottom: 4%;">
								<div style="float:left; width:15%;">
									<label>Note</label></br>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="diseasenote" placeholder="DiseaseNote" name="txtdiseasenote"></textarea></br>
								</div>
							</div></br>
							<div style="margin: 2% 2% 5% 39%;">	
								<div class="btn" style="float:left;" onclick="addDisease()">Add</div>
								<div class="btn" style="float:left;" onclick="removefromTable(diseasetable)">Remove</div>
							</div>
							<div class="form_content">
								<table id="diseasetable" Style="width:100%;">
									<tr class="thead" id="thead">
										<th id="thead">Code</th>
										<th id="thead">Name</th>
										<th id="thead">Date</th>
										<th id="thead">Note</th>
									</tr>
								</table>
							</div>
						</div>
					</div></br>
					<div class="form_content" style="border-style:dotted;">
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
								<input type="hidden" id="epidemicdate" name="txtepidemicdate">
							</div></br>
							<div class="form_content" style="margin-bottom: 4%;">
								<div style="float:left; width:15%;">
									<label>Note</label>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="epidemicnote" placeholder="EpdemicNote" name="txtepidemicnote"></textarea></br>
								</div>
							</div></br>
							<div style="margin: 2% 2% 5% 39%;">
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
					</div></br>
					<div class="form_content" style="border-style:dotted;">
						<div id="childdeath1" class="dropb">
							<h4 style="color:red;">If any child deaths</h4>
							<img  src="images/icons/dropdown.png" alt="" />
						</div>
						<div class="hide_div" id="childdeaths1">
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Child's Full Name</label>
								</div>
								<div style="float:left; width:85%;">
									<select style="width:40%;" id="children" name="deathchildname">
										<option disabled selected value>Child Name</option>
									</select>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Child ID</label>
								</div>
								<div style="float:left; width:85%;">
									<input style="background:#E6E6E6;" type="text" id="deathchildid" placeholder="DeathChildID" name="txtdeathchildid" readonly/></br>
								</div>
							</div></br>
							<div class="form_content" style="margin-bottom: 1%;">
								<div style="float:left; width:15%;">
									<label>Date of death</label>
								</div>
								<div id="date_dateofdeath" style="float:left; width:85%;">
									<jsp:include page="/date.jsp" />
								</div>
								<input type="hidden" id="dateofdeath" name="txtdateofdeath">
							</div></br>
							<div class="form_content" style="margin-bottom: 4%;">
								<div style="float:left; width:15%;">
									<label>Reason</label>
								</div>
								<div style="float:left; width:85%;">
									<textarea style="width: 90%; height: 90%;" rows="4" cols="50" id="reasonfordeath" placeholder="Reason" name="txtreasonfordeath"></textarea></br>
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
										<th id="thead">ID</th>
										<th id="thead">Date</th>
										<th id="thead">Reason</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
					</div>
					<div class="form_content" style="float:right;">
						<button type="button" onclick="doSubmit()" id="myform">Update</button>
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
%>
</body>

</html>