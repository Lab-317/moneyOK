package moneyOK.account;

import java.util.Set;

import moneyOK.accountBook.AccountBook;
import moneyOK.accountBook.AccountBookService;
import moneyOK.budget.BudgetService;
import moneyOK.transaction.VariableTransaction;


public class AccountService {
	private AccountDAO accountDAO;
	private AccountBookService accountBookService;
	private BudgetService budgetService;
	
	

	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	public void setAccountBookService(AccountBookService accountBookService) {
		this.accountBookService = accountBookService;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void addAccount(AccountBook accountBook,Account account){
		Set<Account> accounts = accountBook.getAccounts();
		accounts.add(account);
		this.accountBookService.updateAccountBook(accountBook);
		
	}
	
	public void addDefaultItem(Account account){
		
	}
	public void removeAccount(Account account){
		this.accountDAO.delete(account);
	}
	
	public void updateAccount(Account account){
		this.accountDAO.update(account);
	}
		
	public void updateAccountTotal(Account account,VariableTransaction variableTransaction,int oldAmount,boolean oldType){
		int sum=oldAmount-variableTransaction.getAmount(); //新舊金額差;
		if(oldType!=variableTransaction.isType()){//有變更收支
			if(variableTransaction.isType()){ //變更為收入
				account.setTotal(account.getTotal()+(2*variableTransaction.getAmount()));//將支出部分加回來	
			}
			else{ //支出
				account.setTotal(account.getTotal()-(2*variableTransaction.getAmount()));//將原收入金額部分扣回來
				this.budgetService.updateBudgetTotal(account, variableTransaction, 3);
			}
		}
		//無變更收支，但有改金額
		if(variableTransaction.isType()){ //收入
			account.setTotal(account.getTotal()-sum);
		}
		else{ //支出
			account.setTotal(account.getTotal()+sum);
			this.budgetService.updateBudgetTotal(account, variableTransaction, 4,sum);
		}
		updateAccount(account);
	}	
	
	public Account findAccountById(String id){
		return accountDAO.findAccountById(Integer.parseInt(id));
	}
	
	public Account findAccountByVarTransactionId(String tid){
		Account account=this.accountDAO.findAccountByVarTransactionId(Integer.parseInt(tid));
		return account;
	}
	
	public Account findAccountByFixedTransactionId(String tid){
		Account account=this.accountDAO.findAccountByFixedTransactionId(Integer.parseInt(tid));
		return account;
	}
	
	public Account findAccountByBid(String bid){
		Account account=this.accountDAO.findAccountByBid(Integer.parseInt(bid));
		return account;
	}
}