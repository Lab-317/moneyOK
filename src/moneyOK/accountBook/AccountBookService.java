package moneyOK.accountBook;

import java.util.Iterator;

import moneyOK.account.Account;
import moneyOK.transaction.VariableTransaction;
import moneyOK.user.User;

public class AccountBookService {
	private AccountBookDAO accountBookDAO;
	
	public AccountBookDAO getAccountBookDAO() {
		return accountBookDAO;
	}
	public void setAccountBookDAO(AccountBookDAO accountBookDAO) {
		this.accountBookDAO = accountBookDAO;
	}
	public AccountBook getDefaultAccountBookByUser(User user){
		AccountBook accountBook = new AccountBook();
	
		Iterator<AccountBook> it = user.getAccountBook().iterator();
		while(it.hasNext()){
			accountBook = (AccountBook)it.next();
		}
		//AccountBook accountBook=this.accountBookDAO.findDefaultAccountBookByUser(user.getId());
		return accountBook;
	}
	public void updateAccountBook(AccountBook accountBook) {
		this.accountBookDAO.update(accountBook);
	}
	
	public void setTotalIncome(AccountBook accountBook){
		int totalIncome=0;
		for(Account account:accountBook.getAccounts()){
			for(VariableTransaction vt:account.getVar_transaction()){
				if(vt.isType())
					totalIncome+=vt.getAmount();
			}
		}
		
		accountBook.setTotalIncome(totalIncome);
		this.accountBookDAO.update(accountBook);
	}
	
	public void setTotalExpense(AccountBook accountBook){
		int totalExpense=0;
		for(Account account:accountBook.getAccounts()){
			for(VariableTransaction vt:account.getVar_transaction()){
				if(!vt.isType())
					totalExpense+=vt.getAmount();
			}
		}
		accountBook.setTotalExpense(totalExpense);
		this.accountBookDAO.update(accountBook);
	}
}
