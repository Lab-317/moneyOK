<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<script type="text/javascript">
function check(){
	if(document.addItem.parentCategory.value == ""){
		if(document.addItem.userCategory.value == ""){
			window.alert("請選取母類別\n自訂子類別請勿空白");
		}
		window.alert("請選取母類別\n自訂子類別請勿空白");
		return false;
	}
	else{
		if(document.addItem.userCategory.value == ""){
			window.alert("自訂子類別請勿空白");
			return false;
		}
		return true;
	}
}
</script>

</head>
<body>
<form name="addItem" method="POST" action="item.do?action=add"
	onSubmit="return check(this)">
<table width="300" border="1" align="center">
	<tr>
		<td>類型</td>
		<td><select size="6" name="parentCategory">
			<option value="1">食</option>
			<option value="2">衣</option>
			<option value="3">住</option>
			<option value="4">行</option>
			<option value="5">育</option>
			<option value="6">樂</option>
		</select></td>
	</tr>
	<tr>
		<td>子分類</td>
		<td><input type=text name="userCategory"></input></td>
	</tr>
	<tr>
		<td colspan="2" align=center><input type="submit" name="submit"
			value="確認送出"> <input type="reset" name="reset" value="重填">
		</td>
	</tr>
</table>
</body>
</html>