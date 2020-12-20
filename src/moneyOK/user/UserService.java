package moneyOK.user;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import moneyOK.account.Account;
import moneyOK.accountBook.AccountBook;
import moneyOK.item.Item;
import moneyOK.item.ItemService;

public class UserService {
	private UserDAO userDAO;
	private ItemService itemService;
	
	public UserService(){
		
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public boolean register(User user){
		AccountBook defaultAccountBook =new AccountBook();
		defaultAccountBook.setName("default");
		defaultAccountBook.setDescription("預設帳本");
		Set<AccountBook> accountBooks=new HashSet<AccountBook>();
		accountBooks.add(defaultAccountBook);
		user.setAccountBook(accountBooks);
		user=addDefaultItem(user);
		userDAO.add(user);
		return true;
	}
	
	public User login(String email,String password){
		List<User> list = userDAO.findUserByEmailAndPassword(email, password);
		if(list.size()==1){
			return list.get(0);
		}
		else{
			return null;
		}
	}
	
	public User getUserByUid(String uid){
		return this.userDAO.findUserByUid(Integer.parseInt(uid));
	}
	
	public void update(User user){
		userDAO.update(user);
	}
	
	public int getUserTotalMoney(User user){  //使用者的帳戶總資產
		int totalAccountMoney = 0;
		for(AccountBook accountBook:user.getAccountBook()){
			for(Account account : accountBook.getAccounts()){
				totalAccountMoney+=account.getTotal();
			}
		}
		return totalAccountMoney;
	}
	
	public User addDefaultItem(User user){
		Item parentCategory=itemService.getParentItem(1);//食
		Set<Item> childSet=new HashSet<Item>(); //取得parentCategory的childSet
		Item userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("早餐");
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("中餐");
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("晚餐");
		parentCategory.setChildCategory(childSet);
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet
		for(Item item:childSet){
			user=this.itemService.addDefaultItemObject(item, user);
		}	
		
		parentCategory=itemService.getParentItem(2);//衣
		childSet=new HashSet<Item>(); //取得parentCategory的childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("衣服");
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("褲子");
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet
		parentCategory.setChildCategory(childSet);
		childSet.add(userItem); //將使用者自定的項目加入parent的childSet	
		for(Item item:childSet){
			user=this.itemService.addDefaultItemObject(item, user);
		}
		
		parentCategory=itemService.getParentItem(3);//住
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("房租");
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		parentCategory=itemService.getParentItem(4);//行
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("通勤費");
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		
		parentCategory=itemService.getParentItem(5);//育
		childSet=new HashSet<Item>();
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("買書");
		childSet.add(userItem);
		parentCategory.setChildCategory(childSet);
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		parentCategory=itemService.getParentItem(6);//樂
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("逛街");
		user=this.itemService.addDefaultItemObject(userItem, user);
		return user;
	}
	
}
