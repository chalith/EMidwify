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
	$('#mothersearch').keyup(function(){
		if(document.getElementById("mothersearch").value != ""){
			var name=document.getElementById("mothersearch").value;
			loadMothers(name,"mothers");
		}
		else
	    	document.getElementById("mothers").innerHTML = "";
	});
	$('#mothers').click(function(event){
		var gid = event.target.id;
		if(gid!="mothers"){
			//alert(cid);
			if(gid.length>2){
				window.close();
				window.location = "midwife/viewmother.jsp?guardianid="+gid;
			}
		}
	});
	
});
function loadMothers(name,result){
	var xmlhttp = new XMLHttpRequest();
	var url="loadmothersuggestions"; 
	url=url+"?name="+name;
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
		<input class="search" id="mothersearch" placeholder="Enter the mother name here  &#128270"></input>
		<div id="mothers" class="suggession">
			
		</div>
	</div>
</body>
</html>