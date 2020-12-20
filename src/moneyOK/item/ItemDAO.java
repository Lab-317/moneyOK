package moneyOK.item;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import moneyOK.user.User;
import moneyOK.util.CommonDAO;

public class ItemDAO extends CommonDAO{
		
	public Item findItemByName(String name){
		List<Item> item = this.hibernateTemplate.find("From Item item  where item.name like '"+name+"'");
		if(item.size()==0){
			return null;
		}else
		return item.get(0);
	}
	
//	public boolean findUserItemByUid(int id){
//		List<Item> item = this.hibernateTemplate.find("From Item item  where item.name like ?",name);
//		
//		return
//	}
	public List<Item> findItemsByParentIidAndUid(int iid,int uid){
		Object values[] = {iid,uid};
		//List<Item> item = this.hibernateTemplate.find("From Item item");
		List item = this.hibernateTemplate.find("select i From Item i join i.users as user where i.parentCategory.id = ? AND user.id = ? ",values);
		return item;
	}
	
	public Item findItemByIid(int id){
		List<Item> item = this.hibernateTemplate.find("From Item item  where item.id = ?",id);
		return item.get(0);
	}
	
	public void update(Item item){
		this.hibernateTemplate.merge(item);
	}
	
	public void save(Item item){
		this.hibernateTemplate.save(item);
	}
}
