<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" type="image/x-icon" href="./images/hp_logo_hpr.gif" />
<link href="css/wizard.css" type="text/css" rel="stylesheet" />
<title>WMS - Demo</title>
<script type="text/javascript">
	$('document').ready(function() {
		$('#email').focus();
	});
	
	function validation() {
		if($('#eamil').val() == '' || $('#password').val() == '') {
			alert('Please input the email and password, thanks! ');
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<img src="images/header.gif" class="header">
	<form name="loginForm" id="loginForm" action="login.action" method="post" onsubmit="return validation();">
		<div class="right">
			<h6>Logo in</h6>
			<p>CHOOSE <br />ALOG-ON OPTION:</p>
		</div>
		<div class="left">
			<h6>Use your e-mail address</h6>
			<dl>
				<dt>E-mail Address:</dt>
				<dd><input type="text" id="email" name="email" /></dd>
				<dd><span>Example: AuthAdmin</span></dd>
				<dt>Password:</dt>
				<dd><input type="password" id="password" name="password" /></dd>
				<dd><span>Default:admin888</span></dd>
			</dl>
		</div>
	<input type="submit" value="login" class="login" />
	</form>
<img src="images/footer.gif" class="footer">
</body>
</html>