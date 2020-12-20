package moneyOK.test;

import java.util.Iterator;
import java.util.Set;

import moneyOK.account.Account;
import moneyOK.accountBook.AccountBook;
import moneyOK.item.Item;
import moneyOK.transaction.FixedTransaction;
import moneyOK.transaction.VariableTransaction;
import moneyOK.user.User;
import moneyOK.user.UserDAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateDemo {
    public static void main(String[] args) {
    	//session
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Account account = (Account)session.get(Account.class, new Integer(1));
        Item item = (Item)session.get(Item.class,new Integer(1));
        moneyOK.transaction.VariableTransaction tran = new VariableTransaction();
        tran.setItem(item);
        tran.setDescription("吃午餐");
        tran.setItem(item);
        tran.setAmount(100);
        account.getVar_transaction().add(tran);
        session.save(account);
        tx.commit(); 
        session.close(); 
        
        System.out.println("新增資料OK!請先用MySQL觀看結果！");
        
        HibernateUtil.shutdown();
    }
}
