/**send messages to supervisor*/
function sendMessage(){
	var xmlhttp = new XMLHttpRequest();
	var message=document.getElementById("message").value;
	var sid=document.getElementById("supervisorid").value;
	var url="sendmessage"; 
	url=url+"?gid="+sid+"&message="+message;
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
	var sid=document.getElementById("supervisorid").value;
	var url="setmessageread"; 
	url=url+"?gid="+sid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadMessages(){
	var xmlhttp = new XMLHttpRequest();
	var sid=document.getElementById("supervisorid").value;
	var url="loadmessages"; 
	url=url+"?gid="+sid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("msgs").innerHTML = out;
		    $('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
$(window).load(function(){
	setMsgAsRead();
	loadMessages();
	$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
});
$(document).ready(function(){
	setInterval("loadMessages();", 3000);
	$('#sendmessage').on("click",function(){
		var id = document.getElementById("supervisorid").value;
		if(id!="")
			sendMessage();
	});
});