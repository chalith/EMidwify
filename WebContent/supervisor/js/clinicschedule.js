$(document).ready(function(){
	$("input").keypress(function(){
		alert();
		if(event.which == 13){
			event.preventDefault();
			doSubmit();
		}
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
	$('#scheduledyear,#scheduledarea').change(function(){
		var year = document.getElementById("scheduledyear").value;
		var area = document.getElementById("scheduledarea").value;
		if((year!="")&&(area!="")){
			clearTable("scheduledclinictbl");
			loadClinic(year, area);
		}
	});
});
function loadClinic(year,area){
	var xmlhttp = new XMLHttpRequest();
    var url="loadclinicschedule";
    url = url+"?year="+year+"&area="+area;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addClinicRow(objct[k[i]].date,objct[k[i]].venue,objct[k[i]].time);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addClinicRow(date,venue,time) {
	var table = document.getElementById("scheduledclinictbl");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML=date;
	cell2.innerHTML=venue;
	cell3.innerHTML=time;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function addEvent() {
	var area = document.getElementById("area1");
	var date = document.getElementById("eventdate");
	var time = document.getElementById("eventtime");
	var venue = document.getElementById("eventvenue");
	var earea = document.forms["eventForm"]["area1"];
	var etime = document.forms["eventForm"]["txteventtime"];
	var evenue = document.forms["eventForm"]["txteventvenue"];
	var edate = document.forms["eventForm"]["txteventdate"];
	if(edate.value==""){
		setError(date);
		showError("Please enter the date");
	}
	else if(earea.value==""){
		setError(area);
		showError("Select a area");
	}
	else if(etime.value==""){
		setError(time);
		showError("Enter the time");
	}
	else if(evenue.value==""){
		setError(venue);
		showError("Enter the venue");
	}
	else{
		var table = document.getElementById("eventtable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=date.value;
		cell2.innerHTML=area.value;
		cell3.innerHTML=venue.value;
		cell4.innerHTML=time.value;
		area.value="";
		date.value="";
		time.value="";
		venue.value="";
	}
}
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function sendEvents(){
	var TableData="";
	$('#eventtable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+" ~column~ "+$(tr).find('td:eq(4)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("eventtable");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please add clinic schedule");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("evnts").value = sendEvents();
		document.forms["eventForm"].submit();
		document.forms["eventForm"].reset();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["eventForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}