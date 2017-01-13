<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<title>Edit Area</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="admin/css/editvaccine.css">
<link rel="stylesheet" type="text/css" href="admin/css/main.css">
<link rel="stylesheet" type="text/css" href="admin/css/form.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="admin/js/editarea.js"></script>
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
				<form name="areaForm" method="post">
					<div class="form_content" style="border: solid;">
						<center>
							<h1>Area</h1>
						</center>
						<div class="area">
							<table id="areatbl" Style="width:100%;">
								<tr class="thead" id="thead">
									<th id="thead">AreaCode</th>
									<th id="thead">AreaName</th>
									<th id="thead">MidwifeID</th>
									<th id="thead">MidwifeName</th>
								</tr>
							</table>
						</div>
					</div>
					<div class="form_content">
					<div class="form_content" style="padding-top:4%; padding-bottom: 5%;">
						<div class="form_content">
							<div style="float:left; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">AreaCode</label>
								</div>
								<div style="float:right; width:70%;">
									<input style="width:80%; background:#E6E6E6; " type="text" id="areacode" placeholder="AreaCode" name="txtareacode" readonly="readonly"/>
								</div>
							</div>
							<div style="float:right; width:50%;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">AreaName</label>
								</div>
								<div style="float:left; width:70%;">
									<input style="width:80%;" type="text" id="areaname" placeholder="AreaName" name="txtareaname">
								</div>
							</div>
							
						</div>
						<div class="form_content">
							<div style="width:50%; float: center;">
								<div style="float:left; width:30%;">
									<label style="float:left; margin-right: 5%;">Midwife</label>
								</div>
								<div style="float:left; width:70%;">
									<select name="txtmidwifeid" id="midwifeid" style="width: 80%;">
										<option selected disabled option value="">Midwife</option>
										<%
											JDBC jdbc = new JDBC();
											ArrayList<String[]> midwives = new ArrayList<String[]>();
											String options="";
											try{
												String q="SELECT midwifeID,name FROM midwife;";
												Statement st=jdbc.conn.createStatement();
												st.executeQuery(q);
												ResultSet rs = st.getResultSet();
												while(rs.next()){
													String s[] = {(String) rs.getString("midwifeID"),(String) rs.getString("name")};
													midwives.add(s);
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
											for(int i=0;i<midwives.size();i++){
												options = options+"<option value="+midwives.get(i)[0]+">("+midwives.get(i)[0]+") "+midwives.get(i)[1]+"</option>";
											}
											out.print(options);
										%>
									</select>
								</div>
							</div>
						</div>
						</br>
						<div style="margin: 5% 2% 8% 39%;">
							<div class="btn" style="float:left;" onclick="doSubmit()">Edit</div>
							<div class="btn" style="float:left;" onclick="deleteArea()">Delete</div>
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