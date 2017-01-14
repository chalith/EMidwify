<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<title>Edit Stats</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="admin/css/editvaccine.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<link rel="stylesheet" type="text/css" href="admin/css/form.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="admin/js/editstatistics.js"></script>
<script src="admin/js/createviewforall.js"></script>
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
<%
	String alert = (String) request.getAttribute("finalAlert");
	if(alert != null){
		out.print(alert);
	}
%>
<div>
<div class="container">
		<div style="width:75%; height:10%; position:relative;">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="backbody" id="container">
			<div class="body">
				<form name="statForm" method="post">
					<div class="form_content" style="border: solid; display: inline-block; width: 95%;">
					<div class="form_content" style="float:left; width: 46%;">
						<center>
							<h1>Mother Vaccine</h1>
						</center>
						<div class="area">
							<table id="mothervaccinetbl" Style="width:100%;">
								<tr class="thead" id="thead">
									<th id="thead">VaccineCode</th>
									<th id="thead">VaccineName</th>
									<th id="thead">Age</th>
									<th id="thead">Amount</th>
								</tr>
							</table>
						</div>
					</div>
					<div class="form_content" style="float:left; width: 46%;">
						<center>
							<h1>Child Vaccine</h1>
						</center>
						<div class="area">
							<table id="childvaccinetbl" Style="width:100%;">
								<tr class="thead" id="thead">
									<th id="thead">VaccineCode</th>
									<th id="thead">VaccineName</th>
									<th id="thead">Age</th>
									<th id="thead">Amount</th>
								</tr>
							</table>
						</div>
					</div>
					</div>
					<div class="form_content">
					<div class="form_content" style="padding-top:4%; padding-bottom: 5%;">
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">VaccineCode</label>
								</div>
								<div style="float:left; width:70%;">
									<select name="txtcode" id="code" style="width: 80%;">
										<option selected disabled option value="">Vaccine</option>
										<%
											JDBC jdbc = new JDBC();
											ArrayList<String[]> vaccines = new ArrayList<String[]>();
											String options="";
											try{
												String q="SELECT vaccineCode,vaccineName FROM vaccine;";
												Statement st=jdbc.conn.createStatement();
												st.executeQuery(q);
												ResultSet rs = st.getResultSet();
												while(rs.next()){
													String s[] = {(String) rs.getString("vaccineCode"),(String) rs.getString("vaccineName")};
													vaccines.add(s);
												}
											}catch(Exception e){
												System.out.println(e);
											}
											finally{	
												try {
													jdbc.conn.close();
												} catch (SQLException e1) {
													e1.printStackTrace();
												}
											}
											for(int i=0;i<vaccines.size();i++){
												options = options+"<option value="+vaccines.get(i)[0]+">("+vaccines.get(i)[0]+") "+vaccines.get(i)[1]+"</option>";
											}
											out.print(options);
										%>
									</select>
								</div>
							</div>
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Table To Add</label>
								</div>
								<div style="float:left; width:70%;">
									<select name="txttable" id="table" style="width: 80%;">
										<option selected disabled option value="">Table</option>
										<option option value="child">Child</option>
										<option option value="mother">Mother</option>
									</select>
								</div>
								<a style="color: red;">Only for add fields</a>
							</div>
						</div>
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Age</label>
								</div>
								<div style="float:right; width:70%;">
									<input style="width:80%;" type="text" id="age" placeholder="Age" name="txtage"/>
								</div>
							</div>
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Amount</label>
								</div>
								<div style="float:right; width:70%;">
									<input style="width:80%;" type="text" id="amount" placeholder="Amount" name="txtamount"/>
								</div>
							</div>
						</div>
						</br>
						<div style="margin: 5% 2% 8% 30%;">
							<div class="btn" style="width:8%; float:left;" onclick="add()">Add</div>
							<!-- <div class="btn" style="width:8%; float:left;" onclick="edit()">Edit</div>  -->
							<div class="btn" style="width:8%; float:left;" onclick="deleteit()">Delete</div>
						</div>
						</div>
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
</body>

</html>