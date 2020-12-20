<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="JavaScript" src="lib/FusionCharts.js"></script>
<title>���O�M��</title>
<link rel=stylesheet type="text/css" href="css/master.css">
</head>

<body>
<a href="http://localhost:8080/moneyOK/account.do?action=add">�s�Waccount</a>
<a href="http://localhost:8080/moneyOK/transaction.do?action=add">�s�WTransaction</a>
<a href="http://localhost:8080/moneyOK/item.do?action=add">�s�Witem</a>
<a href="http://localhost:8080/moneyOK/account.do">��^�D��</a>
<form name="TransactionList" method="POST"
	action="transaction.do?action=removeVarTransaction">
<table width="40%" border="1" align="left">
	<tr>
		<td>
		<div id="chartdiv" align="center"></div>
		</td>
	</tr>
</table>
<table width="60%" border="1" align="center">
	<tr>
		<td>���</td>
		<td>���</td>
		<td>����</td>
		<td>����</td>
		<td>�Բӱԭz</td>
		<td>�b��</td>
		<td>���B</td>
	</tr>

	<c:forEach var="accountBook" items="${user.accountBook}">
		<input type="hidden" name="accountBookId" value="${accountBook.id}">
		<c:forEach var="account" items="${accountBook.accounts}">
			<c:forEach var="var_transaction" items="${account.var_transaction}">
				<tr>
					<td><input type=checkbox name="select"
						value="${var_transaction.id}"> <input type="hidden"
						name="account" value="${account.id}"></td>
					<td><c:out value="${var_transaction.date}" /></td>
					<td><c:if test="${var_transaction.type eq false}">
						<c:out value="��X" />
					</c:if> <c:if test="${var_transaction.type eq true}">
						<c:out value="���J" />
					</c:if></td>
					<td><c:out
						value="${var_transaction.item.parentCategory.name}-${var_transaction.item.name}" /></td>
					<td><c:out value="${var_transaction.description}" /></td>
					<td><c:out value="${account.name}" /></td>
					<td><c:out value="${var_transaction.amount}" /></td>
				</tr>
			</c:forEach>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="3"><input type="submit" name="delete" value="�R��">
		</td>
		<td>�`���J: <c:out value="${accountBooks.totalIncome}" /></td>
		<td>�`��X: <c:out value="${accountBooks.totalExpense}" /></td>
	</tr>
</table>
</form>
</body>
</div>
<script type="text/javascript">
	var chart = new FusionCharts("Charts/Pie2D.swf", "ChartId", "400", "400", "0", "0");
	chart.setDataXML("${transactionXml}");   
	chart.render("chartdiv");
</script>
<div class="toptop"></div>
</div>

</html>