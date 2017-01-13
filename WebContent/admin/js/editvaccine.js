$(window).load(function(){
	loadVaccines();
});
$(document).ready(function(){
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
			selectIt();
		}
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
});
function selectIt(){
	var vcode = $('.selected').closest("tr").find('td:eq(0)').text();
	var vname = $('.selected').closest("tr").find('td:eq(1)').text();
	var description = $('.selected').closest("tr").find('td:eq(2)').text();
	document.getElementById('vaccinecode').value = vcode;
	document.getElementById('vaccinename').value = vname;
	document.getElementById('description').value = description;
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
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function testSubmit(){
	var code = document.getElementById('vaccinecode').value;
	if(code==""){
		showError("Please select vaccine");
		return false;
	}
	return true;
}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		editVaccine();
	}
}
function editVaccine(){
	if(testSubmit()){
		var code = document.getElementById('vaccinecode').value;
		var name = document.getElementById('vaccinename').value;
		var description = document.getElementById('description').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="editdata";
	    url = url+"?table=vaccine&code="+code+"&name="+name+"&description="+description;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('vaccinecode').value = "";
				document.getElementById('vaccinename').value = "";
				document.getElementById('description').value = "";
				clearTable('vaccinetbl');				
				loadVaccines();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function deleteVaccine(){
	var ok = confirm("Do you wan't to delete?");
	if(testSubmit()&&ok==true){
		var code = document.getElementById('vaccinecode').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="deletedata";
	    url = url+"?table=vaccine&code="+code;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('vaccinecode').value = "";
				document.getElementById('vaccinename').value = "";
				document.getElementById('description').value = "";
				clearTable('vaccinetbl');				
				loadVaccines();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}
function validateNumber(txtnumber){
	var number = document.forms["vaccineForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}