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
    var url="systemdiseases";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addDiseaseRow(objct[k[i]].diseaseCode,objct[k[i]].diseaseName,objct[k[i]].description);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addDiseaseRow(diseasecode,diseasename,discription) {
	var table = document.getElementById("diseasetbl");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML=diseasecode;
	cell2.innerHTML=diseasename;
	cell3.innerHTML=discription;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function addDiseaseToAdd() {
	var diseasecode = document.getElementById("diseasecode");
	var diseasename = document.getElementById("diseasename");
	var description = document.getElementById("description");
	if(diseasecode.value==""){
		setError(diseasecode);
		showError("Please enter the diseasecode");
	}
	else if(diseasename.value==""){
		setError(diseasename);
		showError("Please enter the disease name");
	}
	else{
		var table = document.getElementById("diseasetoaddtbl");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		cell1.innerHTML=diseasecode.value;
		cell2.innerHTML=diseasename.value;
		cell3.innerHTML=description.value;
		diseasecode.value=increaseCode(diseasecode.value);
		diseasename.value="";
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

function sendDiseases(){
	var TableData="";
	$('#diseasetoaddtbl tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("diseasetoaddtbl");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please add disease");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("diseases").value = sendDiseases();
		document.forms["diseaseForm"].submit();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["diseaseForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}