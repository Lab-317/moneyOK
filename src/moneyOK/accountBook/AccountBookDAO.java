package moneyOK.accountBook;

import java.util.List;

import moneyOK.account.Account;
import moneyOK.user.User;
import moneyOK.util.CommonDAO;

public class AccountBookDAO extends CommonDAO{
	public void update(AccountBook accountBook){
		this.hibernateTemplate.update(accountBook);
	}
			
}
