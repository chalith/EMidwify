function isDate(date){
	pattern=/^([0-9]{4})\/([0-9]{2})\/([0-9]{2})$/;
	//if(!isNaN(Date.parse(date))){
		//return true;
	//}
	//return false;
	return pattern.test(date);
}
function isLeapYear(year){
	if(year%400==0){
		return true;
	}
	else if((year%4==0)&&(year%100!=0)){
		return true;
	}
	return false;
}
function isCharacter(c){
	var arr = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",".",""," "];
	var bool = false;
	for(var i=0;i<arr.length;i++){
		if(c==arr[i]){
			bool = true;
		}
	}
	return bool;
	
}
function isName(id) {
	var element = document.getElementById(id);
	var c = element.value.substring(element.value.length-1);
	if(!isCharacter(c)){
		showError("This field only contains charachters");
		element.value = element.value.substring(0,element.value.length-1);
	}
}
function showDiv(id1,id2){
	var show = false;
	$(id1).click(function(){
		if(show==false){
			$(id1+' img').attr('src','images/icons/dropup.png');
			$(id2).slideDown("slow");
			show = true;
		}
		else{
			$(id1+' img').attr('src','images/icons/dropdown.png');
			$(id2).slideUp("slow");
			show = false;
		}
	});
}
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
