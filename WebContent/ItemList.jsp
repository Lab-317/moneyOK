<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>項目清單</title>
</head>
<body>
<form name="BudgetList" method="GET" action="user.do?action=getBudget">
<table width="100%" border="1" align="center">
	<tr>
		<td>類別</td>
		<td>項目</td>
	</tr>
	<c:forEach var="item" items="${user.items}">
		<tr>
			<td><c:out value="${item.parentCategory}" /></td>
			<td><c:out value="${item.name}" /></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>