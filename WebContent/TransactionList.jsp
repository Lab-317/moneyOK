<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消費清單</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>
<body>
<form name="TransactionList" method="POST"
	action="transaction.do?action=removeVarTransaction">
<table width="100%" border="1" align="center">
	<tr>
		<td>選取</td>
		<td>日期</td>
		<td>類型</td>
		<td>項目</td>
		<td>詳細敘述</td>
		<td>帳戶</td>
		<td>金額</td>
	</tr>
	<c:forEach var="account" items="${accountBook.accounts}">
		<c:forEach var="var_transaction" items="${account.var_transaction}">
			<tr>
				<td><input type=checkbox name="select"
					value="${var_transaction.id}"> <input type="hidden"
					name="account" value="${account.id}"></td>
				<td><c:out value="${var_transaction.date}" /></td>
				<td><c:out value="${var_transaction.type}" /></td>
				<td><c:out value="${var_transaction.item}" /></td>
				<td><c:out value="${var_transaction.description}" /></td>
				<td><c:out value="${account.name}" /></td>
				<td><c:out value="${var_transaction.amount}" /></td>
			</tr>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="3"><input type="submit" name="delete" value="刪除">
		</td>
		<td>總收入<input type=text name="totalIncome" value=></td>
		<td>總支出<input type=text name="totalExpense" value=></td>
	</tr>
</table>
</form>
</body>
</html>