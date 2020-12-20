<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MoneyOK</title>

<link rel="stylesheet" type="text/css"
	href="lib/ext/resources/css/ext-all.css">
<script type="text/javascript" src="lib/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="lib/ext/ext-all.js"></script>
<script type="text/javascript" scr="lib/ext/examples/ux/Spotlight.js"></script>
<script type="text/javascript" src="js/register.js"></script>
<script type="text/javascript" src="js/Login.js"></script>
<link rel="stylesheet" type="text/css" href="css/MoneyOK.css">
<script type="text/javascript">
function check(){
	if( document.login.email.value != (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)){
    	window.alert("格式錯誤請確實填寫!!");
        return false;
    }
    else return true;
}
</script>

<link rel=stylesheet type="text/css" href="css/master.css">
</head>
<body>
<script type="text/javascript" src="lib/ext/examples/shared/examples.js"></script>
<div id="home">
<div class="top"></div>
<center><img src="css/images/MoneyOK.png"></img></center>
<div id="container">
<div id="login"></div>
<div id="register"></div>
</div>
<div class="bottom"></div>
</div>
</body>
</html>