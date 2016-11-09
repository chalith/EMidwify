function createBreadcrumb(){
	var breadcrumb="<div class=\"breadcrumb_div\"><a href="+ document.location.href +">"+ document.title +"</a><h1>></h1></a></div>"
	if(sessionStorage.getItem('breadcrumb')){
		sessionStorage.setItem('breadcrumb', sessionStorage.getItem('breadcrumb')+breadcrumb);
	}
	else{
		sessionStorage.setItem('breadcrumb', breadcrumb);
	}
	document.getElementById("breadcrumb").innerHTML=sessionStorage.getItem('breadcrumb');
}
$(window).load(function(){
	//createBreadcrumb();
});