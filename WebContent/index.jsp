<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>LogIn</title>
<head>
<base href="${pageContext.request.contextPath}/" />
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$('#loginbtn').on('click',function(){
		$('#login').slideDown("slow");
		document.getElementById("container_block").style.opacity="0.6";
	});
});
</script>
</head>
		
<body>
	<jsp:include page="alert.jsp" />
	<jsp:include page="login.jsp" />
	
	<%
		String mid = (String)session.getAttribute("mid");
		if(mid!=null){
			if(mid.startsWith("Midwife")){
				response.sendRedirect("midwife/midwifefrontpage.jsp");
			}
			if(mid.startsWith("Supervisor")){
				response.sendRedirect("supervisor/supervisorfrontpage.jsp");
			}
			else if(mid.startsWith("Guard")){
				response.sendRedirect("mother/motherfrontpage.jsp");
			}
		}
		String warning = (String) request.getAttribute("warning");
		if(warning != null){
			out.print(warning);
		}
	%>
	<div class="header">
		<table style="width: 100%; height: 100%;">
			<tr>
				<th style="width: 15%; color: #796CCA;">
					<h2>Easy Midwify</h2>
				</th>
				<th style="width: 75%; color: white;">
					<h1 align="center">With women, for a life time</h2>	
				</th>
				<th id="loginbtn" style="font-family:Trebuchet MS, Helvetica, sans-serif; font-size: 110%; width: 10%; color:gray;">
					<a style="padding: 5px;" id="loginbtn">Log In</a>
				</th>
			</tr>
		</table>
	</div>
	<div id="container" style="margin-top: 5%;">
		<div class="container_block" id="container_block">
			<div class="section">
				<div class="mySlides fade" id="slide1" style="display: none;">
				 	<img src="images/Woman.jpg" style="width:100%; box-shadow:1px 10px 5px 1px black inset;">
				</div>
				<div class="mySlides fade" id="slide2" style="display: none;">
				 	<img src="images/mother.jpg" style="width:100%;">
				</div>
				<div class="mySlides fade" id="slide3" style="display: none;">
				 	<img src="images/baby.jpg" style="width:100%;">
				</div>
				<div class="mySlides fade" id="slide4" style="display: none;">
				 	<img src="images/banner.jpg" style="width:100%;">
				</div>
				<div class="mySlides fade" id="slide5" style="display: none;">
					<img src="images/nursecareers.jpg" style="width:100%;">
				</div>
				<div class="mySlides fade" id="slide6" style="display: none;">
					<img src="images/banner2.jpg" style="width:100%;">
				</div>
				<div class="mySlides fade" id="slide7" style="display: none;">
					<img src="images/banner3.jpg" style="width:100%;">
				</div>
				<script>
					var inrvl = 3000;
					var slides = document.getElementsByClassName("mySlides");
					var n = slides.length;
					slider();
					setInterval("slider();", inrvl*n);
					function slider(){
						for(var i=0;i<n;i++){
							var id = "#slide"+(i+1);
							showSlide(id,i*inrvl);
							hideSlide(id,inrvl+(i*inrvl));	
						}
					}
					function showSlide(id,time) {
						var timeout;
						timeout = window.setTimeout(function(){
							$(id).toggle("slow");
						}, time);
					}
					function hideSlide(id,time) {
						var timeout;
						timeout = window.setTimeout(function(){
							$(id).toggle("slow");
						}, time);
					}
				</script>
			</div>
			<div class="about">
				<center>
					<div class="sec">
						<p><font color="black"><b> We are here to educate you, guide you and take care of you and your child
						We simply generate reports and graphs according to the collected data.
						provide a better communication amoung midwiwives and your self!
						</b></p>
					</div>
					<div class="sec">
						<p><font color="black"><b> Advance the health and wellbeing of women and newborns by setting the standards for midwifery excellence by automating the current system”
						</b></p>
					</div>
				</center>
			</div>
			<div class="mid">
				<div class="midsec" style="width: 30%">
					<h3 align="left"><font color="black">Articles</h3></font>
					<h3><font color="blue"><a href="">Birth Is a Human Rights Issue</a></h3></font>
		
					<h5><font color="brown">CONFERENCE 2016.8.28 </font></h5>
					<p align="left"><font color="black">Join us in Strasbourg, France, this October as we highlight
						the human rights violations around the world.
						You need to feel safe in your practice.
						Women need to feel safe in the way births are done.
						In addition to exploring these issues,
						our conference will inspire, encourage and refresh you. 
					</p>
				</div>
				<div class="midsec" style="border: solid;">
					<video width="400" controls>
						<source src="The Invisible Hand.mp4" type="video/mp4">
					</video>
				</div>
			</div>
		</div>
		<div class="footer">
			<div style="width: 70%; float: left;">
				<div style="width: 100%;">
					<a href="">Home</a>
					<a href="">Our Work</a>
					<a href="">Contact Us</a>
					<a href="">Privacy Policy</a>
					<a href="">FAQ</a>
					<a href="">shop</a>
				</div><br><br><br>
				<div class="follow">
					<img src="images/fb.png" >
					<img src="images/tw.png" >
					<img src="images/pi.png" >
					<img src="images/g.ico" >
				</div>
			</div>
			<div class="details">
				Copyrights &copy; Domain Name. All rights Reseved</br>
				No56565</br>
				Maharagama</br>
				Colombo</br>
				Htc@yahoo.com</br>
				011-67656564
				</br>
				</br>
			</div>
		</div>
	</div>
		
</body>
</html>