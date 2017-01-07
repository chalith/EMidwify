<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/search.css">
<link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<%
String utype = (String) session.getAttribute("usertype");
if(utype==null){
	out.print("<script>window.location=\"/EMidwify\";</script>");
}
%>
<script>
$(document).ready(function(){
	$('#search').keyup(function(){
		if(document.getElementById("search").value != ""){
			var name=document.getElementById("search").value;
			loadSuggessions(name,"results");
		}
		else
	    	document.getElementById("results").innerHTML = "";
	});
	$('#results').on('click',function(event){
		var id = event.target.id;
		var person;
		var parent = $(event.target).parent().attr('id');
		if(parent=="motherresults"){
			person="mother";
		}
		if(parent=="childrenresults"){
			person="child";
		}
		var parent = $("#"+parent).parent().attr('id');
		if(parent=="motherresults"){
			person="mother";
		}
		if(parent=="childrenresults"){
			person="child";
		}
		if(person=="child"){
			//alert(cid);
			if(id.length>2){
				window.close();
				<%
					if(utype.equals("Midwife")){
				%>
				window.location = "midwife/viewchild.jsp?childid="+id;
				<%
					}else{
				%>
				window.location = "supervisor/viewchild.jsp?childid="+id;
				<%
					}
				%>
			}
		}else if(person=="mother"){
			if(id.length>2){
				window.close();
				window.close();
				<%
					if(utype.equals("Midwife")){
				%>
				window.location = "midwife/viewmother.jsp?guardianid="+id;
				<%
					}else{
				%>
				window.location = "supervisor/viewmother.jsp?guardianid="+id;
				<%
					}
				%>
			}
		}
	});
});
function loadSuggessions(name,result){
	var xmlhttp = new XMLHttpRequest();
	var url="loadsuggestions"; 
	url=url+"?name="+name;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
			    var out = xmlhttp.responseText;
			    if(out!="")
			    	document.getElementById(result).innerHTML = out;
			    else
			    	documxent.getElementById(result).innerHTML = "";
			}
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}

</script>
<title>Insert title here</title>
</head>
<body>
	<div>
		<input class="search" id="search" placeholder="Enter the name here"></input>
		<div id="results" class="suggession">
			
		</div>
	</div>
</body>
</html>