$(window).load(function(){
	loadEpidemics();
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
function loadEpidemics(){
	var xmlhttp = new XMLHttpRequest();
    var url="systemepidemics";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addEpidemicRow(objct[k[i]].epidemicCode,objct[k[i]].epidemicName,objct[k[i]].description);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addEpidemicRow(epidemiccode,epidemicname,discription) {
	var table = document.getElementById("epidemictbl");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML=epidemiccode;
	cell2.innerHTML=epidemicname;
	cell3.innerHTML=discription;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function addEpidemicToAdd() {
	var epidemiccode = document.getElementById("epidemiccode");
	var epidemicname = document.getElementById("epidemicname");
	var description = document.getElementById("description");
	if(epidemiccode.value==""){
		setError(epidemiccode);
		showError("Please enter the epidemiccode");
	}
	else if(epidemicname.value==""){
		setError(epidemicname);
		showError("Please enter the epidemic name");
	}
	else{
		var table = document.getElementById("epidemictoaddtbl");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		cell1.innerHTML=epidemiccode.value;
		cell2.innerHTML=epidemicname.value;
		cell3.innerHTML=description.value;
		epidemiccode.value=increaseCode(epidemiccode.value);
		epidemicname.value="";
		description.value="";
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

function sendEpidemics(){
	var TableData="";
	$('#epidemictoaddtbl tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("epidemictoaddtbl");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please add epidemic");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("epidemics").value = sendEpidemics();
		document.forms["epidemicForm"].submit();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["epidemicForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}