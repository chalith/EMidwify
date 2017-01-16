/**main front interface of mother*/
/*function loadMessagecounts(){
	var xmlhttp = new XMLHttpRequest();
    var url="viewmidwifemsgcount"; 
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var counts = xmlhttp.responseText;
	        if(counts!="0"){
	        	$("#messagecount").css("display","block");
	        	document.getElementById("messagecount").innerHTML = counts;
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}*/
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
	var url="getallunreadmsgcount"; 
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    if(out!='0'){
		    	document.getElementById("msgnotify").innerHTML = out;
		    	$("#messagecount").css("display","block");
	        	document.getElementById("messagecount").innerHTML = out;
		    }
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

$(window).load(function(){
	document.getElementById('receiveri').innerHTML=document.getElementById('receiverii').innerHTML;
	loadChildren();
	getUnreadMsgCount()
	loadNews();
});
function show(id1,id2) {
	var timeout;
	$(id1).hover(function(){
		timeout = window.setTimeout(function(){
			$(id2).slideDown("slow");
		}, 500);
	},function(){
		window.clearTimeout(timeout);
		$(id2).slideUp("slow");
	});
}
$(document).ready(function(){
	setInterval("loadMessages();", 3000);
	setInterval("getUnreadMsgCount();", 3000);
	show('#view', '#viewdrop');
	show('#register', '#registerdrop');
	show('#update', '#updatedrop');
	show('#clinic', '#clinicdrop');
	$('.timeline').click(function(event){
		var id = event.target.id;
		if(id.length>2){
			loadNotification(id);
			$('#notification').slideDown("slow");
			document.getElementById("container").style.opacity="0.6";
		}
	});
	$('#babies').click(function(event){
		var cid = event.target.id;
		if(cid!="babies"){
			//alert(cid);
			if(cid.length>2){
				window.location = "mother/viewchild.jsp?childid="+cid;
			}
		}
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
function loadChildren(){
	var xmlhttp = new XMLHttpRequest();
	var id=document.getElementById("guardianid").value;
	var url="loadchildren"; 
	url=url+"?guardianid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    if(out=="")
		    	document.getElementById("babies").innerHTML = "You have no babies to show";
		    else
		    	document.getElementById("babies").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function loadNotification(id){
	var xmlhttp = new XMLHttpRequest();
    var url="loadnotification";
    url=url+"?notificationid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var news = xmlhttp.responseText;
	        if(news!="")
	        	document.getElementById("notifications").innerHTML = news;
	        else
	        	document.getElementById("notifications").innerHTML = "No notifications to show up";
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
