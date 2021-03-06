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
	var code = document.getElementById("epidemiccode");
	var name = document.getElementById("epidemicname");
	var description = document.getElementById("description");
	if(code.value==""){
		setError(code);
		showError("Please enter the Epidemic code");
		return false;
	}
	else if(name.value==""){
		setError(name);
		showError("Please enter the Epidemic name");
		return false;
	}
	return true;
}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		addEpidemic();
	}
}
function addEpidemic(){
	if(testSubmit()){
		var code = document.getElementById('epidemiccode').value;
		var name = document.getElementById('epidemicname').value;
		var description = document.getElementById('description').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="adddata";
	    url = url+"?table=epidemic&code="+code+"&name="+name+"&description="+description;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('epidemiccode').value=increaseCode(code);
				document.getElementById('epidemicname').value = "";
				document.getElementById('description').value = "";
				clearTable('epidemictbl');				
				loadEpidemics();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function validateNumber(txtnumber){
	var number = document.forms["epidemicForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}