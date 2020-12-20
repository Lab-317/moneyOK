package moneyOK.item;

import java.util.List;

import moneyOK.account.Account;
import moneyOK.accountBook.AccountBook;
import moneyOK.transaction.TransactionService;
import moneyOK.transaction.VariableTransaction;
import moneyOK.user.User;

public class ItemService {
	private ItemDAO itemDAO;
	private TransactionService transactionService;
		
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void setItemDAO(ItemDAO itemDAO){
		this.itemDAO=itemDAO;
	}
	
	public Item getParentItem(int id){
		Item parentItem=itemDAO.findItemByIid(id);
		return parentItem;
	}
		
	public User addItemObject(Item item, User user){  //檢查Item是否重覆
		Item localItem = itemDAO.findItemByName(item.getName()); 
		if (localItem == null) {  //如果資料庫無這項子分類
			System.out.println("item.getName():"+item.getName());
			user.getItems().add(item);
			this.itemDAO.save(item);
		} 
		else if (!localItem.getUsers().contains(user)) {  //如果使用者無這項子分類
			user.getItems().add(localItem);
		}
		return user;
	}
	
	public User addDefaultItemObject(Item item, User user){  //對新註冊的使用者加預設的item
		user=addItemObject(item,user);
		return user;
	}
	
	public List<Item> findItemByParentIDAndUser(User user,Item item){
		return this.itemDAO.findItemsByParentIidAndUid(item.getId(),user.getId());
	}
	
	public Item findItemById(String id){
		int iid  = Integer.parseInt(id);
		return this.itemDAO.findItemByIid(iid);
	}
	
	public Item findItemByName(String name){
		return this.itemDAO.findItemByName(name);
	}
	
	public int findParentCategoryTotalByUserAndIid(AccountBook accountBook,int iid,String dayNum){
		int parentCategoryTotal=0;
		for(Account account:accountBook.getAccounts()){
			if(dayNum.equals("0")){  //選取全部TR的item total
				System.out.println("here!!!!!");
				for(VariableTransaction variableTransaction:account.getVar_transaction()){
					if(variableTransaction.getItem().getParentCategory().getId()==iid && !variableTransaction.isType()){
						parentCategoryTotal+=variableTransaction.getAmount();
					}
				}
			}
			else{  //選取部分天數TR的item total
				List<VariableTransaction> varList=transactionService.findVariableTransactionByDay(dayNum, account.getId());
				System.out.println("varListSize:"+varList.size());
				for(int i=0;i<varList.size();i++){
					VariableTransaction variableTransaction=varList.get(i);
					if(variableTransaction.getItem().getParentCategory().getId()==iid && !variableTransaction.isType()){
						parentCategoryTotal+=variableTransaction.getAmount();
					}
				}
			}
		}
		return parentCategoryTotal;
	}
	
	public void updateItem(Item item){
		this.itemDAO.update(item);
	}
}
