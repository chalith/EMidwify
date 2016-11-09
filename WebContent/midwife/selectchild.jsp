<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Select a child</title>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/select.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/selectchild.js"></script>
<script>
$(document).ready(function(){
	$('#childselectclose').on("click",function(){
		$('#childselect').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
	$('#children').click(function(event){
		var child = event.target.id;
		if(child!="children"){
			var cid = child;
			if(child.length>2){
				window.location = "midwife/viewchild.jsp?childid="+cid;
			}
		}
	});
	$('#area').change(function(){
		var xmlhttp = new XMLHttpRequest();
		var id=document.getElementById("area").value;
	    var url="mothersselect"; 
	    url=url+"?area="+id;
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
		        var users = xmlhttp.responseText;
		        document.getElementById("selectmothers").innerHTML = users;
			}
		};
		
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	});
	$('#selectmothers').change(function(){
		var xmlhttp = new XMLHttpRequest();
		var id=document.getElementById("selectmothers").value;
		var url="childrenselect"; 
		url=url+"?gid="+id;
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
		        var users = xmlhttp.responseText;
		        document.getElementById("children").innerHTML = users;
			}
		};
		
		xmlhttp.open("GET",url,true);
		xmlhttp.send(null);
	});
});

</script>
</head>
<body>
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			out.print("<script>close.window(); open.window(\"/EMidwify\",\"_self\"); </script>");
		}
	%>
	<div class="select" id="childselect">
		<h1 id="childselectclose" class="selectclose">X</h1>
		<div class="content">
			<div style="width: 99%;">
			<jsp:include page="/childsearch.jsp" />
			</div>
			<select name="area" id="area" style="margin-top: -3%">
				<%
					Areas a = new Areas(mid);
					ArrayList<String[]> areaArr = null;
					areaArr = a.getAreas();
					String areas = "<option disabled selected value>Area</option>";
					for(int i=0;i<areaArr.size();i++){
						areas = areas+"<option value="+areaArr.get(i)[0]+">("+areaArr.get(i)[0]+") "+areaArr.get(i)[1]+"</option>";
					}
					out.print(areas);
				%>
			</select>
			<select name="selectmothers" id="selectmothers">
				<option disabled selected value>Mother Name</option>
			</select>
			
			<div style="width:100%; height:100%;" id="children">
				
			</div>
		</div>
	</div>
</body>
</html>