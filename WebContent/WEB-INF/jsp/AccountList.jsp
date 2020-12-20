<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="JavaScript" src="lib/FusionCharts.js"></script>
<title>Insert title here</title>

</head>
<body>
Main
<br>
Welcome ${user.email}
<br />
�b��T��
<br />
<a href="http://localhost:8080/moneyOK/account.do?action=add">�s�Waccount</a>
<a href="http://localhost:8080/moneyOK/transaction.do?action=add">�s�WTransaction</a>
<a href="http://localhost:8080/moneyOK/item.do?action=add">�s�Witem</a>
<form name="AccountList" method="POST"
	action="account.do?action=removeAccount">
<table width="100%" border="1" align="center">
	<tr>
		<td>���</td>
		<td>�W��</td>
		<td>����</td>
		<td>�ԭz</td>
		<td>���B</td>
	</tr>
	<c:forEach var="accoutBook" items="${user.accountBook}">
		<c:forEach var="account" items="${accoutBook.accounts}">
			<tr>
				<td><input type=checkbox name="select" value="${account.id}">
				</td>
				<td><c:out value="${account.name}" /></td>
				<td><c:if test="${account.type eq 0}">
					<c:out value="�{��" />
				</c:if> <c:if test="${account.type eq 1}">
					<c:out value="�l��" />
				</c:if> <c:if test="${account.type eq 2}">
					<c:out value="�x�ȥd" />
				</c:if></td>
				<td><c:out value="${account.total}" /></td>
			</tr>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="2"><input type="submit" name="delete" value="�R��">
		</td>
		<td>�`�겣 <c:out value="${user.totalAccountMoney}" /></td>
	</tr>
</table>
</form>

<div id="chartdiv" align="center"></div>
<script type="text/javascript">
		   var chart = new FusionCharts("Charts/Bar2D.swf", "ChartId", "300", "350", "0", "0");
		   chart.setDataXML("${xml}");   
		   chart.render("chartdiv");
</script>
</body>
</html>