$(window).load(function(){
	loadEpidemics();
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
	var code = $('.selected').closest("tr").find('td:eq(0)').text();
	var name = $('.selected').closest("tr").find('td:eq(1)').text();
	var description = $('.selected').closest("tr").find('td:eq(2)').text();
	document.getElementById('epidemiccode').value = code;
	document.getElementById('epidemicname').value = name;
	document.getElementById('description').value = description;
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
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function testSubmit(){
	var code = document.getElementById('epidemiccode').value;
	if(code==""){
		showError("Please select epidemic");
		return false;
	}
	return true;
}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		editEpidemic();
	}
}
function editEpidemic(){
	if(testSubmit()){
		var code = document.getElementById('epidemiccode').value;
		var name = document.getElementById('epidemicname').value;
		var description = document.getElementById('description').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="editdata";
	    url = url+"?table=epidemic&code="+code+"&name="+name+"&description="+description;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('epidemiccode').value = "";
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
function deleteEpidemic(){
	var ok = confirm("Do you wan't to delete?");
	if(testSubmit()&&ok==true){
		var code = document.getElementById('epidemiccode').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="deletedata";
	    url = url+"?table=epidemic&code="+code;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('epidemiccode').value = "";
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