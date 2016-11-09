/**header for all files*/
$(window).load(function(){
	document.getElementById("title").innerHTML = document.title;
});
$(document).ready(function(){
	$('#picture').hover(function(){
		$('#changepic').css({'display':'block'});
	},function(){
		$('#changepic').css({'display':'none'});
	});
	$('#home').on('click',function(){
		window.location = "/EMidwify";
	});
	$('#logout').on('click',function(){
		endSession();
	});
	$('#profile').on('click',function(){
		window.location = "midwife/midwife.jsp";
	});
	$('#changepic').click(function(){
		$('#uploadpicture').toggle("slow");
		document.getElementById("container").style.opacity="0.2";
	});
});
function endSession(){
	var xmlhttp = new XMLHttpRequest();
	var url = "logout";
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			if(xmlhttp.responseText == "closed"){
				window.location = "/EMidwify";
			}
		}
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send(null);
}