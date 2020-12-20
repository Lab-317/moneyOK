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

        // �}��Session�A�۷��}��JDBC��Connection
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
        Set<Account> accounts=new HashSet(); //�إߤ@accounts Set
        accounts.add(account);	//accounts set�[�Jaccount object
        acOnly.setAccounts(accounts); //aconly�[�Jaccounts set
//        System.out.println("acOnly.getName() : "+acOnly.getName());
        // Transaction��ܤ@�շ|�ܾާ@
        Transaction tx= session.beginTransaction(); 
        // �N����M�g�ܸ�Ʈw��椤�x�s
//        session.save(userOnly);
        tx.commit(); 
        session.close(); 
        
        System.out.println("�s�W���OK!�Х���MySQL�[�ݵ��G�I");
        
        HibernateUtil.shutdown();
    }
}
