/**send messages to group of mothers*/
function loadgrpMothers(){
		var xmlhttp = new XMLHttpRequest();
		var id=document.getElementById("grpmotherarea").value;
	    var url="loadgroupmothers"; 
	    url=url+"?area="+id;
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
		        var users = xmlhttp.responseText;
		        document.getElementById("grpmotherbar").innerHTML = users;
			}
		};
		
		xmlhttp.open("GET",url,true);
	    xmlhttp.send(null);
}
$(window).load(function(){
	loadgrpMothers();
	//countUnreadMessages();
});
function loadgrpMessages(gid){
	var xmlhttp = new XMLHttpRequest();
	var url="loadlastmessage"; 
	url=url+"?gid="+gid;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
		    var out = xmlhttp.responseText;
		    document.getElementById("grpmsgs").innerHTML = out;
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}
function sendgrpMessage(gid){
	var xmlhttp = new XMLHttpRequest();
	var message=document.getElementById("grpmessage").value;
	var url="sendmessage"; 
	url=url+"?gid="+gid+"&message="+message;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			loadgrpMessages(gid);
		    var out = xmlhttp.responseText;
		    var message=document.getElementById("grpmessage").value = "";
		    $('#grpmsgs').animate({scrollTop:$('#grpmsgs')[0].scrollHeight});
		}
	};
	
	xmlhttp.open("GET",url,true);
	xmlhttp.send(null);
}

function select(targetid){
	var id = event.target.id;
	if(id!=targetid){
		var Gid = id;
		if(Gid.length>2){
			if($('#'+targetid).find('[name = "div'+Gid+'"]').css("background-color") == "rgb(208, 211, 10)")
				$('#'+targetid).find('[name = "div'+Gid+'"]').css("background-color","#D0D3D4");
			else
				$('#'+targetid).find('[name = "div'+Gid+'"]').css("background-color","rgb(208, 211, 10)");
		}
	}
}
function isAdded(id,e1){
	for(var i=0;i<e1.length;i++){
		if(id==e1[i].id){
			return true;
		}
	}
	return false;
}
$(document).ready(function(){
	$('#groupicon').click(function(){
		$('#groupmessage').toggle("slow");
		document.getElementById("container").style.opacity="0.7";
	});
	$('#grpclose').click(function(){
		$('#groupmessage').toggle("slow");
		document.getElementById("container").style.opacity="1";
	});
	$('#grpmotherarea').change(function(){
		loadgrpMothers();
	});
	$('#grpmotherbar').click(function(event){
		select('grpmotherbar');
	});
	$('#grpmemberbar').click(function(event){
		select('grpmemberbar');
	});
	$('#add').click(function(){
		var e = $('#grpmemberbar').find(".mother_bar");
		var e1 = $('#grpmotherbar').find(".mother_bar");
		
		for(var i=0;i<e1.length;i++){
			if(e1[i].style.backgroundColor=="rgb(208, 211, 10)"){
				var d = e1[i].id;
				if(!isAdded(d,e)){
					$('#grpmotherbar').find('[name = "div'+d+'"]').css("background-color","#D0D3D4");
					$('[name = "div'+d+'"]').clone().appendTo("#grpmemberbar");
				}
				else{
					$('#grpmotherbar').find('[name = "div'+d+'"]').css("background-color","#D0D3D4");
					alert(d+" is already added");
				}
			}
		}
	});
	$('#remove').click(function(){
		var e1 = $('#grpmemberbar').find(".mother_bar");
		for(var i=0;i<e1.length;i++){
			if(e1[i].style.backgroundColor=="rgb(208, 211, 10)"){
				var d = e1[i].id;
				$('#grpmemberbar').find('[name = "div'+d+'"]').remove();
			}
		}
	});
	$('#grpsendmessage').on("click",function(){
		var e1 = $('#grpmemberbar').find(".mother_bar");
		if(e1.length>0){
			for(var i=0;i<e1.length;i++){
				sendgrpMessage(e1[i].id);
			}
		}
		else{
			alert("please select mothers to send message");
		}
	});
});