/**clinics for mothers*/
function loadClinics(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("clinicareas").value;
	var url="loadclinicdates"; 
	url=url+"?area="+area;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    //alert(out);
		    document.getElementById("clinicdates").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadChildren(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("clinicareas").value;
	var date=document.getElementById("clinicdates").value;
	var url="loadclinicmothers";
	url=url+"?area="+area+"&date="+date;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var outob = JSON.parse(xmlhttp.responseText);
			var out = outob.ob1;
			var out2 = outob.ob2;
			if(out.venue!='undefined')
		    	document.getElementById("venue").value = out.venue;
			else
				document.getElementById("venue").value = "";
			if(out.time!='undefined')
				document.getElementById("time").value = out.time;
			else
				document.getElementById("time").value = "";
			var k = Object.keys(out);
			for(var i=0;i<k.length;i++){
				if((k[i]!='venue')&&(k[i]!='time'))
					addChildren(out[k[i]].name, out[k[i]].age, out[k[i]].vname, out[k[i]].vamount);
			}
			k = Object.keys(out2);
			for(var i=0;i<k.length;i++){
				addTriposha(out2[k[i]].name, out2[k[i]].triamount);
			}
		}
    };
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function addTriposha(name,tAmount) {
	var table = document.getElementById("triposhaTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	cell1.innerHTML=name;
	cell2.innerHTML=tAmount;
}

function addChildren(name,age,vName,vAmount,tAmount) {
	var table = document.getElementById("motherTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	cell1.innerHTML=name;
	cell2.innerHTML=age;
	cell3.innerHTML=vName;
	cell4.innerHTML=vAmount;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}
$(document).ready(function(){
	$('#clinicdates').change(function(){
		clearTable("motherTable");
		clearTable("triposhaTable");
		loadChildren();
	});
	$('#clinicareas').change(function(){
		loadClinics();
	});
});