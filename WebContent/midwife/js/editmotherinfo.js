function isTP(tpnumber){
	if((!isNaN(tpnumber))&&(tpnumber.length == 10)){
		return true;
	}
	return false;
}
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
function testSubmit(){
	var id = document.forms["mothereditForm"]["txtguardianid"];
	var fullname = document.forms["mothereditForm"]["txtfullname"];
	var dateofbirth = document.forms["mothereditForm"]["txtdateofbirth"];
	var areacode = document.forms["mothereditForm"]["txtareacode"];
	var addressline1 = document.forms["mothereditForm"]["txtaddressline1"];
	var addressline2 = document.forms["mothereditForm"]["txtaddressline2"];
	var addressline3 = document.forms["mothereditForm"]["txtaddressline3"];
	var addressline4 = document.forms["mothereditForm"]["txtaddressline4"];
	var location = document.forms["mothereditForm"]["txtlocation"];
	var edulevel = document.forms["mothereditForm"]["edulevel"];
	var tpnumber1 = document.forms["mothereditForm"]["txttpnumber1"];
	var tpnumber2 = document.forms["mothereditForm"]["txttpnumber2"];
	var tpnumber3 = document.forms["mothereditForm"]["txttpnumber3"];
	
	var email = document.forms["mothereditForm"]["txtemail"];
	
	var nofchildren = document.forms["mothereditForm"]["txtnofchildren"];
	
	var checkArray = [id,fullname,areacode,location];
	var alertArray = ["NIC or ID","full name","area code","location"];

	for(var i=0;i<checkArray.length;i++){
		if(checkArray[i].value == ""){
			setError(checkArray[i]);
			showError("Please Enter the "+alertArray[i]);
			return false;
		}
	}
	if((addressline1.value == "") && (addressline2.value == "") && (addressline3.value == "") && (addressline4.value == "")){
		setError(addressline1);
		showError("Please Enter the address");
		return false;
	}
	if(dateofbirth.value == ""){
		setError(dateofbirth);
		showError("Please enter the date of birth");
		return false;
	}
	checkArray = [tpnumber1,tpnumber2,tpnumber3];
	alertArray = ["1","2","3"];
	for(var i=0;i<checkArray.length;i++){
		if((checkArray[i].value!="")&&(!isTP(checkArray[i].value))){
			setError(checkArray[i]);
			showError("Telephone number "+alertArray[i]+" is invalid");
			return false;
		}
	}
	if((email.value!="")&&(!validateEmail(email.value))){
		setError(email);
		showError("Email address is invalid");
		return false;
	}
	if(isNaN(nofchildren.value)){
		setError(nofchildren);
		showError("This field only contains numarical values");
		return false;
	}
	if(!($('[name="edulevel"]').is(':checked'))){
		showError("Please select a education level");
		return false;
	}
	if(!($('[name="motherguardian"]').is(':checked'))){
		showError("Please select mother or guardian");
		return false;
	}
	
	return true;
}
function reload(){
	var xmlhttp = new XMLHttpRequest();
    var url="loadguardianregistration";
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited info?");
	if(testSubmit()&&ok==true){
		document.getElementById("eps").value = sendEpidemics();
		document.getElementById("abs").value = sendAbortions();
		document.getElementById("cds").value = sendChilddeaths();
		document.forms["mothereditForm"].submit();
		document.forms["mothereditForm"].reset();
	}
}
function sendEpidemics(){
	var TableData="";
	$('#epidemictable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function sendAbortions(){
	var TableData="";
	$('#abortiontable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+"~row~";
	});
	return TableData;
}
function sendChilddeaths(){
	var TableData="";
	$('#childdeathtable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+"~row~";
	});
	return TableData;
}
function isCharacter(c){
	var arr = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",".",""," "];
	var bool = false;
	for(var i=0;i<arr.length;i++){
		if(c==arr[i]){
			bool = true;
		}
	}
	return bool;
	
}
function isName(id) {
	var element = document.getElementById(id);
	var c = element.value.substring(element.value.length-1);
	if(!isCharacter(c)){
		showError("This field only contains charachters");
		element.value = element.value.substring(0,element.value.length-1);
	}
}
function validateNumber(txtnumber){
	var number = document.forms["mothereditForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}
function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function validateNIC(nic,bdate){
	if(nic.length!=10){
		return false;
	}
	else if(nic.substring(0,2)!=bdate.substring(2,4)){
		return false;
	}
	else if((nic.substring(9,10)!="v")&&(nic.substring(9,10)!="V")){
		return false;
	}
	return (!isNaN(nic.substring(0,9)));
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
	$("#areacode").change(function(){
		$(this).css({"background-color":'white'});
	});
	$('#fullname').keyup(function(){
		isName("fullname");
	});
	$('#deathchildname').keyup(function(){
		isName("deathchildname");
	});
	$('[name="txttpnumber1"]').keyup(function(){
		validateNumber("txttpnumber1");
	});
	$('[name="txttpnumber2"]').keyup(function(){
		validateNumber("txttpnumber2");
	});
	$('[name="txttpnumber3"]').keyup(function(){
		validateNumber("txttpnumber3");
	});
	$('[name="txtnofchildren"]').keyup(function(){
		validateNumber("txtnofchildren");
	});
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
		}
	});
	var dropb = ['#motherep','#motherab','#childdeath'];
	var hidediv = ['#mothereps','#motherabs','#childdeaths'];
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
function addEpidemic() {
	var epCode = document.getElementById("epidemiccode");
	var epName = document.getElementById("epidemicname");
	var epDate = document.getElementById("epidemicdate");
	var epNote = document.getElementById("epidemicnote");
	var epidemiccode = document.forms["mothereditForm"]["txtepidemiccode"];
	var dateofepidemic = document.forms["mothereditForm"]["txtepidemicdate"];
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
function addAbortion() {
	var abDate = document.getElementById("dateofabortion");
	var abReason = document.getElementById("reasonforabortion");
	var dateofabortion = document.forms["mothereditForm"]["txtdateofabortion"];
	if(dateofabortion.value==""){
		setError(dateofabortion);
		showError("Please enter the date");
	}
	else{
		var table = document.getElementById("abortiontable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		cell1.innerHTML=abDate.value;
		cell2.innerHTML=abReason.value;
		abDate.value="";
		abReason.value="";
	}
}
function addChilddeath() {
	var childName = document.getElementById("deathchildname");
	var date = document.getElementById("dateofdeath");
	var reason = document.getElementById("reasonfordeath");
	var childname = document.forms["mothereditForm"]["txtdeathchildname"];
	var dateofdeath = document.forms["mothereditForm"]["txtdateofdeath"];
	if(childname.value==""){
		setError(childname);
		showError("Enter the child's name first");
	}
	else if(dateofdeath.value==""){
		setError(dateofdeath);
		showError("Date is invalid");
	}
	else{
		var table = document.getElementById("childdeathtable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		cell1.innerHTML=childName.value;
		cell2.innerHTML=date.value;
		cell3.innerHTML=reason.value;
		childName.value="";
		date.value="";
		reason.value="";
	}
}
//function submit() {