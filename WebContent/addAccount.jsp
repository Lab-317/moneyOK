<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增帳戶</title>
</head>
<body>
<form name="addAccount" method="POST" action="account.do?action=add">
<table width="300" border="1" align="center">
	<tr>
		<td>類型</td>
		<td><select size="6" name="category">
			<option value="cash">現金</option>
			<option value="bank">銀行/郵局</option>
			<option value="prepaidCard">儲值卡</option>
		</select></td>
	</tr>
	<tr>
		<td>帳戶</td>
		<td><input type=text name="account"></input></td>
	</tr>
	<tr>
		<td>敘述</td>
		<td><input type=text name="description"></input></td>
	</tr>
	<tr>
		<td>金額</td>
		<td><input type=text name="amount"></input></td>
	</tr>
	<tr>
		<td colspan="2" align=center><input type="submit" name="submit"
			value="確認送出"> <input type="reset" name="reset" value="重填">
		</td>
	</tr>
</table>
</form>
</body>
</html>