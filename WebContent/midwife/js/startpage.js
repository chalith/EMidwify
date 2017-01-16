/**main front interface of midwife*/
function loadNews(){
	var xmlhttp = new XMLHttpRequest();
    var url="createnotification"; 
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
function loadMessagecounts(){
	var xmlhttp = new XMLHttpRequest();
    var url="viewcounts"; 
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var counts = JSON.parse(xmlhttp.responseText);
	        if(counts.messagecount!="0"){
	        	$("#messagecount").css("display","block");
	        	document.getElementById("messagecount").innerHTML = counts.messagecount;
	        }
	        if(counts.supervisormessagecount!="0"){
	        	document.getElementById("supervisormessagecount").innerHTML = "("+counts.supervisormessagecount+")";
	        }
	        if(counts.mothermessagecount!="0"){
	        	document.getElementById("mothermessagecount").innerHTML = "("+counts.mothermessagecount+")";
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function showSelect(id){
	$('#'+id).slideDown("slow");
	document.getElementById("container").style.opacity="0.6";
}
$(window).load(function(){
	loadMessagecounts();
	loadNews();
});
/*function show(id1,id2) {
	var timeout;
	$(id1).hover(function(){
		timeout = window.setTimeout(function(){
			$(id2).slideDown("slow");
		}, 500);
	},function(){
		window.clearTimeout(timeout);
		$(id2).slideUp("slow");
	});
}*/
$(document).ready(function(){
	setInterval("loadMessagecounts();", 3000);
	//show('#register', '#registerdrop');
	//show('#update', '#updatedrop');
	//show('#clinic', '#clinicdrop');
	//show('#message', '#messagedrop');
	$('body').on('click',function(event){
		var id = event.target.id;
		if((id!="search")&&(id!="result")&&(id!="mothersearch")&&(id!="mothers")&&(id!="childsearch")&&(id!="children")){
			document.getElementById("search").value="";
			document.getElementById("results").innerHTML = "";
		}
	});
});