<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>註冊</title>

<script type="text/javascript">
function check(){
	var var_value = document.register.email.value;
	if( !var_value.match( "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$" ) ){
    	window.alert("E-mail格式錯誤，請填寫正確格式!!");
        return false;
    }
    else return true;
}

function pwcheck(){
	if( document.register.password1.value != document.register.password2.value ){
    	window.alert("密碼錯囉，請確認輸入!!");
        return false;
    }
    else return true;
}
</script>
<link rel=stylesheet type="text/css" href="css/master.css">
</head>
<body>
<div id="home">
<div class="toptop"></div>
<div class="top"></div>

<form name="register" method="POST" action="user.do?action=register">
<center>
<h1>註冊</h1>
</center>
<table width="300" border="1" align="center">
	<tr>
		<td>E-mail</td>
		<td><input type=text name="email" id="email" onBlur="check()"></input></td>
	</tr>
	<tr>
		<td>密碼</td>
		<td><input type=text name="password" id="password1"></input></td>
	</tr>
	<tr>
		<td>確認密碼</td>
		<td><input type=text name="password" id="password2"
			onChange="pwcheck()"></input></td>
	</tr>
	<tr>
		<td colspan="2" align=center><input type="submit" name="submit"
			value="確認送出"> <input type="reset" name="reset" value="重填">
		</td>
	</tr>
</table>
</form>
<div class="bottom"></div>
</div>
</body>
</html>