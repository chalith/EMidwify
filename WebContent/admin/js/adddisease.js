$(window).load(function(){
	loadDiseases();
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
function loadDiseases(){
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
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}

function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
function testSubmit(){
	var code = document.getElementById("diseasecode");
	var name = document.getElementById("diseasename");
	var description = document.getElementById("description");
	if(code.value==""){
		setError(code);
		showError("Please enter the Disease code");
		return false;
	}
	else if(name.value==""){
		setError(name);
		showError("Please enter the Disease name");
		return false;
	}
	return true;
}function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		addDisease();
	}
}
function addDisease(){
	if(testSubmit()){
		var code = document.getElementById('diseasecode').value;
		var name = document.getElementById('diseasename').value;
		var description = document.getElementById('description').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="adddata";
	    url = url+"?table=disease&code="+code+"&name="+name+"&description="+description;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('diseasecode').value=increaseCode(code);
				document.getElementById('diseasename').value = "";
				document.getElementById('description').value = "";
				clearTable('diseasetbl');				
				loadDiseases();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}

function validateNumber(txtnumber){
	var number = document.forms["diseaseForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}