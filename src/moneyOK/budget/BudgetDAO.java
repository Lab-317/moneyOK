package moneyOK.budget;
import java.util.Date;
import java.util.List;

import moneyOK.util.CommonDAO;

public class BudgetDAO extends CommonDAO {
		
	public void delete(Budget budget){
		this.hibernateTemplate.delete(budget);
	}
	
	public void update(Budget budget){
		this.hibernateTemplate.merge(budget);
	}
	
	public Budget findBudgetById(int id){
		List<Budget> budget = this.hibernateTemplate.find("From Budget budget where budget.id = ? ",id);
		return budget.get(0);	
	}
	
	public void updateBudgetTotal(){
		
	}
	
	public boolean checkTransactionDateInBudgetDate(int budgetId,Date date){
		Object [] para ={budgetId,date};
		List<Budget> budget = this.hibernateTemplate.find("From Budget budget where budget.id = ? and ? between budget.startDate and budget.endDate",para);
		if(budget.size()!=0)
			return true;
		else
			return false;
	}
}
