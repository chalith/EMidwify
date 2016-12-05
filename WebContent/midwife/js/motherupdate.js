$(window).load(function(){
	var id = document.getElementById("tempguardianid").value;
	var areacode = document.getElementById("tempareacode").value;
	if((id!="")&&(areacode!="")){
		viewMother(id);
		$('#view').css("display","inline-block");
		viewChildren(id);
	}
});

function setError(element){
		element.style.backgroundColor = "#FFB6C1";
}
function loadMothers(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("motherareas").value;
    var url="updateloadmothers"; 
    url=url+"?area="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var users = xmlhttp.responseText;
	        document.getElementById("mothers").innerHTML = users;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function viewMother(id){
	var xmlhttp = new XMLHttpRequest();
    var url="viewmotherpicture";
    url=url+"?gid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var users = JSON.parse(xmlhttp.responseText);
	        //showError(users);
	        document.getElementById("mothername").innerHTML = users.name;
	        document.getElementById("motherpicture").src = users.picture;
	        document.getElementById("motherid").value = id;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function viewChildren(id){
	var xmlhttp = new XMLHttpRequest();
    var url="updateloadchildren";
    url=url+"?gid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var children = xmlhttp.responseText;
	        //showError(users);
	        document.getElementById("children").innerHTML = children;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function loadVaccineName(){
	var code = document.getElementById("vaccinecode").value;
	var xmlhttp = new XMLHttpRequest();
    var url="loadvaccine"; 
    url=url+"?vcode="+code;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var vaccine = xmlhttp.responseText;
	        document.getElementById("vaccineName").value = vaccine;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function loadVaccineAmount(){
	var id = document.getElementById("motherid").value;
	var code = document.getElementById("vaccinecode").value;
	var date = document.getElementById("clinictdate").value;
	if((date.length>=8)&&(id!="")&&(date!="")){
		var xmlhttp = new XMLHttpRequest();
	    var url="loadvaccineamounts"; 
	    url=url+"?id="+id+"&vcode="+code+"&date="+date;
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
		        var amount = xmlhttp.responseText;
		        if(amount != "null"){
		        	document.getElementById("vaccineamount").value = amount;
		        }
		        else{
		        	document.getElementById("vaccineamount").value = "";
		        }
			}
		};
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
	}
}
$(document).ready(function(){
	$("input").keypress(function(){
		if(event.which == 13){
			event.preventDefault();
			doSubmit();
		}
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
	$("select").change(function(){
		$(this).css({"background-color":'white'});
	});
	$('#motherareas').change(function(){
		loadMothers();
	});
	$('#mothers').change(function(){
		var id = $(this).children(":selected").attr("id");
		viewMother(id);
		$('#view').css("display","inline-block");
		viewChildren(id);
	});
	$('#children').change(function(){
		var id = $(this).children(":selected").attr("id");
		if(id!=undefined)
			document.getElementById("deathchildid").value = id;
	});
	$('[name="txtmotherweight"]').keyup(function(){
		validateNumber("txtmotherweight");
	});
	$('[name="txtvaccinecode"]').change(function(){
		loadVaccineName();
		loadVaccineAmount();
	});
	$('[name="txttriposhaamount"]').keyup(function(){
		validateNumber("txttriposhaamount");
	});
	$('[name="txtvaccineamount"]').keyup(function(){
		validateNumber("txtvaccineamount");
	});
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
		}
	});
	var dropb = ['#motherep','#childdeath1','#motherds'];
	var hidediv = ['#mothereps','#childdeaths1','#motherdss'];
	for(var i=0;i<dropb.length;i++){
		showDiv(dropb[i],hidediv[i]);
	}
});
function removefromTable(table){
	var row = $('.selected').closest("tr").index();
	if(row>0){
		table.deleteRow(row);
	}
}
function testSubmit(){
	var motherid = document.forms["mupdateForm"]["txtmotherid"];
	var motherweight = document.forms["mupdateForm"]["txtmotherweight"];
	var cdate = document.forms["mupdateForm"]["txtclinicdate"];
	if(motherid.value == ""){
		showError("Please select a mother first");
		return false;
	}
	if(motherweight.value == ""){
		setError(motherweight);
		showError("Please enter the mother weight");
		return false;
	}
	if(cdate.value == ""){
		setError(cdate);
		showError("Plese enter clinic date");
		return false;
	}
	return true;
}
function doSubmit(){
	//showError("okay");
	if(testSubmit()){
		document.getElementById("epidemic").value = sendEpidemics();
		document.getElementById("disease").value = sendDiseases();
		document.getElementById("vaccine").value = sendVaccine();
		document.getElementById("triposha").value = sendTriposha();
		document.getElementById("childdeath").value = sendChilddeaths();
		document.forms["mupdateForm"].submit();
		document.forms["mupdateForm"].reset();
	}
}
function sendEpidemics(){
	var TableData="";
	$('#epidemictable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function sendDiseases(){
	var TableData="";
	$('#diseasetable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function sendVaccine(){
	var TableData="";
	$('#vaccinetable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+"~row~";
	});
	return TableData;
}
function sendTriposha(){
	var TableData="";
	$('#triposhatable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+"~row~";
	});
	return TableData;
}
function sendChilddeaths(){
	var TableData="";
	$('#childdeathtable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function validateNumber(txtnumber){
	var number = document.forms["mupdateForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}
function addDisease() {
	var dsCode = document.getElementById("diseasecode");
	var dsName = document.getElementById("diseasename");
	var dsDate = document.getElementById("diseasedate");
	var dsNote = document.getElementById("diseasenote");
	var diseasecode = document.forms["mupdateForm"]["txtdiseasecode"];
	var dateofdisease = document.forms["mupdateForm"]["txtdiseasedate"];
	if(diseasecode.value==""){
		setError(diseasecode);
		showError("Enter the disease code first");
	}
	else if(dateofdisease.value==""){
		setError(dateofdisease);
		showError("Please enter the date");
	}
	else{
		var table = document.getElementById("diseasetable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=dsCode.value;
		cell2.innerHTML=dsName.value;
		cell3.innerHTML=dsDate.value;
		cell4.innerHTML=dsNote.value;
		dsCode.value="";
		dsName.value="";
		dsDate.value="";
		dsNote.value="";
	}
}

function addEpidemic() {
	var epCode = document.getElementById("epidemiccode");
	var epName = document.getElementById("epidemicname");
	var epDate = document.getElementById("epidemicdate");
	var epNote = document.getElementById("epidemicnote");
	var epidemiccode = document.forms["mupdateForm"]["txtepidemiccode"];
	var dateofepidemic = document.forms["mupdateForm"]["txtepidemicdate"];
	if(epidemiccode.value==""){
		setError(epidemiccode);
		showError("Enter the epidemic code first");
	}
	else if(dateofepidemic.value==""){
		setError(dateofepidemic);
		showError("Please enter the date");
	}
	else{
		var table = document.getElementById("epidemictable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=epCode.value;
		cell2.innerHTML=epName.value;
		cell3.innerHTML=epDate.value;
		cell4.innerHTML=epNote.value;
		epCode.value="";
		epName.value="";
		epDate.value="";
		epNote.value="";
	}
}
function addChilddeath() {
	var childname = document.forms["mupdateForm"]["deathchildname"];
	var childid = document.forms["mupdateForm"]["txtdeathchildid"];
	var dateofdeath = document.forms["mupdateForm"]["txtdateofdeath"];
	var reason = document.forms["mupdateForm"]["txtreasonfordeath"];
	if(childid.value==""){
		//setError(childname);
		showError("Select a child first");
	}
	else if(dateofdeath.value==""){
		setError(dateofdeath);
		showError("Please enter the date");
	}
	else{
		var table = document.getElementById("childdeathtable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=childname.value;
		cell2.innerHTML=childid.value;
		cell3.innerHTML=dateofdeath.value;
		cell4.innerHTML=reason.value;
		childname.value="";
		childid.value="";
		dateofdeath.value="";
		reason.value="";
	}
}
function addVaccine() {
	var vaccineCode = document.forms["mupdateForm"]["txtvaccinecode"];
	var vaccineName = document.forms["mupdateForm"]["txtvaccinename"];
	var vaccineAmount = document.forms["mupdateForm"]["txtvaccineamount"];
	if(vaccineCode.value==""){
		setError(vaccineCode);
		showError("Enter the vaccine code first");
	}
	else if(vaccineAmount.value==""){
		setError(vaccineAmount);
		showError("Enter the vaccine amount");
	}
	else{
		var table = document.getElementById("vaccinetable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		
		cell1.innerHTML=vaccineCode.value;
		cell2.innerHTML=vaccineName.value;
		cell3.innerHTML=vaccineAmount.value;
		
		vaccineCode.value="";
		vaccineName.value="";
		vaccineAmount.value="";
	}
}
function addTriposha() {
	var amount = document.forms["mupdateForm"]["txttriposhaamount"];
	var table = document.getElementById("triposhatable");
	var rowcount = table.rows.length;
	if(rowcount>1){
		showError("Only two triposha packets are allowed");
	}
	else if(amount.value==""){
		setError(amount);
		showError("Enter the triposha amount");
	}
	else{
		var table = document.getElementById("triposhatable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		
		cell1.innerHTML=amount.value;
		amount.value="";
	}
}