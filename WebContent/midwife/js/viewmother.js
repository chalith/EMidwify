function sendMessage(){
	var xmlhttp = new XMLHttpRequest();
	var message=document.getElementById("message").value;
	var gid=document.getElementById("guardianid").value;
	var url="sendmessage"; 
	url=url+"?gid="+gid+"&message="+message;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			loadMessages();
		    var out = xmlhttp.responseText;
		    var message=document.getElementById("message").value = "";
		    $('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function setMsgAsRead(){
	var xmlhttp = new XMLHttpRequest();
	var gid=document.getElementById("guardianid").value;
	var url="setmessageread"; 
	url=url+"?gid="+gid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("msgnotify").innerHTML = "";
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}

function getUnreadMsgCount(){
	var xmlhttp = new XMLHttpRequest();
	var gid=document.getElementById("guardianid").value;
	var url="getunreadmsgcount"; 
	url=url+"?gid="+gid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    if(out!='0')
		    	document.getElementById("msgnotify").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadMessages(){
	var xmlhttp = new XMLHttpRequest();
	var gid=document.getElementById("guardianid").value;
	var url="loadmessages"; 
	url=url+"?gid="+gid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("msgs").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
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
	setInterval("loadMessages();", 3000);
	setInterval("getUnreadMsgCount();", 3000);
	$('#babies').click(function(event){
		var cid = event.target.id;
		if(cid!="babies"){
			//alert(cid);
			if(cid.length>2){
				window.location = "midwife/viewchild.jsp?childid="+cid;
			}
		}
	});
	$('#clinicdate').change(function(){
		clearTable("vaccinetable");
		loadVaccination();
		$('#clinicupdates').css("display","inline-block");
	});
	$('#updatetab').on("click",function(){
		var id = document.getElementById("guardianid").value;
		var areacode = document.getElementById("areacode").value;
		window.location="midwife/motherupdate.jsp?id="+id+"&areacode="+areacode;
	});
	$('#editinfotab').on("click",function(){
		var id = document.getElementById("guardianid").value;
		window.location="midwife/editmotherinfo.jsp?id="+id;
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
	$('#msgheader').on("click",function(){
		setMsgAsRead();
		$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
	});
	$('#sendmessage').on("click",function(){
		sendMessage();
		$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
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