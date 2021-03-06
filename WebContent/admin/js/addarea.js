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
	var code = document.getElementById("areacode");
	var name = document.getElementById("areaname");
	var midwifeid = document.getElementById("midwifeid");
	if(code.value==""){
		setError(code);
		showError("Please enter the Area code");
		return false;
	}
	else if(name.value==""){
		setError(name);
		showError("Please enter the Area name");
		return false;
	}
	else if(midwifeid.value==""){
		setError(midwifeid);
		showError("Please select a midwife");
		return false;
	}
	return true;

}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		addArea();
	}
}
function addArea(){
	if(testSubmit()){
		var code = document.getElementById('areacode').value;
		var name = document.getElementById('areaname').value;
		var midwifeid = document.getElementById('midwifeid').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="adddata";
	    url = url+"?table=area&code="+code+"&name="+name+"&midwifeid="+midwifeid;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('areacode').value=increaseCode(code);
				document.getElementById('areaname').value = "";
				document.getElementById('midwifeid').value = "";
				clearTable('areatbl');				
				loadArea();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function validateNumber(txtnumber){
	var number = document.forms["midwifeidForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}