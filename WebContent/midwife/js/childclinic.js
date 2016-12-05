/**scripts for clinic page of midwife*/
/**load clinic dates entered by the supervisor*/
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
function loadPastClinics(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("pastclinicareas").value;
	var url="loadpastclinicdates";
	url=url+"?area="+area;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    //alert(out);
		    document.getElementById("pastclinicdates").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadPastClinicData(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("pastclinicareas").value;
	var date=document.getElementById("pastclinicdates").value;
	var url="loadpastclinicchildren";
	url=url+"?area="+area+"&date="+date;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var outob = JSON.parse(xmlhttp.responseText);
			var out = outob.ob1;
			var out2 = outob.ob2;
			if(out.venue!='undefined')
		    	document.getElementById("pastvenue").value = out.venue;
			else
				document.getElementById("pastvenue").value = "";
			if(out.time!='undefined')
				document.getElementById("pasttime").value = out.time;
			else
				document.getElementById("pasttime").value = "";
			if(out.count!='undefined' && out.count!='0')
				document.getElementById("count").innerHTML = "<center><h2>"+out.count+" children didn't attend for the clinic"+"</h2></center>";
			else
				document.getElementById("count").innerHTML = "";
			var k = Object.keys(out);
			for(var i=0;i<k.length;i++){
				if((k[i]!='venue')&&(k[i]!='time')&&(k[i]!='count'))
					addChildren(out[k[i]].name, out[k[i]].age, out[k[i]].vname, out[k[i]].vamount,"pastChildTable");
			}
			k = Object.keys(out2);
			for(var i=0;i<k.length;i++){
				addTriposha(out2[k[i]].name, out2[k[i]].triamount,"pastTriposhaTable");
			}
		}
    };
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}

/**load children who have to give vaccines*/
function loadChildren(){
	var xmlhttp = new XMLHttpRequest();
	var area=document.getElementById("clinicareas").value;
	var date=document.getElementById("clinicdates").value;
	var url="loadclinicchildren";
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
					addChildren(out[k[i]].name, out[k[i]].age, out[k[i]].vname, out[k[i]].vamount,"chlidTable");
			}
			k = Object.keys(out2);
			for(var i=0;i<k.length;i++){
				addTriposha(out2[k[i]].name, out2[k[i]].triamount,"triposhaTable");
			}
		}
    };
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function addTriposha(name,tAmount,id) {
	var table = document.getElementById(id);
	table.style.display="block";
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	cell1.innerHTML=name;
	cell2.innerHTML=tAmount;
}

function addChildren(name,age,vName,vAmount,id) {
	var table = document.getElementById(id);
	table.style.display="block";
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
	table.style.display="none";
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}
$(window).load(function(){
	tabActive('#upcomingtab');
});
$(document).ready(function(){
	$('#clinicdates').change(function(){
		clearTable("chlidTable");
		clearTable("triposhaTable");
		loadChildren();
	});
	$('#clinicareas').change(function(){
		loadClinics();
	});
	$('#pastclinicdates').change(function(){
		clearTable("pastChildTable");
		clearTable("pastTriposhaTable");
		loadPastClinicData();
	});
	$('#pastclinicareas').change(function(){
		loadPastClinics();
	});
	$('#upcomingtab').on("click",function(){
		tabActive('#upcomingtab');
		tabInactive('#pasttab');
		$('#past').css("display", "none");
		$('#upcoming').css("display", "inline-block");
		$('#upcoming').css("margin-top", "0");
	});
	$('#pasttab').on("click",function(){
		tabActive('#pasttab');
		tabInactive('#upcomingtab');
		$('#past').css("display", "inline-block");
		$('#past').css("margin-top", "0");
		$('#upcoming').css("display", "none");
	});
});
function tabActive(id){
	$(id).css("background-color","#2E51B9");
	$(id).css("box-shadow","0 0 10px 0px black inset");	
}
function tabInactive(id){
	$(id).css("background-color","#798DC9");
	$(id).css("box-shadow","3px -3px 20px 0px black inset");
}