/**message interface scripts for midwife*/
function loadMothers(){
		var xmlhttp = new XMLHttpRequest();
		var id=document.getElementById("motherarea").value;
	    var url="loadmessagedmothers"; 
	    url=url+"?area="+id;
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
		        var users = xmlhttp.responseText;
		        document.getElementById("motherbar").innerHTML = users;
			}
		};
		
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
}
$(window).load(function(){
	loadMothers();
	//countUnreadMessages();
});
/**send messages*/
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
/**set read time of messages*/
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
/**get messages that haven't read to notify*/
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
		    if(out.trim()!=""){
			    document.getElementById("msgs").innerHTML = out;
		    }
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
$(document).ready(function(){
	setInterval("loadMothers();", 3000);
	setInterval("loadMessages();", 3000);
	$('#motherarea').change(function(){
		loadMothers();
	});
	$('#sendmessage').on("click",function(){
		var id = document.getElementById("guardianid").value;
		if(id!=""){
			sendMessage();
			$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
		}
		else
			showError("please select a mother first");
	});
	$('#motherbar').click(function(event){
		var id = event.target.id;
		if(id!="motherbar"){
			var Gid = id;
			if(Gid.length>2){
				document.getElementById("guardianid").value = Gid;
				document.getElementById("sendername").innerHTML = document.getElementById(Gid).innerHTML;
				setMsgAsRead();
				loadMothers();
				loadMessages();
				$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
			}
		}
	});
});