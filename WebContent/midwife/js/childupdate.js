function setUneditable(id){
	document.getElementById(id).style.backgroundColor = "#E6E6E6";
	document.getElementById(id).value = "";
	$(id).attr('readonly',true);
	//document.getElementById(id).value = "";
}
function setEditable(id){
	document.getElementById(id).style.backgroundColor = "white";
	document.getElementById(id).value = "";
	$(id).attr('readonly',false);
	//document.getElementById(id).value = "";
}
function setError(element){
		element.style.backgroundColor = "#FFB6C1";
}
function loadMothers(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("motherareas").value;
    var url="updatechildloadmothers"; 
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
function loadChildren(id){
	var xmlhttp = new XMLHttpRequest();
    var url="loadchildreninupdate"; 
    url=url+"?gid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var out = JSON.parse(xmlhttp.responseText);
	        document.getElementById("children").innerHTML = out.userbar;
	        if(out.name != undefined){
	        	setEditable("dateofdeath");
	        	setEditable("reasonfordeath");
	        	document.getElementById("deathmothername").value = out.name;
		        document.getElementById("deathmotherid").value = id;
	        }
	        else{
	        	setUneditable("dateofdeath");
	        	setUneditable("reasonfordeath");
	        	document.getElementById("deathmothername").value = "";
		        document.getElementById("deathmotherid").value = "";
	        }
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
	var id = document.getElementById("childid").value;
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
function viewChild(id){
	var xmlhttp = new XMLHttpRequest();
    var url="viewchildpicture";
    url=url+"?cid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var users = JSON.parse(xmlhttp.responseText);
	        //showError(users);
	        document.getElementById("childname").innerHTML = users.name;
	        document.getElementById("childpicture").src = users.picture;
	        document.getElementById("childid").value = id;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
$(window).load(function(){
	var id = document.getElementById("tempchildid").value;
	var guardianid = document.getElementById("tempguardianid").value;
	var areacode = document.getElementById("tempareacode").value;
	if((id!="")&&(guardianid!="")&&(areacode!="")){
		loadChildren(guardianid);
		viewChild(id);
		$('#view').css("display","inline-block");
	}
});
$(document).ready(function(){
	$("input").keypress(function(event){
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
		loadChildren(id);
	});
	$('#children').change(function(){
		var id = $(this).children(":selected").attr("id");
		viewChild(id);
		$('#view').css("display","inline-block");
	});
	$('[name="txtvaccinecode"]').change(function(){
		loadVaccineName();
		loadVaccineAmount();
	});
	$('[name="txtchildweight"]').keyup(function(){
		validateNumber("txtchildweight");
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
	var dropb = ['#childep','#motherdeath1','#childds'];
	var hidediv = ['#childeps','#motherdeaths1','#childdss'];
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
	var childid = document.forms["cupdateForm"]["txtchildid"];
	var childweight = document.forms["cupdateForm"]["txtchildweight"];
	var cdate = document.forms["cupdateForm"]["txtclinicdate"];
	if(childid.value == ""){
		showError("Please select a child first");
		return false;
	}
	if(childweight.value == ""){
		setError(childweight);
		showError("Please enter the child weight");
		return false;
	}
	if(cdate.value == ""){
		setError(cdate);
		showError("Please enter the clinic date");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("epidemic").value = sendEpidemics();
		document.getElementById("disease").value = sendDiseases();
		document.getElementById("vaccine").value = sendVaccine();
		document.getElementById("triposha").value = sendTriposha();
		document.getElementById("motherdeath").value = sendMotherdeaths();
		document.forms["cupdateForm"].submit();
		document.forms["cupdateForm"].reset();
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
function sendMotherdeaths(){
	var TableData="";
	$('#motherdeathtable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function validateNumber(txtnumber){
	var number = document.forms["cupdateForm"][txtnumber];
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
	var diseasecode = document.forms["cupdateForm"]["txtdiseasecode"];
	var dateofdisease = document.forms["cupdateForm"]["txtdiseasedate"];
	if(diseasecode.value==""){
		setError(diseasecode);
		showError("Enter the disease code first");
	}
	else if(dateofdisease.value==""){
		setError(dateofdisease);
		showError("Plese enter the date");
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
	var epidemiccode = document.forms["cupdateForm"]["txtepidemiccode"];
	var dateofepidemic = document.forms["cupdateForm"]["txtepidemicdate"];
	if(epidemiccode.value==""){
		setError(epidemiccode);
		showError("Enter the epidemic code first");
	}
	else if(dateofepidemic.value==""){
		setError(dateofepidemic);
		showError("Plese enter the date");
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
function addMotherdeath() {
	var mothername = document.forms["cupdateForm"]["txtdeathmothername"];
	var motherid = document.forms["cupdateForm"]["txtdeathmotherid"];
	var dateofdeath = document.forms["cupdateForm"]["txtdateofdeath"];
	var reason = document.forms["cupdateForm"]["txtreasonfordeath"];
	var table = document.getElementById("motherdeathtable");
	var rowcount = table.rows.length;
	if(rowcount>1){
		showError("Only one mother death is allowed");
	}
	else if(motherid.value==""){
		showError("Select a child first");
	}
	else if(dateofdeath.value==""){
		setError(dateofdeath);
		showError("Plese enter the date");
	}
	else{
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		cell1.innerHTML=mothername.value;
		cell2.innerHTML=motherid.value;
		cell3.innerHTML=dateofdeath.value;
		cell4.innerHTML=reason.value;
		mothername.value="";
		motherid.value="";
		dateofdeath.value="";
		reason.value="";
	}
}
function addVaccine() {
	var vaccineCode = document.forms["cupdateForm"]["txtvaccinecode"];
	var vaccineName = document.forms["cupdateForm"]["txtvaccinename"];
	var vaccineAmount = document.forms["cupdateForm"]["txtvaccineamount"];
	if(vaccineCode.value==""){
		setError(vaccineCode);
		showError("Enter the vaccine code first");
	}
	else if(vaccineAmount.value==""){
		setError(vaccineAmount);
		showError("Enter the vaccine amount");
	}
	else{
		checkVaccineGiven();
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
function checkVaccineGiven(){
	var id = document.getElementById("childid").value;
	var code = document.getElementById("vaccinecode").value;
	var date = document.getElementById("clinictdate").value;
	if((date.length==10)&&(id!="")&&(date!="")){
		var xmlhttp = new XMLHttpRequest();
	    var url="checkvaccinegiven"; 
	    url=url+"?id="+id+"&vcode="+code+"&date="+date;
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange = function() {
	    	if(xmlhttp.readyState==4 && xmlhttp.status==200){
	    		if(xmlhttp.responseText == "true")
	    			confirm("Vaccine is already given for this period, Do you want to enter it again");
	    	}
	    };
	    xmlhttp.send(null);
	}
}
function addTriposha() {
	var amount = document.forms["cupdateForm"]["txttriposhaamount"];
	var date = document.forms["cupdateForm"]["txttriposhadate"];
	var table = document.getElementById("triposhatable");
	var rowcount = table.rows.length;
	if(amount.value==""){
		setError(amount);
		showError("Enter the triposha amount");
	}
	else if(rowcount>1){
		showError("Only two triposha packets are allowed");
	}
	else{
		var table = document.getElementById("triposhatable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		
		cell1.innerHTML=amount.value;
		amount.value="";
	}
}