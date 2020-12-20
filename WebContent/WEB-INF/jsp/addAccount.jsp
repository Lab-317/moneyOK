<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增帳戶</title>

<script type="text/javascript">
function check(){
	if(document.addAccount.category.value == ""){
		if( document.addAccount.account.value == "" ){
			if ( document.addAccount.amount.value == "" ){
		    	window.alert("帳戶類型、帳戶名稱、金額欄位請勿空白"); }
			else window.alert("帳戶類型、帳戶名稱欄位請勿空白"); 
	    }
		else{
			if ( document.addAccount.amount.value == "" ){
	    		window.alert("帳戶類型、金額欄位請勿空白"); }
			else window.alert("帳戶類型欄位請勿空白1");
		}
		return false;
	}
	else{
		if( document.addAccount.account.value == "" ){
			if ( document.addAccount.amount.value == "" ){
		    	window.alert("帳戶名稱、金額欄位請勿空白");
			}
			else window.alert("帳戶名稱欄位請勿空白2"); 
        	return false;
    	}
		else{
			if ( document.addAccount.amount.value == "" ){
	    		window.alert("金額欄位請勿空白");
		    	return false;
			}
			else return true;
		}
	}
}
</script>

</head>
<body>
<form name="addAccount" method="POST" action="account.do?action=add"
	onSubmit="return check(this)">
<table width="300" border="1" align="center">
	<tr>
		<td>類型</td>
		<td><select size="6" name="category">
			<option value="0">現金</option>
			<option value="1">銀行/郵局</option>
			<option value="2">儲值卡</option>
		</select></td>
	</tr>
	<tr>
		<td>帳戶</td>
		<td><input type=text name="account"></input></td>
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
			value="確認送出 "> <input type="reset" name="reset" value="重填">
		</td>
	</tr>

</table>
</form>
</body>
</html>