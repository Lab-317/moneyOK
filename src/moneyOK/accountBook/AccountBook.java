package moneyOK.accountBook;

import java.util.Map;
import java.util.Set;

import moneyOK.account.Account;
import moneyOK.user.User;

public class AccountBook {
	private int id;
	private String name;
	private Set<User> users;
	private Set<Account> accounts;
	private String description;
	private int totalIncome;
	private int totalExpense;
	private Map<String,Object> ParentCategoryTotalMap;  //各項分類(食衣住行..)總和
	
	public int getTotalIncome() {
		return totalIncome;
	}

	public Map<String, Object> getParentCategoryTotalMap() {
		return ParentCategoryTotalMap;
	}

	public void setParentCategoryTotalMap(Map<String, Object> parentCategoryTotalMap) {
		ParentCategoryTotalMap = parentCategoryTotalMap;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(int totalExpense) {
		this.totalExpense = totalExpense;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
