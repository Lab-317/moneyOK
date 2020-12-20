<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>新增交易</title>
<script type="text/javascript" src="lib/jquery.min.js"></script>
<script type="text/javascript" src="lib/jquery.selectboxes.min.js"></script>
<script>
function getSubItem(){
var cid = $("#category").val();
$.getJSON("item.do?action=getItem",{Pid:cid},
        function(data){
		  $("#item").removeOption(/./);
          $.each(data, function(i,item){
        	  $("#item").addOption(item.id, item.name);
          });
        });
}

function check(){   //檢查使用者是否填選了正確的資訊
	if(document.addTransaction.date.value == ""){   //日期
		if(document.addTransaction.amount.value == ""){ //金額
			window.alert("請選取日期\n請填入金額");}
		else{
			window.alert("請選取日期\n");  }
		return false;
	}
	else{
		if(document.addTransaction.amount.value == ""){ //金額
			window.alert("請填入金額");	
			return false;
		}
		return true;
	}
}
</script>
</head>
<body>
<form name="addTransaction" method="POST"
	action="transaction.do?action=add" onSubmit="return check(this)">
<table width="300" border="1" align="center">
	<tr>
		<td>日期</td>
		<td><input type=text name="date"></input></td>
	</tr>
	<tr>
		<td>類型</td>
		<td><select id="category" name="category"
			onchange="javascript:getSubItem()">
			<option value="1">食</option>
			<option value="2">衣</option>
			<option value="3">住</option>
			<option value="4">行</option>
			<option value="5">育</option>
			<option value="6">樂</option>
		</select> <select id="item" name="item">
			<c:forEach var="subItem" items="${subItem}">
				<option value="${subItem.id}">${subItem.name}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td colspan="2" align=center><input type=radio name="type"
			value="true" checked />收入 <input type=radio name="type"
			value="false" />支出</td>
	</tr>

	<tr>
		<td>帳戶</td>
		<td><select id="account" name="account"
			onchange="javascript:getSubItem()">
			<c:forEach var="accoutBook" items="${user.accountBook}">
				<c:forEach var="account" items="${accoutBook.accounts}">
					<option value="${account.id}">${account.name}</option>
				</c:forEach>
			</c:forEach>
		</select></td>
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