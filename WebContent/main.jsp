<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Insert title here</title>
</head>
<body>
Main
<br>
Welcome ${user.email}
<br />
�b��T��
<table>
	<c:forEach var="accoutBook" items="${user.accountBook}">

		<c:forEach var="account" items="${accoutBook.accounts}">
			<tr>
				<td><c:out value="${account.name}" /></td>
				<td><c:out value="${account.total}" /></td>
			</tr>
		</c:forEach>

	</c:forEach>
</table>
<a href="http://localhost:8080/moneyOK/account.do?action=add">�s�Waccount</a>
<a href="http://localhost:8080/moneyOK/transaction.do?action=add">�s�WTransaction</a>
<a href="http://localhost:8080/moneyOK/item.do?action=add">�s�Witem</a>
</body>
</html>