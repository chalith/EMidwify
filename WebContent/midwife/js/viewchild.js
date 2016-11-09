/**view child's vaccination fields*/
function loadVaccination(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("childid").value;
	var date=document.getElementById("clinicdate").value;
	var url="viewchildvaccinations";
	url=url+"?clinicdate="+date+"&cid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			var out = JSON.parse(xmlhttp.responseText);
			if(out.main.age!='undefined')
		    	document.getElementById("childage").innerHTML = out.main.age;
			else
				document.getElementById("childage").innerHTML = "";
			if(out.main.weight!='undefined')
				document.getElementById("childweight").innerHTML = out.main.weight;
			else
				document.getElementById("childweight").innerHTML = "";
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
function loadNews(){
	var xmlhttp = new XMLHttpRequest();
    var url="createnotification"; 
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var news = xmlhttp.responseText;
	        document.getElementById("notifications").innerHTML = news;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
$(window).load(function(){
	loadNews();
	tabActive('#detailstab');
});
$(document).ready(function(){
	$('#parent').on('click',function(event){
		var id = event.target.id;
		var pre = id.substring(0,5);
		if(pre == "Guard"){
			window.location = "midwife/viewmother.jsp?guardianid="+id;
		}
	});
	var timeout;
	$(".dropdown").hover(function(){
		timeout = window.setTimeout(function(){
			$(".drop_content").slideDown("slow");
		}, 500);
	},function(){
		window.clearTimeout(timeout);
		$(".drop_content").slideUp("slow");
	});
	$('#updatetab').on("click",function(){
		var id = document.getElementById("childid").value;
		var guardianid = document.getElementById("guardianid").value;
		var areacode = document.getElementById("areacode").value;
		window.location="midwife/childupdate.jsp?id="+id+"&guardianid="+guardianid+"&areacode="+areacode;
	});
	$('#editinfotab').on("click",function(){
		var id = document.getElementById("childid").value;
		window.location="midwife/editchildinfo.jsp?id="+id;
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
/**details and vaccine tabs set active*/
function tabActive(id){
	$(id).css("background-color","#2E51B9");
	$(id).css("box-shadow","0 0 10px 0px black inset");	
}
function tabInactive(id){
	$(id).css("background-color","#798DC9");
	$(id).css("box-shadow","3px -3px 20px 0px black inset");
}