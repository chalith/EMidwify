<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<base href="${pageContext.request.contextPath}/" />
<title>Insert title here</title>
</head>
<body>
<select id="hours" class="hours">
	<option selected value>Hours</option>
	<%	
		for(int i=1;i<13;i++){
			out.print("<option>"+i+"</option>");
		}
	%>	
</select>
<select id="minutes" class="minutes">
	<option selected value>Minutes</option>
	<%	
		for(int i=0;i<60;i++){
			if(i<10){
				out.print("<option>0"+i+"</option>");
			}
			else{
				out.print("<option>"+i+"</option>");
			}
		}
	%>
</select>
<select id="ampm" class="ampm">
	<option selected value>am/pm</option>
	<option>am</option>
	<option>pm</option>
</select>
<script>
	var hours;
	var minutes;
	var ampm;
	changetime(".ampm");
	changetime(".hours");
	changetime(".minutes");
	function changetime(classname){
		$(classname).change(function(event){
			var parentid = event.target.parentNode.id;
			hours = $('#'+parentid+' #hours').val();
			minutes = $('#'+parentid+' #minutes').val();
			ampm = $('#'+parentid+' #ampm').val();
			if((hours!='')&&(minutes!='')&&(ampm!='')){
				var time;
				if(ampm=="am"){
					if(hours<10){
						time = "0"+hours+":"+minutes+":00";
					}
					else{
						time = hours+":"+minutes+":00";
					}
				}else{
					hours = parseInt(hours,"") + 12;
					time = hours+":"+minutes+":00";
				}
				document.getElementById(parentid.substring(5)).value = time;
			}
			else{
				document.getElementById(parentid.substring(5)).value = "";
			}
		});
	}
	var parents = document.getElementsByClassName("hours");
	/**for(var i=0;i<parents.length;i++){
		var parentid = parents[i].parentNode.id;
		var textid = parentid.substring(5);
		if(document.getElementById(textid)!=null){
			$('#'+textid).on('blur','input',function(){
				alert();
				if(document.getElementById(textid).value==""){
					$('#'+parentid+' #year').val("");
					$('#'+parentid+' #month').val("");
					$('#'+parentid+' #day').val("");
				}
			});
		}
	}*/

</script>
</body>
</html>