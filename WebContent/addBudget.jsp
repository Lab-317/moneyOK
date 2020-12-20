<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增預算</title>
</head>
<body>
<form name="addBudget" method="POST" action="user.do?action=addBudget">
<table width="350" border="1" align="center">
	<tr>
		<td>名稱</td>
		<td><input type=text name="budget_name"></input></td>
	</tr>
	<tr>
		<td>起始日</td>
		<td><input type=text name="date_start"></input></td>
	</tr>
	<tr>
		<td>終止日</td>
		<td><input type=text name="date_end"></input></td>
	</tr>
	<tr>
		<td>用途</td>
		<td><input type="checkbox" name="use" value="1" id="u1">
		<label for="u1">食</label> <input type="checkbox" name="use" value="2"
			id="u2"> <label for="u2">衣</label> <input type="checkbox"
			name="use" value="3" id="u3"> <label for="u3">住</label> <input
			type="checkbox" name="use" value="4" id="u4"> <label for="u4">行</label>
		<input type="checkbox" name="use" value="5" id="u5"> <label
			for="u5">育</label> <input type="checkbox" name="use" value="6"
			id="u6"> <label for="u6">樂</label></td>
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