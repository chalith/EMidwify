$(window).load(function(){
	loadArea();
});
$(document).ready(function(){
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
		}
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
});
function removefromTable(table){
	var row = $('.selected').closest("tr").index();
	if(row>0){
		table.deleteRow(row);
	}
}
function getRowID(row){
	return $(tr).find('td:eq(0)').text();
}
function loadArea(){
	var xmlhttp = new XMLHttpRequest();
    var url="systemareas";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addAreaRow(objct[k[i]].areaCode,objct[k[i]].area,objct[k[i]].midwifeID,objct[k[i]].midwifeName);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addAreaRow(areacode,area,midwifeid,midwifename) {
	var table = document.getElementById("areatbl");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	cell1.innerHTML=areacode;
	cell2.innerHTML=area;
	cell3.innerHTML=midwifeid;
	cell4.innerHTML=midwifename;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function addAreaToAdd() {
	var areacode = document.getElementById("areacode");
	var areaname = document.getElementById("areaname");
	var midwifeid = document.getElementById("midwifeid");
	if(areacode.value==""){
		setError(areacode);
		showError("Please enter the areacode");
	}
	else if(areaname.value==""){
		setError(areaname);
		showError("Please enter the area name");
	}
	else if(midwifeid.value==""){
		setError(midwifeid);
		showError("Please select a midwife");
	}
	else{
		var midwife = midwifeid.value;
		var midwifename = midwifeid.options[midwifeid.selectedIndex].text.split(')')[1];
		var table = document.getElementById("areatoaddtbl");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=areacode.value;
		cell2.innerHTML=areaname.value;
		cell3.innerHTML=midwife;
		cell4.innerHTML=midwifename;
		areacode.value=increaseCode(areacode.value);
		areaname.value="";
		midwifeid.value="";
	}
}
function increaseCode(code){
	var prefix="";
	var number="";
	var i=0;
	while(isNaN(code.charAt(i))){
		prefix += code.charAt(i);
		i++;
	}
	while((i<code.length)&&(!isNaN(code.charAt(i)))){
		number += code.charAt(i);
		i++;
	}
	var number=parseInt(number)+1;
	return prefix+number;
}
function decreaseCode(code){
	var prefix="";
	var number="";
	var i=0;
	while(isNaN(code.charAt(i))){
		prefix += code.charAt(i);
		i++;
	}
	while((i<code.length)&&(!isNaN(code.charAt(i)))){
		number += code.charAt(i);
		i++;
	}
	var number=parseInt(number)-1;
	return prefix+number;
}
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function sendAreas(){
	var TableData="";
	$('#areatoaddtbl tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("areatoaddtbl");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please add area");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("areas").value = sendAreas();
		document.forms["areaForm"].submit();
		loadArea();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["eventForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}