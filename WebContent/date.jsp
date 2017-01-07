<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="js/date.js"></script>
<title>Insert title here</title>
<base href="${pageContext.request.contextPath}/" />
</head>
<body>
<%
	String cDate = (String) session.getAttribute("date");
	if(cDate==null){
		response.sendRedirect("/EMidwify");
		return;
	}
%>
<select id="year" class="year">
	<option selected value>Year</option>
	<%	
		for(int i=1900;i<=Integer.parseInt(cDate.substring(0,4));i++){
			out.print("<option>"+i+"</option>");
		}
	%>	
</select>
<select id="month" class="month">
	<option selected value>Month</option>
	<option>January</option>
	<option>February</option>
	<option>March</option>
	<option>April</option>
	<option>May</option>
	<option>June</option>
	<option>July</option>
	<option>August</option>
	<option>September</option>
	<option>October</option>
	<option>November</option>
	<option>December</option>
</select>
<select id="day" class="day">
	<option selected value>Day</option>
</select>
<script>
	d('.month');
	d('.year');
	var m = {'January':'1','February':'2','March':'3','April':'4','May':'5','June':'6','July':'7','August':'8','September':'9','October':'10','November':'11','December':'12'};
	var year,month,day;
	//document.getElementById(parentid.substring(5)).value = "";
	function d(c){
		$(c).change(function(event){
			var parentid = event.target.parentNode.id;
			year = $('#'+parentid+' #year').val();
			var x = $('#'+parentid+' #month').val();
			month = m[x];
			var days = getDays(year,month);
			var out = "<option selected>Day</option>";
			for(var i=1;i<=days;i++){
				out = out + "<option>"+i+"</option>";
			}
			$('#'+parentid+' #day').html(out);
		});
	}
	$('.day').change(function(event){
		var parentid = event.target.parentNode.id;
		year = $('#'+parentid+' #year').val();
		var x = $('#'+parentid+' #month').val();
		month = m[x];
		day = $('#'+parentid+' #day').val();
		if(day!='Day'){
			document.getElementById(parentid.substring(5)).value = year+"-"+month+"-"+day;
		}
		else{
			document.getElementById(parentid.substring(5)).value = "";
		}
	});
	var parents = document.getElementsByClassName("year");
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