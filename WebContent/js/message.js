$(document).ready(function(){
	$('#msgheader').click(function(){
		$('#msgcontent').toggle("slow");
		$('#msgs').animate({scrollTop:$('#msgs')[0].scrollHeight});
	});
});