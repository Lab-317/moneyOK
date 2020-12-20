package moneyOK.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moneyOK.util.CommonDAO;

public class ChartDAO extends CommonDAO {
	public Map<Object,Map<String,Object>>  findMonthSumary(int accountBook,int month){
		//找出每個月份中該account book的母分類消費總額，Ex - 食100 衣100
    Object values[] = {accountBook,month};
	List QueryResult = this.hibernateTemplate.find("select ab.id,t.item.parentCategory.name,t.item.parentCategory.id," +
			"sum(t.amount) from AccountBook ab left join ab.accounts acco left join acco.var_transaction t " +
			"where ab.id=? AND t.type = 0 AND Month(t.date) = ? group by t.item.parentCategory",values);
	 Map<Object,Map<String,Object>> catgoryElement = new HashMap<Object,Map<String,Object>>();
	 for(int i=0;i<QueryResult.size();i++){
		  Object[] o = (Object[])QueryResult.get(i);
		 
		  Map<String,Object> element = new HashMap<String,Object>();
		  element.put("accountBookId",o[0]);
		  element.put("parentCatName",o[1]);
		  element.put("parentCatID",o[2]);
		  element.put("total", o[3]);
		  catgoryElement.put(o[2],element);
	 }
	 return catgoryElement;
	}
	public Map<Object,Map<String,Object>> findMonthTransactionDetail(int accountBook,int month,int catgoryID){
		Object values[] = {accountBook,month,catgoryID};
		List QueryResult = this.hibernateTemplate.find("select ab.id,t.item.name,sum(t.amount) from AccountBook ab left join ab.accounts acco " +
				"left join acco.var_transaction t where ab.id= ? AND t.type = 0 AND Month(t.date) = ? " +
				"AND t.item.parentCategory.id = ? group by t.item.name",values);
		Map<Object,Map<String,Object>> catgoryElement = new HashMap<Object,Map<String,Object>>();
		 System.out.println("List size = "+QueryResult.size());
		 for(int i=0;i<QueryResult.size();i++){
			  Object[] o = (Object[])QueryResult.get(i);
			  System.out.println("i = "+i);
			  Map<String,Object> element = new HashMap<String,Object>();
			  element.put("accountBookId",o[0]);
			  element.put("CatName",o[1]);
			  element.put("total", o[2]);
			  catgoryElement.put(o[1],element);
		 }
		 System.out.println("size = "+catgoryElement.size());
		return catgoryElement;
	}
	
}