package moneyOK.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moneyOK.account.Account;
import moneyOK.accountBook.AccountBook;
import moneyOK.item.Item;
import moneyOK.user.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateUserItemDemo {
    public static void main(String[] args) {

    	Item parentItem=new Item();
    	parentItem.setParentCategory(null);
    	parentItem.setChildCategory(null);
    	parentItem.setName("entertainment");
    	Item childItem=new Item();
    	childItem.setParentCategory(parentItem);
    	childItem.setChildCategory(null);
    	childItem.setName("hot spring");
    	
    	//以下為user加入一自訂項目hanmbuger
    	Set items=new HashSet();
    	items.add(childItem);
    	User user=new User();
    	user.setEmail("dino@gamil.com");
    	user.setPassword("123");
        user.setItems(items);
    	
    	// 開啟Session，相當於開啟JDBC的Connection
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction(); 
        // 將物件映射至資料庫表格中儲存
        session.save(parentItem);
        session.save(childItem);
        session.save(user);
        tx.commit(); 
        session.close(); 
        System.out.println("新增資料OK!請先用MySQL觀看結果！");
        HibernateUtil.shutdown();
    }
}
