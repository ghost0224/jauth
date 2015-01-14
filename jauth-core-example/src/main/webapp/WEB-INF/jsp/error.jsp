<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%  Exception ex = (Exception)request.getAttribute("ex"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Exception</title>
<script type="text/javascript">
	function showStackTrace() {
		var stackTrace = document.getElementById('stackTrace');
		if (stackTrace.style.display == 'none') {
			stackTrace.style.display = 'block';
		} else {
			stackTrace.style.display = 'none';
		}
	}
</script>
<style>
#error {
	width: 1068px;
	margin: 0 auto;
	font-family: Courier New;
	font-size: 14px;
}

#error .header {
	margin:50px 0 0 50px;
}

#error .header h1 {
	width: 668px;
	font-size: 32px;
	color: #000;
	background: url("images/sad.png") no-repeat 0 0;
	height: 102px;
	padding-left: 100px;
	line-height: 102px;
}

#error .header h5 {
	font-size: 16px;
	color: #767676;
	font-weight: normal;
	line-height: 24px;
	width: 668px;
	font-family: Courier New;
}

#error .header input {
	border: 0;
	background: #767676;
	height: 26px;
	padding: 0 15px;
	color: #fff;
	font-weight: bold;
	font-family: HP Simplified;
	border-top-right-radius: 4px;
	border-bottom-left-radius: 4px;
	margin: 0 20px 10px 0;
	cursor: pointer;
	font-size: 16px;
	line-height: 26px;
}

#error .header input:hover {
	background: #5a5a5a;
}

#error #stackTrace {
	line-height: 20px;
	width: 1000px;
}
</style>
</head>
<body>
	<div id="error">
		<div class="header">
			<h1>An exception was thrown:</h1>
			<h5>
				<%=ex.getClass()%>:<%=ex.getMessage()%>
			</h5>
			<input type="button" value="detail" onclick="showStackTrace()">
			<input type="button" value="back" onclick="history.go(-1);">
		</div>
		<div id="stackTrace" style="display: none;">
			
		</div>
	</div>
</body>
</html>