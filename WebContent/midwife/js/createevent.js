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
	$('[name="txteventtime"]').keyup(function(){
		validateNumber("txteventtime");
	});
	$('#mothersearch').keyup(function(){
		if(document.getElementById("mothersearch").value != "")
			loadMothers();
		else
			loadSelectUsers();
	});
	$('#area1').change(function(){
		loadSelectUsers();
	});
	$('#mothers').click(function(event){
		select('mothers');
	});
	$('#selectedmothers').click(function(event){
		select('selectedmothers');
	});
	$('#add').click(function(){
		var e = $('#selectedmothers').find(".mother");
		var e1 = $('#mothers').find(".mother");
		for(var i=0;i<e1.length;i++){
			if(e1[i].style.backgroundColor=="rgb(208, 211, 10)"){
				var d = e1[i].id;
				if(!isAdded(d,e)){
					$('#mothers').find('#'+d).css("background-color","white");
					$('#'+d).clone().appendTo("#selectedmothers");
				}
				else{
					$('#mothers').find('#'+d).css("background-color","white");
					showError(d+" is already added");
				}
			}
		}
	});
	$('#addall').click(function(){
		var e = $('#selectedmothers').find(".mother");
		var e1 = $('#mothers').find(".mother");
		for(var i=0;i<e1.length;i++){
			var d = e1[i].id;
			if(!isAdded(d,e)){
				$('#mothers').find('#'+d).css("background-color","white");
				$('#'+d).clone().appendTo("#selectedmothers");
			}
			else{
				$('#mothers').find('#'+d).css("background-color","white");
				showError(d+" is already added");
			}
		}
	});
	$('#remove').click(function(){
		var e1 = $('#selectedmothers').find(".mother");
		for(var i=0;i<e1.length;i++){
			if(e1[i].style.backgroundColor=="rgb(208, 211, 10)"){
				var d = e1[i].id;
				$('#selectedmothers').find('#'+d).remove();
			}
		}
	});
	$('#removeall').click(function(){
		var e1 = $('#selectedmothers').find(".mother");
		for(var i=0;i<e1.length;i++){
			var d = e1[i].id;
			$('#selectedmothers').find('#'+d).remove();
		}
	});
	$(document).on("click","tr",function(e){
		if(e.target.id != "thead"){
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
		}
	});
});
function getGuardians(){
	var e1 = $('#selectedmothers').find(".mother");
	var txt = "";
	if(e1.length>0){
		for(var i=0;i<e1.length;i++){
			if(txt!=""){
				txt = txt + "," + e1[i].id;
			}else{
				txt = txt + e1[i].id;
			}
		}
	}
	else{
		return false;
	}
	return txt;
}
function isAdded(id,e1){
	for(var i=0;i<e1.length;i++){
		if(id==e1[i].id){
			return true;
		}
	}
	return false;
}
function addEvent() {
	var name = document.getElementById("name");
	var area = document.getElementById("area1");
	var date = document.getElementById("eventdate");
	var time = document.getElementById("eventtime");
	var venue = document.getElementById("eventvenue");
	var ename = document.forms["eventForm"]["txtname"];
	var earea = document.forms["eventForm"]["area1"];
	var etime = document.forms["eventForm"]["txteventtime"];
	var evenue = document.forms["eventForm"]["txteventvenue"];
	var edate = document.forms["eventForm"]["txteventdate"];
	var guardians = getGuardians();
	if(ename.value==""){
		setError(name);
		showError("Enter the event name first");
	}
	else if(earea.value==""){
		setError(area);
		showError("Select a area first");
	}
	else if(edate.value==""){
		setError(date);
		showError("Please enter the date");
	}
	else if(etime.value==""){
		setError(time);
		showError("Enter the time");
	}
	else if(evenue.value==""){
		setError(venue);
		showError("Enter the venue");
	}
	else if(!guardians){
		showError("please select mothers for the event");
	}
	else{
		$('#eventtable').css({'display':'block'});	
		var table = document.getElementById("eventtable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		var cell5 = row.insertCell(4);
		var cell6 = row.insertCell(5);
		cell1.innerHTML=name.value;
		cell2.innerHTML=area.value;
		cell3.innerHTML=date.value;
		cell4.innerHTML=time.value;
		cell5.innerHTML=venue.value;
		cell6.innerHTML=guardians;
		name.value="";
		area.value="";
		date.value="";
		time.value="";
		venue.value="";
		document.getElementById("selectedmothers").innerHTML = "";
		document.getElementById("mothers").innerHTML = "";
	}
}
function removefromTable(table){
	var row = $('.selected').closest("tr").index();
	if(row>0){
		table.deleteRow(row);
	}
}

function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function sendEvents(){
	var TableData="";
	$('#eventtable tr').each(function(row,tr){
		TableData = TableData+" "+$(tr).find('td:eq(0)').text()+" ~column~ "+$(tr).find('td:eq(1)').text()+" ~column~ "+$(tr).find('td:eq(2)').text()+" ~column~ "+$(tr).find('td:eq(3)').text()+" ~column~ "+$(tr).find('td:eq(4)').text()+" ~column~ "+$(tr).find('td:eq(5)').text()+"~row~";
	});
	return TableData;
}
function testSubmit(){
	var table = document.getElementById("eventtable");
	var rowcount = table.rows.length;
	if(rowcount==1){
		showError("Please enter clinic details");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.getElementById("evnts").value = sendEvents();
		document.forms["eventForm"].submit();
		document.forms["eventForm"].reset();
	}
}
function validateNumber(txtnumber){
	var number = document.forms["eventForm"][txtnumber];
	if(isNaN(number.value)){
		showError("This field only contain numeric values");
		number.value = "";
	}
}
function loadMothers(){
	var xmlhttp = new XMLHttpRequest();
	var name=document.getElementById("mothersearch").value;
	var e1 = $('#mothers').find(".mother");
	var elements = "";
	for(var i=0;i<e1.length;i++){
		var d = e1[i].id;
		var names = $('#'+d).find('h1');
		var cname = names[0].innerHTML;
		if((cname.toLowerCase()).startsWith(name.toLowerCase())){
			elements = elements +"<div class=\"mother\" id=\""+d+"\">"+document.getElementById(d).innerHTML+"</div>";
		}
		else{
			var arr1 = cname.split(" ");
			for(var j=0;j<arr1.length;j++){
				if((arr1[j].toLowerCase()).startsWith(name.toLowerCase())){
					elements = elements +"<div class=\"mother\" id=\""+d+"\">"+document.getElementById(d).innerHTML+"</div>";
				}
				else{
					var arr2 = arr1[j].split(".");
					for(var k=0;k<arr2.length;k++){
						if((arr2[k].toLowerCase()).startsWith(name.toLowerCase())){
							elements = elements +"<div class=\"mother\" id=\""+d+"\">"+document.getElementById(d).innerHTML+"</div>";
							break;
						}
					}
				}
			}
		}
	}
	document.getElementById("mothers").innerHTML = elements;
}
function loadSelectUsers(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("area1").value;
    var url="onlineusers"; 
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
function select(targetid){
	var id = event.target.id;
	if(id!=targetid){
		var Gid = id;
		if(Gid.length>2){
			if($('#'+targetid).find('#'+Gid).css("background-color") == "rgb(208, 211, 10)")
				$('#'+targetid).find('#'+Gid).css("background-color","white");
			else
				$('#'+targetid).find('#'+Gid).css("background-color","rgb(208, 211, 10)");
		}
	}
}