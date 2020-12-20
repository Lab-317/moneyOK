package moneyOK.item;

import java.util.HashSet;
import java.util.Set;

import moneyOK.user.User;

public class Item {
	private int id;
	private String name;
	private Item parentCategory;
	private Set<Item> childCategory=new HashSet<Item>();
	private Set<User> users=new HashSet<User>();

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Item getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Item parentCategory) {
		this.parentCategory = parentCategory;
	}
	public Set<Item> getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(Set<Item> childCategory) {
		this.childCategory = childCategory;
	}

}
