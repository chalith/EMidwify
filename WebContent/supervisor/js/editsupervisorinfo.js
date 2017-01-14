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
	var id = document.forms["midwifeeditForm"]["txtmidwifeid"];
	var fullname = document.forms["midwifeeditForm"]["txtfullname"];
	var dateofbirth = document.forms["midwifeeditForm"]["txtdateofbirth"];
	var addressline1 = document.forms["midwifeeditForm"]["txtaddressline1"];
	var addressline2 = document.forms["midwifeeditForm"]["txtaddressline2"];
	var addressline3 = document.forms["midwifeeditForm"]["txtaddressline3"];
	var addressline4 = document.forms["midwifeeditForm"]["txtaddressline4"];
	var tpnumber1 = document.forms["midwifeeditForm"]["txttpnumber1"];
	var tpnumber2 = document.forms["midwifeeditForm"]["txttpnumber2"];
	var tpnumber3 = document.forms["midwifeeditForm"]["txttpnumber3"];
	
	var email = document.forms["midwifeeditForm"]["txtemail"];
	
	var checkArray = [id,fullname];
	var alertArray = ["NIC or ID","full name"];

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
		document.forms["midwifeeditForm"].submit();
		document.forms["midwifeeditForm"].reset();
	}
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
	var number = document.forms["midwifeeditForm"][txtnumber];
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
	$('#fullname').keyup(function(){
		isName("fullname");
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
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
		}
	});
});
//function submit() {