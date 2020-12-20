package moneyOK.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moneyOK.accountBook.AccountBook;
import moneyOK.user.User;
import moneyOK.user.UserDAO;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

public class HibernateDemoUserDAO {
	public static void main(String[] args) {
		UserDAO userDAO=new UserDAO();
	    SessionFactory session = HibernateUtil.getSessionFactory();
	    userDAO.setSessionFactory(session);		
	    User findUser=userDAO.findUserByUid(15);
//	    AccountBook myaccountBook=new AccountBook();
//	    myaccountBook.setName("flowerflowerAccount");
//	    Set<AccountBook> accountBooks=new HashSet();
//	    accountBooks.add(myaccountBook);
//	    findUser.setEmail("a1234245@hotmail.com");
//	    findUser.setAccountBook(accountBooks);
//	    userDAO.update(findUser);
	    
//	    System.out.println("id : ");
//	    Set<AccountBook> temp  = findUser.getAccountBook();
//	    Hibernate.initialize(Object.getLazyCollection());
//	    Hibernate.initialize(temp);
	    Iterator it = findUser.getAccountBook().iterator();
        AccountBook dinoBook = new AccountBook();
        while(it.hasNext()){
        	dinoBook = (AccountBook)it.next();
        }	    
	    
	    System.out.println("dinoAccount : "+dinoBook.getName());
//        System.out.println(" §ä¨ìªºuser: "+findUser.getEmail());
        
        session.close(); 
        HibernateUtil.shutdown();
    }
}
