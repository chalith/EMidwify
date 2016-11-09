$(document).ready(function() {
	$('#closenotification').on("click",function(){
		$('#notification').slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
	$('#notification').on("click",function(){
		$(this).slideUp("slow");
		document.getElementById("container").style.opacity="1";
	});
});
