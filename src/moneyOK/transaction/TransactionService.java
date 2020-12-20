package moneyOK.transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import moneyOK.account.Account;
import moneyOK.account.AccountService;
import moneyOK.budget.Budget;
import moneyOK.budget.BudgetDAO;
import moneyOK.budget.BudgetService;
import moneyOK.item.Item;
import moneyOK.item.ItemDAO;

public class TransactionService {
	private ItemDAO itemDAO;	
	private TransactionDAO transactionDAO;
	private AccountService accountService;
	private BudgetService budgetService;
	
	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public void addFixTransaction(Account account,FixedTransaction fixedTransaction){
		
	}
	
	public Item findItemById(int id){
		return itemDAO.findItemByIid(id);
	}
			
	public void addVarTransaction(Account account,VariableTransaction variableTransaction){
		account.getVar_transaction().add(variableTransaction);
		if(variableTransaction.isType()) //Μ
			account.setTotal(account.getTotal()+variableTransaction.getAmount());
		else //や
		{
			int sum=account.getTotal()-variableTransaction.getAmount();
			//if(sum>=0)
			account.setTotal(account.getTotal()-variableTransaction.getAmount());
			this.budgetService.updateBudgetTotal(account, variableTransaction, 1);
		}
		this.accountService.updateAccount(account);
	}
		
	public void removeVarTransaction(Account account,VariableTransaction variableTransaction){
		if(variableTransaction.isType()) //Μ
			account.setTotal(account.getTotal()-variableTransaction.getAmount());
		else //や
		{
			account.setTotal(account.getTotal()+variableTransaction.getAmount());
			this.budgetService.updateBudgetTotal(account, variableTransaction, 2);
		}
		account.getVar_transaction().remove(variableTransaction);
		this.accountService.updateAccount(account);
		this.transactionDAO.deleteVarTransaction(variableTransaction);
	}
	
	public void updateVarTransaction(VariableTransaction variableTransaction){
		this.transactionDAO.update(variableTransaction);
	}
		
	public void removeFixTransaction(Account account,FixedTransaction fixedTransaction){
//		account.getFix_transaction().remove(fixedTransaction);
//		accountDAO.update(account);
	}
	
	public VariableTransaction findVarTransactionById(String id){
		return this.transactionDAO.findVarTransactionById(Integer.parseInt(id));
	}	
	
	public void changeTransactionAccount(Account fromAccount,Account toAccount,VariableTransaction variableTransaction){
		this.addVarTransaction(toAccount, variableTransaction);
		this.removeVarTransaction(fromAccount, variableTransaction);
		this.accountService.updateAccount(toAccount);
		this.accountService.updateAccount(fromAccount);
	}
		
	public List<VariableTransaction> findVariableTransactionByDay(String dayNum,int accountId){
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		Date todayDate =new Date();//さぱdate
		Date beforeDate = new Date();
		int day=todayDate.getDate()-Integer.parseInt(dayNum); //ぱ计Ι奔块玡nぱ
		if(day>0){
			beforeDate.setDate(day);
		}
		else{ //狦ぱ计Ι璽计(璶铬玡る)
			if(beforeDate.getMonth()!=1){ //狦ぃる
				beforeDate.setMonth(beforeDate.getMonth()-1);
				int monthTotalDays=getDaysInMonth(beforeDate.getYear(), beforeDate.getMonth()+1);
				beforeDate.setDate(monthTotalDays+day);
				//System.out.println("Month:"+(beforeDate.getMonth()+1));
				//System.out.println("day:"+beforeDate.getDate());
			}
			else {  //疭薄猵(る)
				beforeDate.setYear(beforeDate.getYear()-1);
				beforeDate.setMonth(12);
				beforeDate.setDate(beforeDate.getDay()+day);
			}
		}
		List<VariableTransaction> result=this.transactionDAO.findVariableTransactionByDay(accountId,beforeDate,todayDate);
		return result;
	}
	
	public int getDaysInMonth(int year,int mon){   //眔琘るぱ计
		GregorianCalendar date=new GregorianCalendar(year,mon-1,1);   
		return (date.getActualMaximum(Calendar.DATE));   
	}
	
	public int getTransationTotalIncome(List<VariableTransaction> trList){
		int total=0;
		for(int i=0;i<trList.size();i++){
			VariableTransaction vt=trList.get(i);
			if(vt.isType()){//Μ
				total+=vt.getAmount();
			}
		}
		return total;
	}
	
	public int getTransationTotalExpense(List<VariableTransaction> trList){
		int total=0;
		for(int i=0;i<trList.size();i++){
			VariableTransaction vt=trList.get(i);
			if(!vt.isType()){//や
				total+=vt.getAmount();
			}
		}
		return total;
	}
}
