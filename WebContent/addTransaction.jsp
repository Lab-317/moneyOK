<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>新增交易</title>
</head>
<body>
<form name="addTransaction" method="POST"
	action="transaction.do?action=add">
<table width="300" border="1" align="center">
	<tr>
		<td>日期</td>
		<td><input type=text name="date"></input></td>
	</tr>
	<tr>
		<td>類型</td>
		<td><input type="radio" value="1" name="category">食</input> <input
			type="radio" value="2" name="category">衣</input> <input type="radio"
			value="3" name="category">住</input> <input type="radio" value="4"
			name="category">行</input> <input type="radio" value="5"
			name="category">育</input> <input type="radio" value="6"
			name="category">樂</input></td>
	</tr>
	<tr>
		<td colspan="2" align=center><input type=radio name="type"
			value="1" />收入 <input type=radio name="type" value="0" />支出</td>
	</tr>
	<tr>
		<td colspan="2" align=center><c:forEach var="item"
			items="${user.items}">
			<input type="radio" name="item" value="${item.id}" />${item.name}
			</c:forEach></td>
	</tr>
	<tr>
		<td>帳戶</td>
		<td><c:forEach var="accoutBook" items="${user.accountBook}">
			<c:forEach var="account" items="${accoutBook.accounts}">
				<input type="radio" name="account" value="${account.id}" />${account.name}
				</c:forEach>
		</c:forEach></td>
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