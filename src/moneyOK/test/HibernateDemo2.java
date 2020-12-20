package moneyOK.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moneyOK.account.Account;
import moneyOK.accountBook.AccountBook;
import moneyOK.user.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDemo2 {
    public static void main(String[] args) {

    	Account account=new Account();
    	account.setType(0);
    	account.setName("Cash");    	

        // 開啟Session，相當於開啟JDBC的Connection
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("From User user  where user.id = ?");
        query.setParameter(0,15);
        Iterator it = query.list().iterator();
        User userOnly = new User();
        while(it.hasNext()){
        	userOnly = (User)it.next();
        }
        Set<AccountBook> accountBookOnly  = userOnly.getAccountBook();
        Iterator it2 = accountBookOnly.iterator();
        AccountBook acOnly = new AccountBook();
        while(it2.hasNext()){
        	acOnly = (AccountBook)it2.next();
        }
        acOnly.setName("Funcking bug");
        Set<Account> accounts=new HashSet(); //建立一accounts Set
        accounts.add(account);	//accounts set加入account object
        acOnly.setAccounts(accounts); //aconly加入accounts set
//        System.out.println("acOnly.getName() : "+acOnly.getName());
        // Transaction表示一組會話操作
        Transaction tx= session.beginTransaction(); 
        // 將物件映射至資料庫表格中儲存
//        session.save(userOnly);
        tx.commit(); 
        session.close(); 
        
        System.out.println("新增資料OK!請先用MySQL觀看結果！");
        
        HibernateUtil.shutdown();
    }
}
