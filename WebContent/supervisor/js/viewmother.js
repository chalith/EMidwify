function loadChildren(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("guardianid").value;
	var url="loadchildren"; 
	url=url+"?guardianid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("babies").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadVaccination(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("guardianid").value;
	var date=document.getElementById("clinicdate").value;
	var url="viewvaccinations";
	url=url+"?clinicdate="+date+"&gid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var out = JSON.parse(xmlhttp.responseText);
			if(out.main.age!='undefined')
		    	document.getElementById("motherage").innerHTML = out.main.age;
			else
				document.getElementById("motherage").innerHTML = "";
			if(out.main.weight!='undefined')
				document.getElementById("motherweight").innerHTML = out.main.weight;
			else
				document.getElementById("motherweight").innerHTML = "";
			if(out.main.tamount!='undefined')
				document.getElementById("tamount").innerHTML = out.main.tamount;
			else
				document.getElementById("tamount").innerHTML = "";
			var k = Object.keys(out);
			for(var i=0;i<k.length-1;i++){
				//alert(k[i]);
				addVaccine(out[k[i]].code, out[k[i]].name, out[k[i]].amount);
			}
		}
    };
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function addVaccine(vCode,vName,vAmount) {
	var table = document.getElementById("vaccinetable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML=vCode;
	cell2.innerHTML=vName;
	cell3.innerHTML=vAmount;
}
function clearTable(tableid){
	var table = document.getElementById(tableid);
	var rowcount = table.rows.length;
	for(var i=1;i<rowcount;i++){
	  table.deleteRow(1);
	}
}
$(document).ready(function(){
	$('#babies').click(function(event){
		var cid = event.target.id;
		if(cid!="babies"){
			//alert(cid);
			if(cid.length>2){
				window.location = "supervisor/viewchild.jsp?childid="+cid;
			}
		}
	});
	$('#clinicdate').change(function(){
		clearTable("vaccinetable");
		loadVaccination();
		$('#clinicupdates').css("display","inline-block");
	});
	$('#vaccinationtab').on("click",function(){
		tabActive('#vaccinationtab');
		tabInactive('#detailstab');
		$('#details').css("display", "none");
		$('#vaccination').css("display", "inline-block");
	});
	$('#detailstab').on("click",function(){
		tabActive('#detailstab');
		tabInactive('#vaccinationtab');
		$('#details').css("display", "inline-block");
		$('#vaccination').css("display", "none");
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