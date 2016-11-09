/**validation and script for all files*/
function getDays(year,month){
	if((month==1)||(month==3)||(month==5)||(month==7)||(month==8)||(month==10)||(month==12)){
		return 31;
	}
	else if((month==4)||(month==6)||(month==9)||(month==11)){
		return 30;
	}
	else if(month==2){
		if(isLeapYear(year)){
			return 29;
		}
		else{
			return 28;
		}
	}
	return 0;
}
function loadNews(){
	var xmlhttp = new XMLHttpRequest();
    var url="createnotification"; 
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
	        var news = xmlhttp.responseText;
	        document.getElementById("notifications").innerHTML = news;
		}
	};
	xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
$(window).load(function(){
	loadNews();
});