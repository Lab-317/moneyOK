package moneyOK.user;

import java.util.HashSet;
import java.util.Set;

import moneyOK.accountBook.AccountBook;
import moneyOK.item.Item;

public class User {
	private int id=0;
	private String email;
	private String password;
	private Set<AccountBook> accountBook;
	private Set<Item> items=new HashSet<Item>();
	
	public User(){
		
	}
	public User(String email,String password){
		this.email = email;
		this.password = password;
	}
	
	
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public void setAccountBook(Set<AccountBook> accountBook) {
		this.accountBook = accountBook;
	}
	public Set<AccountBook> getAccountBook() {
		return accountBook;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
