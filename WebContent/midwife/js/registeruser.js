/**create a account for mother after registration*/
function setError(element){
	element.style.backgroundColor = "#FFB6C1";
}
function testSubmit() {
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var confirmpassword = document.getElementById("confirmpassword");
	if(username.value == ""){
		showError("Please select a user first");
		setError(username);
		return false;
	}
	else if(password.value == ""){
		showError("Please enter a password");
		setError(password);
		return false;
	}
	else if(confirmpassword.value == ""){
		showError("Please enter the password again");
		setError(confirmpassword);
		return false;
	}
	else if (password.value != confirmpassword.value) {
		setError(confirmpassword);
		setError(password);
		showError("Two passwords must be equal");
		return false;
	}
	return true;
}
function doSubmit() {
	if(testSubmit()){
		var username = document.getElementById("username").value;
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
});