package moneyOK.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moneyOK.account.Account;
import moneyOK.util.CommonDAO;

public class TransactionDAO extends CommonDAO{
	
	public VariableTransaction findVarTransactionById(int id){
		List<VariableTransaction> variableTransaction = this.hibernateTemplate.find("From VariableTransaction variableTransaction  where variableTransaction.id = ?",id);
		return variableTransaction.get(0);
	}
	
	public void update(VariableTransaction variableTransaction){
		this.hibernateTemplate.merge(variableTransaction);
	}

	public void deleteVarTransaction(VariableTransaction variableTransaction){
		this.hibernateTemplate.delete(variableTransaction);
	}
	
	public List<Map> findTotalByDate(int aid,int type){//透過帳戶ID 找出每日支出(0)和收入(1)總額 
	 List<Map> result = new ArrayList<Map>();
	 boolean btype = (type==1)?true:false;
	 Object [] para ={aid,btype};
	 List<Object> QueryResult = this.hibernateTemplate.find("select varTra.date, sum(varTra.amount) from Account as account left join account.var_transaction as varTra where account.id  = ? AND varTra.type = ? group by varTra.date",para);
	 for(int i=0;i<QueryResult.size();i++){
		  Object[] o = (Object[])QueryResult.get(i);
		  Map<String,Object> element = new HashMap<String,Object>();
		  element.put("date",o[0]);
		  element.put("total", o[1]);
		  result.add(element);
	 } 
	 return result;
	}
	
	public List<VariableTransaction> findVariableTransactionByDay(int accountId,Date beforDate,Date todayDate){		
		Object [] para ={accountId,beforDate,todayDate};
		List<VariableTransaction> result= this.hibernateTemplate.find("select vt from Account as account left join account.var_transaction as vt where account.id  = ? and vt.date between ? and ?",para);
		return result;
	}
}
