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
    	
    	//�H�U��user�[�J�@�ۭq����hanmbuger
    	Set items=new HashSet();
    	items.add(childItem);
    	User user=new User();
    	user.setEmail("dino@gamil.com");
    	user.setPassword("123");
        user.setItems(items);
    	
    	// �}��Session�A�۷��}��JDBC��Connection
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction(); 
        // �N����M�g�ܸ�Ʈw��椤�x�s
        session.save(parentItem);
        session.save(childItem);
        session.save(user);
        tx.commit(); 
        session.close(); 
        System.out.println("�s�W���OK!�Х���MySQL�[�ݵ��G�I");
        HibernateUtil.shutdown();
    }
}
