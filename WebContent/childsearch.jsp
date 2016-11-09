<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/search.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script>
$(document).ready(function(){
	$('#childsearch').keyup(function(){
		if(document.getElementById("childsearch").value != ""){
			var name=document.getElementById("childsearch").value;
			loadChildren(name,"children");
		}
		else
	    	document.getElementById("children").innerHTML = "";
	});
	$('#children').click(function(event){
		var cid = event.target.id;
		if(cid!="children"){
			//alert(cid);
			if(cid.length>2){
				window.close();
				window.location = "midwife/viewchild.jsp?childid="+cid;
			}
		}
	});
});
function loadChildren(name,result){
	var xmlhttp = new XMLHttpRequest();
	var url="loadchildsuggestions"; 
	url=url+"?cname="+name;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    if(out!="")
		    	document.getElementById(result).innerHTML = out;
		    else
		    	document.getElementById(result).innerHTML = "";
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
		<input class="search" id="childsearch" placeholder="Enter the child name here  &#128270"></input>
		<div id="children" class="suggession">
			
		</div>
	</div>
</body>
</html>