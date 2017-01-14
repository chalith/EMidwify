$(window).load(function(){
	loadMotherVaccine();
	loadChildVaccine();
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
	$("#code").change(function(){
		$('.selected').removeClass("selected");
		document.getElementById('age').value = "";
		document.getElementById('amount').value = "";
	});
	$('[name="txtamount"]').keyup(function(){
		validateNumber("txtamount");
	});
	$('[name="txtage"]').keyup(function(){
		validateNumber("txtage");
	});
});
function validateNumber(txtnumber){
	var number = document.forms["statForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}

function selectIt(){
	var code = $('.selected').closest("tr").find('td:eq(0)').text();
	var name = $('.selected').closest("tr").find('td:eq(1)').text();
	var age = $('.selected').closest("tr").find('td:eq(2)').text();
	var amount = $('.selected').closest("tr").find('td:eq(3)').text();
	
	document.getElementById('code').value = code;
	document.getElementById('age').value = age;
	document.getElementById('amount').value = amount;
}

function removefromTable(table){
	var row = $('.selected').closest("tr").index();
	if(row>0){
		table.deleteRow(row);
	}
}
function selectedTableID(){
	var row = $('.selected').closest('table').attr('id')
	return row;
}
function getRowID(row){
	return $(tr).find('td:eq(0)').text();
}
function loadChildVaccine(){
	var xmlhttp = new XMLHttpRequest();
    var url="systemclinicvaccine?table=childvaccineamounts";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addRow("childvaccinetbl",objct[k[i]].code,objct[k[i]].name,objct[k[i]].age,objct[k[i]].amount);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function loadMotherVaccine(){
	var xmlhttp = new XMLHttpRequest();
    var url="systemclinicvaccine?table=mothervaccineamounts";
    xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var objct = JSON.parse(xmlhttp.responseText);
	        var k = Object.keys(objct);
	        for(var i=0;i<k.length;i++){
	        	addRow("mothervaccinetbl",objct[k[i]].code,objct[k[i]].name,objct[k[i]].age,objct[k[i]].amount);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function addRow(table,areacode,area,midwifeid,midwifename) {
	var table = document.getElementById(table);
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
	var code = document.getElementById("code");
	var age = document.getElementById("age");
	var amount = document.getElementById("amount");
	if(code.value==""){
		setError(code);
		showError("Please enter code");
		return false;
	}
	else if(age.value==""){
		setError(age);
		showError("Please enter age");
		return false;
	}
	else if(amount.value==""){
		setError(amount);
		showError("Please enter amount");
		return false;
	}
	return true;

}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited?");
	if(testSubmit()&&ok==true){
		add();
	}
}
function deleteit(){
	var table = selectedTableID();
	if(table == undefined){
		showError("Please select a feild");
		return;
	}
	if(table == "mothervaccinetbl"){
		table = "mothervaccineamounts";
	}else if(table == "childvaccinetbl"){
		table = "childvaccineamounts";
	}
	
	var ok = confirm("Do you wan't to delete?");
	if(testSubmit()&&ok==true){
		var code = document.getElementById('code').value;
		var age = document.getElementById('age').value;
		var amount = document.getElementById('amount').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="deletestatdata";
	    url = url+"?table="+table+"&code="+code+"&age="+age+"&amount="+amount;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('code').value = "";
				document.getElementById('amount').value = "";
				document.getElementById('age').value = "";
				clearTable('mothervaccinetbl');
				clearTable('childvaccinetbl');
				loadMotherVaccine();
				loadChildVaccine();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function edit(){
	var table = selectedTableID();
	if(table == undefined){
		showError("Please select a feild");
		return;
	}
	if(table == "mothervaccinetbl"){
		table = "mothervaccineamounts";
	}else if(table == "childvaccinetbl"){
		table = "childvaccineamounts";
	}
	if(testSubmit()){
		var code = document.getElementById('code').value;
		var age = document.getElementById('age').value;
		var amount = document.getElementById('amount').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="editstatdata";
	    url = url+"?table="+table+"&code="+code+"&age="+age+"&amount="+amout;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('code').value = "";
				document.getElementById('age').value = "";
				document.getElementById('amount').value = "";
				clearTable('mothervaccinetbl');
				clearTable('childvaccinetbl');
				loadMotherVaccine();
				loadChildVaccine();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
function add(){
	if(testSubmit()){
		var code = document.getElementById('code').value;
		var age = document.getElementById('age').value;
		var amount = document.getElementById('amount').value;
		var xmlhttp = new XMLHttpRequest();
	    var url="addstatdata";
	    var table="";
	    table = document.getElementById('table').value;
	    if(table==""){
	    	setError(document.getElementById('table'));
			showError("Please select a table to insert");
			return;
	    }
	    else if(table=="child"){
	    	table = "childvaccineamounts";
	    }
	    else if(table=="mother"){
	    	table = "mothervaccineamounts";
	    }
	    url = url+"?table="+table+"&code="+code+"&age="+age+"&amount="+amount;
	    xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				showsuccessmessage(xmlhttp.responseText);
				document.getElementById('code').value = "";
				document.getElementById('age').value = "";
				document.getElementById('amount').value = "";
				clearTable('mothervaccinetbl');
				clearTable('childvaccinetbl');
				loadMotherVaccine();
				loadChildVaccine();
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}