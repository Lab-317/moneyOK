<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="JavaScript" src="lib/FusionCharts.js"></script>
<link rel=stylesheet type="text/css" href="css/master.css">
<title>Insert title here</title>
<style>
body {
	background-color: #aedfe5;
	background-repeat: repeat-x;
}

#main_menu {
	float: right;
	display: inline;
	margin-top: 20px;
	width: 980px;
	border-bottom: 1px solid #c7c7c7;
}

#main_menu li {
	float: left;
}

#main_menu a {
	float: left;
	background: #ececec url(../images/bg-menu_separator.gif) right bottom
		no-repeat;
	color: #4b4b4b;
	text-transform: uppercase;
	font-family: Arial, Helvetica, sans-serif;
	padding: 6px 14px;
	text-decoration: none;
}

#main_menu a:hover {
	background: url(images/bg-menu_separator-hover.gif) right bottom
		no-repeat;
}

#main_menu a.selected {
	background: url(../images/bg-menu_separator-active.gif) right bottom
		no-repeat;
	position: relative;
	top: 1px;
	padding-bottom: 7px;
	padding-top: 5px;
}
</style>
<link rel=stylesheet type="text/css" href="css/master.css">
</head>
<body>
<div id="home">
<div id="user">Welcome ${user.email} <a
	href="user.do?action=logout">登出</a></div>
<ul id="main_menu">
	<li><a href="">account</a></li>
	<li><a href="http://localhost:8080/moneyOK/transaction.do">Tranaction</a></li>
	<li><a href="http://localhost:8080/moneyOK/item.do?action=add">item</a></li>
	<li><a>Chart</a></li>
</ul>
<div id="content">
<form name="AccountList" method="POST"
	action="account.do?action=removeAccount">
<table width="40%" border="1" align="left">
	<tr>
		<td>
		<div id="chartdiv" align="center"></div>
		</td>
	</tr>
</table>
<table width="60%" border="1" align="right">
	<tr>
		<td>選取11</td>
		<td>名稱</td>
		<td>類型</td>
		<td>金額</td>
	</tr>
	<c:forEach var="accoutBook" items="${user.accountBook}">
		<c:forEach var="account" items="${accoutBook.accounts}">
			<tr>
				<td><input type=checkbox name="select" value="${account.id}"></td>
				<td><c:out value="${account.name}" /></td>
				<td><c:if test="${account.type eq 0}">
					<c:out value="現金" />
				</c:if> <c:if test="${account.type eq 1}">
					<c:out value="金融帳戶" />
				</c:if> <c:if test="${account.type eq 2}">
					<c:out value="儲值卡" />
				</c:if></td>
				<td><c:out value="${account.total}" /></td>
			</tr>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="2"><input type="submit" name="delete" value="刪除">
		</td>
		<td>總資產</td>
		<td><c:out value="${user.totalAccountMoney}" /></td>
	</tr>

	</form>
	<form name="addAccount" method="POST" action="account.do?action=add"
		onSubmit="return check(this)">
	<tr>
		<td></td>
		<td><input type=text name="account"></input></td>
		<!--  <td><input type=text name="description"></input></td>-->
		<td><select name="category">
			<option value="0">現金</option>
			<option value="1">金融帳戶</option>
			<option value="2">儲值卡</option>
		</select></td>

		<td colspan="1" align=center><input type=text name="amount"
			size="6"></input> <input type="submit" name="submit" value="確認送出 ">
		<input type="reset" name="reset" value="重填"></td>
	</tr>
	</form>
</table>
</div>
<script type="text/javascript">
	var chart = new FusionCharts("Charts/Column2D.swf", "ChartId", "400", "400", "0", "0");
	chart.setDataXML("${accountXml}");   
	chart.render("chartdiv");
</script>
<div class="toptop"></div>
</html>