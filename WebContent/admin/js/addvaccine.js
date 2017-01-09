$(window).load(function(){
	loadVaccines();
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
function loadVaccines(){
	var xmlhttp = new XMLHttpRequest();
    var url="systemvaccines";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var objct = JSON.parse(xmlhttp.responseText);	        
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addVaccineRow(objct[k[i]].vaccineCode,objct[k[i]].vaccineName,objct[k[i]].description);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addVaccineRow(vaccinecode,vaccinename,discription) {
	var table = document.getElementById("vaccinetbl");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML=vaccinecode;
	cell2.innerHTML=vaccinename;
	cell3.innerHTML=discription;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function addVaccineToAdd() {
	var vaccinecode = document.getElementById("vaccinecode");
	var vaccinename = document.getElementById("vaccinename");
	var description = document.getElementById("description");
	if(vaccinecode.value==""){
		setError(vaccinecode);
		showError("Please enter the vaccinecode");
	}
	else if(vaccinename.value==""){
		setError(vaccinename);
		showError("Please enter the vaccine name");
	}
	else{
		var table = document.getElementById("vaccinetoaddtbl");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		cell1.innerHTML=vaccinecode.value;
		cell2.innerHTML=vaccinename.value;
		cell3.innerHTML=description.value;
		vaccinecode.value=increaseCode(vaccinecode.value);
		vaccinename.value="";
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

function sendVaccines(){
	var TableData="";
	$('#vaccinetoaddtbl tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("vaccinetoaddtbl");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please add vaccine");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("vaccines").value = sendVaccines();
		document.forms["vaccineForm"].submit();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["vaccineForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}