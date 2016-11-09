/**alerts, error messages*/ 
$(document).ready(function() {
	$('#errorclose').on("click",function(){
		$('#erroralert').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
	$('#erroralert').on("click",function(){
		$(this).slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
});
function showError(warning){
	document.getElementById("erroralert").style.backgroundColor="#250109";
	document.getElementById("errorwarning").style.color="#BA6477";
	document.getElementById("erroricon").style.backgroundColor="#3D3A3A";
	document.getElementById("errorclose").style.backgroundColor="#392126";
	document.getElementById("errorwarning").innerHTML = warning;
	document.getElementById("erroricon").innerHTML = "!";
	$('#erroralert').slideDown("slow");
	document.getElementById("container").style.opacity="0.2";
}