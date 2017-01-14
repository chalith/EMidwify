/**image slider*/
adImages=new
Array("images/banner3.jpg","images/banner2.jpg","images/banner.jpg")
thisAd=0;
imgCt=adImages.length;
function rotate(){
	if(document.images){
		thisAd++;
		if(thisAd==imgCt){
			thisAd=0;
		}
		document.adBanner.src=adImages[thisAd];
		setTimeout("rotate()",3*1000);
	}
}
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
/**validation before submit*/
function testSubmit(){
	var uname = document.getElementById("username");
	var pword = document.getElementById("password");
	if((uname.value == "")&&(pword.value == "")){
		setError(uname);
		setError(pword);
		showalert("Please enter the username and password");
		return false;
	}
	else if(uname.value == ""){
		setError(uname);
		showalert("Please enter the username");
		return false;
	}
	else if(pword.value == ""){
		setError(pword);
		showalert("Please enter the password");
		return false;
	}
	return true;
}
function doSubmit(){
	if(testSubmit()){
		document.forms["myForm"].submit();
		document.forms["myForm"].reset();
	}
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
	$('#loginclose').on('click',function(){
		$('#login').slideUp("slow");
		document.getElementById("container_block").style.opacity="1";
	});
	$('#frgtpswrd').on('click',function(){
		$('#reset').slideDown("slow");
		document.getElementById("container_block").style.opacity="0.6";
	});
});