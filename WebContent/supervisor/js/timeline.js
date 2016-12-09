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
$(document).ready(function(){
	$('.timeline').click(function(event){
		var id = event.target.id;
		if(id.length>2){
			loadNotification(id);
			$('#notification').slideDown("slow");
			document.getElementById("container").style.opacity="0.6";
		}
	});
});