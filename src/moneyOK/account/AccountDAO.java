package moneyOK.account;

import java.util.List;
import moneyOK.util.CommonDAO;

public class AccountDAO extends CommonDAO {
	public void add(Account account){
		this.hibernateTemplate.merge(account);
	}
	
	public void update(Account account){
		this.hibernateTemplate.merge(account);
	}
	
	public Account findAccountByBid(int bid){
		List<Account> account = this.hibernateTemplate.find("select a from Account a left join a.budgets as bud where bud.id = ?",bid);
		return account.get(0);
	}
	
	public Account findAccountById(int id){
		List<Account> account = this.hibernateTemplate.find("From Account account where account.id = ? ",id);
		return account.get(0);
	}
	
	public Account findAccountByVarTransactionId(int tid){
		List<Integer> result= this.hibernateTemplate.find("select a.id from Account a join a.var_transaction as tran where tran.id = ?",tid);
		int accountId=result.get(0);
		Account account=this.findAccountById(accountId);
		return account;
	}
	
	public Account findAccountByFixedTransactionId(int tid){
		List<Integer> result= this.hibernateTemplate.find("select a.id from Account a join a.fix_transaction as tran where tran.id = ?",tid);
		int accountId=result.get(0);
		Account account=this.findAccountById(accountId);
		return account;
	}
	
	public void delete(Account account){
		this.hibernateTemplate.delete(account);
	}
	
}
