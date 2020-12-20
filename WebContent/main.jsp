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
帳戶訊息
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
<a href="http://localhost:8080/moneyOK/account.do?action=add">新增account</a>
<a href="http://localhost:8080/moneyOK/transaction.do?action=add">新增Transaction</a>
<a href="http://localhost:8080/moneyOK/item.do?action=add">新增item</a>
</body>
</html>