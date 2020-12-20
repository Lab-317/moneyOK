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

public class HibernateDemoItem {
    public static void main(String[] args) {

    	Item parentItem=new Item();
    	parentItem.setParentCategory(null);
    	parentItem.setChildCategory(null);
    	parentItem.setName("food");
    	Item childItem=new Item();
    	childItem.setParentCategory(parentItem);
    	childItem.setChildCategory(null);
    	childItem.setName("hambuger");
    	
    	
        // 開啟Session，相當於開啟JDBC的Connection
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction(); 
        // 將物件映射至資料庫表格中儲存
        session.save(parentItem);
        session.save(childItem);
        tx.commit(); 
        session.close(); 
        System.out.println("新增資料OK!請先用MySQL觀看結果！");
        HibernateUtil.shutdown();
    }
}
