function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}

function sendCode(){
	var uname = document.getElementById("resetusername");
	if(uname.value == ""){
		setError(uname);
		showalert("Please enter a username");
	}else{
		send(uname.value);
		uname.readOnly = true;
	}
}
function resetPword(){
	var code = document.getElementById("code");
	var uname = document.getElementById("resetusername");
	if(code.value == ""){
		setError(code);
		showalert("Please enter the code you received");
	}else if(uname.value == ""){
		setError(uname);
		showalert("Please enter the username");
	}else{
		checkCode(code.value, uname.value);
	}
}
function checkCode(code,uname){
	var xmlhttp = new XMLHttpRequest();
	var url="checkresetcode"; 
    url=url+"?username="+uname+"&code="+code;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var alrt = xmlhttp.responseText;
	        if(alrt=="code is correct"){
	        	$('#resetpword').slideDown("slow");
	        	document.getElementById('code').readOnly = true;
	        }else{
	        	showalert(alrt);
	        }
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function send(uname){
	var xmlhttp = new XMLHttpRequest();
	var url="sendresetcode"; 
    url=url+"?username="+uname;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var alrt = xmlhttp.responseText;
	        alert(alrt);
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
$(document).ready(function(){
	$("input").keypress(function(){
		if(event.which == 13){
			event.preventDefault();
			doSubmit();
		}
	});
	$("input").change(function(){
		$(this).css({"background-color":'white'});
	});
	$('#resetclose').on('click',function(){
		$('#reset').slideUp("slow");
		document.getElementById("container_block").style.opacity="1";
	});
});