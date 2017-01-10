function sendMessage(){
	var xmlhttp = new XMLHttpRequest();
	var message=document.getElementById("message").value;
	var gid=document.getElementById("midwifeid").value;
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
	var gid=document.getElementById("midwifeid").value;
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
	var gid=document.getElementById("midwifeid").value;
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
	var gid=document.getElementById("midwifeid").value;
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

$(document).ready(function(){
	setInterval("loadMessages();", 3000);
	setInterval("getUnreadMsgCount();", 3000);
	
	$('#editinfotab').on("click",function(){
		window.location="midwife/editmidwifeinfo.jsp";
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
