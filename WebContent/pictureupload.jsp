<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/pictureupload.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<title>Insert title here</title>
<script>
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
function doUpload(){
	if(testUpload()){
		document.forms["pictureForm"].submit();
		document.forms["pictureForm"].reset();
	}
}
function testUpload(){
	var path = document.getElementById("sourcepath");
	var extention = path.value.split(".").pop();
	if(path.value == ""){
		setError(path);
		showalert("Please select an image");
		return false;
	}
	else if(!((extention=="jpg")||(extention=="jpeg")||(extention=="bmp")||(extention=="gif")||(extention=="png"))){
		setError(path);
		showalert("Invalid file format");
		return false;
	}
	return true;
}
function uploadImage(){
	var xmlhttp = new XMLHttpRequest();
    var url="setimage";
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var path = xmlhttp.responseText;
	        alert(path)
	        $('#pic').attr("src",path);
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
$(document).ready(function(){
	$('#sourcepath').change(function(){
		//uploadImage();
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
	$('#uploadclose').on('click',function(){
		$('#uploadpicture').toggle("slow");
		document.getElementById("container").style.opacity="1";
	});
});
</script>
</head>
<body>
	<%
		String id = (String)session.getAttribute("mid");
		if(id==null){
			response.sendRedirect("/EMidwify");
		}
	%>
	<div class="uploadpicture" id="uploadpicture">
	<a id="uploadclose" class="uploadclose">X</a>
		<form class="picform" name="pictureForm" method="post" action="uploadimage" enctype="multipart/form-data">
			<input style="width:100%; background-color:white; margin-top: 5%;" type="file" id="sourcepath" name="txtsourcepath"/>
			<table class="pictable">
				</tr>
				<tr style="height: 80%;">
					<th style="width: 70%;">
						<img src="" style="width:100%;" alt="pic" id="pic"></img>
					</th>
					<th style="width: 30%;">
						<center>
						<button class="button" type="button" style="width:100%; margin-top: 100%;" onclick="doUpload()">Upload</button>
						</center>
					</th>
				</tr>
			</table>
		</form>	
	</div>
	<jsp:include page="/alert.jsp" />
	<jsp:include page="/error.jsp" />
	<%
		String alert = (String) request.getAttribute("warning");
		if(alert != null){
			out.print(alert);
		}
	%>
</body>
</html>