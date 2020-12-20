<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消費清單</title>
</head>
<body>
<form name="BudgetList" method="GET" action="user.do?action=getBudget">
<table width="100%" border="1" align="center">
	<tr>
		<td>選取</td>
		<td>名稱</td>
		<td>起始日</td>
		<td>終止日</td>
		<td>用途</td>
		<td>預算金額</td>
		<td>剩餘金額</td>
	</tr>

	<tr>
		<td></td>
	</tr>

	<tr>
		<td colspan="4"><input type="button" name="delete" value="刪除">
		</td>
		<td>總預算<input type=text name="totalBudget" value=></td>
		<td>總剩餘金額<input type=text name="total" value=></td>
	</tr>
</table>
</form>
</body>
</html>