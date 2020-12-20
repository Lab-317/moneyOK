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
		defaultAccountBook.setDescription("�w�]�b��");
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
	
	public int getUserTotalMoney(User user){  //�ϥΪ̪��b���`�겣
		int totalAccountMoney = 0;
		for(AccountBook accountBook:user.getAccountBook()){
			for(Account account : accountBook.getAccounts()){
				totalAccountMoney+=account.getTotal();
			}
		}
		return totalAccountMoney;
	}
	
	public User addDefaultItem(User user){
		Item parentCategory=itemService.getParentItem(1);//��
		Set<Item> childSet=new HashSet<Item>(); //���oparentCategory��childSet
		Item userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("���\");
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("���\");
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("���\");
		parentCategory.setChildCategory(childSet);
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet
		for(Item item:childSet){
			user=this.itemService.addDefaultItemObject(item, user);
		}	
		
		parentCategory=itemService.getParentItem(2);//��
		childSet=new HashSet<Item>(); //���oparentCategory��childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("��A");
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("�Ǥl");
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet
		parentCategory.setChildCategory(childSet);
		childSet.add(userItem); //�N�ϥΪ̦۩w�����إ[�Jparent��childSet	
		for(Item item:childSet){
			user=this.itemService.addDefaultItemObject(item, user);
		}
		
		parentCategory=itemService.getParentItem(3);//��
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("�Я�");
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		parentCategory=itemService.getParentItem(4);//��
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("�q�ԶO");
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		
		parentCategory=itemService.getParentItem(5);//�|
		childSet=new HashSet<Item>();
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("�R��");
		childSet.add(userItem);
		parentCategory.setChildCategory(childSet);
		user=this.itemService.addDefaultItemObject(userItem, user);
		
		parentCategory=itemService.getParentItem(6);//��
		parentCategory.setChildCategory(null);
		userItem=new Item();
		userItem.setParentCategory(parentCategory);
		userItem.setChildCategory(null);
		userItem.setName("�}��");
		user=this.itemService.addDefaultItemObject(userItem, user);
		return user;
	}
	
}
