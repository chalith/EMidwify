/**main front interface of mother*/
function loadMessagecounts(){
	var xmlhttp = new XMLHttpRequest();
    var url="viewmessagecountsinsupervisor";
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
}
$(window).load(function(){
	loadMessagecounts();
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
	setInterval("loadMessagecounts();", 3000);
	//show('#view', '#viewdrop');
	//show('#register', '#registerdrop');
	//show('#update', '#updatedrop');
	$('.timeline').click(function(event){
		var id = event.target.id;
		if(id.length>2){
			loadNotification(id);
			$('#notification').slideDown("slow");
			document.getElementById("container").style.opacity="0.6";
		}
	});	
});
function loadNotification(id){
	var xmlhttp = new XMLHttpRequest();
    var url="loadnotification";
    url=url+"?notificationid="+id;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var out = xmlhttp.responseText;
	        document.getElementById("notificationcontent").innerHTML = out;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
