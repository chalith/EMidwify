/**show input field that containig errors*/
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
/**set input fields uneditable and editable*/
function setUneditable(id){
	document.getElementById(id).style.backgroundColor = "#E6E6E6";
	//document.getElementById(id).value = "";
}
function setEditable(id){
	document.getElementById(id).style.backgroundColor = "white";
	//document.getElementById(id).value = "";
}
/**validate the inputs before submit*/
function testSubmit(){
	var motherguardianid = document.forms["childeditForm"]["txtmotherguardianid"];
	var motherguardianname = document.forms["childeditForm"]["txtmotherguardianname"];
	var childname = document.forms["childeditForm"]["txtchildname"];
	var dateofdelivery = document.forms["childeditForm"]["txtdateofchilddelivery"];
	var birthweight = document.forms["childeditForm"]["txtchildbirthweight"];
	//var fatherid = document.forms["childeditForm"]["txtfatherid"];
	var fatherid = document.forms["childeditForm"]["txtfatherid"];
	var fatherfullname = document.forms["childeditForm"]["txtfatherfullname"];
	var fatherdateofbirth = document.forms["childeditForm"]["txtfatherdateofbirth"];
	var occupation = document.forms["childeditForm"]["txtoccupation"];
	var addressline1 = document.forms["childeditForm"]["txtaddressline1"];
	var addressline2 = document.forms["childeditForm"]["txtaddressline2"];
	var addressline3 = document.forms["childeditForm"]["txtaddressline3"];
	var addressline4 = document.forms["childeditForm"]["txtaddressline4"];
	var edulevel = document.forms["childeditForm"]["edulevel"];
	var deathmotherid = document.forms["childeditForm"]["txtdeathmotherid"];
	var deathmothername = document.forms["childeditForm"]["txtdeathmothername"];
	var deathmotherareacode = document.forms["childeditForm"]["txtdeathmotherareacode"];
	var dateofmotherdeath = document.forms["childeditForm"]["txtdateofmotherdeath"];
	var reasonfordeath = document.forms["childeditForm"]["txtresonfordeath"];
	var checkArray = [motherguardianid,motherguardianname,childname,dateofdelivery,birthweight];
	var alertArray = ["Guardian ID","","child's name","child's delivery date","birth weight"];
	//alert(dateofdelivery.value);
	for(var i=0;i<checkArray.length;i++){
		if(checkArray[i].value == ""){
			setError(checkArray[i]);
			if(i==1){
				showError("Please register the guardian in the system");
			}
			else if(i==3){
				showError("Plese enter "+alertArray[i]);
			}
			else{
				showError("Please enter the "+alertArray[i]);
			}
			return false;
		}
	}
	if((fatherid.value!="")||(fatherfullname.value!="")||(fatherdateofbirth.value!="")||(occupation.value!="")||(addressline1.value!="")||(addressline2.value!="")||(addressline3.value!="")||(addressline4.value!="")){
		if(fatherid.value==""){
			setError(fatherid);
			showError("Please Enter the father ID/NIC");
			return false;
		}
		if(fatherfullname.value == ""){
			setError(fatherfullname);
			showError("Please Enter the father's full name");
			return false;
		}
		if(fatherdateofbirth.value == ""){
			setError(fatherdateofbirth);
			showError("Please Enter the father's date of birth");
			return false;
		}
		if((addressline1.value == "") && (addressline2.value == "") && (addressline3.value == "") && (addressline4.value == "")){
			setError(addressline1);
			showError("Please Enter the father's address");
			return false;
		}
		if(!($('[name="edulevel"]').is(':checked'))){
			showError("Please select a education level");
			return false;
		}
	}
	if(isNaN(birthweight.value)){
		setError(nofchildren);
		showError("This field only contains numeric values");
		return false;
	}
	return true;
}
function reload(){
	var xmlhttp = new XMLHttpRequest();
    var url="loadchildregister";
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function doSubmit(){
	var ok = confirm("Do you wan't to save edited info?");
	if(testSubmit()&&ok==true){
		document.getElementById("ceps").value = sendChildEpidemics();
		document.getElementById("feps").value = sendFatherEpidemics();
		document.forms["childeditForm"].submit();
		document.forms["childeditForm"].reset();
	}
}
/**get inputs in the tables to text fields*/
function addfatherEpidemic() {
	var epCode = document.getElementById("fatherepidemiccode");
	var epName = document.getElementById("fatherepidemicname");
	var epDate = document.getElementById("fatherepidemicdate");
	var epNote = document.getElementById("fatherepidemicnote");
	var epidemiccode = document.forms["childeditForm"]["txtfatherepidemiccode"];
	var dateofepidemic = document.forms["childeditForm"]["txtfatherepidemicdate"];
	if(epidemiccode.value==""){
		setError(epidemiccode);
		showError("Enter the epidemic code first");
	}
	else if(dateofepidemic.value==""){
		setError(dateofepidemic);
		showError("Please enter the date");
	}
	else{
		var table = document.getElementById("fatherepidemictable");
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
function addchildEpidemic() {
	var epCode = document.getElementById("childepidemiccode");
	var epName = document.getElementById("childepidemicname");
	var epDate = document.getElementById("childepidemicdate");
	var epNote = document.getElementById("childepidemicnote");
	var epidemiccode = document.forms["childeditForm"]["txtchildepidemiccode"];
	var dateofepidemic = document.forms["childeditForm"]["txtchildepidemicdate"];
	if(epidemiccode.value==""){
		setError(epidemiccode);
		showError("Enter the epidemic code first");
	}
	else if(dateofepidemic.value==""){
		setError(dateofepidemic);
		showError("Plese enter the date");
	}
	else{
		var table = document.getElementById("childepidemictable");
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
/**validate character inputs*/
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

/**validate numeric inputs*/
function validateNumber(txtnumber){
	var number = document.forms["childeditForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}
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
	$('[name="txtchildname"]').keyup(function(){
		isName("childname");
	});
	$('#fatherfullname').keyup(function(){
		isName("fatherfullname");
	});
	$('#deathmothername').keyup(function(){
		isName("deathmothername");
	});
	$('[name="txtchildbirthweight"]').keyup(function(){
		validateNumber("txtchildbirthweight");
	});
	$('[name="txtmotherguardianid"]').keyup(function(){
		//showError("okay");
		loadGaurdian();
	});
	$('[name="txtregisteredfatherid"]').keyup(function(){
		//showError("okay");
		loadFather();
	});
	$('#nonic').on('change',function(){
		$('#fatherid').css('display','none');
		$('#fathernic').css('display','block');
		$('#fatherid').attr('id','tempnic');
		$('#fathernic').attr('id','fatherid');
		$('#tempnic').attr('id','fathernic');
		$('#fatherid').attr('name','txtfatherid');
		$('#fathernic').attr('name','txtfathernic');
	 });
	var dropb = ['#childep','#fatherep','#motherdeath'];
	var hidediv = ['#childeps','#fathereps','#motherdeaths'];
	for(var i=0;i<dropb.length;i++){
		showDiv(dropb[i],hidediv[i]);
	}
});
function loadGaurdian(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("motherguardianid").value;
    var url="loadguardian";
    url=url+"?txtmotherguardianid="+id;
	//document.getElementById("deathmothername").value = "dsfsdf";
	xmlhttp.onreadystatechange = function() {
		var out = JSON.parse(xmlhttp.responseText);
		if(out.name!=undefined){
	    	document.getElementById("motherguardianname").value = out.name;
		}
		else{
			document.getElementById("motherguardianname").value = "";
		}
		if(out.area!=undefined){
			document.getElementById("deathmotherid").value = out.id;
	    	document.getElementById("deathmothername").value = out.name;
	    	document.getElementById("deathmotherareacode").value = out.area;
	    	$('#deathmothername').attr('readonly', true);
	    	$('#deathmotherareacode').attr("disabled", true);
		}
		else{
			document.getElementById("deathmotherid").value = "";
			document.getElementById("deathmothername").value = "";
	    	document.getElementById("deathmotherareacode").value = "";
	    	$('#deathmothername').attr('readonly', false);
	    	$('#deathmotherareacode').attr("disabled", false);
		}
		//showError("sdsad");
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function loadFather(){
	//showError("sdsad");
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("registeredfatherid").value;
    var url="loadfather";
    url=url+"?txtregisteredfatherid="+id;
	//document.getElementById("deathmothername").value = "dsfsdf";
	xmlhttp.onreadystatechange = function() {
		var out = xmlhttp.responseText;
		if(out!="null"){
	    	document.getElementById("registeredfathername").value = out;
		}
		else{
			document.getElementById("registeredfathername").value = "";
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
/**get table values*/
function sendChildEpidemics(){
	var TableData="";
	$('#childepidemictable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}
function sendFatherEpidemics(){
	var TableData="";
	$('#fatherepidemictable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+"~row~";
	});
	return TableData;
}