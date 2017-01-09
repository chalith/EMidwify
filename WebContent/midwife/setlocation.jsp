<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/setlocation.css">
<link rel="stylesheet" type="text/css" href="midwife/css/form.css">
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCH1t63UHJVo8ILQgFrZUUnGQNef9YzgP0&callback=initMap">
</script>
<script type="text/javascript" src="midwife/js/setlocation.js"></script>
</head>
<body>
	<div id="setlocation" class="locationdiv">
  		<div style="widows: 98%; height: 73%; overflow: hidden;">
  			<div id="mapCanvas" style="display:none;"></div>
  		</div>
		<form class="form_content" name="location" action="location" method="post">
			<table>
			<b>Marker status:</b>
		    <div id="markerStatus"><i>Click and drag the marker.</i></div>
		    <b>Current position:</b>
		    <div ><input  size="35" type="text"; name="txtlocation" id="info"></div>
		    <b>Closest matching address:</b>
		    <div id="address"></div>
		    <div style="display: inline-block; width: 100%;">
			    <div class="btn" style="width: 10%; float: right;" id="closelocation">Close</div>
				<div class="btn" style="width: 10%; float: right;" id="addlocation">Ok</div>
			</div>
		</form>
 	</div>
</body>
</html>