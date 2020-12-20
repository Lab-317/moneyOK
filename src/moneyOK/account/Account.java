package moneyOK.account;

import java.util.HashSet;
import java.util.Set;

import moneyOK.budget.Budget;
import moneyOK.transaction.FixedTransaction;
import moneyOK.transaction.VariableTransaction;

public class Account implements java.lang.Comparable{
	private int id;
	private int type;//0現金/1郵局/2儲值卡
	private String name;
	private int total; //餘額
	private String description;
	private Set<FixedTransaction> fix_transaction;
	private Set<VariableTransaction> var_transaction;
	private Set<Budget> budgets=new HashSet<Budget>(); 

	public Set<Budget> getBudgets() {
		return budgets;
	}
	public void setBudgets(Set<Budget> budgets) {
		this.budgets = budgets;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Set<FixedTransaction> getFix_transaction() {
		return fix_transaction;
	}
	public void setFix_transaction(Set<FixedTransaction> fixTransaction) {
		fix_transaction = fixTransaction;
	}
	public Set<VariableTransaction> getVar_transaction() {
		return var_transaction;
	}
	public void setVar_transaction(Set<VariableTransaction> varTransaction) {
		var_transaction = varTransaction;
	}

	public Account(){
		
	}
	public Account(String name,int total){
		this.name = name;
		this.total = total;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(String type) {
		this.type = Integer.parseInt(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int compareTo(Object arg0) {
		Account account = (Account)arg0;
		return this.id - account.id;
	}
}