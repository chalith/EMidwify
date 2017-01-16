<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.main.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>Visit - Locations</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="midwife/css/location.css">
<link rel="stylesheet" type="text/css" href="midwife/css/main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="js/new.js"></script>
<script src="jquery.json-2.4.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="midwife/js/createviewforall.js"></script>
<script src="midwife/js/viewlocation.js"></script>
<script>
</script>
</head>
<body >
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid==null){
			out.print("<script>window.location=\"\";</script>");
			return;
		}
	%>
<div style="position: static;">
	<div class="container">
		<div style="width:75%; height:10%; position:relative;">
			<jsp:include page="header.jsp"/>
		</div>
		<div class="backbody">
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
							request.setAttribute("areas", areas);
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
			<div class="body" style="position: relative; overflow: hidden;">
				<div id="map" style="width:100%; height:100%; position: absolute;">
				
				
				<script>
				  function initMap() {
					var mapDiv = document.getElementById('map');
					var map = new google.maps.Map(mapDiv, {
						center: {lat: 7.027864401135612, lng:79.91975798721319},
						zoom: 15
					});
				  
				  
				  var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
			        var icons = {
			        			info: {
			            		icon: iconBase + 'info-i_maps.png'
			          			}
			        };
			        
			      function addMarker(x,y,t) {
			            var marker = new google.maps.Marker({
			            	position: new google.maps.LatLng(x,y),
	                        type: 'info',
	                        title: t,
	                        map: map
			            });
			          }
			      loadLocation();
			      function loadLocation(){
			    		var xmlhttp = new XMLHttpRequest();
			    		var url="viewlocation";
			    		xmlhttp.onreadystatechange = function() {
			    			if(xmlhttp.readyState==4 && xmlhttp.status==200){
			    				var out = JSON.parse(xmlhttp.responseText);
			    				var k = Object.keys(out);
			    				title = "";
			    				for(var i=0;i<k.length;i++){
			    					var x = out[k[i]].location.split(", ");
			    					if((i>0)&&(out[k[i]].location == out[k[i-1]].location)){
			    						var title = title + "\n..............................\n" + out[k[i]].visittype+"\n Date : "+out[k[i]].date+"\n Child : "+out[k[i]].child+"\n Guardian : "+out[k[i]].guardian;
			    					}else{
			    						var title = out[k[i]].visittype+"\n Date : "+out[k[i]].date+"\n Child : "+out[k[i]].child+"\n Guardian : "+out[k[i]].guardian;
			    					}
			    						addMarker(x[0],x[1],title);
			    				}
			    			}
			    		};
			    		xmlhttp.open("GET",url,true);
			    		xmlhttp.send(null);
			    	}
				  }
				</script>
				
				
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCH1t63UHJVo8ILQgFrZUUnGQNef9YzgP0&callback=initMap"></script>
		
				</div>
				
			</div>
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
</body>
</html>