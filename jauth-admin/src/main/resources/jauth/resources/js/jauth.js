
function deleteConfirm() {
	if(confirm('Are you sure to do this?')) {
		return true;
	} else {
		return false;
	}
}

function changeStatus(id) {
	var target = document.getElementById(id);
	if(target.disabled) {
		target.disabled = false;
	} else {
		target.disabled = true;
	}
}

function setupValidation() {
	var step = document.getElementById("step").value;
	if(step == 2) {
		var sessionID = document.getElementById("sessionID").value;
		if(sessionID == null || sessionID == '') {
			alert('Please type the sessionID of Business User.');
			return false;
		}
	}
	if(step == 3) {
		var sessionACL = document.getElementById("sessionACL").value;
		if(sessionACL == null || sessionACL == '') {
			alert('Please type the sessionACL.');
			return false;
		}
	}
}

function addInput() {
	var area = document.getElementById("holder");
	var li = document.createElement("li");
	li.id="li_" + inputIndex;
	var html = "<input type=\"text\" name=\"value\" size=\"60\" /> <input type=\"button\" value=\"DELETE\" onClick=\"delInput(" + inputIndex + ")\">";
	li.innerHTML = html;
	area.appendChild(li);
}

function delInput(inputIndex) {
	var area = document.getElementById("holder");
	var obj = document.getElementById("li_" + inputIndex);
	area.removeChild(obj);
}

function clickSubmit(url, business) {
	location.href = url + "?" + business;
}

function checkAll(name) {
	var inputs = document.getElementsByName(name);
	for(var i = 0; i < inputs.length; i++) {
		if(inputs[i].type == "checkbox") {
			if(inputs[i].checked == false) {
				document.getElementById("active").checked = true;
				inputs[i].checked = true;
			} else {
				document.getElementById("active").checked = false;
				inputs[i].checked = false;
			}
		}
	}
}