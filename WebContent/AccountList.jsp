<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帳戶紀錄</title>
</head>
<body>
<form name="AccountList" method="GET" action="user.do?action=getAccount">
<table width="100%" border="1" align="center">
	<tr>
		<td>選取</td>
		<td>名稱</td>
		<td>類型</td>
		<td>敘述</td>
		<td>金額</td>
	</tr>
	<c:forEach var="accoutBook" items="${user.accountBook}">
		<c:forEach var="account" items="${accoutBook.accounts}">
			<tr>
				<td><input type=checkbox name="select" value="${account.id}"></td>
				<td><c:out value="${account.name}" /></td>
				<td><c:out value="${account.type}" /></td>
				<td><c:out value="${account.total}" /></td>
			</tr>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="2"><input type="button" name="delete" value="刪除">
		</td>
		<td>總資產<input type=text name="totalAccount" value=></td>
	</tr>
</table>
</form>
</body>
</html>