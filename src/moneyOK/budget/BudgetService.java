package moneyOK.budget;

import moneyOK.account.Account;
import moneyOK.account.AccountService;
import moneyOK.accountBook.AccountBook;
import moneyOK.item.Item;
import moneyOK.item.ItemService;
import moneyOK.transaction.VariableTransaction;
import moneyOK.user.User;

public class BudgetService {
	private BudgetDAO budgetDAO;
	private AccountService accountService;
	private ItemService itemService;
	
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}

	public void add(Account account,Budget budget){
		account.getBudgets().add(budget);
		this.accountService.updateAccount(account);
	}
	
	public void remove(Account account,Budget budget){
		account.getBudgets().remove(budget);
		this.accountService.updateAccount(account);
		this.budgetDAO.delete(budget);
	}
	
	public void update(Budget budget){
		this.budgetDAO.update(budget);
	}
	
	public Budget updateBudgetAmount(Budget budget,int oldAmount){
		int sum=budget.getAmount()-oldAmount;
		budget.setTotal(budget.getTotal()+sum);
		return budget;
	}
	
	public void updateBudgetTotal(Account account,VariableTransaction variableTransaction,int type){
		Item item=variableTransaction.getItem();
		int tr_iid = item.getParentCategory().getId();// var中Type id
		System.out.println("tr_iid:"+tr_iid);
		for (Budget budget : account.getBudgets()) {
			int budgetId = budget.getId();
			int categoryId = budget.getItem().getId();
			boolean result = this.budgetDAO.checkTransactionDateInBudgetDate(budgetId, variableTransaction.getDate());
			//System.out.println("budgetId:"+budgetId);
			//System.out.println("categoryId:"+categoryId);
			//System.out.println("tr_iid:"+tr_iid);
			if (categoryId == tr_iid && result) { // 如果要加的transaction內的item使用者有設budget限制
				if(type==1){//當加的transaction為支出時	
					budget.setTotal(budget.getTotal()- variableTransaction.getAmount());
				}
				else if(type==2){//當刪的transaction為支出時	
					budget.setTotal(budget.getTotal()+ variableTransaction.getAmount());
				}
				else if(type==3){//收入變更為支出
					budget.setTotal(budget.getTotal()-(2*variableTransaction.getAmount()));		
				}
			}
			this.budgetDAO.update(budget);
		}	
	}
	
	public void updateBudgetTotal(Account account,VariableTransaction variableTransaction,int type,int sum){
		Item item=this.itemService.findItemById(Integer.toString(variableTransaction.getItem().getId()));
		int tr_iid = item.getParentCategory().getId();// var中Type id
		for (Budget budget : account.getBudgets()) {
			int budgetId = budget.getId();
			int categoryId = budget.getItem().getId();
			boolean result = this.budgetDAO.checkTransactionDateInBudgetDate(budgetId, variableTransaction.getDate());
			if (categoryId == tr_iid && result) { // 如果要加的transaction內的item使用者有設budget限制
				if(type==4){//無變更收支，但有改支出金額
					budget.setTotal(budget.getTotal()+sum);	
				}
			}
			this.budgetDAO.update(budget);
		}
	}
	
	public Budget findBudgetById(String id){
		int bid=Integer.parseInt(id);
		Budget budget=this.budgetDAO.findBudgetById(bid);
		return budget;
	}
	
	public int getBudgetsSum(User user){
		int sum=0;
		for(AccountBook accountBook:user.getAccountBook()){
			for(Account account : accountBook.getAccounts()){
				for(Budget budget:account.getBudgets()){
					sum+=budget.getTotal();
				}
			}
		}
		return sum;
	}
}
