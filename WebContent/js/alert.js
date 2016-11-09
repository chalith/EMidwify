/**alerts, error messages*/ 
$(document).ready(function() {
	$('#close').on("click",function(){
		$('#alert').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
	$('#alert').on("click",function(){
		$(this).slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
});
function showalert(warning){
	document.getElementById("alert").style.backgroundColor="#250109";
	document.getElementById("warning").style.color="#BA6477";
	document.getElementById("icon").style.backgroundColor="#3D3A3A";
	document.getElementById("close").style.backgroundColor="#392126";
	document.getElementById("warning").innerHTML = warning;
	document.getElementById("icon").innerHTML = "!";
	$('#alert').slideDown("slow");
	document.getElementById("container").style.opacity="0.2";
}
/**messages showing successfully done*/
function showsuccessmessage(message){
	document.getElementById("alert").style.backgroundColor="#34355B";
	document.getElementById("warning").style.color="#9C9DEB";
	document.getElementById("icon").style.backgroundColor="#9C9DEB";
	document.getElementById("close").style.backgroundColor="#9C9DEB";
	document.getElementById("warning").innerHTML = message;
	document.getElementById("icon").innerHTML = "&#10003";
	$('#alert').slideDown("slow");
	document.getElementById("container").style.opacity="0.2";
}